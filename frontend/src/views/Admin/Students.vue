<template>
    <div class="headerContent">毕业生管理</div>
    <!-- 搜索栏和批量操作按钮并排 -->
    <div class="container search-bar-row">
        <SearchBar :fields="searchFields" :model="searchStudent" @search="search" @clear="clear">
            <template #batch-action>
                <el-button type="danger" :disabled="!multipleSelection.length" @click="handleBatchDelete" style="margin-left:8px;">批量删除</el-button>
            </template>
        </SearchBar>
    </div>
    <!-- 数据表格 -->
    <div class="container">
        <DataTable :data="studentInfo" :columns="tableColumns" :actions="tableActions" :pagination="pagination"
            @page-change="handleCurrentChange" @size-change="handleSizeChange" @selection-change="handleSelectionChange" />
    </div>

    <!-- 编辑弹窗 -->
    <div class="container">
        <EditDialog v-model="dialogFormVisible" :title="'学生信息'" :model="student" :fields="editFields"
            @submit="saveStudent" />
    </div>
</template>
<script setup>
import { onMounted, reactive, ref } from 'vue';
import SearchBar from '@/components/SearchBar.vue';
import DataTable from '@/components/DataTable.vue';
import { queryStudentByIdApi, queryStudentsApi, deleteStudentApi } from '@/api/admin/students';
import { ElMessage, ElMessageBox } from 'element-plus';
import EditDialog from '@/components/EditDialog.vue';
import { updateStudentApi } from '@/api/admin/students';
// 搜索栏字段配置
const searchFields = [
    { label: '学号', prop: 'studentId', component: 'el-input', props: { placeholder: '请输入学号', clearable: true } },
    { label: '姓名', prop: 'name', component: 'el-input', props: { placeholder: '请输入姓名', clearable: true } },
    {
        label: '性别', prop: 'gender', component: 'el-select', props: { placeholder: '请选择性别', clearable: true },
        options: [
            { label: '男', value: '男' },
            { label: '女', value: '女' }
        ]
    },
    {
        label: '专业', prop: 'majorId', component: 'el-select', props: { placeholder: '请选择专业', clearable: true },
        options: [
            { label: '计算机科学', value: '1' },
            { label: '软件工程', value: '2' },
            { label: '电子信息', value: '3' }
        ]
    },
    {
        label: '学院', prop: 'departmentId', component: 'el-select', props: { placeholder: '请选择学院', clearable: true },
        options: [
            { label: '计算机科学与技术学院', value: '1' },
            { label: '信息科学与技术学院', value: '2' },
            { label: '纺织学院', value: '3' }
        ]
    },
    {
        label: '毕业年份', prop: 'graduationYear', component: 'el-select', props: { placeholder: '请选择毕业年份', clearable: true },
        options: [
            { label: '2025', value: '2025' },
            { label: '2026', value: '2026' },
            { label: '2027', value: '2027' }
        ]
    },
    {
        label: '就业状态', prop: 'employmentStatus', component: 'el-select', props: { placeholder: '请选择就业状态', clearable: true },
        options: [
            { label: '待业', value: '待业' },
            { label: '就业', value: '就业' },
        ]
    }
]
const searchStudent = reactive({
    studentId: '',
    name: '',
    gender: '',
    majorId: '',
    departmentId: '',
    graduationYear: '',
    employmentStatus: '',
    page: 1,
    pageSize: 10
})
const pagination = reactive({
    page: 1,
    pageSize: 10,
    total: 0
})

// 表格
const studentInfo = ref([])
const tableColumns = [
    { prop: 'studentId', label: '学号' },
    { prop: 'name', label: '姓名' },
    { prop: 'gender', label: '性别' },
    { prop: 'major', label: '专业' },
    { prop: 'department', label: '学院' },
    { prop: 'graduationYear', label: '毕业年份' },
    { prop: 'employmentStatus', label: '就业状态' },
]
const tableActions = [
    {
        label: '编辑',
        icon: 'el-icon-edit',
        type: 'primary',
        onClick: async (row) => {
            // 查询回显
            const result = await queryStudentByIdApi(row.studentId);
            if (result.code === 1) {
                console.log("查询学生信息成功", result.data);
                student.value = result.data;
                student.value.majorId = majorOptions.find(option => option[result.data.major])?.[result.data.major] || '';
                dialogFormVisible.value = true;
            } else {
                ElMessage.error(result.msg);
            }
        }
    },
    {
        label: '删除',
        type: 'danger',
        onClick: (row) => {
            ElMessageBox.confirm('是否确认删除该学生？', '警告', {
                confirmButtonText: '确认删除',
                cancelButtonText: '取消',
                type: 'warning',
            }).then(async () => {
                const result = await deleteStudentApi(row.studentId)
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
const majorOptions = [
    { '计算机科学': '1' },
    { '软件工程': '2' },
    { '电子信息': '3' }
]
// 编辑弹窗
const dialogFormVisible = ref(false);
const student = ref({})
const editFields = [
    { label: '学号', prop: 'studentId', component: 'el-input', props: { placeholder: '请输入学号', clearable: true } },
    { label: '姓名', prop: 'name', component: 'el-input', props: { placeholder: '请输入姓名', clearable: true } },
    {
        label: '专业', prop: 'majorId', component: 'el-select', props: { placeholder: '请选择专业', clearable: true },
        options: [
            { label: '计算机科学', value: '1' },
            { label: '软件工程', value: '2' },
            { label: '电子信息', value: '3' }
        ]
    },
    {
        label: '毕业年份', prop: 'graduationYear', component: 'el-select', props: { placeholder: '请选择毕业年份', clearable: true },
        options: [
            { label: '2025', value: '2025' },
            { label: '2026', value: '2026' },
            { label: '2027', value: '2027' }
        ]
    },
]
const saveStudent = async () => {
    dialogFormVisible.value = false;
    const result = await updateStudentApi(student.value);
    if (result.code === 1) {
        ElMessage.success('用户信息更新成功')
        search()
    } else {
        ElMessage.error(result.msg)
    }
}

// 批量删除
const multipleSelection = ref([])
const handleSelectionChange = (val) => {
    multipleSelection.value = val
}
const handleBatchDelete = () => {
    if (!multipleSelection.value.length) return
    ElMessageBox.confirm(`确定要删除选中的${multipleSelection.value.length}个学生吗？`, '警告', {
        confirmButtonText: '确认删除',
        cancelButtonText: '取消',
        type: 'warning',
    }).then(async () => {
        const ids = multipleSelection.value.map(item => item.studentId)
        const result = await deleteStudentApi(ids)
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

// 分页处理
const handleCurrentChange = (page) => {
    pagination.page = page;
    searchStudent.page = page;
    search();
    console.log(`当前页码: ${page}`)
}
const handleSizeChange = (size) => {
    pagination.pageSize = size;
    searchStudent.pageSize = size;
    search();
    console.log(`每条页数: ${size}`)
}
const search = async () => {
    const res = await queryStudentsApi(searchStudent);
    if (res.code === 1) {
        console.log("查询学生列表成功", res.data);
        studentInfo.value = res.data.rows;
        pagination.total = res.data.total;
    }
    else {
        console.error("查询学生列表失败", res.data);
    }
}
const clear = () => {
    searchStudent.studentId = '';
    searchStudent.name = '';
    searchStudent.gender = '';
    searchStudent.majorId = '';
    searchStudent.departmentId = '';
    searchStudent.graduationYear = '';
    searchStudent.employmentStatus = '';
    searchStudent.page = 1;
    searchStudent.pageSize = 10;
    search();
}
onMounted(() => {
    search();
});
</script>
<style>
.container {
    margin: 20px;
}
.search-bar-row {
    display: flex;
    align-items: flex-end;
}
</style>