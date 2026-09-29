package io.nology.project.config;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;


import io.nology.project.config.factory.employee.EmployeeFactory;
import io.nology.project.config.factory.employee.EmployeeFactoryOptions;
import io.nology.project.employee.entity.Employee;

@Component 
@Profile({"dev", "test"})
public class DataSeeder implements CommandLineRunner {

    private final EmployeeFactory employeeFactory;

    public DataSeeder(EmployeeFactory employeeFactory){
        this.employeeFactory = employeeFactory;
    }
    
    @Override
    public void run(String... args) throws Exception {
        
        if(employeeFactory.repoEmpty()){
            EmployeeFactoryOptions options = EmployeeFactoryOptions.builder().build();
            List<Employee> employees = employeeFactory.create(options,20);
            System.out.println("Seeded" + employees.size() + " employees.");
        }

    }

}
