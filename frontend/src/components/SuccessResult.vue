<template>
  <el-result
    icon="success"
    :title="title"
    :sub-title="subTitle"
  >
    <template #extra>
      <el-button type="primary" @click="handleClick">{{ buttonText }}</el-button>
    </template>
  </el-result>
</template>

<script setup>
import { defineProps, defineEmits } from 'vue'

const props = defineProps({
  title: { type: String, default: '操作成功！' },
  subTitle: { type: String, default: '请继续您的操作' },
  buttonText: { type: String, default: '返回' }
})
const emit = defineEmits(['back'])

function handleClick() {
  emit('back')
}
</script>