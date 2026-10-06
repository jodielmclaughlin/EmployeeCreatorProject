package io.nology.project.employee;


import io.nology.project.auth.JwtService;
import io.nology.project.auth.Role;
import io.nology.project.auth.entity.AppUser;
import io.nology.project.common.BaseE2ETest;
import io.nology.project.config.factory.app_user.AppUserFactory;
import io.nology.project.config.factory.app_user.AppUserFactoryOptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import java.time.LocalDate;
import java.util.HashMap;
import io.nology.project.config.factory.employee.EmployeeFactory;
import io.nology.project.config.factory.employee.EmployeeFactoryOptions;
import io.nology.project.employee.entity.ContractType;
import io.nology.project.employee.entity.Employee;
import io.restassured.http.ContentType;

public class EmployeeE2ETest extends BaseE2ETest {

    private EmployeeFactory employeeFactory;
    private AppUser admin;
    private AppUserFactory appUserFactory;

    @Autowired 
    public EmployeeE2ETest(EmployeeFactory employeeFactory, JwtService jwtService, AppUserFactory appUserFactory){
        super(jwtService);
        this.employeeFactory = employeeFactory;
        this.appUserFactory = appUserFactory;
    }

    @BeforeEach
    private void setupAdminUser(){
        var opts = AppUserFactoryOptions.builder().role(Role.ADMIN).build();
        admin = this.appUserFactory.create(opts);
    }

    @Test
    public void appReturns401_whenNoJWTGiven(){
    given().when().get("/employees")
            .then().statusCode(HttpStatus.UNAUTHORIZED.value())
            .body("status", equalTo(401))
            .body("error", equalTo("Unauthorized"))
            .body("message", containsString("Authentication required"))
            .body(matchesJsonSchemaInClasspath("schema/api-error-response-schema.json"));

    }

    @Test 
    public void getAllEmployees_whenNoEmployees_returnsEmptyArray(){
        spec().withJwt(admin).build().when().get("/employees")
                .then().statusCode(HttpStatus.OK.value())
                .body("$", hasSize(0));
    }

     @Test 
     public void getAllEmployees_whenEmployeesInDB_returnsAllEmployees(){
        EmployeeFactoryOptions options = EmployeeFactoryOptions.builder().build();
        this.employeeFactory.create(options, 10);

        spec().withJwt(admin).build().when().get("/employees")
                .then().statusCode(HttpStatus.OK.value())
                .body("$", hasSize(10))
                .body(matchesJsonSchemaInClasspath("schema/employee-list-schema.json"));
     }

    @Test 
    public void getById_validId_returnsEmployee(){
        EmployeeFactoryOptions options = EmployeeFactoryOptions.builder()
            .firstName("Jodie")
            .lastName("McLaughlin")
            .email("fake.email@google.com")
            .phoneNumber("07934988273")
            .address("Fake address, uk")
            .contractType(ContractType.FULL_TIME)
            .jobTitle("Test Engineer")
            .startDate(LocalDate.of(2020,5,15)).build();

        Employee jodie = this.employeeFactory.create(options);
        Long id = jodie.getId();

        spec().withJwt(admin).build().when().get("/employees/" + id)
                .then().statusCode(HttpStatus.OK.value())
                .body("firstName", equalTo("Jodie"))
                .body("lastName", equalTo("McLaughlin"))
                .body("jobTitle",equalTo("Test Engineer"))
                .body(matchesJsonSchemaInClasspath("schema/employee-schema.json"));
    }

    @Test 
    public void getById_nonExistentId_returns404(){
        spec().withJwt(admin).build().when().get("/employees/" + 1l)
                .then().statusCode(HttpStatus.NOT_FOUND.value())
                .body("status", equalTo(404))
                .body("error", equalTo("Not Found"))
                .body("message", containsString("Employee not found with ID: 1"))
                .body(matchesJsonSchemaInClasspath("schema/api-error-response-schema.json"));
    }

    @Test 
    public void getById_invalidDataTypeId_returnsBadRequest(){
        spec().withJwt(admin).build().when().get("/employees/" + "apple")
                // assert
                .then().log().all().statusCode(HttpStatus.BAD_REQUEST.value())
                .body("status", equalTo(400))
                .body("error", equalTo("Bad Request"))
                .body("message", containsString("Failed to convert value"))
                .body(matchesJsonSchemaInClasspath("schema/api-error-response-schema.json"));
    }

    @Test 
    public void createEmployee_withMissingData_ReturnsBadRequest(){
        HashMap<String, String> data = new HashMap<>();
        data.put("firstName", "Jodie");
        data.put("lastName", "McLaughlin");

        spec().withJwt(admin).build().contentType(ContentType.JSON).body(data).when().log().all()
                .post("/employees")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test 
    public void createEmployee_withValidData_ReturnsCreatedEmployee(){
        HashMap<String, String> data = new HashMap<>();
        data.put("firstName", "Jodie");
        data.put("lastName", "McLaughlin");
        data.put("email", "fake.email@google.com");
        data.put("phoneNumber","07934988273");
        data.put("address", "Fake address, uk");
        data.put("contractType", "FULL_TIME");
        data.put("jobTitle", "Test Engineer");
        data.put("startDate", "2008-10-07");

        spec().withJwt(admin).build().contentType(ContentType.JSON).body(data).when().log().all()
                .post("/employees")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .body("firstName", equalTo("Jodie"))
                .body("lastName", equalTo("McLaughlin"))
                .body("jobTitle",equalTo("Test Engineer"))
                .body(matchesJsonSchemaInClasspath("schema/employee-schema.json"));
    }

    @Test 
    public void updateEmployee_withInvalidData_ReturnsBadRequest(){
        EmployeeFactoryOptions options = EmployeeFactoryOptions.builder().build();
        Employee fakeEmployee = this.employeeFactory.create(options);

        HashMap<String, String> data = new HashMap<>();
        data.put("firstName", "  ");
        spec().withJwt(admin).build().contentType(ContentType.JSON).body(data).when().log().all()
                .patch("/employees/" + fakeEmployee.getId())
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test 
    public void updateEmployee_EmployeeDoesNotExist_ReturnsNotFound(){
        
        HashMap<String, String> data = new HashMap<>();
        data.put("firstName", "updated");

        spec().withJwt(admin).build().contentType(ContentType.JSON).body(data).when().log().all()
                .patch("/employees/2")
                .then().log().all()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .body("message", equalTo("Employee not found with ID: 2"))
                .body(matchesJsonSchemaInClasspath("schema/api-error-response-schema.json"));
    }

    @Test 
    public void updateEmployee_withValidData_ReturnsOK(){
        EmployeeFactoryOptions options = EmployeeFactoryOptions.builder().build();
        Employee fakeEmployee = this.employeeFactory.create(options);

        HashMap<String, String> data = new HashMap<>();
        data.put("firstName", "updated");

        spec().withJwt(admin).build().contentType(ContentType.JSON).body(data).when().log().all()
                .patch("/employees/" + fakeEmployee.getId())
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body("firstName", equalTo("updated"))
                .body(matchesJsonSchemaInClasspath("schema/employee-schema.json"));
    }

    @Test 
    public void deleteEmployee_EmployeeDoesNotExist_ReturnsNotFound(){
        spec().withJwt(admin).build()
                .when()
                .delete("/employees/1")
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .body("message", equalTo("Employee not found with ID: 1"))
                .body(matchesJsonSchemaInClasspath("schema/api-error-response-schema.json"));
    }

    @Test 
    public void deleteEmployee_EmployeeExists_ReturnsNoContent(){
        EmployeeFactoryOptions options = EmployeeFactoryOptions.builder().build();
        Employee fakeEmployee = this.employeeFactory.create(options);

        spec().withJwt(admin).build()
                .when()
                .delete("/employees/" + fakeEmployee.getId())
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    
}
