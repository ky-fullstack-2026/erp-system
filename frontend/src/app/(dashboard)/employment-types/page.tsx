"use client";

import { FormEvent, useEffect, useState } from "react";
import {
    createEmploymentType,
    deleteEmploymentType,
    listEmploymentTypes,
    updateEmploymentType,
} from "@/lib/api/employmentTypes";
import { EmploymentType } from "@/lib/types/employmentType";
import { ApiError } from "@/lib/api/client";
import { Button, Card, ErrorText, Field, Input, PageHeader } from "@/components/ui";
import { Modal } from "@/components/Modal";

export default function employmentTypes() {
    const [employmentTypes, setEmploymentTypes] = useState<EmploymentType[]>([]);
    const [loading, setLoading] = useState(true);
    const [editing, setEditing] = useState<EmploymentType | null>(null);
    const [showForm, setShowForm] = useState(false);

    function load() {
        listEmploymentTypes()
            .then(setEmploymentTypes)
            .finally(() => setLoading(false));
    }

    useEffect(() => {
        load();
    }, []);

    function openCreate() {
        setEditing(null);
        setShowForm(true);
    }

    function openEdit(employmentType: EmploymentType) {
        setEditing(employmentType);
        setShowForm(true);
    }

    async function handleDelete(id: number) {
        if (!confirm("이 사원타입을 삭제하시겠습니까?")) return;
        await deleteEmploymentType(id);
        load();
    }

    return (
        <div>
            <PageHeader
                title="사원타입 관리"
                breadcrumb="인사관리 > 기준정보관리 > 사원타입관리"
                actions={<Button onClick={openCreate}>사원타입 추가</Button>}
            />
            <Card>
                <table className="w-full text-sm">
                    <thead className="border-b border-slate-200 bg-slate-50 text-left text-slate-500">
                        <tr>
                            <th className="px-4 py-2">사원타입명</th>
                            <th className="px-4 py-2" />
                        </tr>
                    </thead>
                    <tbody>
                        {loading && (
                            <tr>
                                <td className="px-4 py-4 text-slate-400" colSpan={2}>
                                    불러오는 중...
                                </td>
                            </tr>
                        )}
                        {!loading && employmentTypes.length === 0 && (
                            <tr>
                                <td className="px-4 py-4 text-slate-400" colSpan={2}>
                                    등록된 사원타입이 없습니다.
                                </td>
                            </tr>
                        )}
                        {employmentTypes.map((employmentType) => (
                            <tr key={employmentType.employmentTypeId} className="border-b border-slate-100">
                                <td className="px-4 py-2">{employmentType.employmentTypeName}</td>
                                <td className="px-4 py-2 text-right space-x-2">
                                    <Button variant="secondary" onClick={() => openEdit(employmentType)}>
                                        수정
                                    </Button>
                                    <Button variant="danger" onClick={() => handleDelete(employmentType.employmentTypeId)}>
                                        삭제
                                    </Button>
                                </td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </Card>
            {showForm && (
                <EmploymentTypeFormModal
                    employmentType={editing}
                    onClose={() => setShowForm(false)}
                    onSaved={() => {
                        setShowForm(false);
                        load();
                    }}
                />
            )}
        </div>
    );
}

function EmploymentTypeFormModal({
    employmentType,
    onClose,
    onSaved,
}: {
    employmentType: EmploymentType | null;
    onClose: () => void;
    onSaved: () => void;
}) {
    const [employmentTypeName, setEmploymentTypeName] = useState(employmentType?.employmentTypeName ?? "");
    const [error, setError] = useState<string | null>(null);
    const [submitting, setSubmitting] = useState(false);

    async function handleSubmit(e: FormEvent) {
        e.preventDefault();
        setError(null);
        setSubmitting(true);
        try {
            const payload = { employmentTypeName };
            if (employmentType) {
                await updateEmploymentType(employmentType.employmentTypeId, payload);
            } else {
                await createEmploymentType(payload);
            }
            onSaved();
        } catch (err) {
            setError(err instanceof ApiError ? err.message : "저장에 실패했습니다.");
        } finally {
            setSubmitting(false);
        }
    }

    return (
        <Modal title={employmentType ? "사원타입 수정" : "사원타입 추가"} onClose={onClose}>
            <form onSubmit={handleSubmit} className="space-y-4">
                <Field label="사원타입명">
                    <Input value={employmentTypeName} onChange={(e) => setEmploymentTypeName(e.target.value)} required />
                </Field>
                <ErrorText>{error}</ErrorText>
                <div className="flex justify-end gap-2 pt-2">
                    <Button type="button" variant="secondary" onClick={onClose}>
                        취소
                    </Button>
                    <Button type="submit" disabled={submitting}>
                        저장
                    </Button>
                </div>
            </form>
        </Modal>
    );
}
