"use client";

import { FormEvent, useState } from "react";
import { useRouter } from "next/navigation";
import { Button, Card, ErrorText, Field, Input } from "@/components/ui";

const MOCK_EMPLOYEE_ID = "1";
const MOCK_PASSWORD = "123456";

export default function LoginPage() {
    const router = useRouter();
    const [employeeId, setEmployeeId] = useState("");
    const [password, setPassword] = useState("");
    const [error, setError] = useState<string | null>(null);

    function handleSubmit(e: FormEvent) {
        e.preventDefault();

        if (employeeId === MOCK_EMPLOYEE_ID && password === MOCK_PASSWORD) {
            setError(null);
            router.push("/dashboard");
            return;
        }

        setError("사번 또는 비밀번호가 올바르지 않습니다.");
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
                    <Button type="submit" className="w-full">
                        로그인
                    </Button>
                </form>
            </Card>
        </div>
    );
}
