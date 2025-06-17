<template>
    <div class="headerContent" v-if="role === 'student' || role === 'company'">岗位列表</div>
    <el-tabs v-if="role === 'admin'" v-model="adminTab" class="demo-tabs" @update:modelValue="handleTabChange">
        <el-tab-pane label="岗位管理" name="manage">岗位管理</el-tab-pane>
        <el-tab-pane label="岗位审批" name="audit">岗位审批</el-tab-pane>
    </el-tabs>
    <!-- 搜索栏 -->
    <div class="container">
        <SearchBar :fields="searchFields" :model="searchJob" @search="search" @clear="clear" />
    </div>

    <!-- 岗位卡片列表 -->
    <div class="card-list" v-if="role === 'student' || role === 'company'">
        <el-card v-for="job in jobInfo" :key="job.jobId" shadow="hover" class="job-card">
            <div class="card-header">
                <div class="title">
                    <h1>{{ job.title }}</h1>
                    <el-tag class="tag big-tag" type="primary">{{ job.jobType }}</el-tag>
                </div>
                <div class="salary">{{ job.salary || '面议' }}</div>
            </div>

            <div class="card-body">
                <div class="info-tags">
                    <el-tag class="tag big-tag" size="small">{{ job.location || '地点待定' }}</el-tag>
                    <el-tag class="tag big-tag" size="small">需求: {{ job.demandNumber }}人</el-tag>
                    <el-tag class="tag big-tag" size="small">已聘: {{ job.hiredNumber }}人</el-tag>
                </div>
                <div class="description">{{ job.description }}</div>
                <div class="action-buttons">
                    <el-button-group v-if="job.hasApplied === '1'">
                        <el-button class="big-button" type="success" plain size="small" disabled>已申请</el-button>
                    </el-button-group>
                    <el-button-group v-else>
                        <el-button v-if="role === 'student'" class="big-button" type="primary" plain size="small"
                            @click="applyForJob(job)">申请职位</el-button>
                    </el-button-group>
                </div>
            </div>

            <div class="card-footer">
                <el-tag class="tag big-tag" type="success">{{ job.companyName }}</el-tag>
                <el-tag class="tag big-tag" size="small" type="info" style="margin-left: auto;">
                    {{ job.status }}
                </el-tag>
            </div>
        </el-card>
    </div>

    <!-- 管理员和企业用户仍使用表格 -->
    <div class="container" v-else>
        <!-- 数据表格 -->
        <DataTable :data="jobInfo" :columns="tableColumns" :actions="tableActions" :pagination="pagination"
            @page-change="handleCurrentChange" @size-change="handleSizeChange" />
    </div>

    <!-- 学生用户的分页 -->
    <div v-if="role === 'student'" class="pagination-container">
        <el-pagination v-model:current-page="pagination.page" v-model:page-size="pagination.pageSize"
            :page-sizes="[5, 10, 20, 50]" :total="pagination.total" layout="total, sizes, prev, pager, next, jumper"
            @size-change="handleSizeChange" @current-change="handleCurrentChange" />
    </div>

    <!-- 编辑弹窗 -->
    <EditDialog v-model="dialogFormVisible" :title="'岗位编辑'" :model="job" :fields="editFields" @submit="saveJob" />
</template>
<script setup>
import DataTable from '@/components/DataTable.vue';
import SearchBar from '@/components/SearchBar.vue';
import EditDialog from '@/components/EditDialog.vue';
import { ref, reactive, onMounted, computed } from 'vue';
import { ElMessage, ElMessageBox } from 'element-plus';
import { queryJobsApi, queryJobByIdApi } from '@/api/public/jobs';
import { updateJobApi, deleteJobApi, insertJobApi } from '@/api/company/jobs';
import { applyJobApi } from '@/api/student/Jobs';
const role = localStorage.getItem('role')


// 搜索栏字段配置
const searchFields = [
    { label: '岗位ID', prop: 'jobId', component: 'el-input', props: { placeholder: '请输入岗位ID', clearable: true } },
    { label: '岗位名称', prop: 'title', component: 'el-input', props: { placeholder: '请输入岗位名称', clearable: true } },
    { label: '职业类型', prop: 'jobType', component: 'el-input', props: { placeholder: '请输入职业类型', clearable: true } },
    { label: '公司名称', prop: 'companyName', component: 'el-input', props: { placeholder: '请输入公司名称', clearable: true } },
    { label: '岗位描述', prop: 'description', component: 'el-input', props: { placeholder: '请输入岗位描述', clearable: true } },
    {
        label: '审核状态', prop: 'status', component: 'el-select', props: { disabled: true },
        options: [
            { label: '待审核', value: '待审核' },
            { label: '已通过', value: '已通过' },
            { label: '不通过', value: '不通过' }
        ]
    }
]
const rawStudentId = localStorage.getItem('studentId')
const searchJob = reactive({
    title: '',
    demandNumber: '',
    hiredNumber: '',
    hireStatus: '招聘中',
    status: '已通过',
    companyName: '',
    studentId: rawStudentId && rawStudentId !== 'null' ? Number(rawStudentId) : undefined,
    page: 1,
    pageSize: 10
})
if (role === 'admin') {
    searchJob.hireStatus = '' // 管理员默认查询已通过的岗位
}
// 查询岗位列表
const search = async () => {
    const res = await queryJobsApi(searchJob);
    if (res.code === 1) {
        console.log("查询用户列表成功", res.data);
        jobInfo.value = res.data.rows;
        pagination.total = res.data.total;
    } else {
        console.error("查询用户列表失败", res.data);
    }
}
const adminTab = ref('manage')
const handleTabChange = (tabName) => {
    console.log(`当前选中的标签: ${adminTab.value}`);
    if (adminTab.value === 'manage') {
        searchJob.status = '已通过'
    } else {
        searchJob.status = '待审核' // 或 ['待审核', '不通过']，看后端支持
    }
    search()
}
// 清空搜索条件
const clear = () => {
    searchJob.jobId = '';
    searchJob.title = '';
    searchJob.jobType = '';
    searchJob.companyName = '';
    searchJob.description = '';
    searchJob.status = searchJob.status;
    searchJob.hireStatus = searchJob.hireStatus;
    searchJob.page = 1;
    searchJob.pageSize = 10;
    search();
}


// 表格列配置
const jobInfo = ref([])
const tableColumns = [
    { prop: 'jobId', label: '岗位ID' },
    { prop: 'title', label: '岗位名称' },
    { prop: 'jobType', label: '职业类型' },
    { prop: 'demandNumber', label: '需求人数' },
    { prop: 'hiredNumber', label: '已聘人数' },
    { prop: 'description', label: '岗位描述' },
    { prop: 'companyName', label: '公司名称' },
    { prop: 'companyIntro', label: '公司简介' },
    { prop: 'hireStatus', label: '招聘状态' },
    { prop: 'status', label: '审核状态' }//可以做成下拉框进行审核操作
]
const pagination = reactive({
    page: 1,
    pageSize: 10,
    total: 0
})
// 表格操作按钮
const dialogFormVisible = ref(false)
const tableActions = (row) => {
    if (role === 'admin') {
        return [
            {
                label: '编辑',
                type: 'primary',
                onClick: async (row) => {
                    dialogFormVisible.value = true//临时移动
                    const result = await queryJobByIdApi(row.jobId)
                    if (result.code === 1) {
                        console.log("查询岗位信息成功", result.data);
                        job.value = result.data
                        search()
                    }
                    else {
                        ElMessage.error(result.msg)
                    }
                }
            },
            {
                label: '删除',
                type: 'danger',
                onClick: (row) => {
                    ElMessageBox.confirm('是否确认删除该岗位？', '警告', {
                        confirmButtonText: '确认删除',
                        cancelButtonText: '取消',
                        type: 'warning',
                    }).then(async () => {
                        const result = await deleteJobApi(row.jobId)
                        if (result.code === 1) {
                            ElMessage.success('成功删除')
                            search()
                        } else {
                            ElMessage.error(result.msg)
                        }
                    }).catch(() => {
                        ElMessage.info('取消删除')
                    })
                }
            }
        ]
    } else if (role === 'student') {
        if (row.hasApplied === "1") {
            return [
                {
                    label: '已申请',
                    type: 'success',
                    disabled: true
                }
            ]
        }
        return [
            {
                label: '去申请',
                type: 'primary',
                onClick: async (row) => {
                    ElMessageBox.confirm('是否确认申请该岗位？', '提示', {
                        confirmButtonText: '确认申请',
                        cancelButtonText: '取消',
                        type: 'info',
                    }).then(async () => {
                        const result = await applyJobApi(localStorage.getItem('studentId'), row.jobId)
                        if (result.code !== 1) {
                            ElMessage.error(result.msg)
                        }
                        ElMessage.success('申请成功')
                        search();
                    }).catch(() => {
                        ElMessage.info('取消申请')
                    })
                }
            }
        ]
    }
    else return []
}

// 申请职位函数（用于卡片视图）
const applyForJob = async (job) => {
    ElMessageBox.confirm('是否确认申请该岗位？', '提示', {
        confirmButtonText: '确认申请',
        cancelButtonText: '取消',
        type: 'info',
    }).then(async () => {
        const result = await applyJobApi(localStorage.getItem('studentId'), job.jobId)
        if (result.code !== 1) {
            ElMessage.error(result.msg)
            return
        }
        ElMessage.success('申请成功')
        search();
    }).catch(() => {
        ElMessage.info('取消申请')
    })
}
// 编辑岗位信息（查询回显） 以及弹窗
const job = ref({})
const editFields = [
    { label: '岗位名称', prop: 'title', component: 'el-input', props: { autocomplete: 'off' } },
    { label: '需求人数', prop: 'demandNumber', component: 'el-input', props: { autocomplete: 'off' } },
    { label: '公司名称', prop: 'companyName', component: 'el-input', props: { disabled: true } },
    {
        label: '审核状态', prop: 'status', component: 'el-select', props: { autocomplete: 'off' },
        options: [
            { label: '待审核', value: '待审核' },
            { label: '已通过', value: '已通过' },
            { label: '不通过', value: '不通过' }
        ]
    },
    { label: '描述', prop: 'description', component: 'el-input', props: { autocomplete: 'off' } }

]
// 保存修改
const saveJob = async () => {
    dialogFormVisible.value = false;
    const result = await updateJobApi(job.value);
    if (result.code === 1) {
        ElMessage.success('岗位信息更新成功')
        search()
    } else {
        ElMessage.error(result.msg)
    }
}

// 分页处理
const handleCurrentChange = (page) => {
    searchJob.page = page;
    search();
}
const handleSizeChange = (size) => {
    searchJob.pageSize = size;
    search();
}
onMounted(() => {
    search();
});
</script>
<style scoped>
.title {
    display: flex;
    gap: 12px;
    align-items: center;
}

.card-list {
    display: flex;
    flex-wrap: wrap;
    gap: 16px;
    font-size: 18px;
    margin: 20px;
}

.big-tag {
    font-size: 18px !important;
}

.big-button {
    font-size: 18px !important;
}

.card-body {
    font-size: 18px;
}

.job-card {
    width: 48%;
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    border-radius: 10px;
    color: #666;
}

.card-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 8px;
}

.card-header h1 {
    margin: 0;
    font-size: 24px;
    color: #333;
}

.salary {
    color: #f56c6c;
    font-weight: bold;
    font-size: 32px;
}

.info-tags {
    display: flex;
    font-size: 18px;
    gap: 8px;
    margin: 12px 0;
}

.description {
    margin: 12px 0;
    line-height: 1.6;
    color: #666;
}

.action-buttons {
    display: flex;
    justify-content: flex-end;
    margin: 12px 0;
}

.card-footer {
    display: flex;
    align-items: center;
    border-top: 1px solid #f0f0f0;
    padding-top: 8px;
    margin-top: 8px;
    font-size: 13px;
    color: #666;
}

.pagination-container {
    display: flex;
    justify-content: center;
    margin: 20px 0;
}
</style>