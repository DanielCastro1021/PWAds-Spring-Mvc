let imgSection = $(".carousel-inner");

if ('serviceWorker' in navigator) {
    window.addEventListener('load', function () {
        let register;
        navigator.serviceWorker
            .register('/sw.js', {scope: '/'})
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

// function for loading each image via XHR

function imgLoad(imgJSON) {
    // return a promise for an image loading
    return new Promise(function (resolve, reject) {
        let request = new XMLHttpRequest();
        request.open('GET', imgJSON.url);
        request.responseType = 'blob';

        request.onload = function () {
            if (request.status === 200) {
                let arrayResponse = [];
                arrayResponse[0] = request.response;
                arrayResponse[1] = imgJSON;
                resolve(arrayResponse);
            } else {
                reject(
                    Error(
                        "Image didn't load successfully; error code:" + request.statusText
                    )
                );
            }
        };

        request.onerror = function () {
            reject(Error('There was a network error.'));
        };

        // Send the request
        request.send();
    });
}

function loadAllImages() {
    for (let i = 0; i <= Gallery.images.length - 1; i++) {
        imgLoad(Gallery.images[i]).then(
            function (arrayResponse) {
                let myImage = document.createElement('img');
                let myFigure = document.createElement('figure');
                myImage.src = window.URL.createObjectURL(arrayResponse[0]);
                myImage.setAttribute('alt', arrayResponse[1].alt);
                imgSection.append(myFigure);
                myFigure.appendChild(myImage);
            },
            function (Error) {
                console.log(Error);
            }
        );
    }
}

function loadNavBar() {
    $('#navbar').load('../html/navbar.html', () => {
        $('#home-tab-button').toggleClass('active');
    });
}

window.onload = function () {
    // load navbar
    loadNavBar();
    // load each set of image, alt text, name and caption
    loadAllImages();
};
