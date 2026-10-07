<script setup lang="ts">

import {onMounted, ref} from 'vue'
import ComponentDetails from "./ComponentDetails.vue"
import type { Component } from "../types/Component"
import { getAllComponents } from "../services/componentApi"

const components = ref<Component[]>([])
async function loadComponents() {
  components.value = await getAllComponents()
}

const selectedComponentId = ref<number | null>(null)

function selectComponent(id: number) {
  selectedComponentId.value = id
}

function handleComponentDeleted(id: number) {
  components.value = components.value.filter(
      component => component.id !== id
  )

  selectedComponentId.value = null
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

  <table>
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
      <td>{{ component.lastUpdated }}</td>
    </tr>
    </tbody>
  </table>
  <ComponentDetails
      v-if="selectedComponentId !== null"
      :id="selectedComponentId"
      @deleted="handleComponentDeleted"
  />
</main>
</template>

<style scoped>

</style>