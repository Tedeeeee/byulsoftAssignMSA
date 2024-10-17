import { defineStore } from 'pinia'
import { ref } from 'vue'

export const ModalStore = defineStore('modal', () => {
  const modalState = ref<boolean>(false);
  const reportModalState = ref<boolean>(false);

  const openModal = () => {
    modalState.value = true;
  }

  const closeModal = () => {
    modalState.value = false;
  }

  const openReportModal = () => {
    reportModalState.value = true;
  }

  const closeReportModal = () => {
    reportModalState.value = false;
  }


  return {modalState, reportModalState, openModal, closeModal, openReportModal, closeReportModal}
})
