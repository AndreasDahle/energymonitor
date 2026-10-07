<script setup lang="ts">
import {type Component, Status} from "../types/Component"
import {createComponent, updateComponent } from "../services/componentApi"
import {computed, ref} from "vue";

const props = defineProps<{
  component?: Component
}>()


const isEditing = computed(() => props.component !== undefined)
const name = ref(props.component?.name ?? "")
const status = ref(props.component?.status ?? Status.ACTIVE)
const type = ref(props.component?.type ?? "")





const emit = defineEmits<{
  created: [component: Component]
  updated: [component: Component]
  cancelled: []
}>()


const errors = ref({
  name: "",
  type: ""
})

function validateForm(): boolean{
  errors.value.name = ""
  errors.value.type = ""

  if (name.value.trim() === "") {
    errors.value.name = "Name is required"
  }

  if (type.value.trim() === "") {
    errors.value.type = "Type is required"
  }
  return errors.value.name === "" && errors.value.type === ""
}


async function handleSave() {
  if(!validateForm()){
    return
  } else{
    const input = {
      name: name.value,
      status: status.value,
      type: type.value
    }

    if(isEditing.value){
      const updated = await updateComponent(input, props.component!.id)
      emit("updated", updated)
    } else{
      const created = await createComponent(input)
      emit("created", created)
    }

}
}


</script>

<template>
  <div>
    <h1>{{ isEditing ? "Edit component" : "Create component" }}</h1>

    <p class="input-title">Name:</p>
    <input v-model="name" placeholder="Write name here" />
    <p class="errortext" v-if="errors.name">
      {{errors.name}}
    </p>
    <p class="input-title">Status:</p>
    <select v-model="status">
      <option disabled value="">Please select one</option>
      <option :value="Status.ACTIVE">Active</option>
      <option :value="Status.INACTIVE">Inactive</option>
      <option :value="Status.MAINTENANCE">Maintenance</option>
    </select>

    <p class="input-title">Type:</p>
    <input v-model="type" placeholder="Write type here" />

    <p class="errortext" v-if="errors.type">
      {{errors.type}}
    </p>
    <div>
      <button @click="handleSave()">
        {{ isEditing ? "Save changes" : "Create"}}
      </button>
      <button type="button" @click="emit('cancelled')">
      Cancel
    </button>
    </div>


  </div>
</template>

<style scoped>

</style>