import axios from "axios";

export default {

    saveItem(item) {
        return axios.post("/item", item)
    }
}