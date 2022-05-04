const login_api_url = '/api/auth/login/';

if (localStorage.getItem('token') !== null)
    window.location.href = '../../index.html';

document.body.addEventListener('submit', async function (event) {
    event.preventDefault();
    let username = $('#username').val();
    let password = $('#password').val();

    const options = {
        method: 'post',
        headers: new Headers({'content-type': 'application/json'}),
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
            localStorage.setItem('roles', json.roles);
            window.location.href = '../../index.html';
        })
        .catch((error) => console.log(error));
});

function loadNavBar() {
    $('#navbar').load('../util/navbar.html');
}

function loadUsername() {
    if (localStorage.length > 0 && localStorage.getItem("temp_username") !== null) {
        $("#username").val(localStorage.getItem("temp_username"));
    }

}

window.onload = () => {
    loadNavBar();
    loadUsername();
}