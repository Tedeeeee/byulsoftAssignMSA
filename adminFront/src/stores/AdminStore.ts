import { defineStore } from 'pinia'
import { computed, ref } from 'vue'
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
  const loginCheck = computed(() => {
    return !!admin.value.adminEmail;
  })

  const adminDataSetting = (adminData: AdminData) => {
    admin.value.adminId = adminData.adminId
    admin.value.adminEmail = adminData.adminEmail
    admin.value.adminName = adminData.adminName
    admin.value.adminNickname = adminData.adminNickname
    admin.value.adminPhoneNumber = adminData.adminPhoneNumber
  }

  const adminDataReset = () => {
    admin.value.adminId = 0;
    admin.value.adminEmail = '';
    admin.value.adminName = '';
    admin.value.adminNickname = '';
    admin.value.adminPhoneNumber = '';
  }

  return { admin, loginCheck, adminDataSetting, adminDataReset }
})
