import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import api from "../services/api";

function SignIn() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [message, setMessage] = useState("");
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();

  const submit = async (event) => {
    event.preventDefault();
    setLoading(true);
    setMessage("");

    try {
      const response = await api.post("/auth/login", { email, password });
      const user = response.data;

      localStorage.setItem("token", user.token);
      localStorage.setItem("userId", user.userId);
      localStorage.setItem("userName", user.name);
      localStorage.setItem("userEmail", user.email);
      localStorage.setItem("userRole", user.role);
      localStorage.setItem("userLocation", user.location || "");

      // The profile already exists because signup created it automatically.
      // Login returns the profile data and immediately opens effort logging.
      navigate(user.role === "ADMIN" ? "/admin" : "/effort");
    } catch (error) {
      setMessage(error.response?.data?.message || "Invalid email or password.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="card">
      <h1>🔐 Sign In</h1>
      <p>Sign in to log your sustainable efforts.</p>

      <form onSubmit={submit}>
        <label>Email</label>
        <input type="email" value={email} onChange={(e) => setEmail(e.target.value)} required />

        <label>Password</label>
        <input type="password" value={password} onChange={(e) => setPassword(e.target.value)} required />

        <button type="submit" disabled={loading}>{loading ? "Signing in..." : "Sign In"}</button>
      </form>

      {message && <p className="error">{message}</p>}
      <p>New user? <Link to="/signup">Create an account</Link></p>
    </div>
  );
}

export default SignIn;
