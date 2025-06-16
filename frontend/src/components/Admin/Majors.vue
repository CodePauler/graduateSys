<template>
    <!-- 搜索栏和批量操作按钮并排 -->
    <div class="container search-bar-row">
        <SearchBar :fields="searchFields" :model="searchMajor" @search="search" @clear="clear">
            <template #batch-action>
                <el-button type="danger" :disabled="!multipleSelection.length" @click="handleBatchDelete"
                    style="margin-left:8px;">批量删除</el-button>
            </template>
        </SearchBar>
    </div>
    <!-- 新增专业 -->
    <div class="container">
        <el-button type="primary" @click="openAddDialog()">新增专业</el-button>
    </div>
    <!-- 新增专业弹窗 -->
    <div class="container">
        <EditDialog v-model="addDialogVisible" :title="'新增专业'" :model="addMajor" :fields="addFields"
            @submit="addMajorSubmit" />
    </div>
    <!-- 数据表格 -->
    <div class="center-container">
        <DataTable :data="majorInfo" :columns="tableColumns" :actions="tableActions" :pagination="pagination"
            @page-change="handleCurrentChange" @size-change="handleSizeChange"
            @selection-change="handleSelectionChange" />
    </div>
    <!-- 编辑弹窗 -->
    <div class="container">
        <EditDialog v-model="dialogFormVisible" :title="'专业'" :model="major" :fields="editFields" @submit="saveMajor" />
    </div>
</template>

<script setup>
import DataTable from '@/components/DataTable.vue';
import SearchBar from '@/components/SearchBar.vue';
import EditDialog from '@/components/EditDialog.vue';
import { ref, reactive, onMounted, computed } from 'vue';
import { ElMessage, ElMessageBox } from 'element-plus';
import { queryMajorApi, queryMajorByIdApi } from '@/api/public/majors';
import { updateMajorApi, addMajorApi, deleteMajorApi } from '@/api/admin/majors';
import { queryDepartmentApi } from '@/api/public/departments';
const options = ref()
const departments = ref([])
// 新增专业弹窗
const addDialogVisible = ref(false)
const openAddDialog = () => {
    addDialogVisible.value = true
    addMajor.value = {
        majorName: '',
        departmentId: '',
        departmentName: ''
    }
}
const addMajor = ref({
    majorName: '',
    departmentId: '',
    departmentName: ''
})
const addFields = [
    { label: '专业名称', prop: 'majorName', component: 'el-input', props: { placeholder: '请输入专业名称', clearable: true } },
    {
        label: '所属专业', prop: 'departmentId', component: 'el-select', props: { placeholder: '请选择所属专业', clearable: true },
        options: options.value
    }
]
const addMajorSubmit = async () => {
    const res = await addMajorApi(addMajor.value)
    if (res.code === 1) {
        ElMessage.success('新增专业成功')
        addDialogVisible.value = false
        search()
    } else {
        ElMessage.error(res.msg)
    }
}

// 编辑弹窗
const dialogFormVisible = ref(false)
const major = ref({
    majorId: '',
    majorName: '',
    departmentId: '',
})
const editFields = [
    { label: '专业ID', prop: 'majorId', component: 'el-input', props: { disabled: true } },
    { label: '专业名称', prop: 'majorName', component: 'el-input', props: { placeholder: '请输入专业名称', clearable: true } },
    { label: '所属专业', prop: 'departmentId', component: 'el-select', props: { placeholder: '请选择所属专业', clearable: true }, options: options.value },
]
const saveMajor = async () => {
    const res = await updateMajorApi(major.value)
    if (res.code === 1) {
        ElMessage.success('修改专业信息成功')
        dialogFormVisible.value = false
        search()
    } else {
        ElMessage.error(res.msg)
    }
}

// 搜索栏
const searchMajor = reactive({
    majorId: '',
    majorName: '',
    departmentId: '',
    departmentName: '',
    page: 1,
    pageSize: 10
})
const departmentOptions = computed(() => options.value || [])
const searchFields = [
    { label: '专业ID', prop: 'majorId', component: 'el-input', props: { placeholder: '请输入专业ID', clearable: true } },
    { label: '专业名称', prop: 'majorName', component: 'el-input', props: { placeholder: '请输入专业名称', clearable: true } },
    {
        label: '所属学院', prop: 'departmentId', component: 'el-select', props: { placeholder: '请输入学院名称', clearable: true }, options: departmentOptions
    }
]



// 表格
const majorInfo = ref([])
const tableColumns = [
    { label: '专业ID', prop: 'majorId', width: 100 },
    { label: '专业名称', prop: 'majorName', width: 200 },
    { label: '所属学院', prop: 'departmentName', width: 200 },
]
const tableActions = [
    {
        label: '编辑',
        type: 'primary',
        icon: 'el-icon-edit',
        onClick: async (row) => {
            dialogFormVisible.value = true
            const res = await queryMajorByIdApi(row.majorId)
            if (res.code === 1) {
                console.log("查询专业信息成功", res.data);
                major.value = res.data
                search()
            } else {
                console.error("查询专业信息失败", res.msg);
                ElMessage.error(res.msg)
            }
        }
    },
    {
        label: '删除',
        type: 'danger',
        icon: 'el-icon-delete',
        onClick: (row) => {
            ElMessageBox.confirm(`确定要删除专业 ${row.majorName} 吗？`, '警告', {
                confirmButtonText: '确认删除',
                cancelButtonText: '取消',
                type: 'warning',
            }).then(async () => {
                const result = await deleteMajorApi(row.majorId)
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
    searchMajor.page = page;
    console.log("当前页码", page);
    search();
}
const handleSizeChange = (pageSize) => {
    pagination.pageSize = pageSize;
    searchMajor.pageSize = pageSize;
    console.log("每页条数", pageSize);
    search();
}

const search = async () => {
    let res;
    res = await queryMajorApi(searchMajor)
    if (res.code === 1) {
        console.log("查询专业列表成功", res.data);
        majorInfo.value = res.data.rows;
        pagination.total = res.data.total;
    } else {
        console.error("查询专业列表失败", res.msg);
        ElMessage.error(res.msg);
    }

    res = await queryDepartmentApi()
    if (res.code === 1) {
        console.log("查询学院列表成功", res.data);
        departments.value = res.data.rows;
        options.value = departments.value.map(dept => ({
            value: dept.departmentId,
            label: dept.departmentName
        }));
        addFields[1].options = options.value;
        editFields[2].options = options.value;
        searchFields[2].options = options.value;
        console.log("专业列表", searchFields[2].options);
    } else {
        console.error("查询专业列表失败", res.msg);
        ElMessage.error(res.msg);
    }
}
const clear = () => {
    searchMajor.majorId = '';
    searchMajor.majorName = '';
    searchMajor.departmentId = '';
    searchMajor.departmentName = '';
    search();
}
onMounted(() => {
    search();
});

// 批量删除
const role = localStorage.getItem('role')
const adminTab = ref('majors')
const multipleSelection = ref([])
const handleSelectionChange = (val) => {
    multipleSelection.value = val
}
const handleBatchDelete = () => {
    if (!multipleSelection.value.length) return
    ElMessageBox.confirm(`确定要删除选中的${multipleSelection.value.length}个专业吗？`, '警告', {
        confirmButtonText: '确认删除',
        cancelButtonText: '取消',
        type: 'warning',
    }).then(async () => {
        const ids = multipleSelection.value.map(item => item.majorId)
        const result = await deleteMajorApi(ids)
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