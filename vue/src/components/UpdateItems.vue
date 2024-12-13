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
          <text-field
            label="Name:"
            id="item-name"
            v-model="item.name"
            itemid="item-name"
            placeholder="Name"
            required
          />
          <!-- <text-field
            label="Category:"
            id="item-category"
            v-model="item.category"
            itemid="item-category"
            placeholder="Category"
          /> -->
          <div class="category-container">
            <category-dropdown
              label="Category"
              id="item-category"
              v-model="item.categoryId"
              :options="categories"
              optionKey="categoryId"
              optionValue="categoryId"
              optionLabel="categoryName"
              required
            />
          </div>
          <div class="date-value-container">
            <date-field
              label="Purchase Date:"
              id="item-purchase-date"
              v-model="item.purchaseDate"
              itemid="item-purchase-date"
            />
            <checkbox-field
              label="This item has personal Value"
              id="item-is-valuable"
              v-model="item.isValuable"
              type="checkbox"
            />
          </div>
          <number-field
            label="Price:"
            id="item-purchase-price"
            v-model="item.purchasePrice"
            itemid="item-purchase-price"
            placeholder="Purchase Price"
          />
          <number-field
            label="Value:"
            id="item-current-value"
            v-model="item.value"
            itemid="item-current-price"
            placeholder="Current Value"
          />
          <text-field
            label="notes:"
            id="item-notes"
            v-model="item.notes"
            itemid="item-notes"
            placeholder="Notes"
          />
          <div v-if="this.photoUrl === null || showFileUpload">
            <file-upload
              label="Upload Photo"
              id="item-photo"
              @file-upload="handleUploadedFile"
              @file-changed="updateFileStatus"
              placeholder="Upload a Picture"
              ref="fileUploadComponent"
            />
          </div>
          <div v-else>
            <button class="button-link" @click.prevent="renderFileUpload">
              edit photo
            </button>
            <!-- here is where the event should go
             after the edit photo button is clicked
             the <file-upload component should render on screen
             after the button is clicked -->
          </div>
        </section>

        <div v-if="isLoading" class="create-class">
          <span>Uploading...</span>
        </div>

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
import TextField from "./componentModules/TextField.vue";
import DateField from "./componentModules/DateField.vue";
import CheckboxField from "./componentModules/CheckboxField.vue";
import NumberField from "./componentModules/NumberField.vue";
import FileUpload from "./componentModules/FileUpload.vue";
import CategoryDropdown from "./componentModules/CategoryDropdown.vue";
import categoryService from "../services/CategoryService.js";

export default {
  components: {
    HeaderModule,
    TextField,
    DateField,
    CheckboxField,
    NumberField,
    FileUpload,
    CategoryDropdown,
  },
  data() {
    return {
      item: {
        name: "",
        purchaseDate: "",
        purchasePrice: null,
        value: null,
        isValuable: false,
        notes: "",
        categoryId: null,
      },
      file: null,
      categories: [],
      photoMetadata: {},
      photoId: null,
      photoUrl: null,
      showFileUpload: false,
      photo: null,
      isNewPhoto: false,
      isUpdatedPhoto: false,
      isLoading: false,
    };
  },
  methods: {
    getCategories() {
      const user = this.$store.state.user;
      categoryService.getCategories(user.id).then((response) => {
        this.categories = response.data.categories;
        console.log(this.categories);
      });
    },
    renderFileUpload() {
      this.showFileUpload = true;
      this.isUpdatedPhoto = true;
    },
    handleUploadedFile(file) {
      this.file = file;
    },
    handleFileUpload(event) {
      this.file = event.target.files[0];
    },
    updateFileStatus(isChanged) {
      this.isNewPhoto = isChanged;
    },
    getItem() {
      const user = this.$store.state.user;
      const itemId = this.$route.params.id;
      service.getItem(itemId).then((response) => {
        this.item = response.data;
        console.log("item on DOM creation: ", this.item);
      });
    },
    getPhoto() {
      const itemId = this.$route.params.id;
      fileService.getPhoto(itemId).then((response) => {
        this.photoMetadata = response.data;
        this.photoId = response.data.photoId;
        this.file = new File([response.data], this.photoMetadata.name);
      });
    },
    getPhotoUrl() {
      const itemId = this.$route.params.id;
      // after implementing the isLoading visual
      // this.isLoading = true;
      fileService
        .getPhotoUrl(itemId, { responseType: "blob" })
        .then((response) => {
          if (response.status === 204) {
            this.photoUrl = null;
            this.file = null;
            this.isNewPhoto = true;
          } else {
            const blob = new Blob([response.data], { type: "image/png" });
            this.photoUrl = URL.createObjectURL(blob);
          }
        })
        .catch((error) => {
          console.error("Error fetching photo ", error);
          // after implementing the isLoading visual
          // this.isLoading = false;
        });
    },
    async updateItem() {
      if (this.item.purchaseDate) {
        this.item.purchaseDate = new Date(this.item.purchaseDate)
          .toISOString()
          .split("T")[0];
      }
      console.log("item: ", this.item);
      const user = this.$store.state.user;
      const itemId = Number(this.$route.params.id);

      try {
        // Await the response of updateItem service call
        const response = await service.updateItem(itemId, this.item);
        if (response.status === 200) {
          alert("item updated successfully! ");

          if (!this.isNewPhoto) {
            this.$router.push({ name: "list" });
            return;
          }

          if (this.file && this.isUpdatedPhoto) {
            // Wait for the photo upload to complete before proceeding
            await this.uploadPhoto(itemId);

            // After upload completes, update the photo metadata
            this.updatePhoto(itemId);
            this.$router.push({ name: "list" });
          } else {
            // Save photo metadata and move to the list page after that
            await this.uploadPhoto(itemId);
            this.savePhoto(itemId);
            this.$router.push({ name: "list" });
          }
        }
      } catch (error) {
        console.log(error);
      }
    },

    savePhoto(itemId) {
      const photoFileName = this.file.name;
      const photoMetadata = {
        itemId: itemId,
        name: photoFileName,
        photoUrl:
          "D:/Kevin_Docs/Engel_Docs/Tech_Elevator/workspace/GitHub/HomeInventoryApp/item-photos/",
      };
      fileService
        .savePhoto(photoMetadata)
        .then((response) => {
          if (response.status === 201) {
            alert("photo metadata saved to db");
          }
        })
        .catch((error) => {
          console.log(error);
          alert("there was a problem saving the photo");
        });
    },

    updatePhoto(itemId) {
      itemId = Number(itemId);
      const photoFileName = this.file.name;
      const photoMetadata = {
        photoId: this.photoId,
        itemId: itemId,
        name: photoFileName,
        photoUrl:
          "D:/Kevin_Docs/Engel_Docs/Tech_Elevator/workspace/GitHub/HomeInventoryApp/item-photos/",
      };
      fileService
        .updatePhoto(photoMetadata, itemId)
        .then((response) => {
          if (response.status === 200) {
            alert("photo metadata saved to db");
          }
        })
        .catch((error) => {
          console.log("updating photo error!", error);
          alert("there was a problem updating the photo");
        });
    },
    // this method works
    async uploadPhoto(itemId) {
      if (!this.file) return;

      this.isLoading = true;

      const formData = new FormData();
      formData.append("file", this.file);
      formData.append("itemId", itemId);

      try {
        const response = await fileService.uploadPhoto(formData);

        if (response.status === 201) {
          alert("photo uploaded successfully!");
        } else {
          alert("file upload failed.");
        }
      } catch (error) {
        console.log(error);
        alert("File upload failed.");
      } finally {
        this.isLoading = false;
      }
    },
    deleteItem() {
      if (confirm("This will permanently delete this item: are you sure?")) {
        const user = this.$store.state.user;
        const itemId = this.$route.params.id;
        const photo = this.$store.state.photo;

        service
          .deleteItem(itemId, this.item)
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
    this.getCategories();
    this.getItem();
    this.getPhoto();
    this.getPhotoUrl();
  },
};
</script>


<style scoped>
.form-container {
  display: flex;
  flex-direction: column;
  font-weight: normal;
  text-align: center;
  gap: 0.2rem;
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
.date-value-container {
  display: grid;
  grid-template-columns: auto auto;
  column-gap: 1rem;
  align-items: center;
}
.header {
  background-color: #2c6e49;
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
  background-color: #77878b;
}
.footer {
  background-color: #4c956c;
  border-radius: 0.4rem;
  padding: 0.5rem;
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
  height: 100vh;
}
</style>