import {instance} from '@/api/interceptor'
import type { AdminData, LoginData } from '@/type/AdminData'
import type { BoardDetailData } from '@/type/BoardData'
import type { CommentDataInBoard } from '@/type/CommentData'

// 이메일 체크
export const checkEmail = async (adminEmail: string) => {
  return instance.get('/adminService/noAuth/admins/check-email', {
    params: {
      email: adminEmail
    }
  })
}
// 닉네임 체크
export const checkNickname = async (adminNickname: string) => {
  return instance.get('/adminService/noAuth/admins/check-nickname', {
    params: {
      nickname: adminNickname
    }
  })
}

// 회원 가입
export const register = async (registerData: AdminData) => {
  return instance.post('/adminService/noAuth/admins/register', registerData)
}

// 로그인
export const login = async (loginData: LoginData) => {
  return instance.post('/authService/admin/login', loginData)
}

// 게시판 한개 가져오기
export const getBoardById = async (boardId: number) : Promise<BoardDetailData> => {
  return instance.get(`/adminBoardService/boards/${boardId}`);
}

// 게시판의 댓글 가져오기
export const getCommentById = async (boardId: number) : Promise<CommentDataInBoard> => {
  return instance.get(`/adminCommentService/comments/boards/${boardId}`);
}
