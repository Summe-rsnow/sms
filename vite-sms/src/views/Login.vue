<template>
  <el-form
      :model="formModel"
      :rules="rules"
      status-icon
      label-width="120px"
      class="demo-ruleForm">
    <el-form-item label="账号" prop="account">
      <el-input
          v-model="formModel.account"
          type="text"
          placeholder="请输入账号"/>
    </el-form-item>
    <el-form-item label="密码" prop="password">
      <el-input v-model="formModel.password"
                type="password"
                placeholder="请输入密码"
                show-password/>
    </el-form-item>
    <el-form-item label="确认密码" prop="confirmPassword">
      <el-input
          v-model="formModel.confirmPassword"
          type="password"
          placeholder="请输入确认密码"
          show-password/>
    </el-form-item>
    <el-form-item label="验证码" prop="verificationCode">
      <el-row :gutter="14">
        <el-col :span="15">
          <el-input v-model="formModel.verificationCode" type="text" placeholder="请按图片输入验证码"/>
        </el-col>
        <el-col :span="9">
          <el-image class="vcodeimg" fit="fill" :src="vcodeSrc" @click="refreshVerificationCode"></el-image>
        </el-col>
      </el-row>
    </el-form-item>
    <el-form-item>
      <el-checkbox v-model="formModel.rememberMe">记住我</el-checkbox>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="login">登录</el-button>
    </el-form-item>
  </el-form>
</template>

<script setup>
import {onBeforeMount, onMounted, reactive, ref} from 'vue'
import {get, post} from '../net/index.js'
import {useUserStore} from '../stores/index.js'
import router from '../router/index.js'
import {v4} from 'uuid'

const userStore = useUserStore()
const vcodeSrc = ref('')

const rules = reactive({
  account: [{required: true, message: '账号不能为空'}],
  password: [{required: true, message: '密码不能为空'}],
  confirmPassword: [{required: true, message: '确认密码不能为空'}],
  verificationCode: [{required: true, message: '验证码不能为空'}]
})

onMounted(() => {
  refreshVerificationCode()
})

onBeforeMount(() => {
  rememberMe()
})

const formModel = reactive({
  account: '',
  password: '',
  confirmPassword: '',
  verificationCode: '',
  imgId: v4(),
  rememberMe: true
})

const login = () => {
  post('/user/login', formModel, (data, msg) => {
    alert(msg)
  })
}

const refreshVerificationCode = () => {
  vcodeSrc.value = '/api/user/vcode/' + formModel.imgId + `${Date.now()}`
}

const rememberMe = () => {
  const cookies = document.cookie.split(';')
  let rememberMeValue = ''

  for (let i = 0; i < cookies.length; i++) {
    const cookie = cookies[i].trim()
    if (cookie.startsWith('rememberMe=')) {
      rememberMeValue = cookie.substring('rememberMe='.length)
      break
    }
  }

  if (rememberMeValue) {
    userStore.token = rememberMeValue;
    get('/user/self/info', (data) => {
      userStore.user = data
      router.push('/home')
    })
  }
}
</script>


<style scoped>
.vcodeimg {
  width: 100%;
}
</style>