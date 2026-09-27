import { useEffect, useState } from "react";
import api from "../services/api";

function AdminDashboard() {
  const [profiles, setProfiles] = useState([]);
  const [efforts, setEfforts] = useState([]);
  const [rewards, setRewards] = useState([]);
  const [message, setMessage] = useState("");

  useEffect(() => {
    const load = async () => {
      try {
        const [profilesResponse, effortsResponse, rewardsResponse] = await Promise.all([
          api.get("/profiles"),
          api.get("/efforts"),
          api.get("/rewards")
        ]);
        setProfiles(profilesResponse.data);
        setEfforts(effortsResponse.data);
        setRewards(rewardsResponse.data);
      } catch (error) {
        console.error(error);
        setMessage(error.response?.status === 403 ? "Admin access is required." : "Could not load admin data.");
      }
    };
    load();
  }, []);

  return (
    <div>
      <h1>🛡️ Admin Dashboard</h1>
      {message && <p className="error">{message}</p>}

      <div className="card">
        <h2>All User Profiles</h2>
        <div className="table-container"><table><thead><tr><th>ID</th><th>Name</th><th>Email</th><th>Location</th><th>Role</th></tr></thead>
          <tbody>{profiles.map((p) => <tr key={p.id}><td>{p.id}</td><td>{p.name}</td><td>{p.email}</td><td>{p.location}</td><td>{p.role}</td></tr>)}</tbody>
        </table></div>
      </div>

      <div className="card">
        <h2>All Efforts</h2>
        <div className="table-container"><table><thead><tr><th>ID</th><th>User ID</th><th>Activity</th><th>Quantity</th><th>CO2</th><th>Points</th></tr></thead>
          <tbody>{efforts.map((e) => <tr key={e.id}><td>{e.id}</td><td>{e.userId}</td><td>{e.activityType}</td><td>{e.quantity} {e.unit}</td><td>{e.co2Saved} kg</td><td>{e.ecoPoints}</td></tr>)}</tbody>
        </table></div>
      </div>

      <div className="card">
        <h2>All Rewards</h2>
        <div className="table-container"><table><thead><tr><th>User ID</th><th>Total Points</th><th>Level</th><th>Badges</th></tr></thead>
          <tbody>{rewards.map((r) => <tr key={r.id}><td>{r.userId}</td><td>{r.totalPoints}</td><td>{r.level}</td><td>{r.badges || "-"}</td></tr>)}</tbody>
        </table></div>
      </div>
    </div>
  );
}

export default AdminDashboard;
