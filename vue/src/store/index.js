import { createStore as _createStore } from 'vuex';
import axios from 'axios';

export function createStore(currentToken, currentUser) {
  let store = _createStore({
    state: {
      token: currentToken || '',
      user: currentUser || {},
      pageTitle: '',
    },
    mutations: {
      SET_AUTH_TOKEN(state, token) {
        state.token = token;
        localStorage.setItem('token', token);
        axios.defaults.headers.common['Authorization'] = `Bearer ${token}`;
      },
      SET_USER(state, user) {
        state.user = user;
        localStorage.setItem('user', JSON.stringify(user));
      },
      LOGOUT(state) {
        localStorage.removeItem('token');
        localStorage.removeItem('user');
        state.token = '';
        state.user = {};
        axios.defaults.headers.common = {};
        state.states = []
      },
      SET_STATES(state, stateCollection) {
        state.states = stateCollection;
      },
      SET_ITEM(state, item) {
        state.item = item;
      },
      SET_PHOTO(state, photo) {
        state.photo = photo;
      },
      SET_PAGE_TITLE(state, title) {
        state.pageTitle = title;
      }
    },
  });
  return store;
}
