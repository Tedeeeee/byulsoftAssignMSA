<template>
  <q-page class="sign-up-page">
    <q-card class="sign-up-card" flat bordered>
      <q-card-section>
        <div class="title">회원가입</div>
        <q-form @submit="handleSubmit">
          <div class="input-btn-container">
            <q-input
              ref="emailInput"
              filled
              maxlength="50"
              v-model="registerData.email"
              label="이메일"
              type="email"
              outlined
              lazy-rules
              :rules="emailRules"
              class="input-field"
              autocomplete="off"
            />
            <q-btn label="중복체크" @click="emailCheckDuplicate" color="primary" class="check-btn" />
          </div>
          <q-input
            filled
            v-model="registerData.password"
            label="비밀번호"
            type="password"
            outlined
            lazy-rules
            :rules="checkPassword"
            class="q-mb-md"
            autocomplete="off"
          />
          <q-input
            filled
            v-model="registerData.confirmPassword"
            label="비밀번호 재확인"
            type="password"
            outlined
            lazy-rules
            :rules="checkConfirmPassword"
            class="q-mb-md"
            autocomplete="off"
          />
          <q-input
            filled
            maxlength="18"
            v-model="registerData.name"
            label="이름"
            lazy-rules
            :rules="nameRules"
            class="q-mb-md"
          />
          <div class="input-btn-container">
            <q-input
              ref="nicknameInput"
              filled
              maxlength="10"
              v-model="registerData.nickname"
              label="닉네임"
              outlined
              lazy-rules
              :rules="nicknameRules"
              class="input-field"
              autocomplete="off"
            />
            <q-btn label="중복체크" @click="nicknameCheckDuplicate" color="primary" class="check-btn" />
          </div>
          <q-input
            filled
            v-model="registerData.phoneNumber"
            label="숫자만 입력해주세요"
            type="tel"
            mask="###-####-####"
            outlined
            lazy-rules
            :rules="phoneNumberRules"
            class="q-mb-md"
            autocomplete="off"
          />
          <q-btn label="회원가입" type="submit" color="primary" class="full-width" />
        </q-form>
      </q-card-section>
    </q-card>
  </q-page>
</template>

<script setup lang="ts">
import type { UserData, UserRegisterData } from '@/type/UserData'
import { useRouter } from 'vue-router'
import { computed, ref } from 'vue'
import { checkEmail, checkNickname, register } from '@/api/NoAuthRequiredApi'
import { useNotifications } from '@/common/CommonNotify'

const {positiveNotify, negativeNotify} = useNotifications();
const emailInput = ref<string>('')
const nicknameInput = ref<string>('')
const router = useRouter()
const registerData = ref<UserRegisterData>({
  email: '',
  emailCheck: false,
  password: '',
  confirmPassword: '',
  name: '',
  nickname: '',
  nicknameCheck: false,
  phoneNumber: ''
})

const signUpData = ref<UserRegisterData>({
  memberEmail: '',
  memberPassword: '',
  memberName: '',
  memberNickname: '',
  memberPhoneNumber: ''
})

const emailRules = [
  (val) =>
    /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/.test(val) || '올바른 이메일을 입력해주세요',
  (val) => (registerData.value.emailCheck || !val ? true : '중복체크를 완료해주세요')
]

const nicknameRules = [
  (val) => (val && val.length >= 3) || '3글자 이상의 닉네임을 입력해주세요',
  (val) => /^[a-zA-Z0-9가-힝]+$/.test(val) || '닉네임에는 특수문자와 공백을 사용할 수 없습니다.',
  (val) => (registerData.value.nicknameCheck || !val ? true : '중복체크를 완료해주세요')
]

const nameRules = [
  (val) => (val && val.length > 0) || '이름을 입력해주세요',
  (val) => /^[a-zA-Z가-힝]+$/.test(val) || '이름에는 특수문자와 공백을 사용할 수 없습니다.'
]

const checkPassword = [
  (val) => (val && val.length > 0) || '비밀번호를 입력해주세요',
  (val) =>
    /^(?=.*[A-Z])(?=.*[!@#$%^*+=-]).{6,}$/.test(val) ||
    '대문자와 지정된 특수문자를 최소 하나씩 포함하고, 6글자 이상이어야 합니다'
]

const checkConfirmPassword = [
  (val) => (val && val.length > 0) || '비밀번호를 입력해주세요',
  (val) => registerData.value.password === val || '비밀번호가 일치하지 않습니다'
]
const phoneNumberRules = [(val) => (val && val.length > 12) || '올바른 전화번호를 입력해주세요']

const isEmailValid = computed(() =>
  /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/.test(registerData.value.email)
)
const isNicknameValid = computed(() => registerData.value.nickname.length >= 3)

const emailCheckDuplicate = async () => {
  if (!isEmailValid.value) {
    negativeNotify('올바른 이메일을 입력해주세요')
    return
  }

  try {
    const response = await checkEmail(registerData.value.email)
    console.log(response)
    positiveNotify(response.data.message)
    registerData.value.emailCheck = true
    emailInput.value.resetValidation()
  } catch (error) {
    console.log(error)
    negativeNotify('실패')
    registerData.value.emailCheck = false
  }
}

const nicknameCheckDuplicate = async () => {
  if (!isNicknameValid.value) {
    negativeNotify('올바른 닉네임을 입력해주세요')
    return
  }

  try {
    const response = await checkNickname(registerData.value.nickname)
    console.log(response)
    positiveNotify(response.data.message)
    registerData.value.nicknameCheck = true
    nicknameInput.value.resetValidation()
  } catch (error) {
    console.log(error)
    negativeNotify('실패')
    registerData.value.nicknameCheck = false
  }
}

const transformSignupData = (registerData: UserRegisterData): UserData => {
  signUpData.value.memberEmail = registerData.email
  signUpData.value.memberPassword = registerData.password
  signUpData.value.memberName = registerData.name
  signUpData.value.memberNickname = registerData.nickname
  signUpData.value.memberPhoneNumber = registerData.phoneNumber.replace(/-/g, '')
}

const handleSubmit = async () => {
  transformSignupData(registerData.value);

  try {
    const response = await register(signUpData.value)
    console.log(response)
    positiveNotify(response.data.message)
    await router.push("/");
  } catch (error) {
    console.log(error)
    registerData.value.email = '';
    registerData.value.emailCheck = false;
    //emailInput.value.focus();
    registerData.value.nickname = '';
    registerData.value.nicknameCheck = false;
    //nicknameInput.value.focus()
    negativeNotify('실패');
  }
}
</script>

<style scoped>
.input-btn-container {
  display: flex;
  align-items: center;
  gap: 18px;
  margin-bottom: 15px;
}

.input-field {
  flex-grow: 1;
}

.check-btn {
  font-size: 14px;
  padding: 4px 8px;
  height: 32px;
  min-width: 120px;
}
.sign-up-page {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
}

.sign-up-card {
  width: 100%;
  max-width: 400px;
}

.title {
  font-size: 24px;
  font-weight: bold;
  text-align: center;
  margin-bottom: 20px;
}
</style>