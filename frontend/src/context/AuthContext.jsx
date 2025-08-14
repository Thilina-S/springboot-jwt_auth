import { createContext, useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { toast } from 'react-toastify';
import api from '../services/api';

export const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  const [user, setUser] = useState(null);
  const [loading, setLoading] = useState(true);
  const navigate = useNavigate();

  useEffect(() => {
    const token = localStorage.getItem('token');
    if (token) {
      api.defaults.headers.common['Authorization'] = `Bearer ${token}`;
      try {
        const payload = JSON.parse(atob(token.split('.')[1]));
        setUser({ username: payload.username });
      } catch (error) {
        console.error('Error parsing token', error);
        logout();
      }
    }
    setLoading(false);
  }, []);

  const login = async (credentials) => {
    try {
      const response = await api.post('/api/auth/login', credentials);
      const { token, username } = response.data;
      
      localStorage.setItem('token', token);
      api.defaults.headers.common['Authorization'] = `Bearer ${token}`;
      setUser({ username });
      
      toast.success('Login successful!');
      navigate('/dashboard');
      return { success: true };
    } catch (error) {
      console.error('Login failed', error);
      toast.error(error.response?.data?.message || 'Login failed');
      return { success: false };
    }
  };

  const register = async (userData) => {
    try {
      await api.post('/api/auth/register', userData);
      toast.success('Registration successful! Please login');
      navigate('/signin');
      return { success: true };
    } catch (error) {
      console.error('Registration failed', error);
      toast.error(error.response?.data?.message || 'Registration failed');
      return { success: false };
    }
  };

  const logout = () => {
    localStorage.removeItem('token');
    delete api.defaults.headers.common['Authorization'];
    setUser(null);
    toast.info('Logged out successfully');
    navigate('/signin');
  };

  return (
    <AuthContext.Provider value={{ user, loading, login, register, logout }}>
      {!loading && children}
    </AuthContext.Provider>
  );
};