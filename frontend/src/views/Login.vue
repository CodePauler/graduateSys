<template>
  <div class="login-container">
    <el-card class="box-card">
      <h2>登录</h2>
      <el-form :model="form" :rules="rules" ref="loginForm">
        <el-form-item label="用户名" prop="username" :label-width="'80px'">
          <el-input v-model="form.username"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password" :label-width="'80px'">
          <el-input v-model="form.password" type="password"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="login">登录</el-button>
          <el-button @click="$router.push('/register')">注册</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script setup>
import { reactive, ref } from 'vue';
import { useRouter } from 'vue-router';
import { ElMessage } from 'element-plus';
import { loginApi } from '@/api/login';

const router = useRouter();
const form = ref({
  username: '',
  password: ''
});
const rules = {
  username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  password: [{ required: true, message: '请输入密码', trigger: 'blur' }]
};
const loginForm = ref(null);

const login = async () => {
  loginForm.value.validate(async (valid) => {
    if (valid) {
      try {
        const res = await loginApi(form.value);
        console.log('登录请求结果:', res);
        if (res.code === 1) {
          // 登录成功，保存 token
          console.log('登录成功:', res.data);
          ElMessage.success('登录成功');
          localStorage.setItem('token', res.data.token);
          localStorage.setItem('role', res.data.role);
          // 你可以根据角色跳转不同页面
          router.push('/');
        } else {
          ElMessage.error(res.msg);
        }
      } catch (e) {
        ElMessage.error('登录失败，请稍后再试');
      }
    }
  });
};
</script>

<style scoped>
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
}

.box-card {
  width: 500px;
  height: 300px;
}
</style>
