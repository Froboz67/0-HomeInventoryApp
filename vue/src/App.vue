<template>
  <div id="home-inventory-app">
    <div id="nav">
      <router-link v-bind:to="{ name: 'home' }">My Home</router-link
      >&nbsp;|&nbsp;
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
#home-inventory-app {
  justify-items: center;
}
</style>
