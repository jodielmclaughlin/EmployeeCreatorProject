import {useNavigate} from "react-router-dom";
import {useForm} from "react-hook-form";
import {type LoginFormData, loginSchema} from "./loginSchema.ts";
import {zodResolver} from "@hookform/resolvers/zod";
import { login } from "../../services/authApi";
import { setToken } from "../../services/auth";
import {useState} from "react";

function LoginForm(){
    const navigate = useNavigate();
    const [loginError, setLoginError] = useState<string | null>();

    const {
        register,
        handleSubmit,
        formState: {errors},
    } = useForm<LoginFormData>({
            resolver: zodResolver(loginSchema),
        });

    const onSubmit = async (data: LoginFormData) => {
        setLoginError(null);
        try{
            const token = await login(data);
            setToken(token);

            navigate("/home");

        } catch(error){
           if(error instanceof Error){
               setLoginError(error.message);
           }
           else{
               setLoginError("Invalid email or password")
           }
        }
    };

    return(
        <div  className="min-h-screen bg-gray-100 px-4 py-10">
            <div className="mx-auto max-w-lg">
                <div data-testid="login-header" className="mb-8 text-center">
                    <h1 data-testid="login-title" className="text-3xl font-bold text-gray-900">
                        Login
                    </h1>
                </div>
                <form data-testid="login-form" onSubmit={handleSubmit(onSubmit)} className="rounded-xl bg-white p-6 shadow-md sm:p-8">
                    {loginError && (
                        <div
                            role="alert"
                            data-testid="login-error"
                            className=" text-center mb-6 rounded-lg border border-red-300 bg-red-50 p-4 text-sm text-red-700"
                        >
                            {loginError}
                        </div>
                    )}

                    <div className="text-center mb-">
                        <div>
                            <label htmlFor="email" className="mb-2 block text-sm font-medium text-gray-700">
                                Email
                            </label>
                            <input
                                id="email"
                                type="email"
                                data-testid="email-login-input"
                                className="mb-2 w-full rounded-lg border border-gray-300 px-4 py-2.5 text-gray-900 shadow-sm outline-none transition focus:border-blue-500 focus:ring-2 focus:ring-blue-200"
                            {...register("email")}
                            />
                            {errors.email && (
                                <p data-testid="email-login-error" className="mt-1 text-sm text-red-600">{errors.email.message}</p>
                            )}
                        </div>
                        <div>
                            <label htmlFor="password" className="mb-2 block text-sm font-medium text-gray-700">
                                Password
                            </label>
                            <input
                                id="password"
                                type="password"
                                data-testid="password-login-input"
                                className="mb-2 w-full rounded-lg border border-gray-300 px-4 py-2.5 text-gray-900 shadow-sm outline-none transition focus:border-blue-500 focus:ring-2 focus:ring-blue-200"

                            {...register("password")}
                            />

                            {errors.password && (
                                <p data-testid="password-login-error" className="mt-1 text-sm text-red-600">{errors.password.message}</p>
                            )}
                        </div>
                        <button type="submit" data-testid="submit-login-button" className="rounded-lg bg-blue-600 px-6 py-3 font-medium text-white transition hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-offset-2">
                            Login
                        </button>
                    </div>
                </form>
            </div>
        </div>
    )
}

export default LoginForm