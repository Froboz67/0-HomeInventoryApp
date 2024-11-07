<template>
  <div id="items" class="text-center">
    <form v-on:submit.prevent="saveItem">
      <h1>Add Items</h1>

      <div class="item-input-group">
        <label for="item-name">Name of Item: </label>
        <input type="text" id="item-name" v-model="item.name" required />
      </div>
      <div class="item-input-group">
        <label for="item-category">Category of Item: </label>
        <input
          type="text"
          id="item-category"
          v-model="item.category"
          required
        />
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
          type="text"
          id="item-purchase-price"
          v-model="item.purchasePrice"
        />
      </div>
      <div class="item-input-group">
        <label for="item-value">Current Value of Item: </label>
        <input type="text" id="item-value" v-model="item.value" required />
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
        <button class="button-link" type="submit">Save Item</button>
      </div>
    </form>
  </div>
</template>

<script>
import service from "../services/ItemService.js";
import fileService from "../services/FileService.js";

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
      file: null,
    };
  },
  methods: {
    handleFileUpdoad(event) {
      this.file = event.target.files[0];
      console.log("here is the file: ", this.file);
    },
    saveItem() {
      if (this.item.purchaseDate) {
        this.item.purchaseDate = new Date(this.item.purchaseDate)
          .toISOString()
          .split("T")[0];
      }
      service
        .saveItem(this.item)
        .then((response) => {
          if (response.status === 201) {
            //
            const itemId = response.data.itemId;
            // add the call to the uploadPhoto() here

            console.log("this is the Id: ", itemId);
            alert("item saved successfully!");
            this.uploadPhoto(itemId);
          }
        })
        .catch((error) => {
          console.log(error);
        });
    },
    uploadPhoto(itemId) {
      if (!this.file) return;

      const formData = new FormData();
      formData.append("file", this.file);
      formData.append("itemId", itemId);
      console.log("we are inside the upload", formData);
      fileService
        .savePhoto(formData)
        .then((response) => {
          if (response.status === 200) {
            alert("photo saved!");
            this.resetForm;
          }
        })
        .catch((error) => {
          console.log(error);
        });
    },
    resetForm() {
      this.item = {
        name: "",
        category: "",
        purchaseDate: "",
        purchasePrice: null,
        value: null,
        isValuable: false,
        notes: "",
        file: null,
      };
    },
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