export interface UserData {
  memberId: number;
  memberEmail: string;
  memberNickname: string;
  memberName: string;
  memberPhoneNumber: string;
  memberIsDelete: boolean;
  memberCreatedAt: string;
  memberUpdatedAt: string;
}

export type UserListData = Pick<UserData, 'memberId' | 'memberNickname' | 'memberEmail' | 'memberIsDelete'>
