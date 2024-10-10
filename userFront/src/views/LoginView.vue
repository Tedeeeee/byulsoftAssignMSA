<template>
  <AlertModalComponent :message="modalMessage" />
  <q-page class="login-page">
    <q-card class="login-card" flat bordered>
      <q-card-section class="q-pt-none">
        <div class="title">로그인</div>
        <q-form @submit="handleSubmit">
          <q-input
            v-model="loginData.memberEmail"
            label="Email"
            type="email"
            outlined
            class="q-mb-md"
            autocomplete="off"
          />
          <q-input
            v-model="loginData.memberPassword"
            label="Password"
            type="password"
            outlined
            class="q-mb-md"
            autocomplete="off"
          />
          <q-btn label="Login" type="submit" color="primary" class="full-width" />
          <div class="text-center q-mt-md">
            <q-btn flat label="혹시 회원이 아니신가요?" to="/signUp" class="custom-link" />
          </div>
        </q-form>
      </q-card-section>
    </q-card>
  </q-page>
</template>

<script setup lang="ts">
import type { LoginData } from '@/type/UserData'
import { ref } from 'vue'
import { login } from '@/api/NoAuthRequiredApi'
import AlertModalComponent from '@/components/modal/AlertModalComponent.vue'
import { ModalStore } from '@/stores/ModalState'

const modalMessage = ref<string>('')
const loginData = ref<LoginData>({
  memberEmail: '',
  memberPassword: ''
})

const handleSubmit = async () => {
  try {
    const response = await login(loginData.value)
    console.log(response)
  } catch (error) {
    modalMessage.value = '아이디 혹은 비밀번호가 틀렸습니다'
    ModalStore().openModal();
    console.log(error)
  }
}
</script>

<style scoped>
.login-page {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
}

.login-card {
  width: 100%;
  max-width: 400px;
}

.title {
  font-size: 24px;
  font-weight: bold;
  text-align: center;
  margin-bottom: 20px;
  margin-top: 20px;
}

.custom-link {
  text-decoration: underline;
  color: red;
  cursor: pointer;
}
</style>