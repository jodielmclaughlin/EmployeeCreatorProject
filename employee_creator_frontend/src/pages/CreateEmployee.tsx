import { useNavigate } from "react-router-dom";
import type { EmployeeFormData } from "../components/EmployeeForm/schema";
import EmployeeForm from "../components/EmployeeForm/EmployeeForm";
import { createEmployee } from "../services/employees";

function CreateEmployee() {
    const navigate = useNavigate();

    const onSubmit = async (data: EmployeeFormData) => {
        await createEmployee(data);
        navigate("/");
    };

    return (
        <EmployeeForm onSubmit={onSubmit} />
    );
}

export default CreateEmployee;