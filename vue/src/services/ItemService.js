import axios from "axios";

export default {

    saveItem(item) {
        return axios.post("/item", item)
    },
    getItems(id) {
        console.log(id);
        return axios.get(`/list-items/${id}`)
    },
    getItem(id, itemId) {
        return axios.get(`/itemdetails/${id}/${itemId}`)
    },
    updateItem(id, itemId, item) {
        return axios.post(`/update/${id}/${itemId}`, item)
    }
}