const get_list_ads_api = '/api/ads/all';
let ads = [];

function loadNavBar() {
    $('#navbar').load('../util/navbar.html', () => {
        $('#ads-tab-button').toggleClass('active');
    });
}

function getHtmlAdItem(ad) {
    let htmlAdItem;
    //Basic Ad
    if (ad["title"] && ad["description"]) {
        let title = "<h6>" + ad["title"] + "</h6>";
        let description = "<p>" + ad["description"] + "</p>";
        htmlAdItem = "<li class='list-group-item'>" + title + description + "</li>";
    }

    //Car Ad
    if (ad["maker"] && ad["model"] && ad["year"]) {
        let title = "<h6>" + ad["maker"] + " " + ad["model"] + "</h6>";
        let description = "<p>" + "Car for sale: " + ad["maker"] + " " + ad["model"] + " of year " + ad["year"] + "</p>";
        htmlAdItem = "<li class='list-group-item'>" + title + description + "</li>";
    }
    $('#ads-list').append(htmlAdItem);
}

function loadCarAdsList() {
    $('#sidebar>a.active').removeClass("active");
    $('#car-ads-btn').addClass("active");
    $('#ads-list').empty();
    $('#list-ad-title').text("All Car Ads List");
    ads['carAdList'].forEach(getHtmlAdItem);
}

function loadBasicAdsList() {
    $('#sidebar>a.active').removeClass("active");
    $('#basic-ads-btn').addClass("active");
    $('#ads-list').empty();
    $('#list-ad-title').text("All Basic Ads List");
    ads['basicAdList'].forEach(getHtmlAdItem);
}

function loadAllAdsList() {
    $('#sidebar>a.active').removeClass("active");
    $('#all-ads-btn').addClass("active");
    $('#ads-list').empty();
    $('#list-ad-title').text("All Ads List");
    let shuffle_arr = ads['basicAdList'].concat(ads['carAdList']).sort();
    shuffle_arr.forEach(getHtmlAdItem);
}


async function fetchAllAds() {
    let headers = {}
    const options = {
        method: "GET", headers: headers
    };
    return await fetch(get_list_ads_api, options)
        .then((response) => {
            return response.json();
        });
}

function getAllAds() {
    fetchAllAds()
        .then((json) => {
            ads = json['_embedded'];
        }).then(loadAllAdsList);
}

function checkUserLogged() {
    if (localStorage.length > 0 && localStorage.getItem("token") != null) $("#my-ads-btn").show(); else $("#my-ads-btn").hide();
}

window.onload = () => {
    checkUserLogged();
    loadNavBar();
    getAllAds();
};
