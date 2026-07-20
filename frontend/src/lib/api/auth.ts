import { apiFetch } from "@/lib/api/client";
import { setToken } from "@/lib/auth/token";

export interface LoginRequest {
  loginId: string;
  password: string;
}

export interface LoginResponse {
  accessToken: string;
  tokenType: string;
  employeeId: number;
  employeeNo: string;
  name: string;
}

export async function login(request: LoginRequest): Promise<LoginResponse> {
  const response = await apiFetch<LoginResponse>("/auth/login", {
    method: "POST",
    body: JSON.stringify(request),
    skipAuth: true,
  });
  setToken(response.accessToken);
  return response;
}
