<template>
  <div>
    <div>
      <h1>{{ pageTitle }}</h1>
    </div>
  </div>
</template>

<script>
import { mapState } from "vuex";

export default {
  computed: {
    ...mapState(["pageTitle"]),
  },
};
</script>

<style scoped>
h1 {
  text-align: center;
  color: yellow;
  font-style: bold;
}
</style>