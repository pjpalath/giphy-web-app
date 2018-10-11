/**
 * 
 */
$(document).ready(function(){
	const PUBLIC_KEY = 'dc6zaTOxFJmzC';
	const BASE_URL = '//api.giphy.com/v1/gifs/';
	const ENDPOINT = 'search';
	const LIMIT = 1;
	const RATING = 'g';
	const SRC = '//giphy.com/embed/xv3WUrBxWkUPC'; 
	const CLASSES = 'gif hidden';
	
	let $queryInput = $('.query');
	let $resultWrapper = $('.result');
	let $loader = $('.loader');
	let $inputWrapper = $('.input-wrapper');
	let $clear = $('.clear');
	let $button = $('.random');
	let currentTimeout;
	
	let query = {
	  text: null,
	  offset: 0,
	  request: function() {
	    return BASE_URL + ENDPOINT + '?q=' + this.text + '&limit=' + LIMIT + '&rating=' + RATING + '&offset=' + this.offset + '&api_key=' + PUBLIC_KEY;
	  },
	  fetch: function(callback) {
	    $.getJSON(this.request())
	      .done(function(data) {
	        let results = data.data;
	        
	        if (results.length) {
	          let url = results[0].images.downsized.url;
	          console.log(results);
	          callback(url);
	        } else {
	          callback('');
	        }
	      })
	      .fail(function(error) {
	        console.log(error);
	      });
	  }
	}
	
	function buildImg(url) {
	  return '<img src=\"' + url + '\" class=\"' + CLASSES + '\" alt=\"gif\" />';
	}
	
	$clear.on('click', function(e) {
	  $queryInput.val('');
	  $inputWrapper.removeClass('active').addClass('empty');
	  $('.gif').addClass('hidden');
	  $loader.removeClass('done');
	  $button.removeClass('active');
	});
	
	$button.on('click', function(e) {
	  query.offset = Math.floor(Math.random() * 25);
	  
	  query.fetch(function(url) {
	    if (url.length) {
	      $resultWrapper.html(buildImg(url));
	
	      $button.addClass('active');
	    } else {
	      $resultWrapper.html('<p class=\"no-results hidden\">No Results found for <strong>' + query.text + '</strong></p>');
	
	      $button.removeClass('active');
	    }
	
	    $loader.addClass('done');
	    currentTimeout = setTimeout(function() {
	      $('.hidden').toggleClass('hidden');
	    }, 1000);
	  });
	});
	
	$queryInput.on('keyup', function(e) {
	  let key = e.which || e.keyCode;
	  query.text = $queryInput.val();
	  query.offset = Math.floor(Math.random() * 25);
	  
	  if (currentTimeout) {
	    clearTimeout(currentTimeout);
	    $loader.removeClass('done');
	  }
	  
	  currentTimeout = setTimeout(function() {
	    currentTimeout = null;
	    $('.gif').addClass('hidden');
	    
	    if (query.text && query.text.length) {
	      $inputWrapper.addClass('active').removeClass('empty');
	      
	      query.fetch(function(url) {
	        if (url.length) {
	          $resultWrapper.html(buildImg(url));
	          
	          $button.removeClass('random');
	          $button.addClass('active');
	          
	        } else {
	          $resultWrapper.html('<p class=\"no-results hidden\">No Results found for <strong>' + query.text + '</strong></p>');
	          $button.removeClass('active');
	        }
	        
	        $loader.addClass('done');
	        currentTimeout = setTimeout(function() {
	          //$('.hidden').toggleClass('hidden');
	        }, 1000);
	      });
	    } else {
	      $inputWrapper.removeClass('active').addClass('empty');
	      $button.removeClass('active');
	    }
	  }, 1000);
	});
});