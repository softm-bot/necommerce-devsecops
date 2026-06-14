import Progress from '../ui/Progress';
import Alert from '../ui/Alert';
import { useContext, useState } from 'react';
import AppContext from '../context';

const OrderStatus = () => {
  const {doStatusCheck} = useContext(AppContext);
  const [orderNum, setOrderNum] = useState('');
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const [complete, setComplete] = useState(false);
  const [orderStatus, setOrderStatus] = useState('');

  const handleChange = evt => {
    const {value} = evt.target;
    setOrderNum(value);
  };

  const handleSubmit = async evt => {
    evt.preventDefault();
    setLoading(true);
    setError(null);
    try {
      const orderStatus = await doStatusCheck(orderNum);
      setOrderStatus(orderStatus);
      setComplete(true);
    } catch (e) {
      setError(e);
    } finally {
      setLoading(false);
    }
  };

  return (
    <>
      {loading && <Progress/>}
      {error && <Alert/>}
      <div className="page-content">
        <h1>Статус заказа</h1>
        <div className="row mb-4">
          <div className="col-md-6 col-lg-4">
            <p>Укажите номер заказа:</p>
            <form onSubmit={handleSubmit}>
              <div className="mb-3">
                <label htmlFor="orderNum" className="form-label">Номер заказа</label>
                <input name="orderNum" className="form-control" id="orderNum"
                       required={true}
                       onChange={handleChange}
                />
              </div>
              <button type="submit" className="btn btn-danger">Проверить</button>
            </form>
          </div>
        </div>
        {complete && <div className="row">
          <div className="col">
            <div className="alert alert-success" role="alert">
              Статус вашего заказа: {orderStatus}
            </div>
          </div>
        </div>}
      </div>
    </>
  );
};

export default OrderStatus;
