<template>
  <img alt="Vue logo" src="../assets/STPAM.png" style="margin-left:45%;margin-top: 3%"/>
  <div
      class="inline-flex"
      style="margin:auto;width: 50%;height: 460px;margin-top: 3%;"
      :style="{
          boxShadow: `var(${'--el-box-shadow-dark'})`,
        }"
  >
    <br>
    <h1 style="margin-left: 45%;margin-top: 50px">注册</h1><br>
  <el-form
      ref="ruleFormRef"
      :model="ruleForm"
      status-icon
      :rules="rules"
      label-width="120px"
      class="demo-ruleForm"
      style="width: 50%;margin-left: 20%"
  >
    <el-form-item label="用户名" prop="username" >
      <el-input v-model="ruleForm.username" placeholder="请输入用户名" clearable/>
    </el-form-item>
    <el-form-item label="密码" prop="password">
      <el-input v-model="ruleForm.password" type="password" placeholder="请输入密码" clearable/>
    </el-form-item>
    <el-form-item label="确认密码" prop="checkPass">
      <el-input v-model="ruleForm.checkPass" type="password" placeholder="请再次输入密码" clearable/>
    </el-form-item>
      <el-form-item prop="mail" label="邮箱">
        <el-input v-model="ruleForm.mail" placeholder="请输入邮箱地址" clearable/>
      </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="submitForm(ruleFormRef)">注册</el-button>
      <el-button @click="resetForm(ruleFormRef)">重置</el-button>
    </el-form-item>
  </el-form>
  </div>
</template>

<script lang="ts" setup>
import { reactive, ref } from 'vue'
import type { FormInstance } from 'element-plus'
const ruleFormRef = ref<FormInstance>()

const validatePass = (rule: any, value: any, callback: any) => {
  if (value === '') {
    callback(new Error('请输入密码'))
  } else {
    if (ruleForm.checkPass !== '') {
      if (!ruleFormRef.value) return
      ruleFormRef.value.validateField('checkPass', () => null)
    }
    callback()
  }
}
const validatePass2 = (rule: any, value: any, callback: any) => {
  if (value === '') {
    callback(new Error('请再次输入密码'))
  } else if (value !== ruleForm.password) {
    callback(new Error("两次密码不匹配!"))
  } else {
    callback()
  }
}

const ruleForm = reactive({
  username:'',
  password: '',
  checkPass: '',
  mail: '',
})

const rules = reactive({
  username:[{ required:true,trigger: 'blur' }],
  password: [{ required:true,validator: validatePass, trigger: 'blur' }],
  checkPass: [{ required:true,validator: validatePass2, trigger: 'blur' }],
  mail: [{ required:true,trigger: 'blur' }],
})

import {useRouter} from 'vue-router'
import {Register} from "../utils/api";
const router=useRouter();
const submitForm = (formEl: FormInstance | undefined) => {
  if (!formEl) return
  formEl.validate((valid) => {
    if (valid) {
      Register(ruleForm).then(res=>{
        if(res){
          //跳转到首页

          router.replace('/login');
        }
      })
    } else {
      console.log('失败!')
      return false
    }
  })
}

const resetForm = (formEl: FormInstance | undefined) => {
  if (!formEl) return
  formEl.resetFields()
}
</script>
