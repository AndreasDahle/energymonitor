<script setup lang="ts">
import type { Component } from "../types/Component"
import {deleteComponent, getComponent, updateComponent} from "../services/componentApi"
import {onMounted, ref, watch} from "vue";

const props = defineProps<{
  component: Component
}>()

const component = ref<Component | null>(null)


const emit = defineEmits<{
  deleted: [id: number]
  edit: [component: Component]
}>()

async function loadComponent() {
  component.value = await getComponent(props.component.id)
}
async function handleDelete() {
  await deleteComponent(props.component.id)
  emit("deleted", props.component.id)

}

function handleEdit() {
  if (component.value) {
    emit("edit", component.value)
  }
}



onMounted(() => {
  loadComponent()
})
watch(
    () => props.component.id,
    () => {
      loadComponent()
}
)


</script>

<template>
<div v-if="component">
  <h1>
    {{props.component.name}}

  </h1>


  <ul>
    <li>
      Type: {{props.component.type}}
    </li>
    <li>
      Status: {{props.component.status}}
    </li>
    <li>
      Last updated: {{props.component.lastUpdated}}
    </li>
  </ul>

  <div>
    <button @click="handleDelete()">
      Delete
    </button>
    <button @click="handleEdit" >
      Edit
    </button>
  </div>

</div>
  <p v-else>
    Laster...
  </p>
</template>

<style scoped>

</style>