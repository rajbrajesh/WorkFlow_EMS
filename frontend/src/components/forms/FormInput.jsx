/**
 * Reusable input component for WorkFlow forms.
 *
 * This component keeps the common input structure
 * in one place:
 * - Label
 * - Input field
 * - Placeholder
 * - Value
 * - Change handler
 */
function FormInput({
    id,
    label,
    type = "text",
    placeholder,
    value,
    onChange,
    required = false
}) {
    return (
        <div className="form-group">

            {/* Input label */}
            <label htmlFor={id}>
                {label}
            </label>

            {/* Reusable input field */}
            <input
                id={id}
                type={type}
                placeholder={placeholder}
                value={value}
                onChange={onChange}
                required={required}
            />

        </div>
    );
}

export default FormInput;