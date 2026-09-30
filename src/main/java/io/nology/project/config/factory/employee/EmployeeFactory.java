package io.nology.project.config.factory.employee;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.github.javafaker.Faker;

import io.nology.project.employee.EmployeeRepository;
import io.nology.project.employee.entity.ContractType;
import io.nology.project.employee.entity.Employee;

@Component 
@Profile({"dev","test"})
public class EmployeeFactory {

    private final Set<String> usedEmails = new HashSet<>();
    private final Set<String> usedPhoneNumbers = new HashSet<>();
    private final Faker faker = new Faker();
    private final EmployeeRepository repo;
    
    public EmployeeFactory(EmployeeRepository repo){
        this.repo = repo;
    }
    
    public boolean repoEmpty() {
        return this.repo.count() == 0;
    }

    
    public Employee create() {
        EmployeeFactoryOptions empty = EmployeeFactoryOptions.builder().build();
        return create(empty);
    }

    public Employee create(EmployeeFactoryOptions options) {
        Employee newEmployee = new Employee();
        newEmployee.setFirstName(
                options.firstName != null ? options.firstName : faker.name().firstName());
        newEmployee.setLastName(
            options.lastName != null ? options.lastName : faker.name().lastName());
        newEmployee.setEmail(generateUniqueEmail(options.email));
        newEmployee.setPhoneNumber(generateUniquePhoneNumber(options.phoneNumber));
        newEmployee.setAddress(
            options.address != null ? options.address : faker.address().fullAddress());
        newEmployee.setContractType(
            options.contractType != null ? options.contractType : ContractType.FULL_TIME);
        newEmployee.setJobTitle(
            options.jobTitle != null ? options.jobTitle : faker.job().position());
        newEmployee.setStartDate(
            options.startDate != null ? options.startDate : faker.date()
                    .between(
                        Date.from(LocalDate.now().minusYears(20)
                            .atStartOfDay(ZoneId.systemDefault()).toInstant()),
                        new Date()
                    )
                    .toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate());
        this.repo.saveAndFlush(newEmployee);
        return newEmployee;
    }


    public List<Employee> create(EmployeeFactoryOptions options, int n) {
        ArrayList<Employee> employees = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            Employee created = create();
            employees.add(created);
        }
        return employees;
    }

    private String generateUniqueEmail(String email) {
        if (email == null || email.isBlank()) {
            email = faker.internet().emailAddress();
        }
        while (usedEmails.contains(email)) {
            email = faker.internet().emailAddress();
        }
        return email;
    }

    private String generateUniquePhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.isBlank()) {
            phoneNumber = faker.phoneNumber().cellPhone();
        }
        while (usedPhoneNumbers.contains(phoneNumber)) {
            phoneNumber = faker.phoneNumber().cellPhone();
        }
        return phoneNumber;
    }

}
