import axios from "axios";

export default {
    getRooms() {
        return axios.get(`/rooms`);
    },
    getRoom(itemId) {
        return axios.get(`/rooms/${itemId}`);
    },
    saveRoom(room, itemId) {
        return axios.post(`rooms/${itemId}`, room);
    }
}