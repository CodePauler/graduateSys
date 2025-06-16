<template>
    <div class="headerContent">用户管理</div>
    <!-- 搜索栏和批量操作按钮并排 -->
    <div class="container search-bar-row">
        <SearchBar :fields="searchFields" :model="searchUser" @search="search" @clear="clear">
            <template #batch-action>
                <el-button type="primary" @click="openCreateDialog" style="margin-left:8px;">创建用户</el-button>
                <el-button type="danger" :disabled="!multipleSelection.length" @click="handleBatchDelete" style="margin-left:8px;">批量删除</el-button>
            </template>
        </SearchBar>
    </div>
    <div class="container">
        <!-- 数据表格 -->
        <DataTable :data="userInfo" :columns="tableColumns" :actions="tableActions" :pagination="pagination"
            @page-change="handleCurrentChange" @size-change="handleSizeChange" @selection-change="handleSelectionChange" />
    </div>

    <!-- 编辑弹窗 -->
    <EditDialog v-model="dialogFormVisible" :title="'用户编辑'" :model="user" :fields="editFields" @submit="saveUser" />
    <!-- 创建用户弹窗（动态表单） -->
    <el-dialog v-model="createDialogVisible" title="创建用户" width="500px">
      <el-form :model="createUser" label-width="80px">
        <el-form-item label="身份" required>
          <el-select v-model="createUser.role" placeholder="请选择身份">
            <el-option label="管理员" value="admin" />
            <el-option label="学生" value="student" />
            <el-option label="企业" value="company" />
          </el-select>
        </el-form-item>
        <el-form-item label="用户名" required>
          <el-input v-model="createUser.username" />
        </el-form-item>
        <el-form-item label="密码" required>
          <el-input v-model="createUser.password" type="password" />
        </el-form-item>
        <el-form-item label="姓名" required>
          <el-input v-model="createUser.name" />
        </el-form-item>
        <el-form-item label="性别" required>
          <el-select v-model="createUser.gender" placeholder="请选择性别">
            <el-option label="男" value="男" />
            <el-option label="女" value="女" />
          </el-select>
        </el-form-item>
        <el-form-item label="邮箱">
          <el-input v-model="createUser.email" />
        </el-form-item>
        <el-form-item label="电话">
          <el-input v-model="createUser.phone" />
        </el-form-item>
        <!-- 学生专属字段 -->
        <template v-if="createUser.role === 'student'">
          <el-form-item label="学号" required>
            <el-input v-model="createUser.studentId" />
          </el-form-item>
          <el-form-item label="专业" required>
            <el-select v-model="createUser.majorId" placeholder="请选择专业">
              <el-option label="计算机科学" value="1" />
              <el-option label="软件工程" value="2" />
              <el-option label="电子信息" value="3" />
            </el-select>
          </el-form-item>
          <el-form-item label="毕业年限" required>
            <el-select v-model="createUser.graduationYear" placeholder="请选择毕业年限">
              <el-option label="2024" value="2024" />
              <el-option label="2025" value="2025" />
              <el-option label="2026" value="2026" />
            </el-select>
          </el-form-item>
        </template>
        <!-- 企业专属字段 -->
        <template v-if="createUser.role === 'company'">
          <el-form-item label="企业名称" required>
            <el-input v-model="createUser.companyName" />
          </el-form-item>
          <el-form-item label="企业简介" required>
            <el-input v-model="createUser.companyIntro" type="textarea" />
          </el-form-item>
        </template>
      </el-form>
      <template #footer>
        <el-button @click="handleCreateDialogCancel">取消</el-button>
        <el-button type="primary" @click="createUserSubmit">确定</el-button>
      </template>
    </el-dialog>
</template>
<script setup>
import DataTable from '@/components/DataTable.vue';
import SearchBar from '@/components/SearchBar.vue';
import EditDialog from '@/components/EditDialog.vue';
import { ref, reactive, onMounted } from 'vue';
import { ElMessage, ElMessageBox } from 'element-plus';
import { queryUsersApi, queryUserByIdApi, updateUserApi, deleteUserApi } from '@/api/admin/users';
import { registerApi } from '@/api/login';
// 搜索栏字段配置
const searchFields = [
    { label: '用户名', prop: 'username', component: 'el-input', props: { placeholder: '请输入用户名', clearable: true } },
    { label: '姓名', prop: 'name', component: 'el-input', props: { placeholder: '请输入姓名', clearable: true } },
    {
        label: '身份', prop: 'role', component: 'el-select', props: { placeholder: '请选择身份', clearable: true },
        options: [
            { label: '管理员', value: 'admin' },
            { label: '学生', value: 'student' },
            { label: '企业', value: 'company' }
        ]
    },
    {
        label: '性别', prop: 'gender', component: 'el-select', props: { placeholder: '请选择性别', clearable: true },
        options: [
            { label: '男', value: '男' },
            { label: '女', value: '女' }
        ]
    }
]
const pagination = reactive({
    page: 1,
    pageSize: 10,
    total: 0
})
// 分页处理
const handleCurrentChange = (page) => {
    pagination.page = page;
    searchUser.page = page;
    search();
    console.log(`当前页码: ${page}`)
}
const handleSizeChange = (size) => {
    pagination.pageSize = size;
    searchUser.pageSize = size;
    search();
    console.log(`每条页数: ${size}`)
}
const searchUser = reactive({
    username: '',
    name: '',
    gender: '',
    role: '',
    page: 1,
    pageSize: 10
})
// 搜索用户列表
const search = async () => {
    const res = await queryUsersApi(searchUser);
    if (res.code === 1) {
        console.log("查询用户列表成功", res.data);
        userInfo.value = res.data.rows;
        pagination.total = res.data.total;
    } else {
        console.error("查询用户列表失败", res.data);
    }
}
// 清空搜索条件
const clear = () => {
    searchUser.username = '';
    searchUser.name = '';
    searchUser.gender = '';
    searchUser.role = '';
    searchUser.page = 1;
    searchUser.pageSize = 10;
    search();
}


// 表格列配置
const userInfo = ref([])
const tableColumns = [
    { prop: 'username', label: '用户名' },
    { prop: 'name', label: '姓名' },
    { prop: 'role', label: '身份' },
    { prop: 'gender', label: '性别' },
    { prop: 'email', label: '邮箱' },
    { prop: 'phone', label: '电话' }
]

// 表格操作按钮
const tableActions = [
    {
        label: '编辑',
        type: 'primary',
        onClick: async (row) => {
            const result = await queryUserByIdApi(row.id)
            if (result.code === 1) {
                user.value = result.data
                dialogFormVisible.value = true
            }
        }
    },
    {
        label: '删除',
        type: 'danger',
        onClick: (row) => {
            ElMessageBox.confirm('是否确认删除用户？', '警告', {
                confirmButtonText: '确认删除',
                cancelButtonText: '取消',
                type: 'warning',
            }).then(async () => {
                const result = await deleteUserApi(row.id)
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
const user = ref({})
const editFields = [
    { label: '用户名', prop: 'username', component: 'el-input', props: { autocomplete: 'off' } },
    { label: '密码', prop: 'password', component: 'el-input', props: { autocomplete: 'off' } },
    { label: '姓名', prop: 'name', component: 'el-input', props: { autocomplete: 'off' } },
    {
        label: '身份', prop: 'role', component: 'el-select', props: { placeholder: '请选择身份' }, options: [
            { label: '管理员', value: 'admin' },
            { label: '学生', value: 'teacher' },
            { label: '企业', value: 'company' }
        ]
    },
    {
        label: '性别', prop: 'gender', component: 'el-select', props: { placeholder: '请选择性别' }, options: [
            { label: '男', value: '男' },
            { label: '女', value: '女' }
        ]
    },
    { label: '邮箱', prop: 'email', component: 'el-input', props: { autocomplete: 'off' } },
    { label: '电话', prop: 'phone', component: 'el-input', props: { autocomplete: 'off' } }
]
// 保存修改
const saveUser = async () => {
    dialogFormVisible.value = false;
    const result = await updateUserApi(user.value);
    if (result.code === 1) {
        ElMessage.success('用户信息更新成功')
        search()
    } else {
        ElMessage.error(result.msg)
    }
}

const createDialogVisible = ref(false)
const createUser = ref({
    username: '',
    password: '',
    name: '',
    gender: '',
    role: '',
    email: '',
    phone: '',
    studentId: '',
    majorId: '',
    graduationYear: '',
    companyName: '',
    companyIntro: ''
})
const openCreateDialog = () => {
    createUser.value = {
        username: '', password: '', name: '', gender: '', role: '', email: '', phone: '', studentId: '', majorId: '', graduationYear: '', companyName: '', companyIntro: ''
    }
    createDialogVisible.value = true
}
const handleCreateDialogCancel = () => {
    createDialogVisible.value = false
}
const createUserSubmit = async () => {
    // 校验基础信息
    if (!createUser.value.username || !createUser.value.password || !createUser.value.name || !createUser.value.role || !createUser.value.gender) {
        ElMessage.error('请填写完整信息')
        return
    }
    // 校验学生专属
    if (createUser.value.role === 'student' && (!createUser.value.studentId || !createUser.value.majorId || !createUser.value.graduationYear)) {
        ElMessage.error('请填写学号、专业和毕业年限')
        return
    }
    // 校验企业专属
    if (createUser.value.role === 'company' && (!createUser.value.companyName || !createUser.value.companyIntro)) {
        ElMessage.error('请填写企业名称和简介')
        return
    }
    try {
        const params = { ...createUser.value }
        // 判断当前登录用户是否为管理员
        const isAdmin = localStorage.getItem('role') === 'admin';
        const res = await registerApi(params, isAdmin && createUser.value.role === 'admin')
        if (res.code === 1) {
            ElMessage.success('创建用户成功')
            createDialogVisible.value = false
            search()
        } else if (res.msg && res.msg.includes('用户名已存在')) {
            ElMessage.error('用户名重复，请更换用户名')
        } else {
            ElMessage.error(res.data?.msg || res.msg || '创建失败')
        }
    } catch (e) {
        ElMessage.error('网络错误')
    }
}

const multipleSelection = ref([])
const handleSelectionChange = (val) => {
    multipleSelection.value = val
}
const handleBatchDelete = () => {
    if (!multipleSelection.value.length) return
    ElMessageBox.confirm(`确定要删除选中的${multipleSelection.value.length}个用户吗？`, '警告', {
        confirmButtonText: '确认删除',
        cancelButtonText: '取消',
        type: 'warning',
    }).then(async () => {
        const ids = multipleSelection.value.map(item => item.id)
        const result = await deleteUserApi(ids)
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

onMounted(() => {
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