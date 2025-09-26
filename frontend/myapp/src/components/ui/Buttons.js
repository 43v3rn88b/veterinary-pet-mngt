export function PrimaryButton({ children, ...props }) {
    return (
        <button
            {...props}
            className="w-full bg-blue-600 hover:bg-blue-700 text-white px-4 py-2 rounded-lg"
        >
            {children}
        </button>
    );
}

export function SecondaryButton({ children, ...props }) {
    return (
        <button
            {...props}
            className="px-3 py-1 bg-yellow-500 hover:bg-yellow-600 text-white rounded"
        >
            {children}
        </button>
    );
}

export function DangerButton({ children, ...props }) {
    return (
        <button
            {...props}
            className="px-3 py-1 bg-red-500 hover:bg-red-600 text-white rounded"
        >
            {children}
        </button>
    );
}
