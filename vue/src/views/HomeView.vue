<template>
  <div class="home">
    <h1>Home Inventory</h1>
    <div class="buttons">
      <button class="button-link" v-on:click="$router.push({ name: 'items' })">
        My Items
      </button>
    </div>
    <!-- <p>You must be authenticated to see this</p> -->
    <!-- <table>
      <thead>
        <tr>
          <td>Abbr</td>
          <td>Name</td>
        </tr>
      </thead>
      <tbody>
        <tr v-for="state in states" :key="state.abbreviation">
          <td>{{ state.abbreviation }}</td>
          <td>{{ state.name }}</td>
        </tr>
      </tbody>
    </table> -->
  </div>
</template>

<script>
import ItemsPage from "../components/ItemsPage.vue";
export default {
  components: { ItemsPage },
};
</script>
<style>
.home {
  justify-items: center;
}
</style>
