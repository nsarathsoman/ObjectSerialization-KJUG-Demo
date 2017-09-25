package kjug;

public interface ObjectMapper {
    String serialize(Object object);
    <T> T deserialize(String object, Class<T> clazz);
}