"use client";

import { ButtonHTMLAttributes, InputHTMLAttributes, ReactNode, SelectHTMLAttributes } from "react";

export function Button({
    variant = "primary",
    className = "",
    ...props
}: ButtonHTMLAttributes<HTMLButtonElement> & { variant?: "primary" | "secondary" | "danger" }) {
    const styles = {
        primary: "bg-slate-900 text-white hover:bg-slate-700",
        secondary: "bg-white text-slate-700 border border-slate-300 hover:bg-slate-50",
        danger: "bg-red-600 text-white hover:bg-red-700",
    }[variant];
    return (
        <button
            className={`px-4 py-2 rounded-md text-sm font-medium transition-colors disabled:opacity-50 disabled:cursor-not-allowed ${styles} ${className}`}
            {...props}
        />
    );
}

export function Input(props: InputHTMLAttributes<HTMLInputElement>) {
    return (
        <input
            {...props}
            className={`w-full rounded-md border border-slate-300 px-3 py-2 text-sm focus:outline-none focus:ring-2 focus:ring-slate-400 ${props.className ?? ""}`}
        />
    );
}

export function Select(props: SelectHTMLAttributes<HTMLSelectElement>) {
    return (
        <select
            {...props}
            className={`w-full rounded-md border border-slate-300 px-3 py-2 text-sm focus:outline-none focus:ring-2 focus:ring-slate-400 ${props.className ?? ""}`}
        />
    );
}

export function Field({ label, children }: { label: string; children: ReactNode }) {
    return (
        <label className="block">
            <span className="mb-1 block text-sm font-medium text-slate-700">{label}</span>
            {children}
        </label>
    );
}

export function Card({ children, className = "" }: { children: ReactNode; className?: string }) {
    return <div className={`rounded-lg border border-slate-200 bg-white shadow-sm ${className}`}>{children}</div>;
}

export function PageHeader({
    title,
    breadcrumb,
    description,
    actions,
}: {
    title: string;
    breadcrumb?: string;
    description?: string;
    actions?: ReactNode;
}) {
    return (
        <div className="mb-6">
            {breadcrumb && <div className="mb-4 text-sm text-slate-500">{breadcrumb}</div>}
            <div className="flex items-center justify-between">
                <div>
                    <h1 className="text-xl font-semibold text-slate-900">{title}</h1>
                    {description && <p className="mt-1 text-sm text-slate-500">{description}</p>}
                </div>
                {actions}
            </div>
        </div>
    );
}

export function ErrorText({ children }: { children: ReactNode }) {
    if (!children) return null;
    return <p className="text-sm text-red-600">{children}</p>;
}