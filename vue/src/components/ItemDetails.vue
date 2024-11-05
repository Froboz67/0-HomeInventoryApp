<template>
  <div>
    <div>
      <h1>Item Details</h1>
    </div>
    <div
      class="card-container"
      v-bind="this.$store.state.item"
      v-if="item && item.name"
    >
      <header class="header">{{ item.name }}</header>
      <article class="notes">
        <p>{{ item.notes }}</p>
      </article>
      <aside class="aside-one">
        <div class="descriptor">Category:</div>
        <div>{{ item.category }}</div>
        <div class="descriptor">Room:</div>
      </aside>
      <aside class="aside-two">
        <div class="descriptor">Created:</div>
        <div class="date">
          {{ item.createdAt ? formatCreatedAt(item.createdAt) : "" }}
        </div>
        <div v-if="item.updatedAt">
          <div class="descriptor">Edited:</div>
          <div class="date">
            {{ item.updatedAt ? formatUpdatedAt(item.updatedAt) : "" }}
          </div>
        </div>
      </aside>
      <footer class="footer" id="button-links">
        <button
          class="button-link"
          v-on:click="$router.push({ name: 'update' })"
        >
          edit item
        </button>
        <button class="button-link" v-on:click="$router.push({ name: 'list' })">
          Items List
        </button>
      </footer>
      <!-- </div> -->
    </div>
    <div v-else>Loading...</div>
  </div>
</template>

<script>
import service from "../services/ItemService";
import { format } from "date-fns";

export default {
  data() {
    return {
      item: {},
      newDate: "",
    };
  },
  methods: {
    getItem() {
      const user = this.$store.state.user;
      console.log("user id: " + user.id);
      const itemId = this.$route.params.id;
      console.log("Item id: " + itemId);
      service.getItem(user.id, itemId).then((response) => {
        this.item = response.data;
        console.log(this.item);
      });
    },
    formatCreatedAt(createdAt) {
      if (!createdAt) {
        return "";
      }
      console.log("this is createdAt: " + createdAt);
      const splitDate = createdAt.split(".")[0];
      console.log(splitDate);
      return format(new Date(splitDate), "MMMM dd, yyyy");
    },
    formatUpdatedAt(updatedAt) {
      if (!updatedAt) {
        return "";
      }
      console.log("this is updatedAt: " + updatedAt);
      const splitDate = updatedAt.split(".")[0];
      console.log(splitDate);
      const newDate = format(new Date(splitDate), "MMMM dd, yyyy");
      return newDate;
      // return format(new Date(splitDate), "MMMM dd, yyyy");
    },
  },
  created() {
    this.getItem();
  },
};
</script>

<style scoped>
h1 {
  text-align: center;
}
.card-container {
  display: flex;
  flex-flow: row wrap;
  font-weight: normal;
  text-align: center;
  gap: 0.5rem;
  padding: 0.5rem;
  margin: 0.5rem;
  border: solid 1px black;
  max-width: 600px;
  box-shadow: 0 0.25rem 0.5rem rgba(0, 0, 0, 0.1);
  border-radius: 0.5rem;
  background-color: aliceblue;
}
.card-container > * {
  padding: 0.6rem;
  flex: 1 100%;
  border-radius: 0.4rem;
}
.header {
  background-color: gray;
  flex: 1 100%;
  color: white;
  font-weight: bold;
}
.footer {
  background-color: lightgray;
  flex: 1 100%;
}
.notes {
  background-color: white;
}
.aside-one {
  background-color: lightcyan;
  flex: 1 1 10%;
}
.descriptor {
  font-size: 0.75rem;
}
.aside-two {
  background-color: lightcyan;
  flex: 1 1 10%;
}
.date {
  font-size: 0.9rem;
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