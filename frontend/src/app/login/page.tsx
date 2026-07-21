"use client";

import { FormEvent, useState } from "react";
import { useRouter } from "next/navigation";
import { Button, Card, ErrorText, Field, Input } from "@/components/ui";
import { useAuth } from "@/lib/auth/AuthContext";


export default function LoginPage() {
    const router = useRouter();
    const { login } = useAuth();
    const [employeeId, setEmployeeId] = useState("");
    const [password, setPassword] = useState("");
    const [error, setError] = useState<string | null>(null);
    const [loading, setLoading] = useState(false);

    async function handleSubmit(e: FormEvent) {
        e.preventDefault();
        setLoading(true)
        try {
            await login({ loginId: employeeId, password });
            router.push("/dashboard");

        } catch (error) {
            if (error instanceof Error) {
                setError(error.message);
            } else {
                setError("서버에 연결할 수 없습니다.");
            }
        } finally {
            setLoading(false)
        }
    }

    return (
        <div className="flex flex-1 items-center justify-center px-4">
            <Card className="w-full max-w-sm p-8">
                <h1 className="mb-6 text-center text-lg font-semibold text-slate-900">인사관리시스템 로그인</h1>
                <form onSubmit={handleSubmit} className="space-y-4">
                    <Field label="사번">
                        <Input
                            value={employeeId}
                            onChange={(e) => setEmployeeId(e.target.value)}
                            autoComplete="username"
                            required
                        />
                    </Field>
                    <Field label="비밀번호">
                        <Input
                            type="password"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                            autoComplete="current-password"
                            required
                        />
                    </Field>
                    <ErrorText>{error}</ErrorText>
                    <Button type="submit" className="w-full" disabled={loading}>
                        {loading ? "로그인 중..." : "로그인"}
                    </Button>
                </form>
            </Card>
        </div>
    );
}
