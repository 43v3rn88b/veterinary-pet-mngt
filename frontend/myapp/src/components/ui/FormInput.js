export default function FormInput({ type = "text", value, onChange, placeholder }) {
    return (
        <input
            type={type}
            value={value}
            onChange={onChange}
            placeholder={placeholder}
            className="w-full border rounded-lg p-2 focus:ring focus:ring-blue-300"
        />
    );
}
