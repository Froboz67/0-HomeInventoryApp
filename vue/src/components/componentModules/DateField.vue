<template>
  <div class="item-input-field">
    <label :for="id">{{ label }}</label>
    <input
      v-bind="$attrs"
      :value="modelValue"
      @input="$emit('update:modelValue', $event.target.value)"
      type="date"
      :id="id"
      :required="required"
    />
  </div>
</template>

<script>
export default {
  props: {
    label: String,
    id: String,
    modelValue: {
      type: [Date, String],
      default: null,
    },
    type: Date,
    required: Boolean,
  },
};
</script>

<style scoped>
.item-input-field {
  display: flex;
  margin-bottom: 0.5rem;
  margin-top: 0.5rem;
  align-items: center;
  /* width: 100%; */
}
label {
  flex: 0 0 auto;
  margin-right: 0.5rem;
}
input {
  padding: 0.15rem;
  border: 1px solid #002855;
  border-radius: 0.25rem;
  /* width: 100%; */
  /* max-width: 100; */
  background-color: white;
}
</style>