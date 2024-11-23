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
          <text-field
            label="Category:"
            id="item-category"
            v-model="item.category"
            itemid="item-category"
            placeholder="Category"
          />
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

export default {
  components: {
    HeaderModule,
    TextField,
    DateField,
    CheckboxField,
    NumberField,
    FileUpload,
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
      photoUrl: null,
      showFileUpload: false,
      photo: null,
    };
  },
  methods: {
    renderFileUpload() {
      this.showFileUpload = true;
    },
    handleUploadedFile(file) {
      this.file = file;
      // this.showFileUpload = false;
    },
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
        console.log("Fetched photo data:", response.data);
        this.photo = response.data;
        console.log("this is the photo: ", this.photo);
        // this.file = new File([response.data], this.photo.name);
      });
    },
    // need to test this method, need the photo file if there is a photo
    // present in the item object...
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
            console.log("there is no photo file", response.status);
          } else {
            const blob = new Blob([response.data], { type: "image/png" });
            this.photoUrl = URL.createObjectURL(blob);
            console.log("there is a photo file", response.status);

            // after implementing the isLoading visual
            // this.isLoading = false;
          }
        })
        .catch((error) => {
          console.error("Error fetching photo ", error);
          // after implementing the isLoading visual
          // this.isLoading = false;
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
            // save the push to list until everything works
            // this.$router.push({ name: "list" });
            if (this.file) {
              console.log(
                "there is a photo file with this item: ",
                this.file.name,
                itemId
              );
              // this.savePhoto(itemId);
              // why does the program think there is a file?
              this.updatePhoto(itemId);
              this.resetForm();
              // push to list if applicable
              this.$router.push({ name: "list" });
            } else {
              console.log("there is no file present with this item");
              /*
              order of operations for photo file saving in the event there
              is NOT a photo file present from the user selection:
              1 - call savePhoto() to store the metadata in database
              2  - uploadPhoto() is called to store the actual file locally
              */
              this.savePhoto(itemId);
              this.resetForm();
              // push to list if applicable
              this.$router.push({ name: "list" });
            }
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
    updatePhoto(itemId) {
      this.getPhoto(itemId);
      const photoFileName = this.file.name;
      const photoMetadata = {
        itemId: itemId,
        name: photoFileName,
        photoUrl:
          "D:/Kevin_Docs/Engel_Docs/Tech_Elevator/workspace/GitHub/HomeInventoryApp/item-photos/",
      };
      console.log("this is the metadata ", photoMetadata);
      fileService
        .updatePhoto(photoMetadata, itemId)
        .then((response) => {
          if (response.status === 200) {
            alert("photo updated successfully");
            this.uploadPhoto(itemId);
          }
        })
        .catch((error) => {
          console.log("updating photo error!", error);
          alert("there was a problem updating the photo");
        });
    },
    uploadPhoto(itemId) {
      console.log(this.file);
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
    this.getPhotoUrl();
    console.log("this is the photoUrl ", this.photoUrl);
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