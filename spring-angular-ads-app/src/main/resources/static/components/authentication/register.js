const login_api_url = '/api/auth/register/';


document.body.addEventListener('submit', async function (event) {
    event.preventDefault();
    let username = $('#username').val();
    let email = $('#email').val();
    let password = $('#password').val();

    const options = {
        method: 'post', headers: new Headers({'content-type': 'application/json'}),
    };
    const body = {
        username: username, email: email, password: password,
    };


    options.body = JSON.stringify(body);

    await fetch(login_api_url, options)
        .then((response) => response.json())
        .then((json) => {
            if (json.message === 'User registered successfully!') {
                localStorage.setItem("temp_username", username);
                window.location.href = 'login.html';
            }
        })
        .catch((error) => console.log(error));
});

function loadNavBar() {
    $('#navbar').load('../../html/navbar.html');
}

window.onload = () => {
    if (localStorage.getItem('token') !== null) window.location.href = '../../index.html';
    loadNavBar();
}