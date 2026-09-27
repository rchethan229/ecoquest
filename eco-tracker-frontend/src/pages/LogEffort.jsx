import { useState } from "react";
import { useNavigate } from "react-router-dom";
import api from "../services/api";

const units = {
  Cycling: "hours",
  "Public Transport": "km",
  "Reusable Bottle": "times",
  "Energy Saving": "kWh",
  "Tree Planting": "trees"
};

function LogEffort() {
  const [activityType, setActivityType] = useState("Cycling");
  const [quantity, setQuantity] = useState("1");
  const [message, setMessage] = useState("");
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();
  const userName = localStorage.getItem("userName");

  const logEffort = async (event) => {
    event.preventDefault();
    setLoading(true);
    setMessage("");

    try {
      const response = await api.post("/efforts", {
        activityType,
        quantity: Number(quantity),
        unit: units[activityType]
      });

      setMessage(`Effort logged! CO2 saved: ${response.data.co2Saved} kg | EcoPoints: ${response.data.ecoPoints}`);
      setQuantity("");
    } catch (error) {
      console.error(error);
      setMessage(error.response?.data?.message || "Could not log effort.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="card">
      <h1>🌱 Log Your Sustainable Effort</h1>
      <p>Welcome, <b>{userName}</b></p>

      <form onSubmit={logEffort}>
        <label>Activity</label>
        <select value={activityType} onChange={(e) => setActivityType(e.target.value)}>
          {Object.keys(units).map((activity) => <option key={activity}>{activity}</option>)}
        </select>

        <label>Quantity ({units[activityType]})</label>
        <input type="number" min="0.1" step="0.1" value={quantity} onChange={(e) => setQuantity(e.target.value)} required />

        <button type="submit" disabled={loading}>{loading ? "Logging..." : "Log Effort"}</button>
      </form>

      {message && <p className="success">{message}</p>}
      <button className="secondary" onClick={() => navigate("/dashboard")}>View My Dashboard</button>
    </div>
  );
}

export default LogEffort;
