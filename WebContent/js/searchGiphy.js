/**
 * Script that handles all of the GIPHY API functionality
 */
$(document).ready(function(){
	
	// Declare constants
	const PUBLIC_KEY = 'dc6zaTOxFJmzC';
	const BASE_URL = '//api.giphy.com/v1/gifs/';
	const ENDPOINT = 'search';
	const LIMIT = 1;
	const RATING = 'g';
	const SRC = '//giphy.com/embed/xv3WUrBxWkUPC'; 
	const CLASSES = 'gif hidden';
	
	// Declare variables
	let $queryInput = $('.query');
	let $resultWrapper = $('.result');
	let $loader = $('.loader');
	let $inputWrapper = $('.input-wrapper');
	let $clear = $('.clear');
	let $button = $("#getAnotherGifButton");
	let $form = $("#addGifForm");
	let $forminput = $("#gifurl");
	let $formbutton = $("#saveGifButton");
	let $gifTable = $("#gifTable");
	let currentTimeout;
	
	// Declare the query to retrieve gif's from GIPHY with callback feature
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
	
	// Build the image tag to display the GIF
	function buildImg(url) {
		return '<img src=\"' + url + '\" class=\"' + CLASSES + '\" alt=\"gif\" />';
	}
	
	// Function to clear out the gif search box
	$clear.on('click', function(e) {
	  $queryInput.val('');
	  $inputWrapper.removeClass('active').addClass('empty');
	  $('.gif').addClass('hidden');
	  $loader.removeClass('done');
	  $button.removeClass('active');
	});
	
	// Submit the form to add a GIF to the list of gif's on the page
	// without refreshing the whole page
	$form.on('submit', function(e){
		$.post($form.attr("action"), $form.serialize(), function(response) {
			$.get("gifList", function(responseText) {
				var html = $($.parseHTML(responseText));
				$gifTable.DataTable().destroy();
				$gifTable.find('tbody').empty();
				$gifTable.find('tbody').append($(html.find('tbody'))["0"].innerHTML);
				$gifTable.DataTable().draw();
            });
	    });
		e.preventDefault();
	});
	
	$formbutton.on('click', function(e) {
		query.fetch(function(url) {			
			$forminput.val(url);			
			$form.submit();
		});
	});	
	
	// Retrieve the next gif for this search in the list of
	// returned gif's
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
	
	// In the gif search box, this function will get called
	// on key up
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
	          $form.removeClass('random');
	          $formbutton.removeClass('random');
	          $button.addClass('active');
	          $form.addClass('active');
	          $formbutton.addClass('active');
	          
	        } else {
	          $resultWrapper.html('<p class=\"no-results hidden\">No Results found for <strong>' + query.text + '</strong></p>');
	          $button.removeClass('active');
	          $form.removeClass('active');
	          $formbutton.removeClass('active');
	          $button.addClass('random');
	          $form.addClass('random');
	          $formbutton.addClass('random');
	        }
	        
	        $loader.addClass('done');	        
	        currentTimeout = setTimeout(function() {
	        	$('.hidden').toggleClass('hidden');
	        }, 1000);
	      });
	    } else {
	      $inputWrapper.removeClass('active').addClass('empty');
	      $button.removeClass('active');
          $form.removeClass('active');
          $formbutton.removeClass('active');
          $button.addClass('random');
          $form.addClass('random');
          $formbutton.addClass('random');
	    }
	  }, 1000);
	});
	
	// Toggle between the two navigation tabs for the login screen
	jQuery('.toggle-nav').click(function(e) {
		jQuery(this).toggleClass('active');
		jQuery('.menu ul').toggleClass('active');

		e.preventDefault();
	});
});