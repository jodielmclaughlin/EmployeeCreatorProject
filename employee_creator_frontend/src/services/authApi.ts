import {getToken} from "./auth.ts";

interface LoginRequestDTO{
    email: string,
    password: string
}

export async function login(loginData: LoginRequestDTO){
    const response = await fetch("http://localhost:8080/auth/login", {
        method:"POST",
        body: JSON.stringify(loginData),
        headers:{"Content-Type": "application/json",
        },
    });

    if(!response.ok){
        throw new Error("Invalid email or password");
    }

    return await response.text();
}

export function authHeaders(){
    const token = getToken();
    return{
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`,
    };
}