import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import type { EmployeeFormData } from "../components/EmployeeForm/schema";
import EmployeeForm from "../components/EmployeeForm/EmployeeForm";
import { getEmployee, editEmployee,} from "../services/employees";
import type { Employee } from "../types/employee";



function EditEmployee() {
    const { id } = useParams();
    const navigate = useNavigate();

    const [employee, setEmployee] = useState<Employee | null>(null);
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {
        if (!id) return;

        getEmployee(Number(id))
            .then(setEmployee)
            .catch(() => {
                setError("Unable to load employee.");
            });
    }, [id]);

    const onSubmit = async (data: EmployeeFormData) => {
        if (!id) return;

        try {
            await editEmployee(Number(id), data);

            navigate("/");
        } catch (error) {
            setError("Unable to update employee. Please try again.");
        }
    };

    if (error) {
        return (
            <div
                data-testid="edit-employee-error"
                className="min-h-screen bg-gray-100 px-4 py-10"
            >
                <div className="mx-auto max-w-3xl rounded-xl bg-white p-8 shadow-md">
                    <p className="text-red-600">{error}</p>
                </div>
            </div>
        );
    }

    if (!employee) {
        return (
            <div
                data-testid="edit-employee-loading"
                className="min-h-screen bg-gray-100 px-4 py-10"
            >
                <div className="mx-auto max-w-3xl rounded-xl bg-white p-8 shadow-md">
                    <p>Loading employee...</p>
                </div>
            </div>
        );
    }

    return (
        <EmployeeForm
            employee={employee}
            onSubmit={onSubmit}
            submitButtonText="Save Changes"
        />
    );
}

export default EditEmployee;