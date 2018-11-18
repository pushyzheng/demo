package site.pushy.protobuf.basic;

import com.google.protobuf.InvalidProtocolBufferException;
import site.pushy.protobuf.PersonEntity;

import java.io.*;

/**
 * @author Pushy
 * @since 2018/11/18 19:20
 */
public class ProtobufSerialization {

    private static void byteArraySerialization() throws InvalidProtocolBufferException {
        PersonEntity.Person person = CreatePerson.create();
        byte[] data = person.toByteArray();

        PersonEntity.Person parsePerson = PersonEntity.Person.parseFrom(data);
        System.out.println(parsePerson);
    }

    private static void streamSerialization() throws IOException {
        PersonEntity.Person person = CreatePerson.create();
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        person.writeTo(os);

        ByteArrayInputStream is = new ByteArrayInputStream(os.toByteArray());
        PersonEntity.Person parsePerson = PersonEntity.Person.parseFrom(is);
        System.out.println(parsePerson);
    }

    private static void fileSerialization() throws IOException {
        PersonEntity.Person person = CreatePerson.create();
        FileOutputStream fos = new FileOutputStream("pushy.dt");
        person.writeTo(fos);
        fos.close();

        FileInputStream fis = new FileInputStream("pushy.dt");
        PersonEntity.Person parsePerson = PersonEntity.Person.parseFrom(fis);
        System.out.println(parsePerson);
        fis.close();
    }

    public static void main(String[] args) throws IOException {
        byteArraySerialization();

        streamSerialization();

        fileSerialization();
    }

}
