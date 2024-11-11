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
          <text-field
            label="Category:"
            id="item-category"
            v-model="item.category"
            itemid="item-category"
            placeholder="Category"
          />
          <text-field
            label="Puchase Date:"
            id="item-purchase-date"
            v-model="item.purchaseDate"
            itemid="item-purchase-date"
            placeholder="Purchase Date"
          />
          <!-- <div class="item-input-group">
            <label for="item-purchase-date">Purchase Date of Item: </label>
            <input
              type="text"
              id="item-purchase-date"
              v-model="item.purchaseDate"
            />
          </div> -->
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
          <div class="item-input-group">
            <label for="item-is-valuable">Is this Item Valuable: </label>
            <input
              type="text"
              id="item-is-valuable"
              v-model="item.isValuable"
            />
          </div>
          <text-field
            label="notes:"
            id="item-notes"
            v-model="item.notes"
            itemid="item-notes"
            placeholder="Notes"
          />
          <div class="photo-input-group">
            <label for="item-photo" id="upload-label">upload photo: </label>
            <input
              type="file"
              id="item-photo"
              v-on:change="handleFileUpload"
              ref="fileInput"
            />
          </div>
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

export default {
  components: {
    HeaderModule,
    TextField,
    NumberField,
  },
  data() {
    return {
      item: {
        name: "",
        category: "",
        purchaseDate: "",
        purchasePrice: 0,
        value: 0,
        isValuable: false,
        notes: "",
      },
      file: null,
    };
  },
  methods: {
    handleFileUpload(event) {
      this.file = event.target.files[0];
      console.log("here is the file: ", this.file, this.file.name);
    },
    saveItem() {
      if (this.item.purchaseDate) {
        this.item.purchaseDate = new Date(this.item.purchaseDate)
          .toISOString()
          .split("T")[0];
      }
      console.log("complete item object: ", this.item);
      service
        .saveItem(this.item)
        .then((response) => {
          if (response.status === 201) {
            const itemId = response.data.itemId;
            const photoName = response.data.name;
            console.log("this is the Id: ", itemId, photoName);

            if (this.file) {
              this.savePhoto(itemId);
            } else {
              alert("item saved successfully!");
              this.resetForm();
            }
          }
        })
        .catch((error) => {
          console.log(error);
        });
    },
    savePhoto(itemId) {
      console.log("save photo called", itemId);
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
    this.$store.commit("SET_PAGE_TITLE", "Add Items");
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
  width: 100%;
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
  display: flex;
  flex-direction: column;
  /* align-items: end; */
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

/* label {
  flex: 0 0 auto;
  margin-right: 0.5rem;
}
input {
  display: flex;
  padding: 0.15rem;
  border: 1px solid #002855;
  border-radius: 0.25rem;
  width: 100%;
  max-width: 100;
} */
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