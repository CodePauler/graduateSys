<template>
  <div class="header-row">
    <div class="headerContent">个人中心</div>
  </div>
  
  <!-- 个人信息板块 -->
  <div class="profile-container">
    <div class="profile-header">
      <h3 class="profile-title">个人信息</h3>
      <el-button type="primary" size="small" class="edit-btn-new" @click="openEditDialog">编辑</el-button>
    </div>
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
  </div>

  <!-- 学生申请统计面板 - 独立板块 -->
  <div v-if="role === 'student'" class="profile-container">
    <div class="stats-panel">
      <h3 class="stats-title">申请统计</h3>
      <div class="stats-grid">
        <div class="stat-card">
          <div class="stat-number">{{ studentStats.totalApplications }}</div>
          <div class="stat-label">总申请数</div>
        </div>
        <div class="stat-card">
          <div class="stat-number">{{ studentStats.pendingCount }}</div>
          <div class="stat-label">待审核</div>
        </div>
        <div class="stat-card">
          <div class="stat-number">{{ studentStats.rejectedCount }}</div>
          <div class="stat-label">已拒绝</div>
        </div>
        <div class="stat-card">
          <div class="stat-number">{{ studentStats.hiredCount }}</div>
          <div class="stat-label">已录用</div>
        </div>
      </div>
      
      <!-- 申请状态比例 -->
      <div class="ratio-section">
        <h4>申请状态分布</h4>
        <div class="ratio-bars">
          <div class="ratio-item">
            <span class="ratio-label">录用率</span>
            <div class="ratio-bar">
              <div class="ratio-fill hired" :style="{ width: studentStats.hireRate + '%' }"></div>
            </div>
            <span class="ratio-text">{{ studentStats.hireRate }}%</span>
          </div>
        </div>
      </div>
    </div>
  </div>

  <!-- 企业招聘统计面板 - 独立板块 -->
  <div v-if="role === 'company'" class="profile-container">
    <div class="stats-panel">
      <h3 class="stats-title">招聘统计</h3>
      <div class="stats-grid">
        <div class="stat-card">
          <div class="stat-number">{{ companyStats.totalJobs }}</div>
          <div class="stat-label">发布岗位数</div>
        </div>
        <div class="stat-card">
          <div class="stat-number">{{ totalDemand }}</div>
          <div class="stat-label">总需求人数</div>
        </div>
        <div class="stat-card">
          <div class="stat-number">{{ companyStats.totalApplications }}</div>
          <div class="stat-label">收到简历数</div>
        </div>
        <div class="stat-card">
          <div class="stat-number">{{ companyStats.hiredCount }}</div>
          <div class="stat-label">录用人数</div>
        </div>
      </div>
      
      <!-- 招聘概览 -->
      <div class="funnel-section">
        <h4>招聘概览</h4>
        <div class="simple-stats">
          <div class="simple-stat-item">
            <span class="simple-stat-label">平均每岗位需求</span>
            <span class="simple-stat-value">{{ avgDemand }}人</span>
          </div>
          <div class="simple-stat-item">
            <span class="simple-stat-label">整体录用率</span>
            <span class="simple-stat-value">{{ companyStats.hireRate }}%</span>
          </div>
        </div>
      </div>
    </div>
  </div>

  <EditDialog v-model="editDialogVisible" :title="'编辑个人信息'" :model="editForm" :fields="editFields"
    @submit="saveEdit" />
</template>

<script setup>
import { onMounted, reactive, ref, computed } from 'vue'
import EditDialog from '@/components/EditDialog.vue'
import { queryUserByIdApi, updateUserApi } from '@/api/public/user'
import { ElMessage } from 'element-plus'
import { queryStudentByUserIdApi } from '@/api/public/students'
import { queryCompanyProfileApi } from '@/api/public/company'
import { getStudentApplicationStatsApi } from '@/api/student/Jobs'
import { getCompanyRecruitmentStatsApi, getCompanyJobSummaryApi } from '@/api/company/jobs'

const role = localStorage.getItem('role')
const userId = localStorage.getItem('userId')
const studentId = localStorage.getItem('studentId')
const companyId = localStorage.getItem('companyId')
const profile = reactive({
  userId: userId,
  username: '',
  password: '',
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

// 学生申请统计数据
const studentStats = reactive({
  totalApplications: 0,
  pendingCount: 0,
  rejectedCount: 0,
  hiredCount: 0,
  hireRate: 0
})

// 企业招聘统计数据
const companyStats = reactive({
  totalJobs: 0,
  totalApplications: 0,
  screeningCount: 0,
  interviewCount: 0,
  hiredCount: 0,
  screeningRate: 0,
  interviewRate: 0,
  interviewPassRate: 0,
  hireRate: 0
})

// 计算平均需求人数
const avgDemand = computed(() => {
  return companyStats.totalJobs > 0 ? Math.round(totalDemand.value / companyStats.totalJobs) : 0
})

const totalDemand = ref(0)

const editDialogVisible = ref(false)
const editForm = reactive({
  userId: '',
  username: '',
  password: '',
  email: '',
  phone: '',
  companyName: '',
  companyIntro: '',
})
const editFields = [
  { label: '用户名', prop: 'username', component: 'el-input', props: { autocomplete: 'off' } },
  { label: '密码', prop: 'password', component: 'el-input', props: { type: 'password', autocomplete: 'off' } },
  { label: '邮箱', prop: 'email', component: 'el-input', props: { autocomplete: 'off' } },
  { label: '电话', prop: 'phone', component: 'el-input', props: { autocomplete: 'off' } },
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
  
  // 获取统计数据
  if (role === 'student' && studentId) {
    try {
      // 直接从申请列表中计算统计数据
      const { queryMyApplicationsApi } = await import('@/api/student/Jobs')
      const applicationsRes = await queryMyApplicationsApi(studentId)
      
      if (applicationsRes.code === 1) {
        const applications = applicationsRes.data || []
        console.log('获取学生申请数据成功:', applications)
        
        // 计算实际统计数据
        studentStats.totalApplications = applications.length
        studentStats.pendingCount = applications.filter(app => app.applicationStatus === '待审核').length
        studentStats.rejectedCount = applications.filter(app => app.applicationStatus === '不通过' || app.applicationStatus === '不录用').length
        studentStats.hiredCount = applications.filter(app => app.applicationStatus === '已录用').length
        
        // 计算录用率（避免除零）
        if (studentStats.totalApplications > 0) {
          studentStats.hireRate = Math.round((studentStats.hiredCount / studentStats.totalApplications) * 100)
        } else {
          studentStats.hireRate = 0
        }
      } else {
        console.error('获取学生申请数据失败:', applicationsRes.msg)
        // 如果获取失败，初始化为0
        Object.assign(studentStats, {
          totalApplications: 0,
          pendingCount: 0,
          rejectedCount: 0,
          hiredCount: 0,
          hireRate: 0
        })
      }
    } catch (error) {
      console.error('获取学生申请数据异常:', error)
      // 异常时初始化为0
      Object.assign(studentStats, {
        totalApplications: 0,
        pendingCount: 0,
        rejectedCount: 0,
        hiredCount: 0,
        hireRate: 0
      })
    }
  }
  
  if (role === 'company' && companyId) {
    try {
      // 从company_job_summary视图获取企业招聘统计数据
      const summaryRes = await getCompanyJobSummaryApi(companyId)
      
      if (summaryRes.code === 1) {
        const summary = summaryRes.data
        console.log('获取企业招聘汇总成功:', summary)
        
        // 使用视图中的真实数据
        companyStats.totalJobs = summary.jobCount || 0
        companyStats.totalApplications = summary.applicationCount || 0
        companyStats.hiredCount = summary.approvedCount || 0
        totalDemand.value = summary.demandCount || 0
        
        // 计算录用率
        if (companyStats.totalApplications > 0) {
          companyStats.hireRate = Math.round((companyStats.hiredCount / companyStats.totalApplications) * 100)
        } else {
          companyStats.hireRate = 0
        }
        
        // 其他字段暂时设为0（因为当前系统没有筛选和面试环节）
        companyStats.screeningCount = 0
        companyStats.interviewCount = 0
        companyStats.screeningRate = 0
        companyStats.interviewRate = 0
        companyStats.interviewPassRate = 0
        
      } else {
        console.error('获取企业招聘汇总失败:', summaryRes.msg)
        // 如果API失败，尝试从jobs API获取基础数据
        const { queryJobsApi } = await import('@/api/public/jobs')
        const jobsRes = await queryJobsApi({ companyId: companyId })
        
        if (jobsRes.code === 1) {
          const jobs = jobsRes.data?.rows || []
          companyStats.totalJobs = jobs.length
          totalDemand.value = jobs.reduce((total, job) => total + (job.demandNumber || 0), 0)
        }
        
        // 其他数据置为0
        Object.assign(companyStats, {
          totalApplications: 0,
          screeningCount: 0,
          interviewCount: 0,
          hiredCount: 0,
          screeningRate: 0,
          interviewRate: 0,
          interviewPassRate: 0,
          hireRate: 0
        })
      }
    } catch (error) {
      console.error('获取企业招聘数据异常:', error)
      Object.assign(companyStats, {
        totalJobs: 0,
        totalApplications: 0,
        screeningCount: 0,
        interviewCount: 0,
        hiredCount: 0,
        screeningRate: 0,
        interviewRate: 0,
        interviewPassRate: 0,
        hireRate: 0
      })
      totalDemand.value = 0
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

/* 个人信息块头部样式 */
.profile-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 24px;
  padding-bottom: 16px;
  border-bottom: 1px solid #e9ecef;
}

.profile-title {
  margin: 0;
  font-size: 18px;
  font-weight: 600;
  color: #333;
}

.edit-btn-new {
  font-size: 18px !important;
}

.profile-container {
  max-width: 600px;
  margin: 0 auto 40px auto;
  padding: 32px 24px;
  background: #fff;
  border-radius: 12px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.08);
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

/* 统计面板样式 */
.stats-panel {
  margin-top: 32px;
  padding: 24px;
  background: #f8f9fa;
  border-radius: 12px;
  border: 1px solid #e9ecef;
}

.stats-title {
  margin: 0 0 20px 0;
  font-size: 18px;
  font-weight: 600;
  color: #333;
}

.stats-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(120px, 1fr));
  gap: 16px;
  margin-bottom: 24px;
}

.stat-card {
  background: white;
  padding: 16px;
  border-radius: 8px;
  text-align: center;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.stat-number {
  font-size: 24px;
  font-weight: bold;
  color: #409EFF;
  margin-bottom: 4px;
}

.stat-label {
  font-size: 14px;
  color: #666;
}

/* 比例条样式 */
.ratio-section h4,
.funnel-section h4 {
  margin: 16px 0 12px 0;
  font-size: 16px;
  color: #333;
}

.ratio-bars {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.ratio-item {
  display: flex;
  align-items: center;
  gap: 12px;
}

.ratio-label {
  min-width: 60px;
  font-size: 14px;
  color: #666;
}

.ratio-bar {
  flex: 1;
  height: 8px;
  background: #e9ecef;
  border-radius: 4px;
  overflow: hidden;
}

.ratio-fill {
  height: 100%;
  border-radius: 4px;
  transition: width 0.3s ease;
}

.ratio-fill.success {
  background: #67C23A;
}

.ratio-fill.hired {
  background: #409EFF;
}

.ratio-text {
  min-width: 40px;
  font-size: 14px;
  font-weight: 500;
  color: #333;
}

/* 招聘漏斗样式 */
.funnel-chart {
  margin-bottom: 20px;
}

.funnel-item {
  margin-bottom: 8px;
}

.funnel-bar {
  height: 40px;
  display: flex;
  align-items: center;
  padding: 0 16px;
  color: white;
  font-size: 14px;
  font-weight: 500;
  border-radius: 4px;
  transition: width 0.3s ease;
}

.funnel-bar.applications {
  background: #409EFF;
}

.funnel-bar.screening {
  background: #67C23A;
}

.funnel-bar.interview {
  background: #E6A23C;
}

.funnel-bar.hired {
  background: #F56C6C;
}

/* 转化率统计 */
.conversion-stats {
  display: flex;
  justify-content: space-around;
  background: white;
  padding: 16px;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.conversion-item {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 4px;
}

.conversion-label {
  font-size: 12px;
  color: #666;
}

.conversion-value {
  font-size: 18px;
  font-weight: bold;
  color: #409EFF;
}

/* 简化版企业统计样式 */
.simple-stats {
  display: flex;
  gap: 24px;
  margin-bottom: 20px;
}

.simple-stat-item {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 16px;
  background: white;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  flex: 1;
}

.simple-stat-label {
  font-size: 14px;
  color: #666;
  margin-bottom: 8px;
}

.simple-stat-value {
  font-size: 20px;
  font-weight: bold;
  color: #409EFF;
}

.tips-section {
  background: #f0f9ff;
  border: 1px solid #0ea5e9;
  border-radius: 8px;
  padding: 16px;
}

.tips-text {
  margin: 0;
  font-size: 14px;
  color: #0369a1;
  line-height: 1.5;
}
</style>
