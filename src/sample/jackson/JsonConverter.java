package sample.jackson;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Stream;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;
import javax.websocket.OnMessage;
import javax.websocket.Session;
import javax.websocket.server.PathParam;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonConverter implements Encoder.Text<Object>, Decoder.Text<Object> {

	public static final String ENDPOINT_CLASS = "json.endpoint.class";

	private ObjectMapper mapper = new ObjectMapper();
	private Map<String, Object> userProperties;
	private Class<?> messageClass;

	@Override
	public void init(EndpointConfig config) {
		this.userProperties = config.getUserProperties();
	}

	@Override
	public Object decode(String s) throws DecodeException {
		try {
			Class<?> messageClass = this.getMessageClass();
			return this.mapper.readValue(s, messageClass);
		} catch (IOException e) {
			throw new DecodeException(s, "failed to decode message.", e);
		}
	}

	private Class<?> getMessageClass() {
		if (this.messageClass == null) {
			this.initMessageClass();
		}

		return this.messageClass;
	}

	private void initMessageClass() {
		Class<?> endpointClass = (Class<?>)userProperties.get(ENDPOINT_CLASS);

		Method onMessageMethod = this.findOnMessageMethod(endpointClass);
		Parameter messageParameter = this.findMessageParameter(onMessageMethod);

		this.messageClass = messageParameter.getType();
	}

	private Method findOnMessageMethod(Class<?> endpointClass) {
		return this.findFormArray(endpointClass.getMethods(), this::isOnMessageMethod, "method annotated by @OnMessage is not found");
	}

	private boolean isOnMessageMethod(Method method) {
		return method.isAnnotationPresent(OnMessage.class);
	}

	private Parameter findMessageParameter(Method onMessageMethod) {
		return this.findFormArray(onMessageMethod.getParameters(), this::isMessageParameter, "message parameter is not found");
	}

	private boolean isMessageParameter(Parameter parameter) {
		return this.isNotSession(parameter) && this.isNotPathParam(parameter);
	}

	private boolean isNotSession(Parameter parameter) {
		return !Session.class.isAssignableFrom(parameter.getType());
	}

	private boolean isNotPathParam(Parameter parameter) {
		return !parameter.isAnnotationPresent(PathParam.class);
	}

	private <T> T findFormArray(T[] array, Predicate<T> condition, String exceptionMessage) {
		return Stream.of(array)
				.filter(condition)
				.findFirst()
				.orElseThrow(() -> new RuntimeException(exceptionMessage));
	}

	@Override
	public String encode(Object object) throws EncodeException {
		try {
			return this.mapper.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			throw new EncodeException(object, "failed to encode message.", e);
		}
	}

	@Override
	public boolean willDecode(String s) {
		return true;
	}

	@Override public void destroy() {/*no use*/}

}
