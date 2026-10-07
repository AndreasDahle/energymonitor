<script setup lang="ts">
import type { Component } from "../types/Component"
import {deleteComponent, getComponent, updateComponent} from "../services/componentApi"
import {onMounted, ref, watch} from "vue";

const props = defineProps<{
  id: number
}>()

const component = ref<Component | null>(null)

async function loadComponent() {
  component.value = await getComponent(props.id)
}
async function handleDelete() {
  await deleteComponent(props.id)
  emit("deleted", props.id)

}
const emit = defineEmits<{
  deleted: [id: number]
}>()

onMounted(() => {
  loadComponent()
})
watch(
    () => props.id,
    () => {
      loadComponent()
}
)


</script>

<template>
<div v-if="component">
  <h1>
    {{component.name}}

  </h1>


  <ul>
    <li>
      Type: {{component.type}}
    </li>
    <li>
      Status: {{component.status}}
    </li>
    <li>
      Last updated: {{component.lastUpdated}}
    </li>
  </ul>

  <div>
    <button @click="handleDelete()">
      Slett
    </button>
    <button >
      Rediger
    </button>
  </div>

</div>
  <p v-else>
    Laster...
  </p>
</template>

<style scoped>

</style>