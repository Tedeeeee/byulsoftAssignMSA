import { instance } from '@/api/Interceptors'
import type { SearchBoard } from '@/type/SearchData'
import type { BoardData } from '@/type/BoardData'
import type { LoginData, UserData } from '@/type/UserData'

export const getBoardList = async (searchBoard: SearchBoard) : Promise<BoardData[]> => {
  return instance.get('/userBoardService/boards', {
    params: {
      searchType: searchBoard.searchType,
      searchText: searchBoard.searchText,
      sortOrder: searchBoard.sortOrder,
      sortType: searchBoard.sortType,
      pageNumber: searchBoard.pageNumber,
    }
  });
}

export const register = (userData: UserData) : Promise<UserData> => {
  return instance.post('/userService/users/register', userData)
}

export const checkEmail = async (email: string): Promise<boolean> => {
  return instance.get('/userService/users/check-email', {
    params: {
      email: email,
    },
  });
};

export const checkNickname = async (nickname: string): Promise<boolean> => {
  return instance.get('/userService/users/check-nickname', {
    params: {
      nickname: nickname,
    },
  });
};

export const login = async (loginData: LoginData) => {
  return instance.post('/authService/login', loginData);
}

export const getBoardById = async (boardId: number) : Promise<BoardData> => {
  return instance.get(`/userBoardService/boards/${boardId}`);
}

export const findCommentsByBoardId = async (boardId: number) => {
  return instance.get(`/userCommentService/comments/posts/${boardId}`);
};