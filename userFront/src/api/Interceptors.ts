import axios from 'axios';

export const setInterceptors = instance => {
  instance.interceptors.request.use(
    config=> config,
    error => {
      return Promise.reject(error);
      },
  );

  instance.interceptors.response.use(
    response => {
      return response;
    },
    error => {
      return Promise.reject(error);
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

export const createInstanceWithAuth = () => {
  const token = localStorage.getItem('token');

  const instance = axios.create({
    baseURL: `/api`,
    headers: {
      Authorization: `Bearer ${token}`,
    }
  })

  return setInterceptors(instance);
}

export const getAccessToken = () => {
  const instance = axios.create({
    baseURL: `/api`,
    withCredentials: true,
  })

  return setInterceptors(instance);
}

export const instance = createInstance();
export const instanceWithAuth = createInstanceWithAuth();
export const instanceForAccessToken = getAccessToken();