import {instance} from '@/api/interceptor'
import type { ReceiveData } from '@/type/AdminData'
import type { SearchData } from '@/type/SearchData'

export const callApi = async (url : string, options? : {
  params? : any,
  method? : any,
  body? : any,
}) : Promise<any> => {
  try {
    if(!options || !options.method || options.method === 'GET') {
      return instance.get(url, {
        params: options?.params
      })
    }

    return instance.post(url, options.body);
  } catch (error) {
    console.log(error);
  }

}

// 관리자 정보 확인
export const getAdminData = async () : Promise<ReceiveData> => {
  return instance.get('/adminService/admins');
}

// 사용자 정보 전체 가져오기
export const getUserDataAll = async () : Promise<ReceiveData> => {
  return instance.get('/adminService/admins/users');
}

// 사용자 정보 가져오기
export const getUserDataDetail = async (memberId: number) : Promise<ReceiveData> => {
  return instance.get(`/adminService/admins/users/${memberId}`);
}

// 사용자 신고 누적 횟수 가져오기
export const getUserReportCount = async (memberId: number) : Promise<ReceiveData> => {
  return instance.get(`/adminReportService/reports/count/${memberId}`)
}

// 사용자 게시글 가져오기
export const getUserBoards = async (search: SearchData) : Promise<ReceiveData> => {
  return instance.get('/adminBoardService/boards', {
    params: {
      searchType: search.searchType,
      searchText: search.searchText,
      startDate: search.startDate,
      endDate: search.endDate,
      memberId: search.memberId,
      pageNumber: search.pageNumber
    }
  });
}

// 사용자 닉네임을 통한 검색
export const getUserByNickname = async (memberNickname: string) : Promise<ReceiveData> => {
  return instance.get('/adminService/admins/users/nickname', {
    params: {
     memberNickname: memberNickname,
    }
  })
}

// 사용자 댓글 가져오기
export const getUserComments = async (search: SearchData) : Promise<ReceiveData> => {
  return instance.get('/adminCommentService/comments', {
    params: {
      startDate: search.startDate,
      endDate: search.endDate,
      memberId: search.memberId,
      pageNumber: search.pageNumber
    }
  });
}

// 사용자 신고 가져오기
export const getUserReports = async (memberId: number) : Promise<ReceiveData> => {
  return instance.get(`/adminReportService/reports/${memberId}`)
}

// 사용자 신고 처리하기

// 사용자 신고 취소하기
