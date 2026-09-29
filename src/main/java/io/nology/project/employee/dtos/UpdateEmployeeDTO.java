package io.nology.project.employee.dtos;

import java.time.LocalDate;

import io.nology.project.employee.entity.ContractType;
import jakarta.validation.constraints.Pattern;



public class UpdateEmployeeDTO {
    @Pattern(regexp = ".*\\S.*", message = "First name must not be empty")
    private String firstName;

    @Pattern(regexp = ".*\\S.*", message = "Last name must not be empty")
    private String lastName;

    @Pattern(regexp = ".*\\S.*", message = "Email must not be empty")
    private String email;

    @Pattern(regexp = ".*\\S.*", message = "Phone number must not be empty")
    private String phoneNumber;

    @Pattern(regexp = ".*\\S.*", message = "Address must not be empty")
    private String address;

    
    private ContractType contractType;

    @Pattern(regexp = ".*\\S.*", message = "Job title must not be empty")
    private String jobTitle;

    
    private LocalDate startDate;


    public String getFirstName() {
        return firstName;
    }


    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }


    public String getLastName() {
        return lastName;
    }


    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    public String getEmail() {
        return email;
    }


    public void setEmail(String email) {
        this.email = email;
    }


    public String getPhoneNumber() {
        return phoneNumber;
    }


    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


    public String getAddress() {
        return address;
    }


    public void setAddress(String address) {
        this.address = address;
    }


    public ContractType getContractType() {
        return contractType;
    }


    public void setContractType(ContractType contractType) {
        this.contractType = contractType;
    }


    public String getJobTitle() {
        return jobTitle;
    }


    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }


    public LocalDate getStartDate() {
        return startDate;
    }


    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    
}
