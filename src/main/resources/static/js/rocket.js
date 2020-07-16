$(function() {
	var b = $(".bg-color");
 	var e = $("#rocket-to-top"),
 	t = $(document).scrollTop(),
 	n,
 	r,
 	i = true;
 	$(window).scroll(function() {
 		var t = $(document).scrollTop();
 		rocketPosition(t);
		changingStyles(t);
 	});
	rocketPosition(t);
	changingStyles(t);
 	e.hover(function() {
 		$(".level-2").stop(true).animate({
 			opacity: 1
 		})
 	},
 	function() {
 		$(".level-2").stop(true).animate({
 			opacity: 0
 		})
 	});
 	$(".level-3").click(function() {
 		function t() {
 			var t = e.css("background-position");
 			if (e.css("display") == "none" || i == false) {
 				clearInterval(n),
 				e.css("background-position", "0px 0px");
 				return
 			}
 			switch (t){
 			case "0px 0px":
 				e.css("background-position", "-298px 0px");
 				break;
 			case "-298px 0px":
 				e.css("background-position", "-447px 0px");
 				break;
 			case "-447px 0px":
 				e.css("background-position", "-596px 0px");
 				break;
 			case "-596px 0px":
 				e.css("background-position", "-745px 0px");
 				break;
 			case "-745px 0px":
 				e.css("background-position", "-298px 0px");
 			}
 		}
 		if (!i) return;
 		n = setInterval(t, 50),
 		$("html,body").animate({scrollTop: 0},"slow");
 	});
	function rocketPosition(t){
		t == 0 ? e.css("background-position") == "0px 0px" ? e.fadeOut("slow") : i && (i = false, $(".level-2").css("opacity", 1), e.delay(100).animate({
			marginTop: "-1000px"
		},
		"normal",
		function() {
			e.css({
				"margin-top": "-125px",
				display: "none"
			}),
			i = true
		})) : e.fadeIn("slow");
	}
	function changingStyles(t) {
	        var showPosition = 100;
	        if (t < showPosition) {
	            $(b).addClass('nav-transparent');
	        } else {
	            $(b).removeClass('nav-transparent');
	        }
	    };
 });