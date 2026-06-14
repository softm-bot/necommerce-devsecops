import AppContext from '../context';
import Progress from '../ui/Progress';
import Alert from '../ui/Alert';
import { useContext, useEffect, useState } from 'react';
import { Link, Redirect } from 'react-router-dom';
import routes from '../routes';
import ProductCard from '../components/ProductCard';
import OrderCard from '../components/OrderCard';

const MyOrders = () => {
  const {authenticated, doLoadMyOrders, products} = useContext(AppContext);
  const [orders, setOrders] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  useEffect(() => {
    const effect = async () => {
      setLoading(true);
      setError(null);
      try {
        const data = await doLoadMyOrders();
        setOrders(data);
      } catch (e) {
        setError(e);
      } finally {
        setLoading(false);
      }
    };
    effect();
  }, [doLoadMyOrders]);

  if (!authenticated) {
    return <Redirect to={routes.frontpage} />
  }

  return (
    <>
      {loading && <Progress/>}
      {error && <Alert/>}
      <div className="page-content">
        <h1>Ваши заказы</h1>
        {orders.map(o =>
          <div className="col-md-6 col-lg-4 mb-4" key={o.id}>
            <OrderCard id={o.id} name={o.productName} price={o.productPrice} published={o.published} status={o.status} attachment={products.items.find(p => p.id === o.productId)?.attachment} />
          </div>
        )}
      </div>
    </>
  );
};

export default MyOrders;
