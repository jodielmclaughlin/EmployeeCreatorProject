import {useNavigate} from "react-router-dom";
import {useForm} from "react-hook-form";
import {type LoginFormData, loginSchema} from "./loginSchema.ts";
import {zodResolver} from "@hookform/resolvers/zod";
import { login } from "../../services/authApi";
import { setToken } from "../../services/auth";

function LoginForm(){
    const navigate = useNavigate();

    const {
        register,
        handleSubmit,
        formState: {errors},
    } = useForm<LoginFormData>({
            resolver: zodResolver(loginSchema),
        });

    const onSubmit = async (data: LoginFormData) => {
        try{
            const token = await login(data);
            setToken(token);

            navigate("/");

        } catch(error){
            console.error(error);
        }
    };

    return(
        <form onSubmit={handleSubmit(onSubmit)}>
            <div>
                <label htmlFor="email">Email</label>
                <input
                    id="email"
                    type="email"
                    {...register("email")}
                />
                {errors.email && (
                    <p>{errors.email.message}</p>
                )}
            </div>
            <div>
                <label htmlFor="password">Password</label>
                <input
                    id="password"
                    type="password"
                    {...register("password")}
                />

                {errors.password && (
                    <p>{errors.password.message}</p>
                )}
            </div>
            <button type="submit">
                Login
            </button>
        </form>
    )
}

export default LoginForm