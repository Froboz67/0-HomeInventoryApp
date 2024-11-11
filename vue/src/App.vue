<template>
  <div id="home-inventory-app">
    <div id="nav">
      <router-link v-bind:to="{ name: 'home' }">My Home</router-link
      >&nbsp;|&nbsp;
      <!-- <router-link v-bind:to="{ name: 'list-items' }">My Items</router-link
      >&nbsp;|&nbsp; -->
      <router-link
        v-bind:to="{ name: 'logout' }"
        v-if="$store.state.token != ''"
        >Logout</router-link
      >
    </div>
    <router-view />
  </div>
</template>
<style>
html {
  height: 100%;
  margin: 0;
  padding: 0;
  background-color: #001233;
}
/* old original style */
#home-inventory-app {
  display: flex;
  flex-direction: column;
  justify-items: center;
  background-color: #001233;
}
</style>
