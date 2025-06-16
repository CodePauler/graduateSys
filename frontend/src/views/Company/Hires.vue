<template>
    <div>
        <el-collapse v-model="activeJobIds" accordion>
            <el-collapse-item v-for="job in jobList" :key="job.jobId" :name="String(job.jobId)">
                <template #title>
                    <div class="job-header">
                        <strong>{{ job.title }}</strong>
                        <span style="margin-left: 10px; color: #999;">状态：{{ job.status }}</span>
                        <span style="margin-left: 10px;">需求人数：{{ job.demandNumber }}</span>
                    </div>
                </template>

                <!-- 加载学生申请列表 -->
                <el-row :gutter="20">
                    <el-col v-for="student in appliedStudentsMap[job.jobId] || []" :key="student.studentId" :span="12">
                        <el-card shadow="hover" class="student-card">
                            <div class="student-info">
                                <p><strong>姓名：</strong>{{ student.name }}</p>
                                <p><strong>性别：</strong>{{ student.gender }}</p>
                                <p><strong>专业：</strong>{{ student.major }}</p>
                                <p><strong>毕业年份：</strong>{{ student.graduationYear }}</p>
                                <p><strong>状态：</strong>{{ student.employmentStatus }}</p>
                                <el-button v-if="student.resumeUrl" type="primary" link
                                    @click="viewResume(student.resumeUrl)">
                                    查看简历
                                </el-button>
                            </div>
                        </el-card>
                    </el-col>
                </el-row>
            </el-collapse-item>
        </el-collapse>
    </div>
</template>

<script setup>
import { ref, onMounted,watch } from 'vue'
import { getJobsByCompanyIdApi, getApplicationsByJobIdApi } from '@/api/company/jobs'

const jobList = ref([])
const appliedStudentsMap = ref({}) // jobId => 学生数组
const activeJobIds = ref(null) // 当前展开项 jobId

// 模拟 companyId，从登录信息中读取
const companyId = localStorage.getItem('companyId') 

// 加载岗位列表
const loadJobs = async () => {
    const res = await getJobsByCompanyIdApi(companyId)
    if (res.code === 1) {
        jobList.value = res.data
        console.log('加载岗位列表成功', jobList.value)
    }
}

// 每次展开时加载对应学生
const loadStudentsForJob = async (jobId) => {
    if (appliedStudentsMap.value[jobId]) return // 已加载过
    const res = await getApplicationsByJobIdApi(jobId)
    if (res.code === 1) {
        appliedStudentsMap.value[jobId] = res.data
        console.log(`加载岗位 ${jobId} 的学生申请`, appliedStudentsMap.value[jobId])
    }
}

// 监听展开事件
watch(activeJobIds, (newJobId) => {
    if (newJobId) loadStudentsForJob(Number(newJobId))
})

// 打开简历
const viewResume = (url) => {
    window.open(url, '_blank')
}

onMounted(loadJobs)
</script>

<style scoped>
.job-header {
    display: flex;
    align-items: center;
}

.student-card {
    margin-bottom: 20px;
}

.student-info p {
    margin: 5px 0;
}
</style>
