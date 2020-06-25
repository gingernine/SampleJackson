package sample.jackson;

import javax.websocket.EndpointConfig;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value="/json", encoders=JsonConverter.class, decoders=JsonConverter.class)
public class SampleWebSocket {

	@OnOpen
	public void open(EndpointConfig config) {
		config.getUserProperties().put(JsonConverter.ENDPOINT_CLASS, SampleWebSocket.class);
	}

	@OnMessage
	public Hoge message(Hoge message, Session session) {
		System.out.println("message = " + message);

		Hoge hoge = new Hoge();
		hoge.id = 33;
		hoge.name = "fuga";

		return hoge;
	}

	/*@OnMessage
	public void message(String message) {
		System.out.println("message = " + message);
		ObjectMapper mapper = new ObjectMapper();
		try {
			JsonNode node = mapper.readTree(message);
			System.out.println(node.get("id").asInt());
			System.out.println(node.get("name").asText());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}*/
}
