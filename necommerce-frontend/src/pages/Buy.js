import { useParams, Redirect} from 'react-router-dom';
import AppContext from '../context';
import { useContext, useState } from 'react';
import routes from '../routes';
import ProductCard from '../components/ProductCard';
import Progress from '../ui/Progress';
import Alert from '../ui/Alert';

const Buy = () => {
  const {id} = useParams();
  const {products, doOrder} = useContext(AppContext);
  const [phone, setPhone] = useState('');
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const [complete, setComplete] = useState(false);
  const [orderNum, setOrderNum] = useState(0);
  const product = products.items.find(o => o.id == id);

  if (product === undefined) {
    return <Redirect to={routes.notfound}/>
  }

  const handleChange = evt => {
    const {value} = evt.target;
    setPhone(value);
  };

  const handleSubmit = async evt => {
    evt.preventDefault();
    setLoading(true);
    setError(null);
    try {
      const orderNum = await doOrder(id, phone);
      setOrderNum(orderNum);
      setComplete(true);
    } catch (e) {
      setError(e);
    } finally {
      setLoading(false);
    }
  };

  if (complete) {
    return (
      <>
        <div className="page-content">
          <h1>Благодарим за покупку</h1>
          <p>Наши менеджеры свяжутся с вами в ближайшее время.</p>
          <p>Номер заказа: {orderNum}</p>
        </div>
      </>
    );
  }

  return (
    <>
      {loading && <Progress/>}
      {error && <Alert/>}
      <div className="page-content">
        <h1>Покупка</h1>
        <div className="row mb-4">
          <div className="col-md-6 col-lg-4">
            <p>Вы собираетесь приобрести следующий товар:</p>
            <ProductCard {...product} />
          </div>
          <div className="col-md-6 col-lg-4">
            <p>Укажите телефон, чтобы мы могли связаться с вами:</p>
            <form onSubmit={handleSubmit}>
              <input name="productId" type="hidden" value={id}/>
              <div className="mb-3">
                <label htmlFor="phone" className="form-label">Телефон</label>
                <input name="phone" type="phone" className="form-control" id="phone"
                       placeholder="+79xxxxxxxxx"
                       required={true}
                       onChange={handleChange}
                />
              </div>
              <button type="submit" className="btn btn-danger">Заказать</button>
            </form>
          </div>
        </div>
      </div>
    </>
  );
};

export default Buy;
