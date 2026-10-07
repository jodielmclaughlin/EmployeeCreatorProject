import type { Employee, ContractType } from "../types/employee";
import {authHeaders} from "./authApi.ts";


interface CreateEmployeeDTO {
    firstName: string;
    lastName: string;
    email: string;
    phoneNumber: string;
    address: string;
    contractType: ContractType;
    jobTitle: string;
    startDate: string;
}
interface UpdateEmployeeDTO {
    firstName: string;
    lastName: string;
    email: string;
    phoneNumber: string;
    address: string;
    contractType: ContractType;
    jobTitle: string;
    startDate: string;
}

export async function getAllEmployees(){
    const response = await fetch("http://localhost:8080/employees", {
        headers: authHeaders(),
    });
    if (!response.ok){
        throw new Error("Could not fetch employees")
    }
    return (await response.json()) as Employee[];
}

export async function getEmployee(id: number){
    const response = await fetch(`http://localhost:8080/employees/${id}`, {
        headers: authHeaders(),
    });
    if (!response.ok){
        throw new Error("Could not fetch employee")
    }
    return (await response.json()) as Employee;
}

export async function createEmployee(employeeData: CreateEmployeeDTO) {
    const response = await fetch("http://localhost:8080/employees", {
        method: "POST",
        body: JSON.stringify(employeeData),
        headers: authHeaders(),
    });

    if(response.status === 403){
        throw new Error("You do not have permission to create an employee.");
    }

    if(!response.ok){
        throw new Error("Could not create employee");
    }
    return (await response.json()) as Employee;
}

export async function deleteEmployee(id: number){
    const response = await fetch(`http://localhost:8080/employees/${id}`, {
            method: "DELETE",
            headers: authHeaders(),
        });

    if (!response.ok) {
        throw new Error("Failed to delete employee");
    }
}

export async function editEmployee(id:number, employeeData: UpdateEmployeeDTO){
    const response = await fetch(`http://localhost:8080/employees/${id}`, {
            method: "PATCH",
            body: JSON.stringify(employeeData),
            headers: authHeaders(),

        });

    if (!response.ok) {
        throw new Error("Failed to update employee");
    }

}