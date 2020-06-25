package sample.jackson;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;


public class DecodeMain {

	public static void main(String[] args) {
		String script = "{ \"name\":\"Taro tanaka\", \"age\":30, \"nums\":[1,1,1,1,1]}";

		ObjectMapper mapper = new ObjectMapper();
		try {
			Info info = mapper.readValue(script, Info.class);
			System.out.println(info.name + ", " + info.age + ", [" +
							info.nums[0] + "," + info.nums[1] + "," + info.nums[2]
							+ "," + info.nums[3] + "," + info.nums[4] + "]");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
