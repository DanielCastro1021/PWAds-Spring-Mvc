var CACHE_NAME = 'cache-v1';
var staticAssets = [
  '/',
  '/css/style.css',
  '/html/fallout.html',
  '/html/push-notifications.html',
  '/js/app.js',
  '/js/image-list.js',
  '/js/notifications.js',
  '/js/push-notifications.js',
  '/index.html',
];

self.addEventListener('install', (event) => {
  event.waitUntil(
    caches.open(CACHE_NAME).then((cache) => {
      return cache.addAll(staticAssets);
    })
  );
});

self.addEventListener('activation', (event) => {
  let cacheAllowList = ['cache-v1'];
  event.waitUntil(
    caches.forEach((cache, cacheName) => {
      if (cacheAllowList.indexOf(cacheName) == -1) {
        return caches.delete(cacheName);
      }
    })
  );
});

self.addEventListener('fetch', (event) => {
  event.respondWith(
    (async () => {
      const cache = await caches.open(CACHE_NAME);
      try {
        const cachedResponse = await cache.match(event.request);
        // cachedResponse = null; //Test Offline WebPage
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
        const cachedResponse = await cache.match('./html/fallout.html');
        return cachedResponse;
      }
    })()
  );
});

self.addEventListener('push', (event) => {
  const payload = event.data ? event.data.text() : 'no payload';
  // Show a notification with title 'ServiceWorker ' and use the payload
  // as the body.
  event.waitUntil(
    self.registration.showNotification('ServiceWorker Push Notification', {
      body: payload,
    })
  );
});
