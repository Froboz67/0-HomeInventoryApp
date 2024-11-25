<template>
  <div id="home-inventory-app">
    <header>
      <div class="home">
        <h1>Home Inventory</h1>
      </div>
      <div id="nav">
        <router-link id="link-color" v-bind:to="{ name: 'home' }"
          >Home</router-link
        >&nbsp;|&nbsp;
        <router-link id="link-color" v-bind:to="{ name: 'list' }"
          >My Items</router-link
        >&nbsp;|&nbsp;
        <router-link id="link-color" v-bind:to="{ name: 'item' }"
          >Add Items</router-link
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
<style>
/* * {
  outline: 1px solid red;
} */
.home {
  justify-items: center;
}
body {
  margin: 0;
  padding: 0;
}

html {
  height: 100%;
}
#home-inventory-app {
  min-height: 100vh;
  background-color: #a5a5a5;
}

#nav {
  color: black;
  display: flex;
  justify-content: center;
  margin: 2rem;
  max-width: 750px;
  margin: 1rem auto;
}
#link-color {
  color: black;
}
@media (max-width: 1024px) {
  #nav {
    justify-content: center;
  }
}
</style>
