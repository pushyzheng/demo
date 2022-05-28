package org.example.demo.protobuf;

import java.io.FileOutputStream;

/**
 * 将 Person 写入到文件中
 */
public class ProtobufFileStream {
    public static void main(String[] args) throws Exception {
        Person person = Person.newBuilder()
                .setId(1)
                .setName("Mark Miller")
                .setEmail("aaronlong@gmail.com")
                .build();

        FileOutputStream fos = new FileOutputStream("person.dt");
        person.writeTo(fos);
    }
}
