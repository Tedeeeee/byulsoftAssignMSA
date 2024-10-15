import { instanceWithAuth, instanceForAccessToken } from '@/api/Interceptors'
import type { BoardSaveData, BoardUpdateData } from '@/type/BoardData'
import type { CommentData } from '@/type/CommentData'
import type { UserData } from '@/type/UserData'
import type { reportData } from '@/type/ReportData'

export const getAccessTokenApi = async () => {
  return instanceForAccessToken.post('/authService/auth/token/renew');
}

export const getUserData = async () : Promise<UserData> => {
  return instanceWithAuth.get('/userService/users');
}

export const changeNickname = async (nickname: string) => {
  return instanceWithAuth.post('/userService/users/changeNickname', {memberNickname: nickname})
}

export const checkOriginPassword = async (password: string) => {
  return instanceWithAuth.post('/userService/users/checkOriginPassword', {memberPassword: password})
}

export const setNewPassword = async (password: string) => {
  return instanceWithAuth.post('/userService/users/changePassword', {memberPassword: password})
}

export const getMyBoard = async () => {
  return instanceWithAuth.get('/userBoardService/boards');
}

export const insertBoard = async (boardInsertData: BoardSaveData): Promise<void> => {
  return instanceWithAuth.post('/userBoardService/boards', boardInsertData);
}

export const updateBoard = async (boardUpdateData: BoardUpdateData): Promise<void> => {
  return instanceWithAuth.post('/userBoardService/boards/update', boardUpdateData);
}

export const deleteBoard = async (id: number) => {
  return instanceWithAuth.post(`/userBoardService/boards/delete/${id}`);
};

export const getMyComment = async () => {
  return instanceWithAuth.get('/userCommentService/comments');
}

export const insertComment = async (CommentInsertData: CommentData) => {
  return instanceWithAuth.post('/userCommentService/comments', CommentInsertData);
};

export const updateComment = async (data: CommentData) => {
  return instanceWithAuth.post('/userCommentService/comments/update', data);
};

export const deleteComment = async (id: number) => {
  return instanceWithAuth.post(`/userCommentService/comments/delete/${id}`);
};

export const logout = () => {
  return instanceWithAuth.post('/authService/auth/logout');
};

export const report = async (report: reportData)=> {
  return instanceWithAuth.post('/userReportService/reports', report);
}

export const getMyReport = async () => {
  return instanceWithAuth.get('/userReportService/reports');
}

export const cancleReport = async (reportId: number) : Promise<string> => {
  return instanceWithAuth.post(`/userReportService/reports/revoke/${reportId}`);
}
