import AppContext from '../context';
import { Redirect } from 'react-router-dom';
import routes from '../routes';
import Progress from '../ui/Progress';
import Alert from '../ui/Alert';
import { useContext, useState } from 'react';

const Authentication = () => {
  const {doLogin} = useContext(AppContext);
  const [login, setLogin] = useState('');
  const [password, setPassword] = useState('');
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const [complete, setComplete] = useState(false);

  const handleChange = evt => {
    const {name, value} = evt.target;
    if (name === 'login') {
      setLogin(value);
      return;
    }
    if (name === 'password') {
      setPassword(value);
      return;
    }
  };

  const handleSubmit = async evt => {
    evt.preventDefault();
    setLoading(true);
    setError(null);
    try {
      await doLogin(login, password);
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
        <Redirect to={routes.myorders}/>
      </>
    );
  }

  return (
    <>
      {loading && <Progress/>}
      {error && <Alert/>}
      <div className="page-content">
        <h1>Аутентификация</h1>
        <div className="row mb-4">
          <div className="col-md-6 col-lg-4">
            <p>Заполните форму (все поля обязательны):</p>
            <form onSubmit={handleSubmit}>
              <div className="mb-3">
                <label htmlFor="login" className="form-label">Логин</label>
                <input name="login" className="form-control" id="login"
                       required={true}
                       onChange={handleChange}
                />
              </div>
              <div className="mb-3">
                <label htmlFor="password" className="form-label">Пароль</label>
                <input name="password" type="password" className="form-control" id="password"
                       required={true}
                       onChange={handleChange}
                />
              </div>
              <button type="submit" className="btn btn-danger">Войти</button>
            </form>
          </div>
        </div>
      </div>
    </>
  );
}

export default Authentication;
