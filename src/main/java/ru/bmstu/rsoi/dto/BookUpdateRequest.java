package ru.bmstu.rsoi.dto;

import lombok.Data;
import ru.bmstu.rsoi.entity.VersionedEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ali on 26.11.16.
 */
public @Data class BookUpdateRequest extends UpdateVersionedEntityRequest{
    private String bookName;
    private int[] authors;

    public BookUpdateRequest(String bookName, int[] authors, int version) {
        this.bookName = bookName;
        this.authors = authors;
        this.version = version;
    }

    public BookUpdateRequest(String bookName, int[] authors) {
        this.bookName = bookName;
        this.authors = authors;
    }

    public BookUpdateRequest() {
    }

    @Override
    public String toString() {
        return VersionedEntity.toJson(this);
    }
}
