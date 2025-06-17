<template>
  <div class="header-row">
    <div class="headerContent">个人中心</div>
    <el-button type="primary" size="small" class="edit-btn" @click="openEditDialog">编辑</el-button>
  </div>
  <div class="profile-container">
    <div class="profile-info">
      <div class="info-row">
        <div class="info-item">
          <span class="label">用户名：</span>
          <span>{{ profile.username }}</span>
        </div>
        <div class="info-item">
          <span class="label">用户角色：</span>
          <span>{{ profile.role }}</span>
        </div>
      </div>
      <div class="info-row">
        <div class="info-item">
          <span class="label">姓名：</span>
          <span>{{ profile.name }}</span>
        </div>
        <div class="info-item">
          <span class="label">性别：</span>
          <span>{{ profile.gender }}</span>
        </div>
      </div>
      <div class="info-row">
        <div class="info-item">
          <span class="label">邮箱：</span>
          <span>{{ profile.email }}</span>
        </div>
        <div class="info-item">
          <span class="label">电话：</span>
          <span>{{ profile.phone }}</span>
        </div>
      </div>
    </div>
    <!-- student专属信息 -->
    <div v-if="role === 'student'" class="extra-info">
      <div class="info-row">
        <div class="info-item">
          <span class="label">学号：</span>
          <span>{{ studentInfo.studentId }}</span>
        </div>
        <div class="info-item">
          <span class="label">专业：</span>
          <span>{{ studentInfo.major }}</span>
        </div>
        <div class="info-item">
          <span class="label">毕业年份：</span>
          <span>{{ studentInfo.graduationYear }}</span>
        </div>
        <div class="info-item">
          <span class="label">就业状态：</span>
          <span>{{ studentInfo.employmentStatus }}</span>
        </div>
      </div>
    </div>
    <!-- company专属信息 -->
    <div v-if="role === 'company'" class="extra-info">
      <div class="info-row">
        <div class="info-item">
          <span class="label">公司名称：</span>
          <span>{{ companyInfo.companyName }}</span>
        </div>
        <div class="info-item">
          <span class="label">公司简介：</span>
          <span>{{ companyInfo.companyIntro }}</span>
        </div>
      </div>
    </div>
    <EditDialog v-model="editDialogVisible" :title="'编辑个人信息'" :model="editForm" :fields="editFields"
      @submit="saveEdit" />
  </div>
</template>

<script setup>
import { onMounted, reactive, ref } from 'vue'
import EditDialog from '@/components/EditDialog.vue'
import { queryUserByIdApi, updateUserApi } from '@/api/public/user'
import { ElMessage } from 'element-plus'
import { queryStudentByUserIdApi } from '@/api/public/students'
import { queryCompanyProfileApi } from '@/api/public/company'
const role = localStorage.getItem('role')
const userId = localStorage.getItem('userId')
const profile = reactive({
  userId: userId,
  username: '',
  role: '',
  name: '',
  gender: '',
  email: '',
  phone: '',
})
// student专属信息
const studentInfo = reactive({
  studentId: '1',
  major: '',
  graduationYear: '',
  employmentStatus: '',
})

// company专属信息
const companyInfo = reactive({
  companyName: '',
  companyIntro: '',
})

const editDialogVisible = ref(false)
const editForm = reactive({
  userId: '',
  username: '',
  email: '',
  phone: '',
  companyName: '',
  companyIntro: '',
})
const editFields = [
  { label: '用户名', prop: 'username', component: 'el-input', props: { autocomplete: 'off' } },
  { label: '邮箱', prop: 'email', component: 'el-input', props: { autocomplete: 'off' } },
  { label: '电话', prop: 'phone', component: 'el-input', props: { autocomplete: 'off' } }
]
if (role === 'company') {
  editFields.push(
    { label: '公司名称', prop: 'companyName', component: 'el-input', props: {} },
    { label: '公司简介', prop: 'companyIntro', component: 'el-input', props: { type: 'textarea' } }
  )
}
function openEditDialog() {
  editForm.userId = userId
  editForm.username = profile.username
  editForm.email = profile.email
  editForm.phone = profile.phone
  editForm.companyName = companyInfo.companyName
  editForm.companyIntro = companyInfo.companyIntro
  editDialogVisible.value = true
}
const saveEdit = async () => {
  const res = await updateUserApi(editForm)
  if (res.code === 1) {
    ElMessage.success('个人信息更新成功')
    search()
  } else {
    ElMessage.error('个人信息更新失败: ' + res.msg)
  }
  editDialogVisible.value = false
}



const search = async () => {
  let res
  res = await queryUserByIdApi(userId)
  if (res.code === 1) {
    console.log('获取用户信息成功:', res.data)
    profile.username = res.data.username
    profile.name = res.data.name
    profile.gender = res.data.gender
    profile.role = res.data.role
    profile.email = res.data.email
    profile.phone = res.data.phone
  } else {
    console.error('获取用户信息失败:', res.msg)
    ElMessage.error('获取用户信息失败: ' + res.msg)
  }
  if (role === 'student') {
    res = await queryStudentByUserIdApi(userId)
    if (res.code === 1) {
      console.log('获取学生信息成功:', res.data)
      studentInfo.studentId = res.data.studentId
      studentInfo.major = res.data.major
      studentInfo.graduationYear = res.data.graduationYear
      studentInfo.employmentStatus = res.data.employmentStatus
    } else {
      console.error('获取学生信息失败:', res.msg)
      ElMessage.error('获取学生信息失败: ' + res.msg)
    }
  }
  if (role === 'company') {
    res = await queryCompanyProfileApi(userId)
    if (res.code === 1) {
      console.log('获取公司信息成功:', res.data)
      companyInfo.companyName = res.data.companyName
      companyInfo.companyIntro = res.data.companyIntro
    } else {
      console.error('获取公司信息失败:', res.msg)
      ElMessage.error('获取公司信息失败: ' + res.msg)
    }
  }
}
onMounted(() => {
  search()
})
</script>

<style scoped>
.header-row {
  display: flex;
  align-items: center;
  justify-content: space-between;
  max-width: 600px;
  margin: 40px auto 0 auto;
}

.headerContent {
  font-size: 22px;
  font-weight: bold;
  margin-bottom: 32px;
  text-align: left;
}

.edit-btn {
  margin-bottom: 24px;
}

.profile-container {
  max-width: 600px;
  margin: 0 auto 40px auto;
  padding: 32px 24px;
  background: #fff;
  border-radius: 12px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.08);
}

.profile-title {
  display: none;
}

.profile-info {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.info-row {
  display: flex;
  justify-content: space-between;
  gap: 24px;
}

.info-item {
  flex: 1;
  display: flex;
  align-items: center;
  font-size: 16px;
}

.label {
  color: #888;
  min-width: 80px;
}

.extra-info {
  margin-top: 32px;
}
</style>
