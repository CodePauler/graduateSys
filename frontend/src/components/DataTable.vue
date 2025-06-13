<template>
  <el-table :data="data" table-layout="auto" style="width: 100%">
    <el-table-column v-for="col in columns" :key="col.prop" v-bind="col" />
    <el-table-column v-if="actions.length" label="操作" width="180">
      <template #default="scope">
        <el-button v-for="btn in actions" :key="btn.label" :type="btn.type" @click="btn.onClick(scope.row)">
          {{ btn.label }}
        </el-button>
      </template>
    </el-table-column>
  </el-table>
  <el-pagination v-model:current-page="pagination.page" v-model:page-size="pagination.pageSize"
    :total="pagination.total" layout="total, sizes, prev, pager, next, jumper" :page-sizes="[5, 10, 20, 50, 100]"
    @current-change="$emit('page-change', $event)" @size-change="$emit('size-change', $event)" />
</template>

<script setup>
defineProps({
  data: Array,
  columns: Array,
  actions: Array,     // [{ label: '编辑', type: 'primary', onClick: fn }]
  pagination: Object  // { page, pageSize, total }
})
defineEmits(['page-change', 'size-change'])
</script>