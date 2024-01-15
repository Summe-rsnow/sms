<template>
  <el-form
      :model="formModel"
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
          <el-image class="vcodeimg" fit="fill" :src="vcodeImageUrl" @load="loadVcodeImage"></el-image>
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
import {reactive, ref} from 'vue'
import axios from 'axios'

const formModel = reactive({
  account: '',
  password: '',
  confirmPassword: '',
  verificationCode: '',
  rememberMe: false
})

const login = () => {
  // 在这里执行登录逻辑，可以通过 formModel 中的各个字段获取表单数据
  console.log('账号:', formModel.account)
  console.log('密码:', formModel.password)
  console.log('确认密码:', formModel.confirmPassword)
  console.log('验证码:', formModel.verificationCode)
  console.log('记住我:', formModel.rememberMe)
}

// 假设你可以通过接口获取验证码图片的URL
const vcodeImageUrl = ref()

const loadVcodeImage = () => {
  // 获取验证码图片
  axios.post('127.0.0.1/user/vcode', {
    responseType: 'blob' // 设置响应数据类型为 blob
  })
      .then(response => {
        // 创建一个 URL 对象来生成验证码图片的URL
        const vcodeImageUrl = URL.createObjectURL(response.data)

        // 在这里可以使用获取到的验证码图片URL进行后续操作
        console.log('验证码图片URL:', vcodeImageUrl)

        // 获取响应体的数据（根据具体情况进行处理）
        // 由于验证码图片是通过流直接写回的，响应体可能不包含其他数据，需要根据实际情况进行处理
        const responseData = response.data

        // 在这里可以使用响应体的数据进行后续操作
        console.log('响应体数据:', responseData)
      })
      .catch(error => {
        console.error('获取验证码失败:', error)
      })
}
</script>

<style scoped>
.vcodeimg {
  width: 100%;
}

<
/
script >

<
style scoped >
.vcodeimg {
  width: 100%;
}
</style>