<template>
  <div>
    <div>
      <h1>My List of Items</h1>
    </div>
    <div class="cards-container">
      <div class="card" v-for="item in itemList" :key="item.itemId">
        <p>Item name: {{ item.name }}</p>
        <p>Item category: {{ item.category }}</p>
        <p>Item created at: {{ item.createdAt }}</p>
        <p>Item notes: {{ item.notes }}</p>
      </div>
    </div>
  </div>
</template>

<script>
import service from "../services/ItemService";

export default {
  data() {
    return {
      item: {},
      itemList: {},
    };
  },
  methods: {
    getItems() {
      const user = this.$store.state.user;
      // console.log(user.id);
      service.getItems(user.id).then((response) => {
        this.itemList = response.data.itemList;
        console.log(this.itemList);
      });
    },
  },
  created() {
    this.getItems();
  },
};
</script>

<style scoped>
</style>