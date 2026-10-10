import axios from 'axios';

// Backend URL. Set REACT_APP_API_URL in frontend/.env (see .env.example).
export const API_BASE_URL = process.env.REACT_APP_API_URL || 'http://localhost:5454';

const api = axios.create({
  baseURL: API_BASE_URL,
});

// Read the token on every request so it is always current after login/logout.
api.interceptors.request.use((config) => {
  const token = localStorage.getItem('jwt');
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

api.defaults.headers.post['Content-Type'] = 'application/json';

export default api;
