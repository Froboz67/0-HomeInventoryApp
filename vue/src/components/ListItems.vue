<template>
  <div>
    <h1>My List of Items</h1>
    <button @click="getItems">View your Items</button>
  </div>
</template>

<script>
import service from "../services/ItemService";

export default {
  data() {
    return {
      item: {},
    };
  },
  methods: {
    getItems() {
      const user = this.$store.state.user;
      console.log(user.id);
      service.getItems(user.id).then((response) => {
        this.item = response.data;
        console.log(this.item);
      });
    },
  },
};
</script>

<style scoped>
</style>