import axios from "axios";

export default {

    saveItem(item) {
        return axios.post("/item", item)
    },
    getItems(id) {
        console.log(id);
        return axios.get(`/list-items/${id}`)
    }
}