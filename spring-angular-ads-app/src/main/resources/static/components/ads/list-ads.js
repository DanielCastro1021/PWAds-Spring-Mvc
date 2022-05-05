const ads_api = '/api/ads';
let ads = [];
let ads_type;

function loadNavBar() {
    $('#navbar').load('../util/navbar.html', () => {
        $('#ads-tab-button').toggleClass('active');
    });
}

function checkUserLogged() {
    if (localStorage.length > 0 && localStorage.getItem("token") !== null) $("#my-ads-btn").show(); else $("#my-ads-btn").hide();
}

function transitionAdsList() {
    $('#sidebar>a.active').removeClass("active");
    $('#ads-list').empty();
    $("#ads-list").fadeIn("slow");
}

function getHtmlAdItem(ad) {
    console.log(ad);
    let $htmlAdItem = $('<li>').addClass("list-group-item list-group-item-action flex-column align-items-start").css("display", "grid");
    let $title = $('<h4>');
    let $description = $('<p>');
    let $owner = $('<small>');
    let $date = $('<small>');
    let dateParsed = new Date(ad['createdDate']).toUTCString();
    //Basic Ad
    if (ad.hasOwnProperty("title")) {
        $title.text(ad["title"]);
        $description.text(ad["description"]);
    } else
        //Car Ad
    if (ad.hasOwnProperty("maker")) {
        $title.text(ad["maker"] + " " + ad["model"]);
        $description.text("Car for sale: " + ad["maker"] + " " + ad["model"] + " of year " + ad["year"]);
    }

    $owner.text(`Submitted by: ${ad['owner']['username']}`)
    $date.text(`Submitted at: ${dateParsed}`)
    $htmlAdItem.append([$title, $description, $owner, $date]);
    $htmlAdItem.click(() => {
        window.location.href = `view-ad.html?id=${ad["id"]}`;

    })
    $('#ads-list').append($htmlAdItem);
}

function loadAdList() {
    ads.forEach(getHtmlAdItem);
}

function loadMixedAdsList() {
    if (ads.hasOwnProperty("basicAdList") && ads.hasOwnProperty("carAdList")) {
        let shuffle_arr = ads['basicAdList'].concat(ads['carAdList']).sort();
        shuffle_arr.forEach(getHtmlAdItem);
    } else if (ads.hasOwnProperty("basicAdList")) {
        ads['basicAdList'].forEach(getHtmlAdItem);
    } else if (ads.hasOwnProperty("carAdList")) {
        ads['carAdList'].forEach(getHtmlAdItem);
    }
}

async function fetchMyAds() {
    let headers = {
        'Content-type': 'application/json', 'Authorization': `Bearer ${localStorage.getItem("token")}`, // notice the Bearer before your token
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
            ads = json['_embedded'] || [];
        }).then(loadMixedAdsList);
}

function getCarAds() {
    fetchAllAds()
        .then((json) => {
            ads = json['_embedded']['carAdList'] || [];
        }).then(loadAdList);
}

function getBasicAds() {
    fetchAllAds()
        .then((json) => {
            ads = json['_embedded']['basicAdList'] || [];
        }).then(loadAdList);
}

function getAllAds() {
    fetchAllAds()
        .then((json) => {
            ads = json['_embedded'] || [];
        }).then(loadMixedAdsList);
}

function loadAds() {
    transitionAdsList();
    let title = $('#list-ad-title');
    switch (ads_type) {
        case 'all':
            $('#all-ads-btn').addClass("active");
            title.text("All Ads List");
            getAllAds();
            break;
        case 'basic':
            $('#basic-ads-btn').addClass("active");
            title.text("All Basic Ads List");
            getBasicAds();
            break;
        case 'car':
            $('#car-ads-btn').addClass("active");
            title.text("All Car Ads List");
            getCarAds();
            break;
        case 'my_ads':
            $('#my-ads-btn').addClass("active");
            title.text("My Ads List");
            getMyAds();
            break;
    }

}

window.onload = () => {
    const queryString = window.location.search;
    const urlParams = new URLSearchParams(queryString);
    ads_type = urlParams.get('ads');
    checkUserLogged();
    loadNavBar();
    loadAds();
};
