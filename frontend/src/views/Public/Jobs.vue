<template>
    <h1>岗位列表</h1>
    <!-- 搜索栏 -->
    <div class="container">
        <SearchBar :fields="searchFields" :model="searchJob" @search="search" @clear="clear" />
    </div>

    <!-- admin-发布岗位 -->
    <div class="container">
        <el-button type="primary" @click="openAddDialog()">发布岗位</el-button>
    </div>
    <!-- admin-新增岗位弹窗 -->
    <div class="container">
        <EditDialog v-model="addDialogVisible" :title="'发布岗位'" :model="addJob" :fields="addFields"
            @submit="addJobSubmit" />
    </div>

    <div class="container">
        <!-- 数据表格 -->
        <DataTable :data="jobInfo" :columns="tableColumns" :actions="tableActions" :pagination="pagination"
            @page-change="handleCurrentChange" @size-change="handleSizeChange" />"
    </div>
    <!-- 分页 -->
    <div class="container">
        <el-pagination v-model:current-page="currentPage" v-model:page-size="pageSize"
            :page-sizes="[5, 10, 20, 30, 40, 50, 75, 100]" :background="background"
            layout="total, sizes, prev, pager, next, jumper" :total="total" @size-change="handleSizeChange"
            @current-change="handleCurrentChange" />
    </div>
    <!-- 编辑弹窗 -->
    <EditDialog v-model="dialogFormVisible" :title="'岗位编辑'" :model="job" :fields="editFields" @submit="saveJob" />
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
// company-发布岗位
const addDialogVisible = ref(false);
const addJob = ref({});
const addFields = [
    { label: '岗位名称', prop: 'title', component: 'el-input', props: { placeholder: '请输入岗位名称', clearable: true } },
    { label: '职业类型', prop: 'jobType', component: 'el-input', props: { placeholder: '请输入职业类型', clearable: true } },
    { label: '需求人数', prop: 'demandNumber', component: 'el-input', props: { placeholder: '请输入需求人数', clearable: true } },
    { label: '已聘人数', prop: 'hiredNumber', component: 'el-input', props: { placeholder: '请输入已聘人数', clearable: true } },
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
        search();
    } else {
        ElMessage.error(result.msg);
    }
}


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
        label: '编辑',
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
// 编辑用户信息（查询回显） 以及弹窗
const job = ref({})
const editFields = [
    { label: '岗位名称', prop: 'title', component: 'el-input', props: { autocomplete: 'off' } },
    { label: '需求人数', prop: 'demandNumber', component: 'el-input', props: { autocomplete: 'off' } },
    { label: '已聘人数', prop: 'hiredNumber', component: 'el-input', props: { autocomplete: 'off' } },
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
.container {
    margin: 10px;
}
</style>