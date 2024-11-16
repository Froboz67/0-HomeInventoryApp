<template>
  <div>
    <input
      :checked="modelValue"
      type="checkbox"
      :id="id"
      @change="$emit('update:modelValue', $event.target.checked)"
    />
    <label :for="id">{{ label }}</label>
  </div>
</template>

<script>
export default {
  props: {
    label: String,
    id: String,
    modelValue: {
      type: Boolean,
      default: false,
    },
    required: Boolean,
  },
};
</script>

<style scoped>
label {
  padding-left: 0.5rem;
}
</style>