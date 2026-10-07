import { useNavigate } from "react-router-dom";
import type { Employee } from "../../types/employee"
import {isAdmin} from "../../services/auth.ts";


interface EmployeeProps{
    employee: Employee
    onDelete: (employee: Employee) => void;
}

function EmployeeCard({employee, onDelete }: EmployeeProps){
    const navigate = useNavigate();
    
    const handleEdit = (id: number) => {
    navigate(`/employees/${id}/edit`);
    
};

    return(
        <div
            data-testid={`employee-card-${employee.id}`}
            className="rounded-xl bg-white p-6 shadow-sm">
            <div className="flex flex-col gap-4 sm:flex-row sm:items-center sm:justify-between">

                <div>
                    <h2
                        data-testid={`employee-name-${employee.id}`}
                        className="text-xl font-semibold text-gray-900"
                    >
                        {employee.firstName} {employee.lastName}
                    </h2>

                    <p className="mt-1 text-gray-700">
                        {employee.jobTitle}
                    </p>

                    <p className="mt-1 text-sm text-gray-500">
                        {employee.email}
                    </p>
                </div>

                <div className="flex gap-3">
                    <button
                        data-testid={`edit-employee-${employee.id}`}
                        onClick={() => handleEdit(employee.id)}
                        disabled={!isAdmin()}
                        className="rounded-lg border border-gray-300 px-4 py-2 text-sm font-medium text-gray-700
                        hover:bg-gray-50 disabled:bg-zinc-300 disabled:cursor-not-allowed disabled:border-transparent disabled:text-black"
                    >
                        Edit
                    </button>

                    <button
                        data-testid={`delete-employee-${employee.id}`}
                        onClick={() => onDelete(employee)}
                        disabled={!isAdmin()}
                        className="rounded-lg bg-red-600 px-4 py-2 text-sm font-medium text-white hover:bg-red-700 disabled:cursor-not-allowed
                        disabled:bg-zinc-300 disabled:border-transparent disabled:text-black"
                    >
                        Remove
                    </button>
                </div>

            </div>
        </div>
    )
}

export default EmployeeCard;


