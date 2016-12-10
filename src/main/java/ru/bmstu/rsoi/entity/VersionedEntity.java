package ru.bmstu.rsoi.entity;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import lombok.Data;

import javax.persistence.*;
import java.io.IOException;
import java.io.Serializable;
import java.io.StringWriter;
import java.util.List;

/**
 * Created by ali on 24.11.16.
 */
@MappedSuperclass
public @Data class VersionedEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    @Version
    protected Integer version;

    public VersionedEntity() { }

    @Override
    public String toString() {
        try {
            StringWriter sw = new StringWriter();
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new Hibernate5Module());
            objectMapper.writeValue(sw, this);
            return sw.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static<T> String toJson(T object) {
        try {
            StringWriter sw = new StringWriter();
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new Hibernate5Module());
            objectMapper.writeValue(sw, object);
            return sw.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static<T> T fromJson(String str, Class<T> tClass) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(str, tClass);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int[] receiveIds(List<? extends VersionedEntity> list) {
        return list.stream().mapToInt(VersionedEntity::getId).toArray();
    }
}
