function loadNavBar() {
    $('#navbar').load('../util/navbar.html', () => {
        $('#ads-tab-button').toggleClass('active');
    });
}

function checkUserLogged() {
    if (localStorage.length > 0 && localStorage.getItem("token") != null) $("#my-ads-btn").show(); else $("#my-ads-btn").hide();
}

window.onload = () => {
    checkUserLogged();
    loadNavBar();
    const queryString = window.location.search;
    const urlParams = new URLSearchParams(queryString);
    const id = urlParams.get('id');
    $("#ad-id").text(id);
    console.log(id);
};
