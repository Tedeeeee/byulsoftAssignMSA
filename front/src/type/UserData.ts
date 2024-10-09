export interface UserData {
  email : string;
  password : string;
  name : string;
  nickname : string;
  phoneNumber : string;
}

export interface UserRegisterData extends UserData {
  confirmPassword: string;
  emailCheck: boolean;
  nicknameCheck: boolean;
}