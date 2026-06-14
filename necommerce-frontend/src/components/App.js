import { BrowserRouter as Router, Redirect, Route, Switch } from 'react-router-dom';
import routes from '../routes';
import NotFound from '../pages/NotFound';
import Navbar from './Navbar';
import Frontpage from '../pages/Frontpage';
import Buy from '../pages/Buy';
import OrderStatus from '../pages/OrderStatus';
import Authentication from '../pages/Authentication';
import Registration from '../pages/Registration';
import MyOrders from '../pages/MyOrders';

const App = () => {
  return (
    <>
      <Router>
        <Navbar />
        <Switch>
          <Route path={routes.frontpage} exact={true}>
            <Frontpage />
          </Route>
          <Route path={routes.buy} exact={true}>
            <Buy />
          </Route>
          <Route path={routes.status} exact={true}>
            <OrderStatus />
          </Route>
          <Route path={routes.authentication} exact={true}>
            <Authentication />
          </Route>
          <Route path={routes.registration} exact={true}>
            <Registration />
          </Route>
          <Route path={routes.myorders} exact={true}>
            <MyOrders />
          </Route>
          <Route path={routes.notfound} exact={true}>
            <NotFound />
          </Route>
          <Redirect to={routes.notfound} />
        </Switch>
      </Router>
    </>
  );
}

export default App;
