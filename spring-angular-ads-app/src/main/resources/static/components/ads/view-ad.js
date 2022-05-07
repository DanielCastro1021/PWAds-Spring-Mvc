const ads_api = "/api/ads"

function loadNavBar() {
    $('#navbar').load('../util/navbar.html', () => {
        $('#navbar>a.active').removeClass("active");
    });
}

function checkUserLogged() {
    if (localStorage.length > 0 && localStorage.getItem("token") != null) $("#my-ads-btn").show(); else $("#my-ads-btn").hide();
}

async function fetchAd(id) {
    let headers = {
        'Content-type': 'application/json', 'Authorization': `Bearer ${localStorage.getItem("token")}`, // notice the Bearer before your token
    }
    const options = {
        method: "GET", headers: new Headers(headers)
    };
    return await fetch(ads_api + "/" + id, options)
        .then((response) => {
            return response.json();
        });
}

function loadImgList(img_arr) {
    for (let index in img_arr) {
        let $div = $('<div>').addClass("item");
        let $img = $('<img/>', {
            id: `img-${index}`,
            src: `data:image/png;base64,${img_arr[index]}`,
            alt: 'MyAlt',
        });
        $div.append($img);
        if (index === "0") $div.addClass("active")
        $('.carousel-inner').append($div);
    }
}

function loadAd(ad) {
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
    loadImgList(ad['imageList']);
    $('#ad-data').append($htmlAdItem);
}


window.onload = () => {

    checkUserLogged();
    loadNavBar();
    const queryString = window.location.search;
    const urlParams = new URLSearchParams(queryString);
    const id = urlParams.get('id');
    fetchAd(id).then(loadAd)

    $('.carousel').carousel({
        interval: 2000
    })
};
