import {instance} from '@/api/interceptor'
import type { ReceiveData } from '@/type/AdminData'

// 관리자 정보 확인
export const getAdminData = async () : Promise<ReceiveData> => {
  return instance.get('/adminService/admins');
}

// 사용자 정보 전체 가져오기
export const getUserDataAll = async () : Promise<ReceiveData> => {
  return instance.get('/adminService/admins/users');
}

export const getUserDataDetail = async (memberId: number) : Promise<ReceiveData> => {
  return instance.get(`/adminService/admins/users/${memberId}`);
}
