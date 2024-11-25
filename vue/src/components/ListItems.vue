<template>
  <div class="main-div">
    <div>
      <header-module />
      <!-- <h1>My Items List</h1> -->
    </div>
    <!-- <is-loading v-bind="isLoading" />
    <div v-if="!isLoading"> -->
    <div class="wrapper" v-for="item in itemList" :key="item.itemId">
      <header class="header">{{ item.name }}</header>
      <footer class="footer" id="button-links">
        <button
          class="button-link"
          v-on:click="
            $router.push({ name: 'details', params: { id: item.itemId } })
          "
        >
          item details
        </button>
        <button
          class="button-link"
          v-on:click="
            $router.push({ name: 'update', params: { id: item.itemId } })
          "
        >
          edit item
        </button>
      </footer>
    </div>
    <!-- </div> -->
  </div>
</template>

<script>
import service from "../services/ItemService";
import { format } from "date-fns";
import isLoading from "./isLoading.vue";
import HeaderModule from "./componentModules/HeaderModule.vue";

export default {
  components: {
    HeaderModule,
  },
  data() {
    return {
      item: {},
      itemList: {},
      newDate: "",
      // isLoading: true,
    };
  },
  methods: {
    getItems() {
      // this.isLoading = true;
      const user = this.$store.state.user;
      service.getItems(user.id).then((response) => {
        this.itemList = response.data.itemList;
        console.log(this.itemList);
      });
    },
    formatCreatedAt(createdAt) {
      const splitDate = createdAt.split(".")[0];
      const newDate = format(new Date(splitDate), "MMMM dd, yyyy");
      return newDate;
    },
  },
  created() {
    this.$store.commit("SET_PAGE_TITLE", "My List of Items");
    this.getItems();
  },
};
</script>

<style scoped>
/* .main-div {
  display: flex;
  flex-direction: column;
  height: 100vh;
} */
.wrapper {
  display: flex;
  flex-flow: row wrap;
  font-weight: normal;
  text-align: center;
  gap: 0.2rem;
  padding: 0.2rem;
  margin: 0.5rem auto;
  border: solid 1px black;
  max-width: 600px;
  box-shadow: 0 0.25rem 0.5rem rgba(0, 0, 0, 0.1);
  border-radius: 0.5rem;
  background-color: #979dac;
}
.wrapper > * {
  padding: 0.6rem;
  flex: 1 1 auto;
  border-radius: 0.4rem;
}
.header {
  background-color: #2c6e49;
  flex: 1 100%;
  color: white;
  font-weight: bold;
}
.footer {
  background-color: #4c956c;
  flex: 1 100%;
}
.notes {
  background-color: white;
}
.aside-one {
  background-color: lightcyan;
  flex: 1 1 10%;
}
.aside-two {
  background-color: lightcyan;
  flex: 1 1 10%;
  font-size: 0.75rem;
}
#button-links {
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
@media all and (min-width: 600px) {
  .aside {
    flex: 1 0 0;
  }
}
@media all and (min-width: 800px) {
  .notes {
    flex: 3 0px;
  }
  .aside-one {
    order: 1;
  }
  .notes {
    order: 2;
  }
  .aside-two {
    order: 3;
  }
  .footer {
    order: 4;
  }
}
</style>