<template>
    <div class="headerContent">企业管理</div>
    <!-- 搜索栏和批量操作按钮并排 -->
    <div class="container search-bar-row">
        <SearchBar :fields="searchFields" :model="searchCompany" @search="search" @clear="clear">
            <template #batch-action>
                <el-button type="danger" :disabled="!multipleSelection.length" @click="handleBatchDelete"
                    style="margin-left:8px;">批量删除</el-button>
            </template>
        </SearchBar>
    </div>
    <!-- 数据表格 -->
    <div class="container">
        <DataTable :data="company" :columns="tableColumns" :actions="tableActions" :pagination="pagination"
            @page-change="handleCurrentChange" @size-change="handleSizeChange"
            @selection-change="handleSelectionChange" />
    </div>
    <!-- 编辑弹窗 -->
    <div class="container">
        <EditDialog v-model="dialogFormVisible" :title="'企业信息'" :model="Company" :fields="editFields"
            @submit="saveCompany" />
    </div>
</template>
<script setup>
import { onMounted, reactive, ref } from 'vue';
import SearchBar from '@/components/SearchBar.vue';
import DataTable from '@/components/DataTable.vue';
import EditDialog from '@/components/EditDialog.vue';
import { ElMessage, ElMessageBox } from 'element-plus';
import { queryCompanyByIdApi, queryCompaniesApi, deleteCompanyApi, updateCompanyApi } from '@/api/admin/companies';

// 搜索栏字段配置
const searchFields = [
    { label: "企业ID", prop: "companyId", component: "el-input", props: { placeholder: "请输入企业ID", clearable: true } },
    { label: "企业名称", prop: "companyName", component: "el-input", props: { placeholder: "请输入企业名称", clearable: true } },
    { label: "HR姓名", prop: "hrName", component: "el-input", props: { placeholder: "请输入HR姓名", clearable: true } },
    {
        label: "HR性别", prop: "gender", component: "el-select", props: { placeholder: "请选择HR性别", clearable: true },
        options: [
            { label: "男", value: "男" },
            { label: "女", value: "女" }
        ]
    },
    { label: "HR电话", prop: "phone", component: "el-input", props: { placeholder: "请输入HR电话", clearable: true } },
    { label: "HR邮箱", prop: "email", component: "el-input", props: { placeholder: "请输入HR邮箱", clearable: true } },
]

const pagination = reactive({
    page: 1,
    pageSize: 10,
    total: 0
})

// 表格
const company = ref([])
const tableColumns = [
    { label: "企业ID", prop: "companyId" },
    { label: "企业名称", prop: "companyName" },
    { label: "企业简介", prop: "companyIntro" },
    { label: "HR姓名", prop: "hrName" },
    { label: "HR性别", prop: "gender" },
    { label: "HR电话", prop: "phone" },
    { label: "HR邮箱", prop: "email" },
]
const dialogFormVisible = ref(false);
const tableActions = [
    {
        label: "编辑",
        type: "primary",
        icon: "el-icon-edit",
        onClick: async (row) => {
            dialogFormVisible.value = true;
            const result = await queryCompanyByIdApi(row.companyId);
            if (result.code === 1) {
                console.log("查询企业信息成功", result.data);
                Company.value = result.data;
                dialogFormVisible.value = true;
            }
            else {
                ElMessage.error(result.msg);
            }
        }
    },
    {
        label: "删除",
        type: "danger",
        onClick: (row) => {
            ElMessageBox.confirm("是否确认删除该企业？", "提示", {
                confirmButtonText: "确定",
                cancelButtonText: "取消",
                type: "warning"
            }).then(async () => {
                const result = await deleteCompanyApi(row.companyId);
                if (result.code === 1) {
                    ElMessage.success("删除企业成功");
                    search();
                } else {
                    ElMessage.error(result.msg);
                }
            }).catch(() => {
                ElMessage.info("已取消删除");
            })
        }
    }
]

// 弹窗
const Company = ref({})
const editFields = [
    { label: "企业ID", prop: "companyId", component: "el-input", props: { disabled: true } },
    { label: "企业名称", prop: "companyName", component: "el-input", props: { placeholder: "请输入企业名称" } },
    { label: "企业简介", prop: "companyIntro", component: "el-input", props: { type: "textarea", placeholder: "请输入企业简介" } },
    { label: "HR姓名", prop: "hrName", component: "el-input", props: { disabled:true } },
    {
        label: "HR性别", prop: "gender", component: "el-select", props: { disabled:true },
        options: [
            { label: "男", value: "男" },
            { label: "女", value: "女" }
        ]
    },
    { label: "HR电话", prop: "phone", component: "el-input", props: { disabled:true } },
    { label: "HR邮箱", prop: "email", component: "el-input", props: { disabled:true } },
]
const saveCompany = async () => {
    dialogFormVisible.value = false;
    console.log("保存企业信息", Company.value);
    const result = await updateCompanyApi(Company.value);
    if (result.code === 1) {
        console.log("企业信息更新成功", result.data);
        ElMessage.success("企业信息更新成功");
        search(); // 刷新企业列表
    }
    else {
        console.error("企业信息更新失败", result.msg);
        ElMessage.error(result.msg);
    }

}

// 分页
const handleCurrentChange = (page) => {
    pagination.page = page;
    searchCompany.page = page;
    console.log("当前页码", page);
    search();
}
const handleSizeChange = (size) => {
    pagination.pageSize = size;
    searchCompany.pageSize = size;
    console.log("每页条数", size);
    search();
}
const searchCompany = reactive({
    companyId: '',
    companyName: '',
    hrName: '',
    gender: '',
    phone: '',
    email: '',
    page: 1,
    pageSize: 10
})
const search = async () => {
    const result = await queryCompaniesApi(searchCompany);
    if (result.code === 1) {
        console.log("查询企业信息成功", result.data);
        company.value = result.data.rows;
        pagination.total = result.data.total;
    } else {
        ElMessage.error(result.msg);
    }
}
const clear = () => {
    searchCompany.companyId = '';
    searchCompany.companyName = '';
    searchCompany.hrName = '';
    searchCompany.gender = '';
    searchCompany.phone = '';
    searchCompany.email = '';
    searchCompany.page = 1;
    searchCompany.pageSize = 10;
    search();
}
const multipleSelection = ref([])
const handleSelectionChange = (val) => {
    multipleSelection.value = val
}
const handleBatchDelete = () => {
    if (!multipleSelection.value.length) return
    ElMessageBox.confirm(`确定要删除选中的${multipleSelection.value.length}个企业吗？`, '警告', {
        confirmButtonText: '确认删除',
        cancelButtonText: '取消',
        type: 'warning',
    }).then(async () => {
        const ids = multipleSelection.value.map(item => item.companyId)
        const result = await deleteCompanyApi(ids)
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
onMounted(async () => {
    search();
});
</script>
<style scoped>
.container {
    margin: 20px;
}

.search-bar-row {
    display: flex;
    align-items: flex-end;
}
</style>