<template>
  <div class="register-container">
    <el-card class="box-card">
      <h2>注册</h2>
      <el-form :model="form" :rules="rules" ref="registerForm" label-width="80px">
        <el-form-item label="身份" prop="role">
          <el-select v-model="form.role" placeholder="请选择身份">
            <el-option label="学生" value="student"></el-option>
            <el-option label="企业" value="company"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="用户名" prop="username">
          <el-input v-model="form.username"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="form.password" type="password"></el-input>
        </el-form-item>
        <el-form-item label="姓名" prop="name">
          <el-input v-model="form.name"></el-input>
        </el-form-item>
        <el-form-item label="性别" prop="gender">
          <el-select v-model="form.gender" placeholder="请选择性别">
            <el-option label="男" value="男"></el-option>
            <el-option label="女" value="女"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="form.email"></el-input>
        </el-form-item>
        <el-form-item label="电话" prop="phone">
          <el-input v-model="form.phone"></el-input>
        </el-form-item>

        <!-- 学生 -->
        <template v-if="form.role === 'student'">
          <el-form-item label="学号" prop="studentId">
            <el-input v-model="form.studentId"></el-input>
          </el-form-item>
          <el-form-item label="专业" prop="majorId">
            <el-select v-model="form.majorId" placeholder="请选择专业">
              <el-option label="计算机科学" value="1"></el-option>
              <el-option label="软件工程" value="2"></el-option>
              <el-option label="电子信息" value="3"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="毕业年限" prop="graduationYear">
            <el-select v-model="form.graduationYear" placeholder="请选择毕业年限">
              <el-option label="2024" value="2024"></el-option>
              <el-option label="2025" value="2025"></el-option>
              <el-option label="2026" value="2026"></el-option>
            </el-select>
          </el-form-item>
        </template>
        <!-- 企业专属字段 -->
        <template v-if="form.role === 'company'">
          <el-form-item label="企业名称" prop="companyName">
            <el-input v-model="form.companyName"></el-input>
          </el-form-item>
          <el-form-item label="企业简介" prop="companyIntro">
            <el-input v-model="form.companyIntro" type="textarea"></el-input>
          </el-form-item>
        </template>

        <el-form-item>
          <el-button type="primary" @click="onSubmit">注册</el-button>
          <el-button @click="$router.push('/login')">返回登录</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
  <SuccessResult v-if="showSuccess" title="注册成功" subTitle="请登录您的账户" buttonText="返回登录" @back="$router.push('/login')" />
</template>

<script setup>
import { reactive, ref } from 'vue';
import { useRouter } from 'vue-router';
import { ElMessage } from 'element-plus';
import { registerApi } from '@/api/login';

const router = useRouter();
const form = reactive({
  username: '',
  password: '',
  name: '',
  gender: '',
  email: '',
  phone: '',
  role: '',
  majorId: '',
  graduationYear: '',
  company_name: '',
  company_intro: ''
});
const rules = {
  username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  password: [{ required: true, message: '请输入密码', trigger: 'blur' }],
  name: [{ required: true, message: '请输入姓名', trigger: 'blur' }],
  role: [{ required: true, message: '请选择身份', trigger: 'blur' }],
  gender: [{ required: true, message: '请选择性别', trigger: 'blur' }],
  // 动态校验
  studentId: [
    { required: true, message: '请输入学号', trigger: 'blur', validator: (_, value) => form.role !== 'student' || !!value }
  ],
  majorId: [
    { required: true, message: '请选择专业', trigger: 'blur', validator: (_, value) => form.role !== 'student' || !!value }
  ],
  graduationYear: [
    { required: true, message: '请选择毕业年限', trigger: 'blur', validator: (_, value) => form.role !== 'student' || !!value }
  ],
  companyName: [
    { required: true, message: '请输入企业名称', trigger: 'blur', validator: (_, value) => form.role !== 'company' || !!value }
  ],
  companyIntro: [
    { required: true, message: '请输入企业简介', trigger: 'blur', validator: (_, value) => form.role !== 'company' || !!value }
  ]
};
const registerForm = ref(null);
const showSuccess = ref(false);
const onSubmit = () => {
  registerForm.value.validate(async (valid) => {
    if (valid) {
      try {
        const res = await registerApi(form);
        if (res.code === 1) {
          ElMessage.success('注册成功');
          showSuccess.value = true;
          router.push('/login');
        } else if (res.msg && res.msg.includes('用户名已存在')) {
          ElMessage.error('用户名重复，请更换用户名');
        } else {
          ElMessage.error(res.data?.msg || res.msg || '注册失败');
        }
      } catch (e) {
        ElMessage.error('网络错误');
      }
    }
  });
};
</script>

<style scoped>
.register-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
}

.box-card {
  width: 500px;
}
</style>