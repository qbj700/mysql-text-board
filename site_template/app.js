console.clear();
function MobileTopBar__init() {
  $(".mobile-top-bar__btn-toggle-side-bar").click(function () {
    let $this = $(this);

    if ($this.hasClass("active")) {
      $this.removeClass("active");
      $(".mobile-side-bar").removeClass("active");
    } else {
      $this.addClass("active");
      $(".mobile-side-bar").addClass("active");
    }
  });
}

MobileTopBar__init();

function ArticleDetail__Body__init() {
	if (toastui === undefined) {
		return;
	}

	// 유튜브 플러그인 시작
	function youtubePlugin() {
	  toastui.Editor.codeBlockManager.setReplacer('youtube', youtubeId => {
	    // Indentify multiple code blocks
	    const wrapperId = `yt${Math.random().toString(36).substr(2, 10)}`;

	    // Avoid sanitizing iframe tag
	    setTimeout(renderYoutube.bind(null, wrapperId, youtubeId), 0);

	    return `<div id="${wrapperId}"></div>`;
	  });
	}

	function renderYoutube(wrapperId, youtubeId) {
	  const el = document.querySelector(`#${wrapperId}`);

	  el.innerHTML = `<div class="toast-ui-youtube-plugin-wrap"><iframe src="https://www.youtube.com/embed/${youtubeId}" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe></div>`;
	}
	// 유튜브 플러그인 끝

	// codepen 플러그인 시작
	function codepenPlugin() {
	  toastui.Editor.codeBlockManager.setReplacer('codepen', url => {
	    const wrapperId = `yt${Math.random().toString(36).substr(2, 10)}`;

	    // Avoid sanitizing iframe tag
	    setTimeout(renderCodepen.bind(null, wrapperId, url), 0);

	    return `<div id="${wrapperId}"></div>`;
	  });
	}

	function renderCodepen(wrapperId, url) {
	  const el = document.querySelector(`#${wrapperId}`);

	  var urlParams = new URLSearchParams(url.split('?')[1]);
	  var height = urlParams.get('height');

	  el.innerHTML = `<div class="toast-ui-codepen-plugin-wrap"><iframe height="${height}" scrolling="no" src="${url}" frameborder="no" loading="lazy" allowtransparency="true" allowfullscreen="true"></iframe></div>`;
	}
	// codepen 플러그인 끝

	function Editor__init() {
	  $('.toast-ui-editor').each(function(index, node) {
	    var initialValue = $(node).prev().html().trim().replace(/t-script/gi, 'script');

	    var editor = new toastui.Editor({
	      el: node,
	      previewStyle: 'vertical',
	      initialValue: initialValue,
	      height:600,
	      plugins: [toastui.Editor.plugin.codeSyntaxHighlight, youtubePlugin, codepenPlugin]
	    });
	  });
	}
	Editor__init();

	function EditorViewer__init() {
	  $('.toast-ui-viewer').each(function(index, node) {
	    var initialValue = $(node).prev().html().trim().replace(/t-script/gi, 'script');
	    var viewer = new toastui.Editor.factory({
	      el: node,
	      initialValue: initialValue,
	      viewer:true,
	      plugins: [toastui.Editor.plugin.codeSyntaxHighlight, youtubePlugin, codepenPlugin]
	    });
	  });
	}
	EditorViewer__init();
}

ArticleDetail__Body__init();

// 최상단 이동 버튼 시작
$(window).scroll(function(){
	if ($(this).scrollTop() > 300){
		$('.btn_gotop').show();
	} else{
		$('.btn_gotop').hide();
	}
});
$('.btn_gotop').click(function(){
	$('html, body').animate({scrollTop:0},400);
	return false;
});
// 최상단 이동 버튼 끝

// Hide Header on on scroll down
var didScroll;
var lastScrollTop = 0;
var delta = 5;
var navbarHeight = $('.top-bar').outerHeight();

$(window).scroll(function(event){
    didScroll = true;
});

setInterval(function() {
    if (didScroll) {
        hasScrolled();
        didScroll = false;
    }
}, 250);

function hasScrolled() {
    var st = $(this).scrollTop();
    
    // Make sure they scroll more than delta
    if(Math.abs(lastScrollTop - st) <= delta)
        return;
    
    // If they scrolled down and are past the navbar, add class .nav-up.
    // This is necessary so you never see what is "behind" the navbar.
    if (st > lastScrollTop && st > navbarHeight){
        // Scroll Down
        $('.top-bar').removeClass('nav-down').addClass('nav-up');
        $('.mobile-top-bar').removeClass('nav-down').addClass('nav-up');
        $('.mobile-side-bar').removeClass('side-bar-down').addClass('side-bar-up');
    } else {
        // Scroll Up
        if(st + $(window).height() < $(document).height()) {
            $('.top-bar').removeClass('nav-up').addClass('nav-down');
            $('.mobile-top-bar').removeClass('nav-up').addClass('nav-down');
            $('.mobile-side-bar').removeClass('side-bar-up').addClass('side-bar-down');
        }
    }
    
    lastScrollTop = st;
}

hasScrolled();


// serviceWorker 설치

if ( 'serviceWorker' in navigator) {
	window.addEventListener('load', () => {
		navigator.serviceWorker.register('/sw.js')
			.then(reg => {
				console.log('Registered!', reg);
			}).catch(err =>{
				console.log('Registration failed:', err);
			});
	});
}