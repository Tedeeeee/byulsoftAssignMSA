import { instanceWithAuth, instanceForAccessToken } from '@/api/Interceptors'

export const getAccessTokenApi = async (): Promise<string> => {
  return instanceForAccessToken.post('/authService/auth/token/renew');
}