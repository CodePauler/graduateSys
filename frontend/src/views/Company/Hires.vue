<!-- <template>
    <div class="headerContent">
        遇见你的千里马
    </div>
</template> -->
<template>
  <div class="hires-container">
    <div class="headerContent">企业招聘管理</div>
    
    <!-- 岗位主表格 -->
    <el-table 
      :data="jobs" 
      row-key="job_id" 
      :expand-row-keys="expandedRows"
      style="width: 100%"
    >
      <!-- 展开行 -->
      <el-table-column type="expand">
        <template #default="props">
          <!-- 简历卡片展示区 -->
          <div class="resume-container">
            <template v-if="props.row.resumes.length > 0">
              <el-card 
                v-for="resume in props.row.resumes" 
                :key="resume.id"
                class="resume-card"
                shadow="hover"
              >
                <template #header>
                  <div class="card-header">
                    <div class="student-info">
                      <el-avatar :src="resume.avatar" size="small" />
                      <span style="margin-left: 10px;">{{ resume.name }}</span>
                    </div>
                    <el-tag :type="getStatusType(resume.status)" size="small">
                      {{ getStatusText(resume.status) }}
                    </el-tag>
                  </div>
                </template>
                <div class="card-content">
                  <p><i class="el-icon-school"></i> {{ resume.university }} · {{ resume.major }}</p>
                  <p><i class="el-icon-time"></i> 投递时间: {{ resume.apply_time }}</p>
                  <p><i class="el-icon-document"></i> 
                    <el-link type="primary" :href="resume.resume_url" target="_blank">查看完整简历</el-link>
                  </p>
                </div>
                <div class="card-actions">
                  <el-button-group>
                    <el-button 
                      type="primary" 
                      size="small"
                      @click="handleResumeAction(resume, 'view')"
                    >
                      查看详情
                    </el-button>
                    <el-button 
                      size="small"
                      @click="handleResumeAction(resume, 'contact')"
                    >
                      <i class="el-icon-chat-dot-round"></i> 联系
                    </el-button>
                    <el-button 
                      size="small"
                      @click="handleResumeAction(resume, 'interview')"
                      :disabled="resume.status === 2"
                    >
                      {{ resume.status === 2 ? '已安排' : '安排面试' }}
                    </el-button>
                  </el-button-group>
                </div>
              </el-card>
            </template>
            <el-empty v-else description="暂无简历投递" :image-size="80" />
          </div>
        </template>
      </el-table-column>

      <!-- 岗位信息列 -->
      <el-table-column label="岗位名称" prop="title" width="200" />
      <el-table-column label="薪资范围" width="150">
        <template #default="scope">
          {{ scope.row.salary_range }}
        </template>
      </el-table-column>
      <el-table-column label="发布状态" width="120">
        <template #default="scope">
          <el-tag :type="scope.row.status ? 'success' : 'warning'" size="small">
            {{ scope.row.status ? '招聘中' : '已结束' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="简历数量" width="120">
        <template #default="scope">
          <el-badge :value="scope.row.resume_count" :max="99" type="primary" />
        </template>
      </el-table-column>
      <el-table-column label="操作" width="150">
        <template #default="scope">
          <el-button 
            size="small" 
            @click="toggleExpand(scope.row)"
            :icon="expandedRows.includes(scope.row.job_id) ? 'el-icon-arrow-up' : 'el-icon-arrow-down'"
          >
            {{ expandedRows.includes(scope.row.job_id) ? '收起' : '展开' }}
          </el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
export default {
  data() {
    return {
      jobs: [
        {
          job_id: 1001,
          title: "前端开发工程师",
          salary_range: "15k-25k",
          status: true,
          resume_count: 3,
          resumes: [
            {
              id: "resume_3001",
              name: "王小明的简历",
              avatar: "https://pic1.zhimg.com/80/v2-6afa72220d29f045c15217aa6b275808_720w.webp",
              university: "清华大学",
              major: "计算机科学与技术",
              apply_time: "2023-06-15 14:30:22",
              status: 1,
              resume_url: "https://example.com/resumes/3001.pdf"
            },
            {
              id: "resume_3002",
              name: "张伟的简历",
              avatar: "https://pic1.zhimg.com/80/v2-3b7c6d1c4f4e8b4a0b4e4d4e4f4e4d4e_720w.webp",
              university: "北京大学",
              major: "软件工程",
              apply_time: "2023-06-16 09:15:47",
              status: 0,
              resume_url: "https://example.com/resumes/3002.pdf"
            },
            {
              id: "resume_3003",
              name: "李娜的简历",
              avatar: "https://pic1.zhimg.com/80/v2-6afa72220d29f045c15217aa6b275808_720w.webp",
              university: "浙江大学",
              major: "人工智能",
              apply_time: "2023-06-17 11:20:33",
              status: 2,
              resume_url: "https://example.com/resumes/3003.pdf"
            }
          ]
        },
        {
          job_id: 1002,
          title: "Java后端开发",
          salary_range: "20k-30k",
          status: true,
          resume_count: 2,
          resumes: [
            {
              id: "resume_3004",
              name: "陈阳的简历",
              avatar: "https://pic1.zhimg.com/80/v2-3b7c6d1c4f4e8b4a0b4e4d4e4f4e4d4e_720w.webp",
              university: "上海交通大学",
              major: "信息安全",
              apply_time: "2023-06-18 16:45:12",
              status: 0,
              resume_url: "https://example.com/resumes/3004.pdf"
            }
          ]
        },
        {
          job_id: 1003,
          title: "UI设计师",
          salary_range: "12k-18k",
          status: false,
          resume_count: 0,
          resumes: []
        }
      ],
      expandedRows: [1001] // 默认展开第一个岗位
    }
  },
  methods: {
    getStatusType(status) {
      const map = { 0: 'info', 1: 'warning', 2: 'success' }
      return map[status] || 'info'
    },
    getStatusText(status) {
      const map = { 0: '待处理', 1: '已查看', 2: '已安排面试' }
      return map[status] || '未知状态'
    },
    toggleExpand(row) {
      const key = row.job_id
      if (this.expandedRows.includes(key)) {
        this.expandedRows = this.expandedRows.filter(id => id !== key)
      } else {
        this.expandedRows.push(key)
      }
    },
    handleResumeAction(resume, action) {
      this.$message.success(`操作：${action} | 简历ID：${resume.id}`)
      // 实际开发中这里调用API
      if (action === 'interview') {
        resume.status = 2
      }
    }
  }
}
</script>

<style scoped>
.hires-container {
  padding: 20px;
  background: #fff;
  border-radius: 4px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}

.resume-container {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(320px, 1fr));
  gap: 15px;
  padding: 10px 5px;
}

.resume-card {
  transition: all 0.3s;
  margin-bottom: 0;
}

.resume-card:hover {
  transform: translateY(-3px);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 10px 15px !important;
}

.student-info {
  display: flex;
  align-items: center;
}

.card-content {
  padding: 0 15px 10px;
}

.card-content p {
  margin: 8px 0;
  color: #606266;
  font-size: 13px;
  display: flex;
  align-items: center;
}

.card-content i {
  margin-right: 5px;
  color: #909399;
}

.card-actions {
  padding: 10px 15px;
  border-top: 1px solid #f0f0f0;
}

.el-avatar {
  background-color: #f5f7fa;
}
</style>