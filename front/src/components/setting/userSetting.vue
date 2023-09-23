<template>
  <div style="text-align: center;width: 60%;margin-left: 20%">
    <h2>用户信息管理</h2>
    <br/><br/>
    <h3>用户信息修改</h3>
    <br/>
    <el-form
        ref="ruleFormRef"
        :model="ruleForm"
        :rules="rules"
        label-width="120px"
        class="demo-ruleForm"
        :size="formSize"
    >
      <el-form-item label="用户名" prop="username">
        <el-input v-model="ruleForm.username" disabled/>
      </el-form-item>
      <el-form-item label="新密码" prop="password">
        <el-input v-model="ruleForm.password" type="password" autocomplete="off" placeholder="请输入新密码" clearable
                  show-password/>
      </el-form-item>
      <el-form-item label="确认新密码" prop="checkPass">
        <el-input
            v-model="ruleForm.checkPass"
            type="password"
            autocomplete="off"
            clearable
            placeholder="请再次输入新密码"
            show-password
        />
      </el-form-item>
      <el-form-item
          prop="mail"
          label="邮箱"
      >
        <el-input v-model="ruleForm.mail" placeholder="请输入邮箱地址" clearable/>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submitForm(ruleFormRef)"
        >确定
        </el-button
        >
        <el-button @click="resetForm(ruleFormRef)">重置</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script lang="ts" setup>
import {ref, reactive} from 'vue'
import type {FormInstance, FormRules} from 'element-plus'


//用户信息
const formSize = ref('default')
const ruleFormRef = ref<FormInstance>()
const ruleForm = reactive({
  username: localStorage.getItem("name"),
  password: '',
  checkPass: '',
  mail: '',
})


const validatePass = (rule: any, value: any, callback: any) => {
  if (value === '') {
    callback(new Error('密码不得为空！'))
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
    callback(new Error('请再次输入新密码'))
  } else if (value !== ruleForm.password) {
    callback(new Error("两次密码不匹配!"))
  } else {
    callback()
  }
}


const rules = reactive<FormRules>({
  username: [
    {required: true, message: '请输入用户名', trigger: 'blur'},
    {min: 3, max: 9, message: '长度应在3-9之间', trigger: 'blur'},
  ],
  password: [
    {
      required: true,
      message: '请输入密码！',
      trigger: 'blur',
    },
    {
      min: 3,
      max: 18,
      message: '长度需要在3-18位之间！',
      trigger: 'blur'
    },
    {validator: validatePass, trigger: 'blur'},
  ],
  checkPass: [{required: true, validator: validatePass2, trigger: 'blur'}],
  mail: [
    {
      required: true,
      message: '请输入新邮箱地址',
      trigger: 'blur',
    },
  ]
})


import {useRouter} from 'vue-router'

const router = useRouter();
import {UserSetting} from "../../utils/api";

const submitForm = (formEl: FormInstance | undefined) => {
  if (!formEl) return
  formEl.validate((valid) => {
    if (valid) {
      UserSetting(ruleForm).then(res => {
        if (res) {
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

<style scoped>

</style>