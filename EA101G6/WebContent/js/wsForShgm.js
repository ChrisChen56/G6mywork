var mbrno = document.getElementById("mbrno").value;
var MyPoint = "/mainPage/" + mbrno;
var host = window.location.host;
var path = window.location.pathname;
var webCtx = path.substring(0, path.indexOf('/', 1));
var endPointURL = "ws://" + host + webCtx + MyPoint;// 使用者位址

var webSocket;

//在listAllShgm.jsp
var shgmno = $("#wsShgmno").val();

var jsondata;

if (mbrno !== '') {
	console.log(endPointURL);

	webSocket = new WebSocket(endPointURL);// 建立連線到伺服器端→

	webSocket.onopen = function(event) {// 成功連線，伺服器端回應←
		console.log('success');
		//後台向伺服器送出資料
		if(shgmno !== '' && shgmno !== undefined){
			webSocket.send(shgmno);
		}
	};

	webSocket.onmessage = function(event) {// 接收伺服器端回應的json字串←
		alert(event.data);
	};

	webSocket.onclose = function(event) {// 成功關閉，伺服器端回應←
		console.log('closed');
	};

}