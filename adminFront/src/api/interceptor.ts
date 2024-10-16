import axios from "axios";
import { accessToken } from '@/stores/AdminStore'

export const setInterceptor = instance => {
  instance.interceptors.request.use(
    config => {
      config.headers.Authorization = 'Bearer ' + localStorage.getItem(accessToken);
      return config
    },
    error => {
      return Promise.reject(error);
    }
  );

  instance.interceptors.response.use(
    response => {
      return response;
    },
    async error => {
      if (error.status === 401) {
        try {
          const refreshToken = await axios.post('/api/authService/auth/token/renew', {
            withCredentials: true,
          })
          console.log(refreshToken.data);
          const newAccessToken = refreshToken.data.message;
          await localStorage.setItem(accessToken, refreshToken.data.message);

          error.config.headers['Authorization'] = `Bearer ${newAccessToken}`;
          return axios(error.config)
        } catch (refreshTokenError) {
          console.log(refreshTokenError)
          return Promise.reject(error);
        }
      } else {
        return Promise.reject(error);
      }
    }
  );

  return instance;
}

export const createInstance = () => {
  const instance = axios.create({
    baseURL: `/api`
  })

  return setInterceptor(instance);
}

export const getAccessToken = () => {
  const instance = axios.create({
    baseURL: `/api`,
    withCredentials: true,
  });

  return setInterceptor(instance);
}

export const instance = createInstance();
export const instanceForAccessToken = getAccessToken();
