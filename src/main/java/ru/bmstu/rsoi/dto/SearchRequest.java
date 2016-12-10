package ru.bmstu.rsoi.dto;

import lombok.Data;
import ru.bmstu.rsoi.entity.VersionedEntity;

import java.io.Serializable;

/**
 * Created by ali on 27.11.16.
 */
public @Data abstract
class SearchRequest implements Serializable {
    public static final int PAGE_SIZE = 10;

    protected int pageNum;

    public SearchRequest() { }

    @Override
    public String toString() {
        return VersionedEntity.toJson(this);
    }
}
