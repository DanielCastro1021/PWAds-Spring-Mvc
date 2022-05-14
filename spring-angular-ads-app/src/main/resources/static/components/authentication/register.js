const register_api_url = '/api/auth/register/';
if ('serviceWorker' in navigator) {
  window.addEventListener('load', function () {
    navigator.serviceWorker
      .register('../../sw.js', { scope: '../../' })
      .then((reg) => {
        if (reg.installing) {
          console.log('Service worker installing');
        } else if (reg.waiting) {
          console.log('Service worker installed');
        } else if (reg.active) {
          console.log('Service worker active');
        }
      })
      .catch(function (error) {
        // registration failed
        console.log('Registration failed with ' + error);
      });
  });
}

async function postRegister(user) {
  const options = {
    method: 'post',
    headers: new Headers({ 'content-type': 'application/json' }),
  };

  options.body = JSON.stringify(user);
  return await fetch(register_api_url, options)
    .then((response) => {
      if (response.status !== 200) {
        throw new Error('Not 200 response');
      } else return response.json();
    })
    .catch((error) => {
      console.log(error);
    });
}

function register() {
  let username = $('#username').val();
  let email = $('#email').val();
  let password = $('#password').val();
  let user = { username, email, password };
  postRegister(user).then((json) => {
    if (json.message === 'User registered successfully!') {
      localStorage.setItem('temp_username', username);
      window.location.replace(
        'http://localhost:8080/components/authentication/login.html'
      );
    }
  });
}

function loadNavBar() {
  $('#navbar').load('../../html/navbar.html');
}

window.onload = () => {
  if (localStorage.getItem('token') !== null)
    window.location.replace('http://localhost:8080/index.html');
  loadNavBar();
};
