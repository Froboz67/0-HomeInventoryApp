<template>
  <div class="grid-container">
    <div class="grid-item box1">
      <router-link id="link-color" v-bind:to="{ name: 'list' }"
        >My Items</router-link
      >
    </div>
    <div class="grid-item box2">
      <router-link id="link-color" v-bind:to="{ name: 'item' }"
        >Add Items</router-link
      >
    </div>
    <div class="grid-item box3">box 3</div>
    <div class="grid-item box4">box 4</div>
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
* {
  font-family: "Roboto", Arial, Helvetica, sans-serif;
  background-color: #a5a5a5;
  color: black;
  margin: 0;
  padding: 0;
  outline: 2px solid red;
  outline-style: dashed;
}

.grid-container {
  display: grid;
  grid-template-columns: 1fr 1fr;
  grid-template-rows: 1fr 1fr;
  gap: 1rem;
  min-height: 100vh;
  padding: 1rem;
  /* overflow-y: auto; */
}

.grid-item {
  background-color: #f0f0f0;
  /*rgb(2, 36, 2)  green box fill*/
  border: 1px solid #ccc;
  border-radius: 0.5rem;
  display: flex;
  align-items: center;
  justify-content: center;
  text-align: center;
  padding: 1rem;
  box-shadow: 0.125rem 0.125rem 0.625rem rgba(0, 0, 0, 0.3);
  transition: transform 0.3s;
  /* sets the transformation rate for hover */
  background: linear-gradient(to top, #ffffff, #eeeeee);
  background-size: 100% 100%;
  background-attachment: fixed;
}

.grid-item:hover {
  transform: scale(1.02);
}

.grid-item:before {
  content: "";
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  border: 1px solid #ccc;
  border-radius: 0.5rem;
  pointer-events: none;
  /* allows the mouse pointer to scroll smooth */
}

@media (max-width: 1024px) {
  .grid-container {
    grid-template-columns: 1fr;
  }
}
</style>
