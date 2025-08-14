import { useContext } from 'react';
import { AuthContext } from '../context/AuthContext';

function Dashboard() {
  const { user, logout } = useContext(AuthContext);

  return (
    <div className="dashboard-container">
      <h2>Welcome, {user?.username}!</h2>
      <p>You are now logged in to your account.</p>
      <button onClick={logout} className="logout-button">
        Logout
      </button>
    </div>
  );
}

export default Dashboard;