import React, { useEffect, useMemo, useState } from 'react';
import AppContext from '../context';
import { tokenFromStorage, tokenToStorage, clearStorage } from '../storage';
import axios from 'axios';
import routes from '../routes';

const client = axios.create({baseURL: process.env.REACT_APP_API_URL});
const savedToken = tokenFromStorage();

function Context(props) {
  const [token, setToken] = useState(savedToken);
  const [products, setProducts] = useState([]);
  const [productsLoading, setProductsLoading] = useState(false);
  const [productsError, setProductsError] = useState(null);

  const authenticated = useMemo(() => token, [token]);

  const authHeader = useMemo(() => {
    return token ? {headers: {'Authorization': token}} : undefined
  }, [token]);

  useEffect(() => {
    const effect = async () => {
      setProductsLoading(true);
      setProductsError(null);
      try {
        const {data} = await client.get('/products', authHeader);
        setProducts(data);
      } catch (e) {
        setProductsError(e);
      } finally {
        setProductsLoading(false);
      }
    };
    effect();
  }, [authHeader]);

  const doLogout = () => {
    clearStorage();
    setToken('');
  };

  const doOrder = async (productId, phone) => {
    const {data: {id}} = await client.post('/orders', new URLSearchParams([['productId', productId], ['phone', phone]]), authHeader);
    return id;
  };

  const doStatusCheck = async (orderNum) => {
    const {data: {status}} = await client.get(`/orders/${orderNum}`, authHeader);
    return status;
  };

  const doLogin = async (login, password) => {
    const {data} = await client.post('/users/authentication', new URLSearchParams([['login', login], ['pass', password]]));
    setToken(data.token);
    tokenToStorage(data.token, null);
  };

  const doRegister = async (name, login, password) => {
    const {data} = await client.post('/users/registration', new URLSearchParams([['login', login], ['pass', password], ['name', name]]));
    setToken(data.token);
    tokenToStorage(data.token, null);
  };

  const doLoadMyOrders = async () => {
    const {data} = await client.get('/orders/my', authHeader);
    return data;
  };

  return (
    <AppContext.Provider value={{
      authenticated,
      products: {
        items: products,
        loading: productsLoading,
        error: productsError,
      },
      doLogout,
      doOrder,
      doStatusCheck,
      doLogin,
      doRegister,
      doLoadMyOrders,
    }}>
      {props.children}
    </AppContext.Provider>
  );
}

export default Context;
