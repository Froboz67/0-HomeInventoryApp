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
      <header class="header">
        {{ item.name }} | Item id# {{ item.itemId }}
      </header>
      <article class="notes">
        <p>{{ item.notes }}</p>
        <div v-if="item" class="item-photo">
          <img :src="photoUrl" alt="item photo" />
        </div>
      </article>
      <aside class="aside-one">
        <div class="descriptor">Category:</div>
        <div>{{ item.category }}</div>
        <div class="descriptor">Room:</div>
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

export default {
  data() {
    return {
      item: {},
      newDate: "",
      photoUrl: "",
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
        console.log("data: ", this.item);
      });
    },
    getPhoto() {
      const itemId = this.$route.params.id;
      console.log("the current itemId: ", itemId);
      fileService.getPhoto(itemId).then((response) => {
        this.photo = response.data;
        console.log("the photo is: ", this.photo);
      });
    },
    getPhotoUrl() {
      const itemId = this.$route.params.id;
      fileService
        .getPhotoUrl(itemId, { responseType: "blob" })
        .then((response) => {
          const blob = new Blob([response.data], { type: "image/png" });
          this.photoUrl = URL.createObjectURL(blob);
          console.log("file data ", this.photoUrl);
        })
        .catch((error) => {
          console.error("Error fetching photo ", error);
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
    this.getPhoto();
    this.getPhotoUrl();
  },
};
</script>

<style scoped>
h1 {
  text-align: center;
  color: white;
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
  background-color: #023e7d;
  color: white;
  font-size: 1.2rem;
  font-weight: normal;
}
.footer {
  background-color: #0466c8;
}
.notes {
  background-color: #979dac;
  border: 0.05rem solid #001233;
  box-shadow: 0 0.25rem 0.5rem #33415c;
}
img {
  width: 12rem;
  height: auto;
  border-radius: 0.4rem;
}

.descriptor {
  font-size: 0.75rem;
}

.date {
  font-size: 0.9rem;
}
.aside-one {
  color: white;
  background-color: #5c677d;
  flex: 1 1 10%;
}
.aside-two {
  color: white;
  background-color: #5c677d;
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