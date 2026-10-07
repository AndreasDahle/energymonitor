<script setup lang="ts">

import {computed, onMounted, ref} from 'vue'
import ComponentDetails from "./ComponentDetails.vue"
import type { Component } from "../types/Component"
import { getAllComponents } from "../services/componentApi"
import ComponentForm from "./ComponentForm.vue"



const showCreateForm = ref(false)
const componentToEdit = ref<Component | undefined>(undefined)
const components = ref<Component[]>([])
async function loadComponents() {
  components.value = await getAllComponents()
}

const selectedComponentId = ref<number | null>(null)

const selectedComponent = computed(() =>
    components.value.find(
        component => component.id === selectedComponentId.value
    )
)

function selectComponent(id: number) {
  selectedComponentId.value = id
}

function handleComponentDeleted(id: number) {
  components.value = components.value.filter(
      component => component.id !== id
  )

  selectedComponentId.value = null
}

function handleComponentCreated(component: Component) {
  components.value.push(component)
  showCreateForm.value = false
}

function handleEdit(component: Component) {
  componentToEdit.value = component
}

function handleComponentUpdated(updated: Component) {
  const index = components.value.findIndex(
      component => component.id === updated.id
  )



  if (index !== -1) {
    components.value[index] = updated
  }

  componentToEdit.value = undefined
}
function formatDate(dateString: string): string {
  return new Date(dateString).toLocaleString()
}

onMounted(() => {
loadComponents()
})

</script>


<template>
<main>
  <h1>
    Components
  </h1>


  <table class="component-table">
    <thead>
    <tr>
      <th>Name</th>
      <th>Type</th>
      <th>Status</th>
      <th>Last updated</th>
    </tr>
    </thead>

    <tbody>
    <tr
        v-for="component in components"
        :key="component.id"
        @click = "selectComponent(component.id)"
    >
      <td>{{ component.name }}</td>
      <td>{{ component.type }}</td>
      <td>{{ component.status }}</td>
      <td>{{ formatDate(component.lastUpdated) }}</td>
    </tr>
    </tbody>
  </table>

  <button @click="showCreateForm=true">
    Create component
  </button>

  <ComponentForm
      v-if="showCreateForm"
      @created="handleComponentCreated"
  />
  <ComponentForm
      v-if="componentToEdit"
      :component="componentToEdit"
      @updated="handleComponentUpdated"
  />
  <ComponentDetails
      v-if="selectedComponent"
      :component="selectedComponent"
      @deleted="handleComponentDeleted"
      @edit="handleEdit"
  />
</main>
</template>

<style scoped>

</style>