<template>
    <!-- 搜索栏和批量操作按钮并排 -->
    <div class="container search-bar-row">
        <SearchBar :fields="searchFields" :model="searchDepartment" @search="search" @clear="clear">
            <template #batch-action>
                <el-button type="danger" :disabled="!multipleSelection.length" @click="handleBatchDelete"
                    style="margin-left:8px;">批量删除</el-button>
            </template>
        </SearchBar>
    </div>
    <!-- 新增学院 -->
    <div class="container">
        <el-button type="primary" @click="openAddDialog()">新增学院</el-button>
    </div>
    <!-- 新增学院弹窗 -->
    <div class="container">
        <EditDialog v-model="addDialogVisible" :title="'新增学院'" :model="addDepartment" :fields="addFields"
            @submit="addDepartmentSubmit" />
    </div>
    <!-- 数据表格 -->
    <div class="center-container">
        <DataTable :data="departmentInfo" :columns="tableColumns" :actions="tableActions" :pagination="pagination"
            @page-change="handleCurrentChange" @size-change="handleSizeChange"
            @selection-change="handleSelectionChange" />
    </div>
    <!-- 编辑弹窗 -->
    <div class="container">
        <EditDialog v-model="dialogFormVisible" :title="'学院'" :model="department" :fields="editFields"
            @submit="saveDepartment" />
    </div>
</template>

<script setup>
import DataTable from '@/components/DataTable.vue';
import SearchBar from '@/components/SearchBar.vue';
import EditDialog from '@/components/EditDialog.vue';
import { ref, reactive, onMounted } from 'vue';
import { ElMessage, ElMessageBox } from 'element-plus';
import { queryDepartmentApi, queryDepartmentByIdApi } from '@/api/public/departments';
import { updateDepartmentApi, addDepartmentApi, deleteDepartmentApi } from '@/api/admin/departments';

// 新增学院弹窗
const addDialogVisible = ref(false)
const openAddDialog = () => {
    addDialogVisible.value = true
    addDepartment.value = {
        departmentName: ''
    }
}
const addDepartment = ref({
    departmentName: ''
})
const addFields = [
    { label: '学院名称', prop: 'departmentName', component: 'el-input', props: { placeholder: '请输入学院名称', clearable: true } },
]
const addDepartmentSubmit = async () => {
    const res = await addDepartmentApi(addDepartment.value)
    if (res.code === 1) {
        ElMessage.success('新增学院成功')
        addDialogVisible.value = false
        search()
    } else {
        ElMessage.error(res.msg)
    }
}

// 编辑弹窗
const dialogFormVisible = ref(false)
const department = ref({
    departmentName: ''
})
const editFields = [
    { label: '学院ID', prop: 'departmentId', component: 'el-input', props: { disabled: true } },
    { label: '学院名称', prop: 'departmentName', component: 'el-input', props: { placeholder: '请输入学院名称', clearable: true } },
]
const saveDepartment = async () => {
    const res = await updateDepartmentApi(department.value)
    if (res.code === 1) {
        ElMessage.success('修改学院信息成功')
        dialogFormVisible.value = false
        search()
    } else {
        ElMessage.error(res.msg)
    }
}

// 搜索栏
const searchFields = [
    { label: '学院ID', prop: 'departmentId', component: 'el-input', props: { placeholder: '请输入学院ID', clearable: true } },
    { label: '学院名称', prop: 'departmentName', component: 'el-input', props: { placeholder: '请输入学院名称', clearable: true } },
]
const searchDepartment = reactive({
    departmentId: '',
    departmentName: '',
    page: 1,
    pageSize: 10
})

// 表格
const departmentInfo = ref([])
const tableColumns = [
    { label: '学院ID', prop: 'departmentId', width: 100 },
    { label: '学院名称', prop: 'departmentName', width: 200 },
]
const tableActions = [
    {
        label: '编辑',
        type: 'primary',
        icon: 'el-icon-edit',
        onClick: async (row) => {
            dialogFormVisible.value = true
            const res = await queryDepartmentByIdApi(row.departmentId)
            if (res.code === 1) {
                console.log("查询学院信息成功", res.data);
                department.value = res.data
                search()
            } else {
                console.error("查询学院信息失败", res.msg);
                ElMessage.error(res.msg)
            }
        }
    },
    {
        label: '删除',
        type: 'danger',
        icon: 'el-icon-delete',
        onClick: (row) => {
            ElMessageBox.confirm(`确定要删除学院 ${row.departmentName} 吗？`, '警告', {
                confirmButtonText: '确认删除',
                cancelButtonText: '取消',
                type: 'warning',
            }).then(async () => {
                const result = await deleteDepartmentApi(row.departmentId)
                if (result.code === 1) {
                    ElMessage.success('删除成功')
                    search()
                } else {
                    ElMessage.error(result.msg)
                }
            }).catch(() => {
                ElMessage.info('已取消删除')
            })
        }
    }
]


// 分页
const pagination = reactive({
    page: 1,
    pageSize: 10,
    total: 0
})
const handleCurrentChange = (page) => {
    pagination.page = page;
    searchDepartment.page = page;
    console.log("当前页码", page);
    search();
}
const handleSizeChange = (pageSize) => {
    pagination.pageSize = pageSize;
    searchDepartment.pageSize = pageSize;
    console.log("每页条数", pageSize);
    search();
}

// 获取学院
const search = async () => {
    const res = await queryDepartmentApi(searchDepartment)
    if (res.code === 1) {
        console.log("查询学院列表成功", res.data);
        departmentInfo.value = res.data.rows;
        pagination.total = res.data.total;
    } else {
        console.error("查询学院列表失败", res.msg);
        ElMessage.error(res.msg);
    }
}
const clear = () => {
    searchDepartment.departmentId = '';
    searchDepartment.departmentName = '';
    search();
}
onMounted(() => {
    search();
});

// 批量删除
const role = localStorage.getItem('role')
const adminTab = ref('departments')
const multipleSelection = ref([])
const handleSelectionChange = (val) => {
    multipleSelection.value = val
}
const handleBatchDelete = () => {
    if (!multipleSelection.value.length) return
    ElMessageBox.confirm(`确定要删除选中的${multipleSelection.value.length}个学院吗？`, '警告', {
        confirmButtonText: '确认删除',
        cancelButtonText: '取消',
        type: 'warning',
    }).then(async () => {
        const ids = multipleSelection.value.map(item => item.departmentId)
        const result = await deleteDepartmentApi(ids)
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
<style scoped></style>