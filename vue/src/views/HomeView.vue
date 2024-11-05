<template>
  <div class="home">
    <h1>Home Inventory</h1>
    <div class="buttons">
      <button class="button-link" v-on:click="$router.push({ name: 'item' })">
        Add Items
      </button>
      <button class="button-link" v-on:click="$router.push({ name: 'list' })">
        View my Items
      </button>
    </div>
  </div>
</template>

<script>
import AddItems from "../components/AddItems.vue";
import ListItems from "../components/ListItems.vue";
export default {
  components: { ItemsPage: AddItems, ListItems: ListItems },
};
</script>
<style scoped>
.buttons {
  display: flex;
  gap: 1rem;
  justify-content: center;
}
.button-link {
  padding: 0.25rem 1rem;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  border: solid black 0.025rem;
}
.home {
  justify-items: center;
}
</style>
