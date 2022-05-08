let CACHE_NAME = 'cache-v1';
let staticAssets = [
    'manifest.json',
    'index.html',
    'css/style.css',
    'html/fallout.html', 'html/navbar.html',
    'js/app.js', 'js/image-list.js',
    'icons/ios/144.png', 'icons/ios/144.png'
];
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
