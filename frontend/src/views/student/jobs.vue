<template>
    <h1>岗位列表</h1>
    <!-- 搜索栏 -->
    <div class="container">
        <SearchBar :fields="searchFields" :model="searchJob" @search="search" @clear="clear" />
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
// 搜索栏字段配置
const searchFields = [
    { label: '岗位名称', prop: 'title', component: 'el-input', props: { placeholder: '请输入岗位名称', clearable: true } },
    { label: '需求人数', prop: 'demandNumber', component: 'el-input', props: { placeholder: '请输入需求人数', clearable: true } },
    { label: '已聘人数', prop: 'hiredNumber', component: 'el-input', props: { placeholder: '请输入已聘人数', clearable: true } },
    { label: '发布公司', prop: 'companyName', component: 'el-input', props: { placeholder: '请输入发布公司', clearable: true } },
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
// 搜索用户列表*
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
    searchJob.title = '';
    searchJob.demandNumber = '';
    searchJob.hiredNumber = '';
    searchJob.status = '';
    searchJob.companyName = '';
    searchJob.page = 1;
    searchJob.pageSize = 10;
    search();
}


// 表格列配置
const jobInfo = ref([])
const tableColumns = [
    { prop: 'title', label: '岗位名称' },
    { prop: 'demandNumber', label: '需求人数' },
    { prop: 'hiredNumber', label: '已聘人数' },
    { prop: 'companyName', label: '发布公司名称' },
    { prop: 'status', label: '审核状态' }//可以做成下拉框进行审核操作
]
const pagination = reactive({
    page: 1,
    pageSize: 10,
    total: 0
})
// 表格操作按钮
const tableActions = [
    {
        label: '编辑',
        type: 'primary',
        onClick: async (row) => {
            //const result = await queryJobByIdApi(row.id)//queryJobByIdApi待定义
            dialogFormVisible.value = true//临时移动
            if (result.code === 1) {
                user.value = result.data
                //dialogFormVisible.value = true
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
                const result = await deleteJobApi(row.id)//deleteJobApi待定义
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
const dialogFormVisible = ref(false)
const job = ref({})
const editFields = [
    { label: '岗位名称', prop: 'title', component: 'el-input', props: { autocomplete: 'off' } },
    { label: '需求人数', prop: 'demandNumber', component: 'el-input', props: { autocomplete: 'off' } },
    { label: '已聘人数', prop: 'hiredNumber', component: 'el-input', props: { autocomplete: 'off' } },
    { label: '发布公司', prop: 'companyName', component: 'el-input', props: { autocomplete: 'off' } },
    {
        label: '审核状态', prop: 'status', component: 'el-select', props: { autocomplete: 'off' },
        options: [
            { label: '待审核', value: '待审核' },
            { label: '已通过', value: '已通过' },
            { label: '不通过', value: '不通过' }
        ]
    },
    { label: '描述', prop: 'discription', component: 'el-input', props: { autocomplete: 'off' } }

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