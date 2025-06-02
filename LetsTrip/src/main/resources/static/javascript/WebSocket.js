 const socket = new WebSocket ("wss://example/chat");
 
 // open, message , error, close
 
 
 socket.onopen = function(e) {
	alert("[open] 커넥션이 만들어졌습니다.");
	socket.send("안녕");
 };
 
 socket.onmessage = function(event) {
	alert(`[message] 서버로부터 전송받은 데이터 : ${event.data}`);
 };
 
 socket.onclose = function(event) {
	if(event.wasClean) {
		alert (`[close] 커넥션이 정상적으로 종료되었습니다.(code =${event.code} reason=${event.reason})`);
	} else {
		alert ('[close] 커넥션이 죽었습니다.')
	}
 };
 
 socket.onerror = function(error) {
	alert(`[error`);
 };