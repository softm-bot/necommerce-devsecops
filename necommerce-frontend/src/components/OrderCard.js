import { useMemo } from 'react';

const formatValue = value => {
  return value < 10 ? `0${value}` : `${value}`;
}

const OrderCard = ({id, name, price, attachment, status, published}) => {
  const created = useMemo(() => {
    const date = new Date(published * 1000);
    return `${formatValue(date.getDate())}.${formatValue(date.getMonth() + 1)}.${date.getFullYear()} ${formatValue(date.getHours())}:${formatValue(date.getMinutes())}`;
  }, [published])

  return (
    <>
      <div>
        <div className="card">
          {attachment?.type === 'IMAGE' && <img src={`${process.env.REACT_APP_MEDIA_URL}/${attachment.url}`} className="card-img-top" alt={name} />}
          <div className="card-body">
            <h5 className="card-title">{name}</h5>
            <ul className="list-group list-group-flush">
              <li className="list-group-item">Номер заказа: {id}</li>
              <li className="list-group-item">Цена: {price} руб.</li>
              <li className="list-group-item">Создан: {created}</li>
              <li className="list-group-item">Статус: {status}</li>
            </ul>
          </div>
        </div>
      </div>
    </>
  );
};

export default OrderCard;
