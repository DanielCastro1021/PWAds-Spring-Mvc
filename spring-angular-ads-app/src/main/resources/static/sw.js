let CACHE_NAME = 'cache-v1';

let externalAssets = [
    'https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js',
    'https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js',
    'https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css', 'https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js',
    'https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js',
    "https://www.gstatic.com/firebasejs/6.6.2/firebase-app.js",
];

let staticAssets = [
    'manifest.json',
    'index.html',
    'css/style.css',
    'html/fallout.html', 'html/navbar.html',
    'js/app.js', 'js/image-list.js',
    'icons/ios/100.png', 'icons/ios/144.png',
    'img/home-img-placeholder.png', 'img/broken-1.png',
    'components/ads/list-ads.html', 'components/ads/list-ads.js',
    'components/authentication/login.html', 'components/authentication/login.js',
    'components/authentication/register.html', 'components/authentication/register.js'
];

staticAssets.concat(externalAssets);

self.addEventListener('install', (event) => {
    event.waitUntil(caches.open(CACHE_NAME).then((cache) => {
        return cache.addAll(staticAssets);
    }));
});

self.addEventListener('activation', (event) => {
    let cacheAllowList = [CACHE_NAME];
    event.waitUntil(caches.forEach((cache, cacheName) => {
        if (cacheAllowList.indexOf(cacheName) === -1) {
            return caches.delete(cacheName);
        }
    }));
    firebaseInit();
});

self.addEventListener('fetch', (event) => {
    event.respondWith((async () => {
        const cache = await caches.open(CACHE_NAME);
        try {
            const cachedResponse = await cache.match(event.request);
            if (cachedResponse) {
                console.log('cachedResponse: ', event.request.url);
                return cachedResponse;
            }

            const fetchResponse = await fetch(event.request);
            if (fetchResponse) {
                console.log('fetchResponse: ', event.request.url);
                await cache.put(event.request, fetchResponse.clone());
                return fetchResponse;
            }
        } catch (error) {
            console.log('Fetch failed: ', error);
            return await cache.match('./html/fallout.html');
        }
    })());
});

self.addEventListener('push', (event) => {
    const payload = event.data ? event.data.text() : 'no payload';
    // Show a notification with title 'ServiceWorker ' and use the payload
    // as the body.
    event.waitUntil(self.registration.showNotification('ServiceWorker Push Notification', {
        body: payload,
    }));
});

// service-worker.js
self.addEventListener('message', event => {
    console.log(`The client sent me a message: ${event.data}`);
    getToken(event);
});


//Firebase Notification Config

importScripts('https://www.gstatic.com/firebasejs/6.6.2/firebase-app.js');
importScripts('https://www.gstatic.com/firebasejs/6.6.2/firebase-messaging.js');

const vapidKey = "BCX_it2GStqQGsv1IJRzyLzCjEvo-adRhY47KUKvOBGhd0nNH2xyRN90oojy0Da7s66vZ-FwbECkdj9OAXNfMWw";

const firebaseConfig = {
    apiKey: "AIzaSyBH0o09K98VCIAfX2ng8nKj0-_2pbAIPUk",
    authDomain: "pwads-app.firebaseapp.com",
    projectId: "pwads-app",
    storageBucket: "pwads-app.appspot.com",
    messagingSenderId: "63582499105",
    appId: "1:63582499105:web:810f385ca66e61fc3a4e60",
    measurementId: "G-MR0N6J3ZSX"
};
// Initialize Firebase
firebase.initializeApp(firebaseConfig);


// Initialize Firebase Cloud Messaging and get a reference to the service
const messaging = firebase.messaging();


function firebaseInit() {

    messaging.onBackgroundMessage((payload) => {
        console.log('[firebase-messaging-sw.js] Received background message ', payload);
        // Customize notification here
        const notificationTitle = 'Background Message Title';
        const notificationOptions = {
            body: 'Background Message body.',
            icon: '/firebase-logo.png'
        };

        self.registration.showNotification(notificationTitle,
            notificationOptions);
    });

}

function getToken(event) {

    messaging.getToken({vapidKey: vapidKey, serviceWorkerRegistration: self.registration})
        .then((currentToken) => {
            if (currentToken) {
                console.log('current token for client: ', currentToken);
                event.source.postMessage(currentToken);
                // Track the token -> client mapping, by sending to backend server
                // show on the UI that permission is secured
            } else {
                console.log('No registration token available. Request permission to generate one.');
                // shows on the UI that permission is required
            }
        }).catch((err) => {
        console.log('An error occurred while retrieving token. ', err);
        // catch error while creating client token
    });
}


function deleteToken() {
    // [START messaging_delete_token]
    messaging.deleteToken().then(() => {
        console.log('Token deleted.');
        // ...
    }).catch((err) => {
        console.log('Unable to delete token. ', err);
    });
    // [END messaging_delete_token]
}