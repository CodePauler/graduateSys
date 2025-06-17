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
    <!-- admin专属信息 -->
    <div v-if="role === 'admin'" class="extra-info">
      <div class="info-row">
        <div class="info-item">
          <span class="label">学生用户数：</span>
          <span>{{ adminInfo.studentCount }}</span>
        </div>
        <div class="info-item">
          <span class="label">企业用户数：</span>
          <span>{{ adminInfo.companyCount }}</span>
        </div>
      </div>
      <div class="info-row">
        <div class="info-item">
          <span class="label">管理员人数：</span>
          <span>{{ adminInfo.adminCount }}</span>
        </div>
        <div class="info-item">
          <span class="label">用户总数：</span>
          <span>{{ adminInfo.totalCount }}</span>
        </div>
      </div>
    </div>
    <!-- student专属信息 -->
    <div v-if="role === 'student'" class="extra-info">
      <div class="info-row">
        <div class="info-item">
          <span class="label">专业：</span>
          <span>{{ studentInfo.major }}</span>
        </div>
        <div class="info-item">
          <span class="label">毕业年份：</span>
          <span>{{ studentInfo.graduationYear }}</span>
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
          <span class="label">发布岗位总数：</span>
          <span>{{ companyInfo.postedJobs }}</span>
        </div>
      </div>
    </div>
    <EditDialog v-model="editDialogVisible" :title="'编辑个人信息'" :model="editForm" :fields="editFields" @submit="saveEdit" />
  </div>
</template>

<script setup>
import { reactive, ref } from 'vue'
import EditDialog from '@/components/EditDialog.vue'

const role = localStorage.getItem('role') 

const profile = reactive({
  username: 'zhangsan',
  role: role === 'student' ? '学生' : role === 'company' ? '企业' : '管理员',
  name: role === 'company' ? '上海XX科技有限公司' : '张三',
  gender: role === 'company' ? '-' : '男',
  email: 'zhangsan@example.com',
  phone: '13800000000',
})

// admin专属信息
const adminInfo = reactive({
  studentCount: 120,
  companyCount: 15,
  adminCount: 3,
  totalCount: 138,
})
// student专属信息
const studentInfo = reactive({
  major: '计算机科学与技术',
  graduationYear: 2025,
})
// company专属信息
const companyInfo = reactive({
  companyName: '上海XX科技有限公司',
  postedJobs: 8,
})

const editDialogVisible = ref(false)
const editForm = reactive({
  username: '',
  email: '',
  phone: ''
})
const editFields = [
  { label: '用户名', prop: 'username', component: 'el-input', props: { autocomplete: 'off' } },
  { label: '邮箱', prop: 'email', component: 'el-input', props: { autocomplete: 'off' } },
  { label: '电话', prop: 'phone', component: 'el-input', props: { autocomplete: 'off' } }
]
function openEditDialog() {
  editForm.username = profile.username
  editForm.email = profile.email
  editForm.phone = profile.phone
  editDialogVisible.value = true
}
function saveEdit() {
  profile.username = editForm.username
  profile.email = editForm.email
  profile.phone = editForm.phone
  editDialogVisible.value = false
}
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
  box-shadow: 0 2px 8px rgba(0,0,0,0.08);
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
