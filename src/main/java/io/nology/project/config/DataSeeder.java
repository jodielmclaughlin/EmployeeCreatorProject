package io.nology.project.config;

import java.util.List;

import io.nology.project.auth.Role;
import io.nology.project.config.factory.app_user.AppUserFactory;
import io.nology.project.config.factory.app_user.AppUserFactoryOptions;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;


import io.nology.project.config.factory.employee.EmployeeFactory;
import io.nology.project.config.factory.employee.EmployeeFactoryOptions;
import io.nology.project.employee.entity.Employee;

@Component 
@Profile({"dev", "test"})
public class DataSeeder implements CommandLineRunner {

    private final AppUserFactory appUserFactory;
    private final EmployeeFactory employeeFactory;

    public DataSeeder(EmployeeFactory employeeFactory, AppUserFactory appUserFactory){
        this.employeeFactory = employeeFactory;
        this.appUserFactory = appUserFactory;
    }
    
    @Override
    public void run(String... args) throws Exception {
        
        if(employeeFactory.repoEmpty()){
            EmployeeFactoryOptions options = EmployeeFactoryOptions.builder().build();
            List<Employee> employees = employeeFactory.create(options,20);
            System.out.println("Seeded" + employees.size() + " employees.");
        }

        if(!appUserFactory.hasUser("admin@test.com")){
            var options = AppUserFactoryOptions.builder().email("admin@test.com").role(Role.ADMIN).build();
            this.appUserFactory.create(options);
        }

    }

}
