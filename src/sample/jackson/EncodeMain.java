package sample.jackson;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class EncodeMain {

	public static void main(String[] args) {
		Info info = new Info();
		info.name = "Taro Tanaka";
		info.age = 30;
		info.nums = new int[5];

		ObjectMapper mapper = new ObjectMapper();
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		try {
			String script = mapper.writeValueAsString(info);
			System.out.println(script);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
