package kjug;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public class KJUGObjectMapper implements ObjectMapper {
    @Override
    public String serialize(Object object) {
        if(null == object) {
            throw new NullPointerException();
        }
        Class<?> clazz = object.getClass();
        Field[] fields = clazz.getDeclaredFields();
        return Arrays.stream(fields)
                .map(field -> {
                    try {
                        field.setAccessible(true);
                        return field.getName() + ":" + field.get(object) + ";";
                    } catch (IllegalAccessException e) {
                        return "";
                    }
                })
                .reduce((s1, s2) -> s1 + s2 )
                .orElse("");
    }

    @Override
    public <T> T deserialize(String object, Class<T> clazz) {
        if(null == object) {
            throw new NullPointerException();
        }

        try {
            Constructor<T> constructor = clazz.getConstructor();
            if(!constructor.canAccess(null))
                throw new ConstructorNotFoundException();
            //constructor.setAccessible(true);
            T containerObj = constructor.newInstance();
            deserialize(object, containerObj, clazz);
            return containerObj;
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            throw new ConstructorNotFoundException(e);
        }

    }

    private <T> void deserialize(String object, final T containerObj, Class<T> clazz) {
        String[] fileNameAndValArr = object.split(";");
        Arrays.stream(fileNameAndValArr)
                .filter(fileNameAndVal -> null != fileNameAndVal && !"".equals(fileNameAndVal))
                .map(fileNameAndVal -> {
                    String[] fieldAndVal = fileNameAndVal.split(":");
                    return new FieldAndValue(fieldAndVal[0], fieldAndVal[1]);
                })
                .forEach(fieldAndValue -> {
                    try {
                        Field field = clazz.getDeclaredField(fieldAndValue.getName());
                        field.setAccessible(true);
                        setFieldValue(field, fieldAndValue.getValue(), containerObj, field.getType());
                    } catch (NoSuchFieldException | IllegalAccessException e) {
                    }
                });
    }

    private void setFieldValue(Field field, String value, Object containerObj, Class<?> type) throws IllegalAccessException {
        if(int.class.equals(type) || Integer.class.equals(type)) {
            field.set(containerObj, Integer.valueOf(value));
        } else if(long.class.equals(type) || Long.class.equals(type)) {
            field.set(containerObj, Long.valueOf(value));
        } else if(float.class.equals(type) || Float.class.equals(type)) {
            field.set(containerObj, Float.valueOf(value));
        } else if(double.class.equals(type) || Double.class.equals(type)) {
            field.set(containerObj, Double.valueOf(value));
        } else if(String.class.equals(type)) {
            field.set(containerObj, value);
        }
    }
}
