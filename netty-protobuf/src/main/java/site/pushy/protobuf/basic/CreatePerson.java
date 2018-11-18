package site.pushy.protobuf.basic;

import site.pushy.protobuf.PersonEntity;

/**
 * @author Pushy
 * @since 2018/11/18 19:16
 */
public class CreatePerson {

    public static PersonEntity.Person create() {
        PersonEntity.Person person = PersonEntity.Person.newBuilder()
                .setId(1)
                .setName("Pushy")
                .setEmail("1437876073@qq.com")
                .build();
        //System.out.println(person);
        return person;
    }

}
