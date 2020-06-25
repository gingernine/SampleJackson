package sample.jackson;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ReadTreeMain {

	public static void main(String[] args) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			String path = "C:/pleiades/workspace/JacksonSample/src/sample/jackson/sample.json";
			JsonNode node = mapper.readTree(new File(path));

			int age = node.get("age").asInt();
			System.out.println(age);

			String city = node.get("address").get("city").asText();
			System.out.println(city);

			String number = node.get("phoneNumbers").get(0).get("number").asText();
			System.out.println(number);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
