<template>
  <div id="home-inventory-app">
    <header>
      <div class="home">
        <h1>Home Inventory</h1>
        <div class="buttons">
          <button
            class="button-link"
            v-on:click="$router.push({ name: 'item' })"
          >
            Add Items
          </button>
          <button
            class="button-link"
            v-on:click="$router.push({ name: 'list' })"
          >
            View my Items
          </button>
        </div>
      </div>
      <div id="nav">
        <router-link id="link-color" v-bind:to="{ name: 'home' }"
          >My Home</router-link
        >&nbsp;|&nbsp;
        <router-link id="link-color" v-bind:to="{ name: 'list' }"
          >My Items</router-link
        >&nbsp;|&nbsp;
        <router-link
          id="link-color"
          v-bind:to="{ name: 'logout' }"
          v-if="$store.state.token != ''"
          >Logout</router-link
        >
      </div>
    </header>

    <router-view />
  </div>
</template>
<style scoped>
html {
  display: flex;
  align-content: center;
  justify-content: center;
  height: 100%;
  margin: 0;
  padding: 0;
  background-color: #001233;
}
.home {
  display: flex;
  flex-direction: column;
  align-items: center;
}
/* old original style */
#home-inventory-app {
  display: flex;
  flex-direction: column;
  background-color: #a5a5a5;
}
#link-color,
#nav {
  color: black;
  display: flex;
  justify-content: flex-end;
}
</style>
