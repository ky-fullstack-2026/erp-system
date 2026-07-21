import { EmployeeEditForm } from "@/components/employees/EmployeeEditForm";

export default async function EmployeeEditPage({ params }: { params: Promise<{ id: string }> }) {
  const { id } = await params;
  return <EmployeeEditForm employeeId={Number(id)} />;
}
