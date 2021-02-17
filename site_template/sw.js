self.addEventListener('install', event => {
	console.log('Install event!');
});

self.addEventListener('activate', event => {
	console.log('activate event!');
});

self.addEventListener('fetch', event => {
	console.log('Fetch intercepted for:', event.request.url);
});

self.addEventListener('install', function(event) {
  // Perform install steps
});


const cacheName = 'cache-v1';
const resourcesToPrecache = [
  '/',
  '/app.css',
  '/app.js',
  '/article_search.js',
  '/favicon.ico',
  '/img/bg/home.jpg',
  '/img/icons/icons-192.png',
  '/img/icons/icons-512.png',
  '/img/logo/logo_opengraph.png'
];

self.addEventListener('install', function(event) {
	console.log('Service worker install event!');
	event.waitUntil(
		caches.open(cacheName)
			.then(cache => {
				return cache.addAll(resourcesToPrecache);
			})
	);
});


self.addEventListener('fetch', function(event) {
  event.respondWith(
    fetch(event.request).catch(function() {
      return caches.match(event.request);
    })
  );
});

self.addEventListener('fecth', event =>{
	event.respondWith(caches.match(event.request)
		.then(cachedResponse => {
			return cachedResponse || fetch(event.request);
		})
	);
});