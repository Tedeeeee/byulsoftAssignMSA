export interface UserData {
  memberEmail : string;
  memberPassword : string;
  memberName : string;
  memberNickname : string;
  memberPhoneNumber : string;
}

export interface UserRegisterData extends UserData {
  confirmPassword: string;
  emailCheck: boolean;
  nicknameCheck: boolean;
}

export type LoginData = Pick<UserData, 'email' | 'password'>;