const login_api_url = 'http://127.0.0.1:8080/api/auth/register/';

if (localStorage.getItem('token') !== null)
  window.location.href = '../../index.html';

document.body.addEventListener('submit', async function (event) {
  event.preventDefault();
  let username = $('#registerUsername').val();
  let email = $('#registerEmail').val();
  let password = $('#registerPassword').val();

  const options = {
    method: 'post',
    headers: new Headers({ 'content-type': 'application/json' }),
  };
  const body = {
    username: username,
    email: email,
    password: password,
  };

  console.log(body);

  options.body = JSON.stringify(body);

  await fetch(login_api_url, options)
    .then((response) => response.json())
    .then((json) => {
      if (json.message === 'User registered successfully!')
        window.location.href = 'login.html';
    })
    .catch((error) => console.log(error));
});
