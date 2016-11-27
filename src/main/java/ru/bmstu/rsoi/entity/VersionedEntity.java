package ru.bmstu.rsoi.entity;

import com.google.gson.Gson;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import java.io.Serializable;

/**
 * Created by ali on 24.11.16.
 */
@MappedSuperclass
public abstract class VersionedEntity implements Serializable {

    @Id
    @GeneratedValue
    protected int id;

    @Version
    protected int version;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
