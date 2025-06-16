<template>
    <!-- company-发布岗位 -->
    <!-- company-新增岗位弹窗 -->
    <div class="container" v-if="role === 'company'">
        <EditDialog v-model="addDialogVisible" :title="'发布岗位'" :model="addJob" :fields="addFields"
            @submit="addJobSubmit" />
    </div>
    <div class="headerContent">
        我的招聘
    </div>
    <div v-if="!jobList.length">
        <el-empty description="暂无岗位">
            <el-button type="primary" size="large" @click="openAddDialog">
                发布招聘
            </el-button>
        </el-empty>
    </div>
    <div v-else>
        <el-button v-if="jobList.length" type="primary" size="large" @click="openAddDialog">
            发布招聘
        </el-button>
        <el-collapse v-model="activeJobIds" accordion>
            <el-collapse-item v-for="job in jobList" :key="job.jobId" :name="String(job.jobId)">
                <template #title>
                    <div class="job-header">
                        <strong>{{ job.title }}</strong>
                        <span style="margin-left: 10px; color: #999;">审核状态：<el-tag :type="getStatusType(job.status)"
                                size="small">
                                {{ job.status }}
                            </el-tag></span>
                        <span style="margin-left: 10px;">需求人数：{{ job.demandNumber }}</span>
                    </div>
                </template>

                <!-- 加载学生申请列表 -->
                <el-empty v-if="!(appliedStudentsMap[job.jobId] && appliedStudentsMap[job.jobId].length)"
                    description="暂无学生申请">
                    <el-button type="primary" size="small" @click="loadJobs">刷新</el-button>
                </el-empty>
                <el-row :gutter="20">
                    <el-col v-for="student in appliedStudentsMap[job.jobId] || []" :key="student.studentId" :span="12">
                        <el-card shadow="hover" class="student-card">
                            <div class="card-header">
                                <div>
                                    <span style="font-size: 32px;">{{ student.name }}</span>
                                </div>
                                <el-tag :type="getStatusType(student.status)" size="large">
                                    {{ student.status }}
                                </el-tag>
                            </div>
                            <div class="student-info">
                                <p><strong>姓名：</strong>{{ student.name }}</p>
                                <p><strong>性别：</strong>{{ student.gender }}</p>
                                <p><strong>专业：</strong>{{ student.major }}</p>
                                <p><strong>毕业年份：</strong>{{ student.graduationYear }}</p>
                                <p><strong>状态：</strong>{{ student.employmentStatus }}</p>
                                <el-button-group>
                                    <el-button type="primary" size="small" @click="viewResume(student.resumeUrl)">
                                        查看简历
                                    </el-button>
                                    <el-button type="success" size="small"
                                        @click="updateApplication('已录用', student.studentId, job.jobId)">
                                        通过
                                    </el-button>
                                    <el-button type="danger" size="small"
                                        @click="updateApplication('不录用', student.studentId, job.jobId)">
                                        不通过
                                    </el-button>
                                </el-button-group>
                            </div>
                        </el-card>
                    </el-col>
                </el-row>
            </el-collapse-item>
        </el-collapse>
    </div>
</template>

<script setup>
import { ref, onMounted, watch } from 'vue'
import EditDialog from '@/components/EditDialog.vue';
import { getJobsByCompanyIdApi, getApplicationsByJobIdApi, updateApplicationApi } from '@/api/company/jobs'
import { ElMessage, ElMessageBox } from 'element-plus'
import { queryCompanyByHrIdApi } from '@/api/public/company';
import { insertJobApi } from '@/api/company/jobs';

// 发布招聘
// company-发布岗位
const role = localStorage.getItem('role')
const addDialogVisible = ref(false);
const addJob = ref({});
const addFields = [
    { label: '岗位名称', prop: 'title', component: 'el-input', props: { placeholder: '请输入岗位名称', clearable: true } },
    { label: '职业类型', prop: 'jobType', component: 'el-input', props: { placeholder: '请输入职业类型', clearable: true } },
    { label: '需求人数', prop: 'demandNumber', component: 'el-input', props: { placeholder: '请输入需求人数', clearable: true } },
    { label: '公司名称', prop: 'companyName', component: 'el-input', props: { disabled: true } },
    { label: '公司简介', prop: 'companyIntro', component: 'el-input', props: { disabled: true } },
    { label: '岗位描述', prop: 'description', component: 'el-input', props: { type: 'textarea', placeholder: '请输入岗位描述', clearable: true } }
]
const openAddDialog = async () => {
    const company = await queryCompanyByHrIdApi(localStorage.getItem('userId'));
    if (company.code === 1) {
        addJob.value = {
            title: '',
            jobType: '',
            typeId: 1, //测试
            demandNumber: '',
            hiredNumber: '',
            companyId: company.data.companyId,
            companyName: company.data.companyName,
            companyIntro: company.data.companyIntro,
            description: '',
            status: '待审核'
        };
    } else {
        ElMessage.error('获取公司信息失败，请先创建公司');
        return;
    }
    addDialogVisible.value = true;
}
const addJobSubmit = async () => {
    const result = await insertJobApi(addJob.value);
    if (result.code === 1) {
        ElMessage.success('岗位发布成功');
        addDialogVisible.value = false;
        loadJobs();
    } else {
        ElMessage.error(result.msg);
    }
}



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
    for (const job of jobList.value) {
        const res = await getApplicationsByJobIdApi(job.jobId)
        if (res.code === 1) {
            appliedStudentsMap.value[job.jobId] = res.data
            console.log(`加载岗位 ${job.jobId} 的学生申请`, appliedStudentsMap.value[job.jobId])
        }
    }
    activeJobIds.value = jobList.value.length > 0 ? String(jobList.value[0].jobId) : null // 默认展开第一个岗位
}

// // 每次展开时加载对应学生
// const loadStudentsForJob = async (jobId) => {
//     if (appliedStudentsMap.value[jobId]) return // 已加载过
//     const res = await getApplicationsByJobIdApi(jobId)
//     if (res.code === 1) {
//         appliedStudentsMap.value[jobId] = res.data
//         console.log(`加载岗位 ${jobId} 的学生申请`, appliedStudentsMap.value[jobId])
//     }
// }

// // 监听展开事件
// watch(activeJobIds, (newJobId) => {
//     if (newJobId) loadStudentsForJob(Number(newJobId))
// })

// 打开简历
const viewResume = (url) => {
    if (!url) {
        console.error('简历链接无效')
        ElMessage.error('学生暂未上传简历')
        return
    }
    window.open(url, '_blank')
}
// 接受学生申请
const updateApplication = async (status, studentId, jobId) => {
    ElMessageBox.confirm(`确定将学生申请状态更新为 "${status}"？`, '确认操作', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
    }).then(async () => {
        const res = await updateApplicationApi(status, studentId, jobId)
        if (res.code === 1) {
            ElMessage.success('操作成功')
            // 刷新岗位列表
            loadJobs()
        } else {
            ElMessage.error(res.msg || '操作失败，请稍后再试')
        }
    }).catch(() => {
        console.log('用户取消了操作')
    })
}

onMounted(loadJobs)
const getStatusType = (status) => {
    switch (status) {
        case '待审核':
            return 'primary'
        case '已通过':
        case '已录用':
            return 'success'
        case '不通过':
        case '不录用':
            return 'danger'
        default:
            return 'info'
    }
}
</script>

<style scoped>
.job-header {
    display: flex;
    align-items: center;
    font-size: medium;
}

.student-card {
    margin-bottom: 20px;
}

.student-info p {
    margin: 5px 0;
}

.card-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 10px 15px !important;
}

.empty-job-list {
    justify-content: center;
    margin-top: 20px;
}
</style>
