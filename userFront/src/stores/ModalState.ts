import { defineStore } from 'pinia'
import { computed, ref } from 'vue'

export const ModalStore = defineStore("modal", () => {
  const modal = ref<boolean>(false);
  const modalState = computed(() => modal.value);

  const openModal = () => {
    modal.value = true;
  }

  const closeModal = () => {
    modal.value = false;
  }

  return { modalState, openModal, closeModal }
})