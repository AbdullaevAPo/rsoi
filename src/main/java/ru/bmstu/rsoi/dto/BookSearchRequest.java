package ru.bmstu.rsoi.dto;

import lombok.Data;
import ru.bmstu.rsoi.entity.VersionedEntity;

/**
 * Created by ali on 27.11.16.
 */
@Data
public class BookSearchRequest extends SearchRequest {
    private String authorName;
    private String bookName;

    public BookSearchRequest(String authorName, String bookName) {
        this.authorName = authorName;
        this.bookName = bookName;
    }

    public BookSearchRequest() {
    }

    @Override
    public String toString() {
        return VersionedEntity.toJson(this);
    }
}
