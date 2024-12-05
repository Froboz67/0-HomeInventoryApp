import axios from "axios";

export default {

    saveItem(item) {
        return axios.post("/item", item);
    },
    getItems() {
        console.log("testing");
        return axios.get(`/list-items`);
    },
    getItem(itemId) {
        return axios.get(`/itemdetails/${itemId}`);
    },
    updateItem(itemId, item) {
        return axios.post(`/update/${itemId}`, item);
    },
    deleteItem(itemId, item) {
        return axios.delete(`/delete/${itemId}`, item);
    }

}