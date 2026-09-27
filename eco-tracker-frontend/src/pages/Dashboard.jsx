import { useEffect, useState } from "react";
import api from "../services/api";

function Dashboard() {
  const [profile, setProfile] = useState(null);
  const [efforts, setEfforts] = useState([]);
  const [reward, setReward] = useState(null);
  const [message, setMessage] = useState("");
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const loadDashboard = async () => {
      try {
        const [profileResponse, effortResponse, rewardResponse] = await Promise.all([
          api.get("/profiles/me"),
          api.get("/efforts/me"),
          api.get("/rewards/me")
        ]);
        setProfile(profileResponse.data);
        setEfforts(effortResponse.data);
        setReward(rewardResponse.data);
      } catch (error) {
        console.error(error);
        setMessage("Could not load dashboard. Check backend services.");
      } finally {
        setLoading(false);
      }
    };
    loadDashboard();
  }, []);

  const totalCo2 = efforts.reduce((sum, effort) => sum + Number(effort.co2Saved || 0), 0);
  const totalPoints = efforts.reduce((sum, effort) => sum + Number(effort.ecoPoints || 0), 0);

  if (loading) return <div className="card"><h1>Dashboard</h1><p>Loading dashboard...</p></div>;
  if (message) return <div className="card"><h1>Dashboard</h1><p>{message}</p></div>;

  return (
    <div>
      <h1>🌱 My EcoQuest Dashboard</h1>
      {profile && <div className="card">
        <h2>Welcome, {profile.name}!</h2>
        <p><strong>Email:</strong> {profile.email}</p>
        <p><strong>Location:</strong> {profile.location}</p>
      </div>}

      <div className="dashboard-grid">
        <div className="stat-card"><h3>🌍 CO2 Saved</h3><p>{totalCo2.toFixed(2)} kg</p></div>
        <div className="stat-card"><h3>⭐ EcoPoints</h3><p>{reward ? reward.totalPoints : totalPoints}</p></div>
        <div className="stat-card"><h3>🌱 Level</h3><p>{reward ? reward.level : "Beginner"}</p></div>
      </div>

      <div className="card">
        <h2>My Sustainable Efforts</h2>
        {efforts.length === 0 ? <p>No efforts logged yet.</p> : (
          <div className="table-container"><table><thead><tr>
            <th>Activity</th><th>Quantity</th><th>Unit</th><th>CO2 Saved</th><th>Points</th>
          </tr></thead><tbody>
            {efforts.map((effort) => <tr key={effort.id}>
              <td>{effort.activityType}</td><td>{effort.quantity}</td><td>{effort.unit}</td>
              <td>{effort.co2Saved} kg</td><td>{effort.ecoPoints}</td>
            </tr>)}
          </tbody></table></div>
        )}
      </div>
    </div>
  );
}

export default Dashboard;
