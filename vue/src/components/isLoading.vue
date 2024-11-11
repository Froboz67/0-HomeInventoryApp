<template>
  <div v-if="isLoading" class="loading-overlay">
    <div class="loading-image-container">
      <img src="../assets/imagefiles/loading.webp" alt="Loading" />
    </div>
  </div>
</template>

<script>
export default {
  props: {
    isLoading: {
      type: Boolean,
      required: true,
    },
  },
};
</script>

<style scoped>
.loading-overlay {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  display: flex;
  justify-content: center;
  align-items: center;
  /* background: rgba(0, 0, 0, 0.3); */
  z-index: 2;
  border-radius: 10px;
}
.loading-image-container img {
  border: solid 1px black;
  width: auto;
  height: auto;
  opacity: 0.75; /* Adjust transparency */
}
</style>