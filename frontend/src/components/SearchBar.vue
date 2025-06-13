<!-- 搜索栏组件 -->
<template>
  <el-form :inline="true" :model="model" class="demo-form-inline">
    <el-form-item v-for="item in fields" :key="item.prop" :label="item.label">
      <component :is="item.component" v-model="model[item.prop]" v-bind="item.props">
        <template v-if="item.options" #default>
          <el-option v-for="option in item.options" :key="option.value" :label="option.label" :value="option.value" />
        </template>
      </component>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="$emit('search')">查询</el-button>
      <el-button @click="$emit('clear')">清空</el-button>
    </el-form-item>
  </el-form>
</template>

<script setup>
defineProps({
  fields: Array,  // [{ label, prop, component: 'el-input', props: { placeholder: '' } }]
  model: Object
})
defineEmits(['search', 'clear'])
</script>
<style scoped>
.demo-form-inline .el-select {
  --el-select-width: 120px;
}

.demo-form-inline .el-input {
  width: 150px;
}
</style>
