<template>
    <!-- company-发布岗位 -->
    <!-- company-新增岗位弹窗 -->
    <div class="container" v-if="role === 'company'">
        <EditDialog v-model="addDialogVisible" :title="'发布岗位'" :model="addJob" :fields="addFields"
            @submit="addJobSubmit" />
    </div>
    <div class="container" v-if="role === 'company'">
        <EditDialog v-model="editDialogVisible" :title="'编辑岗位'" :model="editJob" :fields="editFields"
            @submit="editJobSubmit" />
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
                            </el-tag>
                            招聘状态：<el-tag :type="getStatusType(job.hireStatus)" size="small">
                                {{ job.hireStatus }}
                            </el-tag>
                        </span>
                        <span style="margin-left: 10px;">需求人数：{{ job.demandNumber }}</span>
                        <span style="margin-left: 10px;">已录用：{{ getHiredCount(job.jobId) }}</span>
                    </div>
                </template>
                <el-row style="margin-bottom: 12px;">
                    <el-button type="primary" size="small" @click="openEditDialog(job)">编辑</el-button>
                    <el-button type="danger" size="small" @click="handleDeleteJob(job)"
                        style="margin-left: 8px;">删除</el-button>
                </el-row>
                
                <!-- 学生筛选器 -->
                <div class="filter-section" style="margin-bottom: 16px; padding: 12px; background-color: #f5f7fa; border-radius: 6px;">
                    <el-row :gutter="16" style="align-items: center;">
                        <el-col :span="5">
                            <el-select 
                                :model-value="studentFilters[job.jobId]?.graduationYear" 
                                placeholder="筛选毕业年份" 
                                clearable 
                                size="small"
                                @update:model-value="(val) => setStudentFilter(job.jobId, 'graduationYear', val)">
                                <el-option 
                                    v-for="year in getGraduationYears(job.jobId)" 
                                    :key="year" 
                                    :label="year + '届'" 
                                    :value="year">
                                </el-option>
                            </el-select>
                        </el-col>
                        <el-col :span="5">
                            <el-select 
                                :model-value="studentFilters[job.jobId]?.major" 
                                placeholder="筛选专业" 
                                clearable 
                                size="small"
                                @update:model-value="(val) => setStudentFilter(job.jobId, 'major', val)">
                                <el-option 
                                    v-for="major in getMajors(job.jobId)" 
                                    :key="major" 
                                    :label="major" 
                                    :value="major">
                                </el-option>
                            </el-select>
                        </el-col>
                        <el-col :span="5">
                            <el-select 
                                :model-value="studentFilters[job.jobId]?.status" 
                                placeholder="筛选录用状态" 
                                clearable 
                                size="small"
                                @update:model-value="(val) => setStudentFilter(job.jobId, 'status', val)">
                                <el-option label="待审核" value="待审核"></el-option>
                                <el-option label="已录用" value="已录用"></el-option>
                                <el-option label="不录用" value="不录用"></el-option>
                            </el-select>
                        </el-col>
                        <el-col :span="4">
                            <el-button 
                                size="small" 
                                @click="clearFilters(job.jobId)"
                                :disabled="!hasActiveFilters(job.jobId)">
                                清空筛选
                            </el-button>
                        </el-col>
                        <el-col :span="5">
                            <span class="filter-result-text">
                                共 {{ getFilteredStudents(job.jobId).length }} 名学生
                            </span>
                        </el-col>
                    </el-row>
                </div>
                
                <!-- 加载学生申请列表 -->
                <el-empty v-if="!getFilteredStudents(job.jobId).length"
                    description="暂无符合条件的学生申请">
                    <el-button type="primary" size="small" @click="loadJobs">刷新</el-button>
                </el-empty>
                <el-row :gutter="20">
                    <el-col v-for="student in getFilteredStudents(job.jobId)" :key="student.studentId" :span="12">
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
                                <p><strong>联系电话：</strong>{{ student.phone }}</p>
                                <p><strong>电子邮箱：</strong>{{ student.email }}</p>
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
import { insertJobApi, updateJobApi, deleteJobApi } from '@/api/company/jobs';
import { queryJobTypesApi } from '@/api/public/jobs';
const typeOptions = ref([])
// 发布招聘
// company-发布岗位
const role = localStorage.getItem('role')
const addDialogVisible = ref(false);
const addJob = ref({});
const addFields = [
    { label: '岗位名称', prop: 'title', component: 'el-input', props: { placeholder: '请输入岗位名称', clearable: true } },
    { label: '职业类型', prop: 'typeId', component: 'el-select', props: { placeholder: '请选择职业类型', clearable: true }, options: typeOptions.value },
    { label: '需求人数', prop: 'demandNumber', component: 'el-input', props: { placeholder: '请输入需求人数', clearable: true } },
    { label: '工作地点', prop: 'location', component: 'el-input', props: { placeholder: '请输入工作地点', clearable: true } },
    { label: '薪资范围', prop: 'salary', component: 'el-input', props: { placeholder: '请输入薪资范围', clearable: true } },
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
            typeId: '',
            demandNumber: '',
            location: '',
            salary: '',
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

// 岗位编辑
// 编辑岗位弹窗
const editDialogVisible = ref(false)
const editJob = ref({})
const editFields = [
    { label: '岗位名称', prop: 'title', component: 'el-input', props: { placeholder: '请输入岗位名称', clearable: true } },
    { label: '职业类型', prop: 'typeId', component: 'el-select', props: { placeholder: '请选择职业类型', clearable: true }, options: typeOptions.value },
    { label: '需求人数', prop: 'demandNumber', component: 'el-input', props: { placeholder: '请输入需求人数', clearable: true } },
    { label: '工作地点', prop: 'location', component: 'el-input', props: { placeholder: '请输入工作地点', clearable: true } },
    { label: '薪资范围', prop: 'salary', component: 'el-input', props: { placeholder: '请输入薪资范围', clearable: true } },
    { label: '岗位描述', prop: 'description', component: 'el-input', props: { type: 'textarea', placeholder: '请输入岗位描述', clearable: true } }
]

// 打开编辑弹窗
const openEditDialog = (job) => {
    editDialogVisible.value = true
    editJob.value = { ...job }
}

// 编辑提交
const editJobSubmit = async () => {
    const res = await updateJobApi(editJob.value)
    if (res.code === 1) {
        ElMessage.success('岗位信息已更新')
        editDialogVisible.value = false
        loadJobs()
    } else {
        ElMessage.error(res.msg || '更新失败')
    }
}

// 删除岗位
const handleDeleteJob = (job) => {
    ElMessageBox.confirm('确定要删除该岗位吗？', '删除确认', {
        confirmButtonText: '删除',
        cancelButtonText: '取消',
        type: 'warning'
    }).then(async () => {
        const res = await deleteJobApi(job.jobId)
        if (res.code === 1) {
            ElMessage.success('删除成功')
            loadJobs()
        } else {
            ElMessage.error(res.msg || '删除失败')
        }
    }).catch(() => { })
}




// 申请列表
const jobList = ref([])
const appliedStudentsMap = ref({}) // jobId => 学生数组
const filteredStudentsMap = ref({}) // jobId => 筛选后的学生数组
const studentFilters = ref({}) // jobId => { graduationYear, major, status }
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
            // 初始化筛选器和筛选结果
            if (!studentFilters.value[job.jobId]) {
                studentFilters.value[job.jobId] = {
                    graduationYear: null,
                    major: null,
                    status: null
                }
            }
            filteredStudentsMap.value[job.jobId] = res.data
            console.log(`加载岗位 ${job.jobId} 的学生申请`, appliedStudentsMap.value[job.jobId])
        }
    }
    activeJobIds.value = jobList.value.length > 0 ? String(jobList.value[0].jobId) : null // 默认展开第一个岗位
}


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

onMounted(async () => {
    loadJobs()
    const res = await queryJobTypesApi()
    if (res.code === 1) {
        typeOptions.value = res.data.map(type => ({
            label: type.typeName,
            value: type.typeId
        }))
        addFields[1].options = typeOptions.value
        editFields[1].options = typeOptions.value
        console.log('职业类型加载成功', typeOptions.value)
    }
    else {
        ElMessage.error('获取职业类型失败，请稍后再试')
    }
})
const getStatusType = (status) => {
    switch (status) {
        case '待审核':
            return 'primary'
        case '已通过':
        case '已录用':
        case '招聘中':
            return 'success'
        case '不通过':
        case '不录用':
        case '已结束':
            return 'danger'
        default:
            return 'info'
    }
}

// 筛选相关方法
const getFilteredStudents = (jobId) => {
    return filteredStudentsMap.value[jobId] || []
}

const getGraduationYears = (jobId) => {
    const students = appliedStudentsMap.value[jobId] || []
    const years = [...new Set(students.map(s => s.graduationYear).filter(year => year))]
    return years.sort((a, b) => b - a) // 降序排列
}

const getMajors = (jobId) => {
    const students = appliedStudentsMap.value[jobId] || []
    const majors = [...new Set(students.map(s => s.major).filter(major => major))]
    return majors.sort()
}

const filterStudents = (jobId) => {
    const allStudents = appliedStudentsMap.value[jobId] || []
    const filters = studentFilters.value[jobId]
    
    if (!filters) {
        filteredStudentsMap.value[jobId] = allStudents
        return
    }
    
    let filtered = allStudents
    
    // 按毕业年份筛选
    if (filters.graduationYear) {
        filtered = filtered.filter(student => student.graduationYear === filters.graduationYear)
    }
    
    // 按专业筛选
    if (filters.major) {
        filtered = filtered.filter(student => student.major === filters.major)
    }
    
    // 按录用状态筛选
    if (filters.status) {
        filtered = filtered.filter(student => student.status === filters.status)
    }
    
    filteredStudentsMap.value[jobId] = filtered
}

const clearFilters = (jobId) => {
    studentFilters.value[jobId] = {
        graduationYear: null,
        major: null,
        status: null
    }
    filteredStudentsMap.value[jobId] = appliedStudentsMap.value[jobId] || []
}

const hasActiveFilters = (jobId) => {
    const filters = studentFilters.value[jobId]
    if (!filters) return false
    return filters.graduationYear || filters.major || filters.status
}

const setStudentFilter = (jobId, filterType, value) => {
    if (!studentFilters.value[jobId]) {
        studentFilters.value[jobId] = {
            graduationYear: null,
            major: null,
            status: null
        }
    }
    studentFilters.value[jobId][filterType] = value
    filterStudents(jobId)
}

const getHiredCount = (jobId) => {
    const students = appliedStudentsMap.value[jobId] || []
    return students.filter(student => student.status === '已录用').length
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

/* 筛选器样式 */
.filter-section {
    border: 1px solid #dcdfe6;
}

.filter-result-text {
    font-size: 14px;
    color: #606266;
    font-weight: 500;
}
</style>
