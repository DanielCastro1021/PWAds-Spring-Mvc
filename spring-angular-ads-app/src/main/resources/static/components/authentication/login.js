const login_api_url = '/api/auth/login/';


document.body.addEventListener('submit', async function (event) {
    event.preventDefault();
    let username = $('#username').val();
    let password = $('#password').val();
    let firebaseToken = localStorage.getItem("firebase-token");

    const options = {
        method: 'post',
        headers: new Headers({'content-type': 'application/json'}),
    };
    const body = {
        username: username,
        password: password,
        firebaseToken: firebaseToken
    };

    options.body = JSON.stringify(body);

    await fetch(login_api_url, options)
        .then((response) => response.json())
        .then((json) => {
            localStorage.setItem('token', json['token']);
            localStorage.setItem('roles', json['roles']);
            localStorage.setItem('firebase-token', json['firebaseToken']);
            window.location.href = '../../index.html';
        });
});

function loadNavBar() {
    $('#navbar').load('../../html/navbar.html');
}


window.onload = () => {
    loadNavBar();
}