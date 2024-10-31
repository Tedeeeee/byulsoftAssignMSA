import axios from 'axios';
import { accessToken } from '@/stores/UserStore'

export const setInterceptors = instance => {
  instance.interceptors.request.use(
    config=> {
      config.headers.Authorization = 'Bearer ' + localStorage.getItem(accessToken);
      return config;
    },
    error => {
      return Promise.reject(error);
      },
  );

  instance.interceptors.response.use(
    response => {
      // 유효기간이 종료된 이후 refreshToken 넣기
      return response;
    },
    async error => {

      if (error.status === 401) {
        try {
          const refreshToken = await axios.post('/api/authService/auth/token/renew', {
            withCredentials: true,
          })
          const newAccessToken = refreshToken.data.message;
          await localStorage.setItem(accessToken, refreshToken.data.message);

          error.config.headers['Authorization'] = `Bearer ${newAccessToken}`;
          return axios(error.config)
        } catch (refreshTokenError) {
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

  return setInterceptors(instance);
}

export const instance = createInstance();
