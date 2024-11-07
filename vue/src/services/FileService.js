import axios from "axios";

export default {

    savePhoto(formData) {
        return axios.post(`/photo`, formData)
    }
}