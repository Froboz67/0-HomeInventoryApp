<template>
  <div class="item-input-group">
    <!-- <label :for="id">{{ label }}</label> -->
    <input
      v-bind="$attrs"
      :value="modelValue"
      @input="$emit('update:modelValue', $event.target.value)"
      :type="type || 'text'"
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
      type: String,
      default: "",
    },
    type: String,
    required: Boolean,
  },
};
</script>

<style scoped>
.item-input-group {
  display: flex;
  margin-bottom: 0.5rem;
  margin-top: 0.5rem;
  align-items: center;
  /* justify-content: space-around; */
  width: 100%;
}

label {
  flex: 0 0 auto;
  margin-right: 0.5rem;
}
input {
  flex: 1;
  padding: 0.15rem;
  border: 1px solid #002855;
  border-radius: 0.25rem;
  box-sizing: border-box;
}
</style>