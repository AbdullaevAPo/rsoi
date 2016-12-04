package ru.bmstu.rsoi.dto;

import ru.bmstu.rsoi.entity.VersionedEntity;

/**
 * Created by ali on 27.11.16.
 */
public class SearchRequst {
    public static final int PAGE_SIZE = 10;

    protected int pageNum;

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    @Override
    public String toString() {
        return VersionedEntity.toJson(this);
    }
}
