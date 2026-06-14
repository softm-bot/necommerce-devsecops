import AppContext from '../context';
import { useContext } from 'react';
import Progress from '../ui/Progress';
import Alert from '../ui/Alert';
import ProductCard from '../components/ProductCard';
import { Link } from 'react-router-dom';
import routes from '../routes';

const Frontpage = () => {
  const {products} = useContext(AppContext);

  return (
    <>
      {products.loading && <Progress/>}
      {products.error && <Alert/>}
      <div className="page-content">
        <h1>Наши лучшие продукты</h1>
        <div className="row">
          {products.items.map(o =>
            <div className="col-md-6 col-lg-4 mb-4" key={o.id}>
              <ProductCard {...o} button={<Link to={routes.buy.replace(':id', o.id)}
                                                className="btn btn-danger">Купить</Link>}/>
            </div>
          )}
        </div>
      </div>
    </>
  );
};

export default Frontpage;
