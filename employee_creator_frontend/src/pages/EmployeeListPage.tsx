import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

import { deleteEmployee, getAllEmployees } from "../services/employees";
import type { Employee } from "../types/employee";
import EmployeeCard from "../components/Employee/EmployeeCard";
import {isAdmin} from "../services/auth.ts";

function EmployeeListPage(){

      const [employees, setEmployees] = useState<Employee[]>([]);
      const [employeeToDelete, setEmployeeToDelete] = useState<Employee | null>(null);
      const [message, setMessage] = useState<string | null>(null);
      const [messageType, setMessageType] = useState<"success" | "error" | null>(null);
      
      useEffect(() => {
        getAllEmployees().then(setEmployees);
        }, []);

        useEffect(() => {
            if (!message) return;

            const timer = setTimeout(() => {
                setMessage(null);
            }, 3000);

            return () => clearTimeout(timer);
        }, [message]);

        const navigate = useNavigate();
    
        const handleAddEmployee = () => {
            navigate("/employees/new");
        };

        const handleDeleteClick = (employee: Employee) => {
            setEmployeeToDelete(employee);
        };

        const handleConfirmDelete = async () => {
            if (!employeeToDelete) return;

            try {
                await deleteEmployee(employeeToDelete.id);

                setEmployees((currentEmployees) =>
                    currentEmployees.filter(
                        (employee) => employee.id !== employeeToDelete.id
                    )
                );

                setMessage(
                    `${employeeToDelete.firstName} ${employeeToDelete.lastName} has been removed.`
                );
                setMessageType("success");

                setEmployeeToDelete(null);
            } catch (error) {
                setMessage("Failed to remove employee. Please try again.");
                setMessageType("error");
                setEmployeeToDelete(null);
            }
        };

        const handleCancelDelete = () => {
            setEmployeeToDelete(null);
        };


        return(
            <div
            data-testid="employee-list-page"
            className="min-h-screen bg-gray-100 px-4 py-10">
                <div className="mx-auto max-w-5xl">

                    <div
                    data-testid="employee-list-header"
                    className="mb-8 flex items-center justify-between">
                    <div>
                        <h1
                            data-testid="employee-list-title"
                            className="text-3xl font-bold text-gray-900">
                            Employees
                        </h1>

                        <p className="mt-1 text-gray-600">
                            Manage your current employees
                        </p>
                    </div>
                    <button
                        data-testid="add-employee-button"
                        onClick={handleAddEmployee}
                        disabled={!isAdmin()}
                        className="rounded-lg bg-blue-600 px-5 py-3 font-medium text-white transition hover:bg-blue-700 disabled:cursor-not-allowed disabled:bg-zinc-300 disabled:border-transparent disabled:text-black">
                        + Add Employee
                    </button>
                </div>
                {message && (
                    <div
                        data-testid="employee-message"
                        className={
                        messageType === "success"
                            ? "mb-6 rounded-lg bg-green-100 px-4 py-3 text-green-800"
                            : "mb-6 rounded-lg bg-red-100 px-4 py-3 text-red-800"}>
                        {message}
                    </div>
                )}

                <div
                    data-testid="employee-list"
                    className="space-y-4"
                >
                    {employees.map((employee) => <EmployeeCard key={employee.id} employee={employee} onDelete={handleDeleteClick}/>)}
                </div>

                </div>
                {employeeToDelete && (
                    <div
                        data-testid="delete-confirmation-modal"
                        className="fixed inset-0 flex items-center justify-center bg-black/50 px-4"
                    >
                        <div className="w-full max-w-md rounded-xl bg-white p-6 shadow-xl">
                            <h2 className="text-xl font-bold text-gray-900">
                                Remove Employee
                            </h2>

                            <p className="mt-3 text-gray-600">
                                Are you sure you want to remove{" "}
                                <span className="font-semibold">
                                    {employeeToDelete.firstName}{" "}
                                    {employeeToDelete.lastName}
                                </span>
                                ?
                            </p>

                            <div className="mt-6 flex justify-end gap-3">
                                <button
                                    data-testid="cancel-delete-button"
                                    onClick={handleCancelDelete}
                                    className="rounded-lg border border-gray-300 px-4 py-2 text-gray-700 hover:bg-gray-100"
                                >
                                    Cancel
                                </button>

                                <button
                                    data-testid="confirm-delete-button"
                                    onClick={handleConfirmDelete}
                                    className="rounded-lg bg-red-600 px-4 py-2 text-white hover:bg-red-700"
                                >
                                    Remove
                                </button>
                            </div>
                        </div>
                    </div>
                )}
            </div>
        )
    }


export default EmployeeListPage