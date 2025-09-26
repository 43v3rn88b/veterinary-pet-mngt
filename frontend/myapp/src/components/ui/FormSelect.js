export default function FormSelect({ label, value, onChange, options, optionLabel, optionValue }) {
    return (
        <div className="mb-4">
            <label className="block text-sm font-medium mb-1">{label}</label>
            <select
                value={value}
                onChange={onChange}
                className="w-full border rounded px-3 py-2"
            >
                <option value="">-- Select --</option>
                {options.map((opt) => (
                    <option key={opt[optionValue]} value={opt[optionValue]}>
                        {opt[optionLabel]}
                    </option>
                ))}
            </select>
        </div>
    );
}
