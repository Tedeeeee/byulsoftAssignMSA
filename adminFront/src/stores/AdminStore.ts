import { defineStore } from 'pinia'
import { ref } from 'vue'
import type { AdminData } from '@/type/AdminData'

export const accessToken = 'accessToken';

export const AdminStore = defineStore('admin', () => {
  const admin = ref<AdminData>({
    adminId: 0,
    adminEmail: '',
    adminName: '',
    adminNickname: '',
    adminPhoneNumber: ''
  })

  const adminDataSetting = (adminData: AdminData) => {
    admin.value.adminId = adminData.adminId
    admin.value.adminEmail = adminData.adminEmail
    admin.value.adminName = adminData.adminName
    admin.value.adminNickname = adminData.adminNickname
    admin.value.adminPhoneNumber = adminData.adminPhoneNumber
  }

  return { admin, adminDataSetting }
})
