<template>
  <div id="items" class="text-center">
    <!-- <h1>Update Item</h1> -->
    <div>
      <header-module />
    </div>
    <div class="form-container">
      <form v-on:submit.prevent="updateItem">
        <header class="header">{{ item.name }}</header>
        <section class="section">
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
            <input
              type="text"
              id="item-is-valuable"
              v-model="item.isValuable"
            />
          </div>
          <div class="item-input-group">
            <label for="item-notes">Notes about this Item: </label>
            <input type="text" id="item-notes" v-model="item.notes" />
          </div>
          <div class="photo-input-group">
            <label for="item-photo">upload photo: </label>
            <input
              type="file"
              id="item-photo"
              v-on:change="handleFileUpload"
              ref="fileInput"
            />
          </div>
        </section>
        <footer class="footer">
          <button class="button-link" type="submit">Save Updated Item</button>
          <button type="button" class="button-link" v-on:click="deleteItem">
            Delete Item
          </button>
          <button type="button" class="button-link" v-on:click="cancelEdit">
            Cancel Return to Items
          </button>
        </footer>
      </form>
    </div>
  </div>
</template>

<script>
import fileService from "../services/FileService";
import service from "../services/ItemService";
import HeaderModule from "./componentModules/HeaderModule.vue";

export default {
  components: {
    HeaderModule,
  },
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
    handleFileUpload(event) {
      this.file = event.target.files[0];
      console.log("here is the file: ", this.file, this.file.name);
    },
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
    savePhoto(itemId) {
      const photoFileName = this.file.name;
      const photoMetadata = {
        itemId: itemId,
        name: photoFileName,
        photoUrl:
          "D:/Kevin_Docs/Engel_Docs/Tech_Elevator/workspace/GitHub/HomeInventoryApp/item-photos/",
      };
      console.log("this is the metadata ", photoMetadata);
      fileService
        .savePhoto(photoMetadata)
        .then((response) => {
          console.log(response);
          if (response.status === 201) {
            alert("photo metadata saved to db");
            this.uploadPhoto(itemId);
          }
        })
        .catch((error) => {
          console.log(error);
          alert("there was a problem saving the photo");
        });
    },
    uploadPhoto(itemId) {
      if (!this.file) return;

      const formData = new FormData();
      formData.append("file", this.file);
      formData.append("itemId", itemId);

      fileService
        .uploadPhoto(formData)
        .then((response) => {
          if (response.status === 201) {
            alert("photo saved!");
            this.resetForm();
          }
        })
        .catch((error) => {
          console.log(error);
          alert("file upload failed.");
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
    resetForm() {
      this.item = {
        name: "",
        category: "",
        purchaseDate: "",
        purchasePrice: null,
        value: null,
        isValuable: false,
        notes: "",
      };
      this.file = null;
      if (this.$refs.fileInput) {
        this.$refs.fileInput.value = null;
      }
    },
  },
  created() {
    this.$store.commit("SET_PAGE_TITLE", "Update Items");
    this.getItem();
    this.getPhoto();
  },
};
</script>


<style scoped>
.form-container {
  display: flex;
  flex-direction: column;
  font-weight: normal;
  text-align: center;
  gap: 0.5rem;
  padding: 0.5rem;
  margin: 0.5rem auto;
  border: solid 1px black;
  max-width: 600px;
  box-shadow: 0 0.25rem 0.5rem rgba(0, 0, 0, 0.1);
  border-radius: 0.5rem;
  background-color: #979dac;

  box-sizing: border-box;
}
.form-container > * {
  padding: 0.4rem;
  border-radius: 0.4rem;
}
.header {
  background-color: #023e7d;
  color: white;
  font-size: 2.2rem;
  font-weight: normal;
  border-radius: 0.4rem;
  padding: 0.5rem;
  margin-bottom: 0.5rem;
}
.section {
  color: white;
  border: 0.05rem solid #001233;
  border-radius: 0.4rem;
  padding: 0.5rem;
  margin-bottom: 0.5rem;
}
.footer {
  background-color: #0466c8;
  border-radius: 0.4rem;
  padding: 0.5rem;
}
.item-input-group,
.photo-input-group {
  display: flex;
  margin-bottom: 0.5rem;
  margin-top: 0.5rem;
  align-items: center;
  justify-content: flex-start;
}
label {
  flex: 0 0 auto;
  margin-right: 0.5rem;
}
input {
  flex: 1;
  padding: 0.15rem;
  border: 1px solid #002855;
  border-radius: 0.25rem;
  width: 100%;
  max-width: 100;
}
input[type="file"] {
  padding: 0.15rem;
  border: 1px solid #002855;
  border-radius: 0.25rem;
  width: 100%;
  max-width: 100;
  background-color: white;
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
#items {
  display: flex;
  flex-direction: column;
  justify-content: center;
  height: 100vh;
}
</style>