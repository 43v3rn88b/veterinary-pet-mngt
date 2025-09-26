export default function Card({ children }) {
    return (
        <div className="max-w-2xl mx-auto p-6 bg-white rounded-2xl shadow-md mb-8">
            {children}
        </div>
    );
}
