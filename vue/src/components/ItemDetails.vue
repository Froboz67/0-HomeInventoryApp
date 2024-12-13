<template>
  <isLoading v-if="isLoading" :isLoading="isLoading" />
  <div class="page">
    <!-- <div v-if="isLoading" class="loading-overlay">
      <div class="loading-image-container">
        <img src="../assets/imagefiles/loading.webp" alt="Loading" />
      </div>
    </div> -->
    <div>
      <header-module />
    </div>
    <div
      class="card-container"
      v-bind="this.$store.state.item"
      v-if="item && item.name"
    >
      <header class="header">
        {{ item.name }} | Item id# {{ item.itemId }}
      </header>
      <article class="notes">
        <p id="notes">{{ item.notes }}</p>
        <div v-if="item" class="item-photo">
          <img :src="photoUrl" alt="item photo" />
        </div>
      </article>
      <aside class="aside-one">
        <div class="descriptor">Category:</div>
        <div>{{ item.category }}</div>
        <div class="descriptor">Room:</div>
        <div>{{ room.roomName }}</div>
        <div class="descriptor">Purchase Date:</div>
        <div class="date">
          {{ item.purchaseDate ? formatCreatedAt(item.purchaseDate) : "" }}
        </div>
        <div class="descriptor">Purchase Price:</div>
        <div>${{ item.purchasePrice }}</div>
        <div class="descriptor">Estimated Value:</div>
        <div>${{ item.value }}</div>
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
import fileService from "../services/FileService";
import service from "../services/ItemService";
import { format } from "date-fns";
import HeaderModule from "./componentModules/HeaderModule.vue";
import isLoading from "./isLoading.vue";
import RoomService from "../services/RoomService";

export default {
  components: {
    HeaderModule,
    isLoading,
  },
  data() {
    return {
      item: {},
      newDate: "",
      photoUrl: "",
      isLoading: true,
    };
  },
  methods: {
    getItem() {
      const user = this.$store.state.user;
      const itemId = this.$route.params.id;
      this.isLoading = true;
      service
        .getItem(itemId)
        .then((response) => {
          this.item = response.data;
          this.isLoading = false;
        })
        .catch((error) => {
          console.log("Error getting the item data", error);
          this.isLoading = false;
        });
    },
    getPhotoUrl() {
      const itemId = this.$route.params.id;
      this.isLoading = true;
      fileService
        .getPhotoUrl(itemId, { responseType: "blob" })
        .then((response) => {
          if (response.status === 204) {
            this.photoUrl = null;
          } else {
            const blob = new Blob([response.data], { type: "image/png" });
            this.photoUrl = URL.createObjectURL(blob);
            this.isLoading = false;
            console.log("photoUrl ", this.photoUrl);
          }
        })
        .catch((error) => {
          console.error("Error fetching photo ", error);
          this.isLoading = false;
        });
    },
    formatCreatedAt(createdAt) {
      if (!createdAt) {
        return "";
      }
      const splitDate = createdAt.split(".")[0];
      return format(new Date(splitDate), "MMMM dd, yyyy");
    },
    formatUpdatedAt(updatedAt) {
      if (!updatedAt) {
        return "";
      }
      const splitDate = updatedAt.split(".")[0];
      const newDate = format(new Date(splitDate), "MMMM dd, yyyy");
      return newDate;
    },
    getRoom() {
      // const user = this.$store.user.id;
      const itemId = this.$route.params.id;
      console.log("item Id ", itemId);
      this.isLoading = true;
      RoomService.getRoom(itemId)
        .then((response) => {
          this.room = response.data;
          this.isLoading = false;
          console.log("room", this.room);
        })
        .catch((error) => {
          console.log("Error getting the room data", error);
          this.isLoading = false;
        });
    },
  },

  created() {
    this.$store.commit("SET_PAGE_TITLE", "Item Details");
    this.getItem();
    this.getRoom();
    this.getPhotoUrl();
  },
};
</script>

<style scoped>
.page {
  height: 100vh;
}
.card-container {
  display: flex;
  flex-direction: column;
  font-weight: normal;
  text-align: center;
  gap: 0.5rem;
  padding: 0.5rem;
  margin: 0.5rem auto;
  border: solid 1px black;
  max-width: 750px;
  box-shadow: 0 0.25rem 0.5rem rgba(0, 0, 0, 0.1);
  border-radius: 0.5rem;
  background-color: #979dac;
}
.card-container > * {
  padding: 0.6rem;
  border-radius: 0.4rem;
}
.header {
  background-color: #2c6e49;
  color: white;
  font-size: 1.2rem;
  font-weight: normal;
}
.footer {
  background-color: #4c956c;
}
.notes {
  background-color: #1e1e24;
  border: 0.05rem solid #001233;
  box-shadow: 0 0.25rem 0.5rem #33415c;
  color: white;
}
img {
  width: 12rem;
  height: auto;
  border-radius: 0.4rem;
}

.descriptor {
  font-size: 0.9rem;
  color: black;
}

.date {
  font-size: 0.9rem;
}
.aside-one {
  color: black;
  background-color: #77878b;
  flex: 1 1 10%;
  font-size: 0.9rem;
  border: 0.1rem solid grey;
}
.aside-two {
  color: black;
  background-color: #77878b;
  flex: 1 1 10%;
  font-size: 0.9rem;
  border: 0.1rem solid grey;
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

@media all and (min-width: 800px) {
  .card-container {
    flex-direction: row;
    flex-wrap: wrap;
  }
  .header {
    flex: 1 100%;
    order: 1;
  }
  .notes {
    flex: 3 0px;
    order: 3;
  }
  .aside-one {
    flex: 1 0 0;
    order: 2;
  }
  .aside-two {
    flex: 1 0 0;
    order: 4;
  }
  .footer {
    flex: 1 100%;
    order: 5;
  }
}
</style>