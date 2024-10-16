import { defineStore } from 'pinia'
import { ref } from 'vue'

export const ModalStore = defineStore('modal', () => {
  const modalState = ref<boolean>(false);

  const openModal = () => {
    modalState.value = true;
  }

  const closeModal = () => {
    modalState.value = false;
  }

  return {modalState, openModal, closeModal}
})
