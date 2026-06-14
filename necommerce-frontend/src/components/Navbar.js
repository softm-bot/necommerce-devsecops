import { Link, NavLink, useHistory } from 'react-router-dom';
import routes from '../routes';
import AppContext from '../context';
import { useContext } from 'react';

const Navbar = () => {
  const history = useHistory();
  const {authenticated, doLogout} = useContext(AppContext);

  const handleLogout = () => {
    doLogout();
    history.push(routes.frontpage);
  };

  return (
    <>
      <nav className="navbar navbar-expand-lg navbar-light bg-light">
        <div className="container-fluid">
          <Link to={routes.frontpage} className="navbar-brand">Necommerce</Link>
          <button className="navbar-toggler" type="button" data-bs-toggle="collapse"
                  data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false"
                  aria-label="Показать навигацию">
            <span className="navbar-toggler-icon"/>
          </button>
          <div className="collapse navbar-collapse" id="navbarSupportedContent">
            <ul className="navbar-nav me-auto mb-2 mb-lg-0">
              <li className="nav-item">
                <NavLink to={routes.status} className="nav-link" activeClassName="active">
                  Статус заказа
                </NavLink>
              </li>
              {!authenticated && <>
                <li className="nav-item">
                  <NavLink to={routes.authentication} className="nav-link" activeClassName="active">
                    Войти
                  </NavLink>
                </li>
                <li className="nav-item">
                  <NavLink to={routes.registration} className="nav-link" activeClassName="active">
                    Регистрация
                  </NavLink>
                </li>
              </>}
              {authenticated && <>
                <li className="nav-item">
                  <NavLink to={routes.myorders} className="nav-link" activeClassName="active">
                    Ваши заказы
                  </NavLink>
                </li>
                <li className="nav-item">
                  <button className="btn nav-link" onClick={handleLogout}>
                    Выйти
                  </button>
                </li>
              </>
              }
            </ul>
          </div>
        </div>
      </nav>
    </>
  );
};

export default Navbar;
