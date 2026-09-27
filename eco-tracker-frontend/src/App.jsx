import { BrowserRouter, Link, Navigate, Route, Routes, useNavigate } from "react-router-dom";
import SignUp from "./pages/SignUp";
import SignIn from "./pages/SignIn";
import LogEffort from "./pages/LogEffort";
import Dashboard from "./pages/Dashboard";
import AdminDashboard from "./pages/AdminDashboard";

function ProtectedRoute({ children }) {
  return localStorage.getItem("token") ? children : <Navigate to="/signin" replace />;
}

function Navigation() {
  const navigate = useNavigate();
  const token = localStorage.getItem("token");
  const role = localStorage.getItem("userRole");

  const logout = () => {
    localStorage.clear();
    navigate("/signin");
  };

  return (
    <nav className="navbar">
      <h2>🌱 EcoQuest - Sustainability Tracking System</h2>
      {token ? (
        <div>
          {role === "ADMIN" ? (
            <Link to="/admin">Admin Dashboard</Link>
          ) : (
            <>
              <Link to="/effort">Log Effort</Link>
              <Link to="/dashboard">My Dashboard</Link>
            </>
          )}
          <button className="secondary" onClick={logout}>Logout</button>
        </div>
      ) : (
        <div>
          <Link to="/signin">Sign In</Link>
          <Link to="/signup">Sign Up</Link>
        </div>
      )}
    </nav>
  );
}

function App() {
  return (
    <BrowserRouter>
      <Navigation />
      <main className="container">
        <Routes>
          <Route path="/" element={<Navigate to={localStorage.getItem("token") ? "/effort" : "/signin"} replace />} />
          <Route path="/signup" element={<SignUp />} />
          <Route path="/signin" element={<SignIn />} />
          <Route path="/effort" element={<ProtectedRoute><LogEffort /></ProtectedRoute>} />
          <Route path="/dashboard" element={<ProtectedRoute><Dashboard /></ProtectedRoute>} />
          <Route path="/admin" element={<ProtectedRoute><AdminDashboard /></ProtectedRoute>} />
        </Routes>
      </main>
    </BrowserRouter>
  );
}

export default App;
