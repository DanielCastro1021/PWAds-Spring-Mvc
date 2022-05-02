const login_api_url = 'http://127.0.0.1:8080/api/auth/login/';

if (localStorage.getItem('token') !== null)
  window.location.href = '../../index.html';

document.body.addEventListener('submit', async function (event) {
  event.preventDefault();
  let username = $('#username').val();
  let password = $('#password').val();

  const options = {
    method: 'post',
    headers: new Headers({ 'content-type': 'application/json' }),
  };
  const body = {
    username: username,
    password: password,
  };

  options.body = JSON.stringify(body);

  await fetch(login_api_url, options)
    .then((response) => response.json())
    .then((json) => {
      localStorage.setItem('token', json.token);
      window.location.href = '../../index.html';
    })
    .catch((error) => console.log(error));
});
