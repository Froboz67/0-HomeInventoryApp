<template>
  <div class="photo-input-group">
    <label :for="id">{{ label }}</label>
    <input
      type="file"
      :id="id"
      @change="handleFileUpload"
      ref="fileInput"
      placeholder="Upload a Photo"
    />
  </div>
</template>

<script>
export default {
  props: {
    label: {
      type: String,
      required: true,
    },
    id: {
      type: String,
      required: true,
    },
  },
  methods: {
    handleFileUpload(event) {
      console.log("handlefileUpload() fileUpload Component");
      const file = event.target.files[0];
      if (file) {
        this.$emit("file-upload", file);
        console.log("handleFileUpload() fileUpComponent", file);
        this.$emit("file-changed", true);
      }
    },
  },
};
</script>

<style scoped>
.photo-input-group {
  display: flex;
  margin-bottom: 0.5rem;
  margin-top: 0.5rem;
  align-items: center;
  width: 100%;
}
label {
  flex: 0 0 auto;
  margin-right: 0.5rem;
}
input {
  padding: 0.15rem;
  border: 1px solid #002855;
  border-radius: 0.25rem;
  width: 100%;
  max-width: 100;
  background-color: white;
  color: black;
}
</style>