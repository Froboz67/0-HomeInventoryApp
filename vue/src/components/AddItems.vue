<template>
  <div id="items" class="text-center">
    <header-module />
    <div class="form-container">
      <form v-on:submit.prevent="saveItem">
        <header class="header">{{ item.name || "Item Name" }}</header>
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
          <div class="form-group">
            <label for="item-category">Category:</label>
            <select id="item-category" v-model="item.category" required>
              <option disabled value="">Select an Category</option>
              <option
                v-for="category in categories"
                :key="category.categoryName"
                value=""
              >
                {{ category.categoryName }}
              </option>
            </select>
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
          <file-upload
            label="Upload Photo"
            id="item-photo"
            @file-upload="handleUploadedFile"
            placeholder="Upload a Picture"
            ref="fileUploadComponent"
          />
        </section>
        <footer class="footer" id="button-links">
          <button class="button-link" type="submit">Save Item</button>
        </footer>
      </form>
    </div>
  </div>
</template>

<script>
import service from "../services/ItemService.js";
import fileService from "../services/FileService.js";
import HeaderModule from "./componentModules/HeaderModule.vue";
import TextField from "./componentModules/TextField.vue";
import NumberField from "./componentModules/NumberField.vue";
import FileUpload from "./componentModules/FileUpload.vue";
import DateField from "./componentModules/DateField.vue";
import CheckboxField from "./componentModules/CheckboxField.vue";
import categoryService from "../services/CategoryService.js";

export default {
  components: {
    HeaderModule,
    TextField,
    NumberField,
    FileUpload,
    DateField,
    CheckboxField,
  },
  data() {
    FileUpload;
    return {
      item: {
        name: "",
        category: "",
        purchaseDate: null,
        purchasePrice: null,
        value: null,
        isValuable: false,
        notes: "",
      },
      file: null,
      categories: [],
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
    handleUploadedFile(file) {
      console.log("handleUploadedFile() Add Items");
      this.file = file;
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
            const itemId = response.data.itemId;
            // don't think this variable is used or needed
            const photoName = response.data.name;
            alert("item saved successfully!");

            // checking for the existence of a photo file
            if (this.file) {
              // call the uploadPhoto first if there is a file present in the UI
              this.uploadPhoto(itemId);
              // calls the savePhoto method in the event the is
              // a file in the UI
              // original code below saved photo metadata first
              // this.savePhoto(itemId);
            } else {
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
    /* 
    savePhoto() is only saving the photo metadata to the database
    the uploadPhoto() is what truly uploads the photo file to 
    local storage
    */
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
          if (response.status === 201) {
            alert("photo metadata saved to db");
            this.resetForm();
            this.$router.push({ name: "list" });
            // originally the code saved meta data first the code is below
            // after metadata has been successfully saved the photo is uploaded
            // to local storage
            // this.uploadPhoto(itemId);
          }
        })
        .catch((error) => {
          console.log(error);
          alert("there was a problem saving the photo");
        });
    },
    uploadPhoto(itemId) {
      console.log("upload file on initial create ", this.file);
      if (!this.file) return;

      const formData = new FormData();
      formData.append("file", this.file);
      formData.append("itemId", itemId);
      console.log("this is the form data ", formData);

      fileService
        .uploadPhoto(formData)
        .then((response) => {
          if (response.status === 201) {
            alert("photo uploaded successfully!");
            this.savePhoto(itemId);
            // originally the upload method happened second. it needs to happen first for data integrity
            // this.resetForm();
            // this.$router.push({ name: "list" });
          }
        })
        .catch((error) => {
          console.log(error);
          alert("file upload failed.");
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
      };
      this.file = null;
      if (this.$refs.fileUploadComponent?.$refs.fileInput) {
        this.$refs.fileUploadComponent.$refs.fileInput.value = "";
      }
    },
  },
  created() {
    this.$store.commit("SET_PAGE_TITLE", "Add Items");
    this.getCategories();
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
  width: 100%;
  box-sizing: border-box;
  box-shadow: 0 0.25rem 0.5rem #33415c;
}
.form-container > * {
  padding: 0.2rem;
  border-radius: 0.4rem;
  gap: 0.2rem;
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
  margin-bottom: 0.2rem;
}
.section {
  display: flex;
  flex-direction: column;
  /* align-items: end; */
  color: white;
  border: 0.05rem solid #001233;
  border-radius: 0.4rem;
  padding: 0.5rem;
  margin-bottom: 0.2rem;
}
.footer {
  background-color: #4c956c;
  border-radius: 0.4rem;
  padding: 0.5rem;
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