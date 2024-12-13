<template>
  <div class="category-input-group">
    <label :for="id">{{ label }}</label>
    <select
      v-bind="$attrs"
      :value="modelValue"
      @change="$emit('update:modelValue', $event.target.value)"
      id="id"
      :required="required"
    >
      <option
        v-for="option in options"
        :key="option[optionKey]"
        :value="option[optionValue]"
      >
        {{ option[optionLabel] }}
      </option>
    </select>
  </div>
</template>

<script>
export default {
  props: {
    label: String,
    id: String,
    modelValue: [String, Number],
    options: {
      type: Array,
      required: true,
    },
    optionKey: {
      type: String,
      default: "value",
    },
    optionValue: {
      type: String,
      default: "value",
    },
    optionLabel: {
      type: String,
      default: "label",
    },
    required: Boolean,
  },
};
</script>

<style scoped>
</style>