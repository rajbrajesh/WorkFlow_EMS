package com.workflow.workflow.dto;

/**
 * Generic DTO used for returning paginated API responses.
 *
 * @param <T> Type of data contained inside the page.
 *
 * This DTO keeps pagination information separate from
 * Spring Data's Page object.
 */
public class PageResponseDto<T> {

    /*
     * Actual records for the current page.
     */
    private T content;

    /*
     * Current page number.
     * Page numbering starts from 0.
     */
    private int page;

    /*
     * Number of records requested per page.
     */
    private int size;

    /*
     * Total number of records available.
     */
    private long totalElements;

    /*
     * Total number of pages available.
     */
    private int totalPages;

    /*
     * Indicates whether this is the last page.
     */
    private boolean last;

    public PageResponseDto() {
    }

    public PageResponseDto(
            T content,
            int page,
            int size,
            long totalElements,
            int totalPages,
            boolean last) {

        this.content = content;
        this.page = page;
        this.size = size;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.last = last;
    }

    public T getContent() {
        return content;
    }

    public int getPage() {
        return page;
    }

    public int getSize() {
        return size;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public boolean isLast() {
        return last;
    }
}