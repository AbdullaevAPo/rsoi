package ru.bmstu.rsoi.dto;

/**
 * Created by ali on 27.11.16.
 */
public abstract class UpdateVersionedEntityRequest {
    protected int version;

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
