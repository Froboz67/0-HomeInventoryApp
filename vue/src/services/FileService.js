import axios from "axios";

export default {

    savePhoto(file, itemId, fileName) {
        return axios.post(`/photo`, file, itemId, fileName)
    },
    uploadPhoto(formData) {
        return axios.post(`/photo/upload`, formData, {
            headers: {
                "Content-Type": "multipart/form-data",
            },
        });
    },
    getPhoto(itemId) {
        console.log("made it to the fileservice:", itemId)
        return axios.get(`/photo/${itemId}`);
    },
    deletePhoto(file, itemId) {
        return axios.delete(`/delete-photo/${itemId}`, file)
    },
    getPhotoUrl(itemId) {
        return axios.get(`/photo/file/item/${itemId}`, { responseType: "blob" });
    },
    updatePhoto(file, itemId, photo) {
        console.log("this is the itemID ", itemId)
        return axios.post(`/update/${itemId}`, file, itemId, photo);
    }

}