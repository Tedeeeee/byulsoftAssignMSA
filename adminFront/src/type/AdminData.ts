export interface AdminData {
  adminId: number;
  adminEmail : string;
  adminName : string;
  adminNickname : string;
  adminPhoneNumber : string;
}

export interface AdminRegisterData extends AdminData {
  adminPassword: string;
  confirmPassword: string;
  emailCheck: boolean;
  nicknameCheck: boolean;
}

export type LoginData = Pick<AdminData, 'adminEmail' | 'adminPassword'>

export interface ReceiveData {
  body: any,
  message: string,
}
