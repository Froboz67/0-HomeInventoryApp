<template>
  <div id="items" class="text-center">
    <form v-on:submit.prevent="updateItem">
      <h1>Update Item</h1>

      <div class="item-input-group">
        <label for="item-name">Name of Item: </label>
        <input type="text" id="item-name" v-model="item.name" required />
      </div>
      <div class="item-input-group">
        <label for="item-category">Category of Item: </label>
        <input type="text" id="item-category" v-model="item.category" />
      </div>
      <div class="item-input-group">
        <label for="item-purchase-date">Purchase Date of Item: </label>
        <input
          type="text"
          id="item-purchase-date"
          v-model="item.purchaseDate"
        />
      </div>
      <div class="item-input-group">
        <label for="item-purchase-price">Purchase Price of Item: </label>
        <input
          type="number"
          id="item-purchase-price"
          v-model.number="item.purchasePrice"
        />
      </div>
      <div class="item-input-group">
        <label for="item-value">Current Value of Item: </label>
        <input type="number" id="item-value" v-model.number="item.value" />
      </div>
      <div class="item-input-group">
        <label for="item-is-valuable">Is this Item Valuable: </label>
        <input type="text" id="item-is-valuable" v-model="item.isValuable" />
      </div>
      <div class="item-input-group">
        <label for="item-notes">Notes about this Item: </label>
        <input type="text" id="item-notes" v-model="item.notes" />
      </div>
      <div class="photo-input-group">
        <label for="item-photo">upload photo: </label>
        <input type="file" id="item-photo" @change="handleFileUpdoad" />
      </div>
      <div id="button-links">
        <button class="button-link" type="submit">Save Updated Item</button>
        <button type="button" class="button-link" v-on:click="deleteItem">
          Delete Item
        </button>
        <button type="button" class="button-link" v-on:click="cancelEdit">
          Cancel Return to Items
        </button>
      </div>
    </form>
  </div>
</template>

<script>
import fileService from "../services/FileService";
import service from "../services/ItemService";
export default {
  data() {
    return {
      item: {
        name: "",
        category: "",
        purchaseDate: "",
        purchasePrice: null,
        value: null,
        isValuable: false,
        notes: "",
      },
      photo: null,
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
    getPhoto() {
      const itemId = this.$route.params.id;
      fileService.getPhoto(itemId).then((response) => {
        this.photo = response.data;
        console.log("this is the photo: ", this.photo);
      });
    },
    updateItem() {
      if (this.item.purchaseDate) {
        this.item.purchaseDate = new Date(this.item.purchaseDate)
          .toISOString()
          .split("T")[0];
      }
      const user = this.$store.state.user;
      console.log("user id: " + user.id);
      const itemId = this.$route.params.id;
      console.log("Item id: " + itemId);
      service
        .updateItem(user.id, itemId, this.item)
        .then((response) => {
          if (response.status === 200) {
            alert("item updated successfully!");
            this.$router.push({ name: "list" });
          }
        })
        .catch((error) => {
          console.log(error);
        });
    },
    deleteItem() {
      if (confirm("This will permanently delete this item: are you sure?")) {
        const user = this.$store.state.user;
        console.log("user id: " + user.id);
        const itemId = this.$route.params.id;
        console.log("Item id: " + itemId);
        const photo = this.$store.state.photo;
        console.log("photo is: ", photo);

        service
          .deleteItem(user.id, itemId, this.item)
          .then((response) => {
            if (response.status === 204) {
              alert("item deleted from inventory");
              this.$router.push({ name: "list" });
            }
          })
          .catch((error) => {
            console.log(error);
          });
      } else {
        console.log("deletion canceled by user");
      }
    },
    cancelEdit() {
      this.item = {};
      this.$router.push({ name: "list" });
    },
  },
  created() {
    this.getItem();
    this.getPhoto();
  },
};
</script>


<style scoped>
#items {
  display: flex;
  justify-content: center;
  height: 100vh;
}
.item-input-group {
  display: flex;
  margin-bottom: 1rem;
  align-items: center;
}
label {
  flex: 0 0 auto;
  margin-right: 0.5rem;
}
input {
  flex: 1;
  padding: 0.15rem;
  border: 1px solid #ccc;
  border-radius: 0.25rem;
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
</style>