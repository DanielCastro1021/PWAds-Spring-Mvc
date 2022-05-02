var ads = [];
const api_list_ads = 'http://127.0.0.1:8080/api/test/';
function loadNavBar() {
  $('#navbar').load('/components/util/navbar.html', () => {
    $('#ads-tab-button').toggleClass('active');
  });
}

async function fetchAds() {
  const options = {
    method: 'get',
    headers: new Headers({
      'Access-Control-Allow-Origin': 'http://localhost:5500',
    }),
  };
  const response = await fetch(api_list_ads, options);
  const names = await response.json();
  console.log(names);
}

window.onload = () => {
  loadNavBar();
  fetchAds();
};
