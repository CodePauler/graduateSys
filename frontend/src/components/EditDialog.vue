<template>
    <el-dialog v-model="visible" :title="title" width="500" @close="handleDialogClose">
        <el-form :model="model">
            <el-form-item v-for="field in fields" :key="field.prop" :label="field.label">
                <component :is="field.component" v-model="model[field.prop]" v-bind="field.props">
                    <template v-if="field.options" #default>
                        <el-option v-for="option in field.options" :key="option.value" :label="option.label"
                            :value="option.value" />
                    </template>
                </component>
            </el-form-item>
        </el-form>
        <template #footer>
            <el-button @click="visible = false">取消</el-button>
            <el-button type="primary" @click="$emit('submit')">确认</el-button>
        </template>
    </el-dialog>
</template>

<script setup>
import { computed } from 'vue';

// 组件接收的属性
const props = defineProps({
    model: Object, //表单数据对象
    fields: Array,   // 表单字段项，[{ label, prop, component, props }]
    title: String,   // 弹窗标题
    modelValue: Boolean // 控制弹窗显示与否
})

// 声明组件可向父组件发送的事件 update:modelValue用于v-model双向绑定，submit用于提交表单
const emit = defineEmits(['update:modelValue', 'submit'])

// 用于控制弹窗的显示与隐藏
const visible = computed({
    get: () => props.modelValue,
    set: (val) => emit('update:modelValue', val)
})
const handleDialogClose = () => {
    for (const key in props.model) {
        props.model[key] = '' // 关闭弹窗时清空表单数据
    }
    emit('update:modelValue', false) // 清空弹窗状态
}
</script>