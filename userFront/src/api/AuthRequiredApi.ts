import { instance } from '@/api/Interceptors'
import type { BoardSaveData, BoardUpdateData } from '@/type/BoardData'
import type { CommentData } from '@/type/CommentData'
import type { UserData } from '@/type/UserData'
import type { reportData } from '@/type/ReportData'

export const getUserData = async () : Promise<UserData> => {
  return instance.get('/userService/users');
}

export const changeNickname = async (nickname: string) => {
  return instance.post('/userService/users/changeNickname', {memberNickname: nickname})
}

export const checkOriginPassword = async (password: string) => {
  return instance.post('/userService/users/checkOriginPassword', {memberPassword: password})
}

export const setNewPassword = async (password: string) => {
  return instance.post('/userService/users/changePassword', {memberPassword: password})
}

export const getMyBoard = async () => {
  return instance.get('/userBoardService/boards');
}

export const insertBoard = async (boardInsertData: BoardSaveData): Promise<void> => {
  return instance.post('/userBoardService/boards', boardInsertData);
}

export const updateBoard = async (boardUpdateData: BoardUpdateData): Promise<void> => {
  return instance.post('/userBoardService/boards/update', boardUpdateData);
}

export const deleteBoard = async (id: number) => {
  return instance.post(`/userBoardService/boards/delete/${id}`);
};

export const getMyComment = async () => {
  return instance.get('/userCommentService/comments');
}

export const insertComment = async (CommentInsertData: CommentData) => {
  return instance.post('/userCommentService/comments', CommentInsertData);
};

export const updateComment = async (data: CommentData) => {
  return instance.post('/userCommentService/comments/update', data);
};

export const deleteComment = async (id: number) => {
  return instance.post(`/userCommentService/comments/delete/${id}`);
};

export const logout = () => {
  return instance.post('/authService/auth/logout');
};

export const report = async (report: reportData)=> {
  return instance.post('/userReportService/reports', report);
}

export const getMyReport = async () => {
  return instance.get('/userReportService/reports');
}

export const cancleReport = async (reportId: number) : Promise<string> => {
  return instance.post(`/userReportService/reports/revoke/${reportId}`);
}
