/** 
navigator.serviceWorker.ready.then(function (registration) {
  // Use the PushManager to get the user's subscription to the push service.
  return registration.pushManager
    .getSubscription()
    .then(async function (subscription) {
      console.log(JSON.stringify(subscription));
    });
});
*/
const publicVapidKey =
  'BE1-RXvPUpKcok65aruCLXTNMoIumjF4HTXcrM2WDLvvVUa6oVMeP6Z3Ek5ac-JJPt3oinCx28old_mtMnwACZY';

//check if the service worker is ready in the current browser
if ('serviceWorker' in navigator) {
  navigator.serviceWorker.ready.then(function (registration) {
    getSubscription(registration)
      .then((subscription) => {
        subscribeToPushServer(subscription);
      })
      .catch((err) => console.error(err));
  });
}

//r register our push api, send the notifation
async function getSubscription(registration) {
  const subscription = await registration.pushManager.subscribe({
    userVisibleOnly: true,
    applicationServerKey: urlBase64ToUint8Array(publicVapidKey),
  });
  return subscription;
}
async function subscribeToPushServer(subscription) {
  await fetch('http://localhost:3000/subscribe', {
    method: 'POST',
    body: JSON.stringify(subscription),
    headers: {
      'content-type': 'application/json',
    },
  });
}

function urlBase64ToUint8Array(base64String) {
  const padding = '='.repeat((4 - (base64String.length % 4)) % 4);
  const base64 = (base64String + padding)
    .replace(/\-/g, '+')
    .replace(/_/g, '/');

  const rawData = window.atob(base64);
  const outputArray = new Uint8Array(rawData.length);

  for (let i = 0; i < rawData.length; ++i) {
    outputArray[i] = rawData.charCodeAt(i);
  }
  return outputArray;
}
