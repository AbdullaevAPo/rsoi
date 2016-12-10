package ru.bmstu.rsoi.dto;

import lombok.Data;

/**
 * Created by ali on 27.11.16.
 */
public @Data  abstract class UpdateVersionedEntityRequest {
    protected int version;

    public UpdateVersionedEntityRequest() {
    }
}

