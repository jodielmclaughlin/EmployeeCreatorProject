export type Role =
    | "ADMIN"
    | "EMPLOYEE"


export interface User {
    email: string;
    role: Role;
    passwordHash: string;

}