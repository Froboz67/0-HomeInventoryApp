<template>
  <div class="home">
    <h1>Home Inventory</h1>
    <div class="buttons">
      <button class="button-link" v-on:click="$router.push({ name: 'item' })">
        My Items
      </button>
    </div>
    <div class="buttons">
      <button
        class="button-link"
        v-on:click="$router.push({ name: 'list-items' })"
      >
        List my Items
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
<style>
.home {
  justify-items: center;
}
</style>
