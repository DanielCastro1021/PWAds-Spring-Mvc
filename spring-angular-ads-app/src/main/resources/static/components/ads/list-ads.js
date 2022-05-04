const ads_api = '/api/ads';
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

    $('#ads-list').append(() => {
        return $(htmlAdItem).click(() => {
            window.location.href = `view-ad.html?id=${ad["id"]}`;

        })
    });
}

function transitionAdsList() {
    $('#sidebar>a.active').removeClass("active");
    $('#ads-list').empty();
    $("#ads-list").fadeIn("slow");
}

function loadMyAdsList() {
    transitionAdsList();
    $('#all-ads-btn').addClass("active");
    $('#list-ad-title').text("My Ads List");
    if (ads.hasOwnProperty("basicAdList") && ads.hasOwnProperty("carAdList")) {
        let shuffle_arr = ads['basicAdList'].concat(ads['carAdList']).sort();
        shuffle_arr.forEach(getHtmlAdItem);
    } else if (ads.hasOwnProperty("basicAdList")) {
        ads['basicAdList'].forEach(getHtmlAdItem);
    } else if (ads.hasOwnProperty("carAdList")) {
        ads['carAdList'].forEach(getHtmlAdItem);
    }
}

function loadCarAdsList() {
    transitionAdsList();
    $('#car-ads-btn').addClass("active");
    $('#list-ad-title').text("All Car Ads List");
    ads['carAdList'].forEach(getHtmlAdItem);
}

function loadBasicAdsList() {
    transitionAdsList();
    $('#basic-ads-btn').addClass("active");
    $('#list-ad-title').text("All Basic Ads List");
    ads['basicAdList'].forEach(getHtmlAdItem);
}

function loadAllAdsList() {
    transitionAdsList();
    $('#all-ads-btn').addClass("active");
    $('#list-ad-title').text("All Ads List");
    let shuffle_arr = ads['basicAdList'].concat(ads['carAdList']).sort();
    shuffle_arr.forEach(getHtmlAdItem);
}

async function fetchMyAds() {
    let headers = {
        'Content-type': 'application/json',
        'Authorization': `Bearer ${localStorage.getItem("token")}`, // notice the Bearer before your token
    }
    const options = {
        method: "GET", headers: headers
    };
    return await fetch(ads_api + "/personal", options)
        .then((response) => {
            return response.json();
        });
}

async function fetchAllAds() {
    let headers = {}
    const options = {
        method: "GET", headers: headers
    };
    return await fetch(ads_api + "/all", options)
        .then((response) => {
            return response.json();
        });
}

function getMyAds() {
    fetchMyAds()
        .then((json) => {
            ads = json['_embedded'];
        }).then(loadMyAdsList);
}

function getBasicAds() {

}

function getAllAds() {
    fetchAllAds()
        .then((json) => {
            ads = json['_embedded'];
        }).then(loadAllAdsList);
}

function checkUserLogged() {
    if (localStorage.length > 0 && localStorage.getItem("token") !== null) $("#my-ads-btn").show(); else $("#my-ads-btn").hide();
}

window.onload = () => {
    checkUserLogged();
    loadNavBar();
    getAllAds();
};
