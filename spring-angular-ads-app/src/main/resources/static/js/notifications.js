const button = document.getElementById('notifications');

button.addEventListener('click', () => {
  Notification.requestPermission().then((result) => {
    if (result === 'granted') {
      randomNotification();
    }
  });
});

function randomNotification() {
  const options = {
    body: 'Notification Body',
    icon: '../icons/windows11/LargeTile.scale-100.png',
  };

  new Notification('Test Notification', options);
  setTimeout(randomNotification, 300000);
}
