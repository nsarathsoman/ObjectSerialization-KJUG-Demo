package example;

import kjug.KJUGObjectMapper;
import kjug.ObjectMapper;

public class TestKJUGMembers {

    public static void main(String[] args) {
        KJUGMembers kjugMembers = new KJUGMembers();
        kjugMembers.setId(1);
        kjugMembers.setName("Sarath");
        kjugMembers.setCompany("Hifx");
        kjugMembers.setPhone(9995189217l);

        ObjectMapper objectMapper = new KJUGObjectMapper();
        String serializedForm = objectMapper.serialize(kjugMembers);

        System.out.println(serializedForm);

        String serializedForm2 = "id:1;name:Sarath;company:Hifx;phone:9995189217;";

        KJUGMembers kjugMembers1 = objectMapper.deserialize(serializedForm2, KJUGMembers.class);
        System.out.println(kjugMembers1);
    }
}
