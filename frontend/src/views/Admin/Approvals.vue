<template>
    <h1>岗位审批</h1>
    <!-- 搜索栏和批量操作按钮并排 -->
    <div class="container search-bar-row">
        <SearchBar :fields="searchFields" :model="searchJob" @search="search" @clear="clear">
            <template #batch-action>
                <el-button type="danger" :disabled="!multipleSelection.length" @click="handleBatchDelete" style="margin-left:8px;">批量删除</el-button>
            </template>
        </SearchBar>
    </div>
    <div class="container">
        <!-- 数据表格 -->
        <DataTable :data="jobInfo" :columns="tableColumns" :actions="tableActions" :pagination="pagination"
            @page-change="handleCurrentChange" @size-change="handleSizeChange" @selection-change="handleSelectionChange" />
    </div>
    <!-- 分页 -->
    <div class="container">
        <el-pagination v-model:current-page="currentPage" v-model:page-size="pageSize"
            :page-sizes="[5, 10, 20, 30, 40, 50, 75, 100]" :background="background"
            layout="total, sizes, prev, pager, next, jumper" :total="total" @size-change="handleSizeChange"
            @current-change="handleCurrentChange" />
    </div>
    <!-- 编辑弹窗 -->
    <EditDialog v-model="dialogFormVisible" :title="'岗位审批'" :model="job" :fields="editFields" @submit="approveJob" />
</template>
<script setup>
import DataTable from '@/components/DataTable.vue';
import SearchBar from '@/components/SearchBar.vue';
import EditDialog from '@/components/EditDialog.vue';
import { ref, reactive, onMounted } from 'vue';
import { ElMessage, ElMessageBox } from 'element-plus';
import { queryJobsApi, queryJobByIdApi } from '@/api/public/jobs';
import { updateJobApi, deleteJobApi, insertJobApi } from '@/api/company/jobs';
import { queryCompanyByHrIdApi } from '@/api/public/company';

// 搜索栏字段配置
const searchFields = [
    { label: '岗位ID', prop: 'jobId', component: 'el-input', props: { placeholder: '请输入岗位ID', clearable: true } },
    { label: '岗位名称', prop: 'title', component: 'el-input', props: { placeholder: '请输入岗位名称', clearable: true } },
    { label: '职业类型', prop: 'jobType', component: 'el-input', props: { placeholder: '请输入职业类型', clearable: true } },
    { label: '公司名称', prop: 'companyName', component: 'el-input', props: { placeholder: '请输入公司名称', clearable: true } },
    { label: '岗位描述', prop: 'description', component: 'el-input', props: { placeholder: '请输入岗位描述', clearable: true } },
    {
        label: '审核状态', prop: 'status', component: 'el-select', props: { placeholder: '请选择审核状态', clearable: true },
        options: [
            { label: '待审核', value: '待审核' },
            { label: '已通过', value: '已通过' },
            { label: '不通过', value: '不通过' }
        ]
    },
]
const searchJob = reactive({
    title: '',
    demandNumber: '',
    hiredNumber: '',
    status: '',
    companyName: '',
    page: 1,
    pageSize: 10
})
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
// 清空搜索条件
const clear = () => {
    searchJob.jobId = '';
    searchJob.title = '';
    searchJob.jobType = '';
    searchJob.companyName = '';
    searchJob.description = '';
    searchJob.status = '';
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
    { prop: 'status', label: '审核状态' }//可以做成下拉框进行审核操作
]
const pagination = reactive({
    page: 1,
    pageSize: 10,
    total: 0
})
// 表格操作按钮
const dialogFormVisible = ref(false)
const tableActions = [
    {
        label: '审核',
        type: 'primary',
        onClick: async (row) => {
            dialogFormVisible.value = true//临时移动
            const result = await queryJobByIdApi(row.jobId)
            if (result.code === 1) {
                console.log("查询岗位信息成功", result.data);
                job.value = result.data
            }
            else {
                ElMessage.error(result.msg)
            }
        }
    },
    
]
// 编辑用户信息（查询回显） 以及弹窗
const job = ref({})
const editFields = [
    {
        label: '审核状态', prop: 'status', component: 'el-select', props: { autocomplete: 'off' },
        options: [
            { label: '待审核', value: '待审核' },
            { label: '已通过', value: '已通过' },
            { label: '不通过', value: '不通过' }
        ]
    },
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

// 处理多选
const multipleSelection = ref([])
const handleSelectionChange = (val) => {
    multipleSelection.value = val
}
const handleBatchDelete = () => {
    if (!multipleSelection.value.length) return
    ElMessageBox.confirm(`确定要删除选中的${multipleSelection.value.length}个岗位吗？`, '警告', {
        confirmButtonText: '确认删除',
        cancelButtonText: '取消',
        type: 'warning',
    }).then(async () => {
        const ids = multipleSelection.value.map(item => item.jobId)
        const result = await deleteJobApi(ids)
        if (result.code === 1) {
            ElMessage.success('批量删除成功')
            search()
        } else {
            ElMessage.error(result.msg)
        }
    }).catch(() => {
        ElMessage.info('已取消删除')
    })
}
</script>
<style scoped>
.container {
    margin: 10px;
}
.search-bar-row {
    display: flex;
    align-items: flex-end;
}
</style>