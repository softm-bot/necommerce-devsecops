const storage = localStorage;
const tokenKey = 'token';

export const tokenFromStorage = () => {
  return storage.getItem(tokenKey) ?? '';
};

export const tokenToStorage = token => {
  storage.setItem(tokenKey, token);
};

export const clearStorage = () => {
  storage.clear();
};
