<template>
    <div class="headerContent">公告管理</div>
    <!-- 搜索栏 -->
    <div class="container">
        <SearchBar :fields="searchFields" :model="searchAnnouncement" @search="search" @clear="clear" />
    </div>

    <!-- 发布公告 -->
    <div class="container">
        <el-button type="primary" @click="openAddDialog()">发布公告</el-button>
    </div>
    <!-- 新增公告弹窗 -->
    <div class="container">
        <EditDialog v-model="addDialogVisible" :title="'发布公告'" :model="addAnnouncement" :fields="addFields"
            @submit="addAnnouncementSubmit" />
    </div>

    <!-- 数据表格 -->
    <div class="container">
        <DataTable :data="announcementInfo" :columns="tableColumns" :actions="tableActions" :pagination="pagination"
            @page-change="handleCurrentChange" @size-change="handleSizeChange" />
    </div>

    <!-- 编辑弹窗 -->
    <div class="container">
        <EditDialog v-model="dialogFormVisible" :title="'企业信息'" :model="announcement" :fields="editFields"
            @submit="saveAnnouncement" />
    </div>

</template>
<script setup>
import { onMounted, reactive, ref, watch } from 'vue';
import SearchBar from '@/components/SearchBar.vue';
import DataTable from '@/components/DataTable.vue';
import EditDialog from '@/components/EditDialog.vue';
import { queryAnnouncementsApi, queryAnnouncementByIdApi } from '@/api/public/announcements';
import { insertAnnouncementApi, updateAnnouncementApi, deleteAnnouncementApi } from '@/api/admin/announcements';
import { ElMessage, ElMessageBox } from 'element-plus';

// 发布公告
const addDialogVisible = ref(false); // 弹窗显示状态
const addAnnouncement = ref({
    title: '',
    content: '',
    creatorId: '',
    attachmentName: '',
    attachmentUrl: '',
    isPinned: false
});
const addFields = [
    { label: '标题', prop: 'title', component: 'el-input', props: { placeholder: '请输入标题', clearable: true } },
    { label: '内容', prop: 'content', component: 'el-input', props: { type: 'textarea', placeholder: '请输入内容', clearable: true } },
    { label: '附件名', prop: 'attachmentName', component: 'el-input', props: { placeholder: '请输入附件名', clearable: true } },
    {
        label: '是否置顶', prop: 'isPinned', component: 'el-select', props: { placeholder: '请选择是否置顶', clearable: true },
        options: [
            { label: '是', value: true },
            { label: '否', value: false }
        ]
    }
]
// 打开新增公告弹窗
const openAddDialog = () => {
    addAnnouncement.value = {
        title: '',
        content: '',
        creatorId: localStorage.getItem('userId') || '', // 自动填充当前用户ID
        creatorName: localStorage.getItem('name') || '', // 自动填充当前用户名
        attachmentName: '',
        attachmentUrl: '',
        isPinned: false
    }
    addDialogVisible.value = true
}
const addAnnouncementSubmit = async () => {
    addDialogVisible.value = true;
    const result = await insertAnnouncementApi(addAnnouncement.value);
    if (result.code === 1) {
        console.log("发布公告成功", result.data);
        ElMessage.success("发布公告成功");
        search(); // 刷新公告列表
    }
    else {
        console.error("发布公告失败", result.msg);
        ElMessage.error("发布公告失败: " + result.msg);
    }
    addDialogVisible.value = false; // 关闭弹窗
}




// 搜索
const searchAnnouncement = reactive({
    announcementId: '',
    title: '',
    creatorName: '',
    date: [], //保存date-picker选择的日期范围
    beginDate: '',
    endDate: '',
    page: 1,
    pageSize: 10
})
// 监听date变化，赋给beginDate和endDate
watch(() => searchAnnouncement.date, (newVal, oldVal) => {
    if (newVal.length == 2) {
        searchAnnouncement.beginDate = newVal[0];
        searchAnnouncement.endDate = newVal[1];
    } else {
        searchAnnouncement.beginDate = "";
        searchAnnouncement.endDate = "";
    }
    console.log("监听到日期变化", newVal);
});

const searchFields = [
    { label: '', prop: 'announcementId', component: 'el-input', props: { placeholder: '请输入公告ID', clearable: true } },
    { label: '', prop: 'title', component: 'el-input', props: { placeholder: '请输入标题', clearable: true } },
    { label: '', prop: 'creatorName', component: 'el-input', props: { placeholder: '请输入发布人', clearable: true } },
    {
        label: '', prop: 'date', component: 'el-date-picker',
        props: {
            type: 'daterange',
            format: 'YYYY-MM-DD',
            placeholder: '请选择日期范围',
            clearable: true
        }
    },
]


// 分页
const pagination = reactive({
    page: 1,
    pageSize: 10,
    total: 0
})
const handleCurrentChange = (page) => {
    pagination.page = page;
    searchAnnouncement.page = page;
    console.log("当前页码", page);
    search();
}
const handleSizeChange = (pageSize) => {
    pagination.pageSize = pageSize;
    searchAnnouncement.pageSize = pageSize;
    console.log("每页条数", pageSize);
    search();
}

// 弹窗
const dialogFormVisible = ref(false); // 弹窗显示状态
const announcement = ref({     //表单公告对象
    announcementId: '',
    title: '',
    content: '',
    creatorName: '',
    createTime: '',
    updateTime: '',
    attachmentName: '',
    attachmentUrl: '',
    isPinned: false,
    viewCount: 0
});
const editFields = [
    { label: '公告ID', prop: 'announcementId', component: 'el-input', props: { disabled: true } },
    { label: '标题', prop: 'title', component: 'el-input', props: { placeholder: '请输入标题', clearable: true } },
    { label: '内容', prop: 'content', component: 'el-input', props: { type: 'textarea', placeholder: '请输入内容', clearable: true } },
    { label: '发布人', prop: 'creatorName', component: 'el-input', props: { disabled: true } },
    { label: '发布时间', prop: 'createTime', component: 'el-input', props: { disabled: true } },
    { label: '修改时间', prop: 'updateTime', component: 'el-input', props: { disabled: true } },
    { label: '附件名', prop: 'attachmentName', component: 'el-input', props: { placeholder: '请输入附件名', clearable: true } },
    { label: '附件地址', prop: 'attachmentUrl', component: 'el-input', props: { placeholder: '请输入附件地址', clearable: true } },
    {
        label: '是否置顶', prop: 'isPinned', component: 'el-select', props: { placeholder: '请选择是否置顶', clearable: true },
        options: [
            { label: '是', value: true },
            { label: '否', value: false }
        ]
    },
    { label: '浏览量', prop: 'viewCount', component: 'el-input', props: { disabled: true } }
]


// 数据表格
const announcementInfo = ref([]); //表格公告列表
const tableColumns = [
    { label: '公告ID', prop: 'announcementId' },
    { label: '标题', prop: 'title' },
    { label: '内容', prop: 'content' },
    { label: '发布人', prop: 'creatorName' },
    { label: '发布时间', prop: 'createTime' },
    { label: '修改时间', prop: 'updateTime' },
    { label: '附件名', prop: 'attachmentName' },
    { label: '附件地址', prop: 'attachmentUrl' },
    { label: '是否置顶', prop: 'isPinned' },
    { label: '浏览量', prop: 'viewCount' }
];
const tableActions = [
    {
        label: '编辑',
        icon: 'el-icon-edit',
        type: 'primary',
        onClick: async (row) => {
            dialogFormVisible.value = true;
            const result = await queryAnnouncementByIdApi(row.announcementId);
            if (result.code === 1) {
                console.log("查询公告信息成功", result.data);
                announcement.value = result.data;
                dialogFormVisible.value = true;
            } else {
                ElMessage.error(result.msg);
            }
        }
    },
    {
        label: '删除',
        icon: 'el-icon-delete',
        type: 'danger',
        onClick: (row) => {
            ElMessageBox.confirm("是否确认删除该公告？", "提示", {
                confirmButtonText: "确定",
                cancelButtonText: "取消",
                type: "warning"
            }).then(async () => {
                const result = await deleteAnnouncementApi(row.announcementId);
                if (result.code === 1) {
                    ElMessage.success("删除公告成功");
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
const saveAnnouncement = async () => {
    console.log("保存公告信息", announcement.value);
    dialogFormVisible.value = false;
    const result = await updateAnnouncementApi(announcement.value);
    if (result.code === 1) {
        console.log("保存公告信息成功", result.data);
        ElMessage.success("保存公告信息成功");
        search();
    } else {
        console.error("保存公告信息失败", result.msg);
        ElMessage.error(result.msg);
    }
}


// 获取公告列表
const search = async () => {
    const { date, ...params } = searchAnnouncement
    const result = await queryAnnouncementsApi(params);
    if (result.code === 1) {
        console.log("查询公告列表成功", result.data);
        announcementInfo.value = result.data.rows;
        pagination.total = result.data.total;
    } else {
        ElMessage.error(result.msg);
    }
}
const clear = () => {
    searchAnnouncement.announcementId = '';
    searchAnnouncement.title = '';
    searchAnnouncement.creatorName = '';
    searchAnnouncement.beginDate = '';
    searchAnnouncement.endDate = '';
    search();
}
onMounted(() => {
    search();
});
</script>
<style>
.demo-date-picker {
    display: flex;
    width: 100%;
    padding: 0;
    flex-wrap: wrap;
}
</style>