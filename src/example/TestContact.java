package example;

import kjug.KJUGObjectMapper;
import kjug.ObjectMapper;

public class TestContact {

    public static void main(String[] args) {
        ObjectMapper objectMapper = new KJUGObjectMapper();
        Contact contact = new Contact();
        contact.setName("Sarath");
        contact.setPhone(9995189217l);

        System.out.println(objectMapper.serialize(contact));

        Contact deserialized = objectMapper.deserialize("name:Sarath;phone:9995189217;", Contact.class);
        System.out.println(deserialized);;
    }
}
