<template>
  <div class="item-input-group">
    <!-- <label :for="id">{{ label }}</label> -->
    <input
      v-bind="$attrs"
      :value="modelValue"
      @input="inputHandler"
      :type="type || 'number'"
      :id="id"
      :placeholder="placeholder"
      step="0.01"
    />
  </div>
</template>

<script>
export default {
  props: {
    label: String,
    id: String,
    modelValue: {
      type: Number,
      default: null,
    },
    type: {
      type: String,
      default: "number",
    },
    placeholder: {
      type: String,
      default: "",
    },
    required: Boolean,
  },
  methods: {
    inputHandler(event) {
      const value = event.target.value;
      const parsedValue = value === "" ? null : parseFloat(value);
      this.$emit("update:modelValue", parsedValue);
    },
  },
};
</script>

<style scoped>
.item-input-group {
  display: flex;
  margin-bottom: 0.5rem;
  margin-top: 0.5rem;
  align-items: center;
  justify-content: flex-start;
  width: 100%;
}

label {
  flex: 0 0 auto;
  margin-right: 0.5rem;
  white-space: nowrap;
}
input {
  flex: 1;
  padding: 0.15rem;
  border: 1px solid #002855;
  border-radius: 0.25rem;
  width: 100%;
  box-sizing: border-box;
}
</style>