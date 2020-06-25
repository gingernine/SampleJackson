$(function() {
	var ws = new WebSocket("ws://localhost:8080/JacksonSample/json");

	ws.onopen = function() {
		$("#send").on("click", function() {
			var message = {id: 15, name: "Hoge"};
			ws.send(JSON.stringify(message));
		});

		$("#close").on("click", function() {
			ws.close();
		});
	}

	ws.onmessage = function(e) {
		console.log(e.data);
	};

});