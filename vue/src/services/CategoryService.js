import axios from "axios";

export default {
    getCategories() {
        console.log("fetching categories");
        return axios.get(`/categories`);
    }
}