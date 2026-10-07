import {useForm} from "react-hook-form";
import {schema, type EmployeeFormData} from "./schema";
import { zodResolver } from "@hookform/resolvers/zod";
import { useEffect } from "react";
import type { Employee } from "../../types/employee";



interface FormProps{
    onSubmit:(data:EmployeeFormData) => unknown;
    employee?: Employee;
    submitButtonText?: string;
}

function EmployeeForm({
    onSubmit,
    employee,
    submitButtonText = "Create Employee"
    }: FormProps) {
        const {
        handleSubmit,
        formState: { errors },
        register,
        reset
    } = useForm<EmployeeFormData>({
        resolver: zodResolver(schema),
        defaultValues: {
            firstName: employee?.firstName ?? "",
            lastName: employee?.lastName ?? "",
            email: employee?.email ?? "",
            phoneNumber: employee?.phoneNumber ?? "",
            address: employee?.address ?? "",
            contractType: employee?.contractType ?? "FULL_TIME",
            jobTitle: employee?.jobTitle ?? "",
            startDate: employee?.startDate ?? "",
        },
    });

    useEffect(() => {
        if (employee) {
            reset({
                firstName: employee.firstName,
                lastName: employee.lastName,
                email: employee.email,
                phoneNumber: employee.phoneNumber,
                address: employee.address,
                contractType: employee.contractType,
                jobTitle: employee.jobTitle,
                startDate: employee.startDate,
            });
        }
    }, [employee, reset]);

    const inputStyles =
    "w-full rounded-lg border border-gray-300 px-4 py-2.5 text-gray-900 shadow-sm outline-none transition focus:border-blue-500 focus:ring-2 focus:ring-blue-200";
    
    console.log(errors, " ERRORS");

    //isSubmitSuccessful && reset();

    return(
        <div  data-testid="employee-page" className="min-h-screen bg-gray-100 px-4 py-10">
            <div className="mx-auto max-w-3xl">

                <div data-testid="employee-form-header" className="mb-8 text-center">
                    <h1 data-testid="employee-form-title" className="text-3xl font-bold text-gray-900">
                        Create Employee
                    </h1>

                    <p data-testid="employee-form-description" className="mt-2 text-gray-600">
                        Add the details for the new employee
                    </p>
                </div>
                <form data-testid="employee-form" onSubmit={handleSubmit(onSubmit)} className="rounded-xl bg-white p-6 shadow-md sm:p-8">
                    <div className="grid grid-cols-1 gap-6 sm:grid-cols-2">

                        <div data-testid="first-name-field">
                            <label
                                htmlFor="firstName"
                                className="mb-2 block text-sm font-medium text-gray-700"
                            >
                                First Name
                            </label>

                            <input
                                id="firstName"
                                data-testid="first-name-input"
                                type="text"
                                {...register("firstName")}
                                className={inputStyles}
                            />

                            {errors.firstName && (
                                <p data-testid="first-name-error" className="mt-1 text-sm text-red-600">
                                    {errors.firstName.message}
                                </p>
                            )}
                        </div>

                        <div data-testid="last-name-field">
                            <label
                                htmlFor="lastName"
                                className="mb-2 block text-sm font-medium text-gray-700"
                            >
                                Last Name
                            </label>

                            <input
                                id="lastName"
                                data-testid="last-name-input"
                                type="text"
                                {...register("lastName")}
                                className={inputStyles}
                            />

                            {errors.lastName && (
                                <p data-testid="last-name-error" className="mt-1 text-sm text-red-600">
                                    {errors.lastName.message}
                                </p>
                            )}
                        </div>

                    </div>
                    <div className="grid grid-cols-1 gap-6 sm:grid-cols-2">

                        <div data-testid="email-field">
                            <label
                                htmlFor="email"
                                className="mb-2 block text-sm font-medium text-gray-700"
                            >
                                Email
                            </label>

                            <input
                                id="email"
                                data-testid="email-input"
                                type="email"
                                {...register("email")}
                                className={inputStyles}
                            />

                            {errors.email && (
                                <p data-testid="email-error" className="mt-1 text-sm text-red-600">
                                    {errors.email.message}
                                </p>
                            )}
                        </div>

                        <div data-testid="phone-number-field">
                            <label
                                htmlFor="phoneNumber"
                                className="mb-2 block text-sm font-medium text-gray-700"
                            >
                                Phone Number
                            </label>

                            <input
                                id="phoneNumber"
                                data-testid="phone-number-input"
                                type="tel"
                                {...register("phoneNumber")}
                                className={inputStyles}
                            />

                            {errors.phoneNumber && (
                                <p data-testid="phone-number-error" className="mt-1 text-sm text-red-600">
                                    {errors.phoneNumber.message}
                                </p>
                            )}
                        </div>

                    </div>
                    <div data-testid="address-field" className="mt-6">

                        <label
                            htmlFor="address"
                            className="mb-2 block text-sm font-medium text-gray-700"
                        >
                            Address
                        </label>

                        <textarea
                            id="address"
                            data-testid="address-input"
                            rows={3}
                            {...register("address")}
                            className={inputStyles}
                        />

                        {errors.address && (
                            <p data-testid="address-error" className="mt-1 text-sm text-red-600">
                                {errors.address.message}
                            </p>
                        )}

                    </div>
                    <div data-testid="contract-type-field">
                        <label
                            htmlFor="contractType"
                            className="mb-2 block text-sm font-medium text-gray-700"
                        >
                            Contract Type
                        </label>

                        <select
                            id="contractType"
                             data-testid="contract-type-select"
                            {...register("contractType")}
                            className={inputStyles}
                            defaultValue=""
                        >
                            <option value="" disabled>
                                Select contract type
                            </option>

                            <option value="FULL_TIME">Full Time</option>
                            <option value="PART_TIME">Part Time</option>
                            <option value="CONTRACT">Contract</option>
                            <option value="TEMPORARY">Temporary</option>
                            <option value="INTERN">Intern</option>
                        </select>

                        {errors.contractType && (
                            <p data-testid="contract-type-error" className="mt-1 text-sm text-red-600">
                                {errors.contractType.message}
                            </p>
                        )}
                    </div>
                    <div data-testid="job-title-field">
                        <label
                            htmlFor="jobTitle"
                            className="mb-2 block text-sm font-medium text-gray-700"
                        >
                            Job Title
                        </label>

                        <input
                            id="jobTitle"
                            data-testid="job-title-input"
                            type="text"
                            {...register("jobTitle")}
                            className={inputStyles}
                        />

                        {errors.jobTitle && (
                            <p data-testid="job-title-error" className="mt-1 text-sm text-red-600">
                                {errors.jobTitle.message}
                            </p>
                        )}
                    </div>
                    <div data-testid="start-date-field">
                        <label
                            htmlFor="startDate"
                            className="mb-2 block text-sm font-medium text-gray-700"
                        >
                            Start Date
                        </label>

                        <input
                            id="startDate"
                            data-testid="start-date-input"
                            type="date"
                            {...register("startDate")}
                            className={inputStyles}
                        />

                        {errors.startDate && (
                            <p data-testid="start-date-error" className="mt-1 text-sm text-red-600">
                                {errors.startDate.message}
                            </p>
                        )}
                    </div>
                    <div className="mt-8 flex justify-end">
                        <button
                            type="submit"
                            data-testid="submit-employee-button"
                            className="rounded-lg bg-blue-600 px-6 py-3 font-medium text-white transition hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-offset-2">
                            {submitButtonText ?? "Create Employee"}
                        </button>
                    </div>
                </form>
            </div>
        </div>
    )
}

export default EmployeeForm;