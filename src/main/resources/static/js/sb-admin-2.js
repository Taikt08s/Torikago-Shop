(function ($) {
    "use strict"; // Start of use strict

    $("#sidebarToggle, #sidebarToggleTop").on('click', function (e) {
        $("body").toggleClass("sidebar-toggled");
        $(".sidebar").toggleClass("toggled");
        if ($(".sidebar").hasClass("toggled")) {
            $('.sidebar .collapse').collapse('hide');
        }
        ;
    });

    $(window).resize(function () {
        if ($(window).width() < 768) {
            $('.sidebar .collapse').collapse('hide');
        }
        ;

        if ($(window).width() < 480 && !$(".sidebar").hasClass("toggled")) {
            $("body").addClass("sidebar-toggled");
            $(".sidebar").addClass("toggled");
            $('.sidebar .collapse').collapse('hide');
        }
        ;
    });

    $('body.fixed-nav .sidebar').on('mousewheel DOMMouseScroll wheel', function (e) {
        if ($(window).width() > 768) {
            var e0 = e.originalEvent,
                delta = e0.wheelDelta || -e0.detail;
            this.scrollTop += (delta < 0 ? 1 : -1) * 30;
            e.preventDefault();
        }
    });

    $(document).on('scroll', function () {
        var scrollDistance = $(this).scrollTop();
        if (scrollDistance > 100) {
            $('.scroll-to-top').fadeIn();
        } else {
            $('.scroll-to-top').fadeOut();
        }
    });

    $(document).on('click', 'a.scroll-to-top', function (e) {
        var $anchor = $(this);
        $('html, body').stop().animate({
            scrollTop: ($($anchor.attr('href')).offset().top)
        }, 1000, 'easeInOutExpo');
        e.preventDefault();
    });

})(jQuery);
$(document).ready(function () {
    $('#myCarousel').carousel({interval: 6000}); // Adjust the interval (in milliseconds) for auto sliding
});

function hoverCard(card) {
    card.querySelector('.card-border').classList.add('card-border-hover');
}

function unhoverCard(card) {
    card.querySelector('.card-border').classList.remove('card-border-hover');
}
$(document).ready(function () {
    // Disable the default option when the dropdown is opened
    $('#price').on('click', function () {
        $(this).find('option[value="default"]').prop('disabled', true);
    });

    // Enable the default option when an option is selected
    $('#price').on('change', function () {
        if ($(this).val() !== "default") {
            $(this).find('option[value="default"]').prop('disabled', false);
        }
    });

    // Enable the default option when clicking outside the dropdown
    $(document).on('click', function (e) {
        if (!$(e.target).closest('#price').length) {
            $('#price').find('option[value="default"]').prop('disabled', false);
        }
    });
});
document.addEventListener('DOMContentLoaded', function () {
    // Get all elements with the class 'clickable-card'
    var clickableCards = document.getElementsByClassName('clickable-card');

    // Attach click event handler to each clickable card
    Array.from(clickableCards).forEach(function (card) {
        card.addEventListener('click', function () {
            // Get the product id from the 'data-id' attribute
            var productId = card.getAttribute('data-id');

            // Construct the URL based on the product id
            var url = '/torikago/product/' + productId;

            // Navigate to the URL
            window.location.href = url;
        });
    });
});
function formatCurrency(input) {
    const value = input.value.replace(/\s+/g, ''); // Remove existing spaces
    input.value = addSpaces(value);
}

function addSpaces(str) {
    let result = '';
    let count = 0;

    for (let i = str.length - 1; i >= 0; i--) {
        result = str.charAt(i) + result;
        count++;

        if (count === 3 && i !== 0) {
            result = ' ' + result;
            count = 0;
        }
    }

    return result;
}
