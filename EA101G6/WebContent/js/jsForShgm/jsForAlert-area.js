$(document).ready(function() {
	var timeFunc;
	$(".alert-area").mouseleave(function() {
		timeFunc = setTimeout(function() {
			if ($("#showMsg").text() == "隱藏訊息")
				// 顯示轉隱藏
				$(".toast").toggle("fast");
			$(".main-area").css("z-index", "10");
			$(".alert-area-msgs").css("z-index", "-1");
			$("#showMsg").text("顯示訊息");
		}, 2000);
	});

	$(".alert-area").mouseenter(function() {
		clearTimeout(timeFunc);
	});

	$("#showMsg").click(function() {
		var showMsgText = $("#showMsg").text();
		$(".toast").toggle("fast", function() {
			if (showMsgText == "隱藏訊息") {
				// 顯示轉隱藏
				$(".main-area").css("z-index", "10");
				$(".alert-area-msgs").css("z-index", "-1");
				$("#showMsg").text("顯示訊息");
			} else {
				// 隱藏轉顯示
				$(".main-area").css("z-index", "-1");
				$(".alert-area-msgs").css("z-index", "10");
				$("#showMsg").text("隱藏訊息");
			}
		});
	});

	$('.toast').toast('show');
});