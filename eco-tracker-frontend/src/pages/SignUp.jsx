import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import api from "../services/api";

function SignUp() {
  const [form, setForm] = useState({ name: "", email: "", password: "", location: "" });
  const [message, setMessage] = useState("");
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();

  const submit = async (event) => {
    event.preventDefault();
    setLoading(true);
    setMessage("");

    try {
      await api.post("/auth/signup", form);
      setMessage("Signup successful! Your profile was created automatically. Please sign in.");
      setTimeout(() => navigate("/signin"), 10000);
    } catch (error) {
      setMessage(error.response?.data?.message || "Signup failed.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="card">
      <h1>🌱 Create Your EcoQuest Account</h1>
      <form onSubmit={submit}>
        <label>Name</label>
        <input value={form.name} onChange={(e) => setForm({ ...form, name: e.target.value })} required />

        <label>Email</label>
        <input type="email" value={form.email} onChange={(e) => setForm({ ...form, email: e.target.value })} required />

        <label>Password</label>
        <input type="password" minLength="6" value={form.password} onChange={(e) => setForm({ ...form, password: e.target.value })} required />

        <label>Location</label>
        <input value={form.location} onChange={(e) => setForm({ ...form, location: e.target.value })} required />

        <button type="submit" disabled={loading}>{loading ? "Creating account..." : "Sign Up"}</button>
      </form>

      {message && <p className="success">{message}</p>}
      <p>Already have an account? <Link to="/signin">Sign in</Link></p>
    </div>
  );
}

export default SignUp;
