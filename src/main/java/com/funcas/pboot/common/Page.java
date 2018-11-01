package com.funcas.pboot.common;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * 分页对象
 *
 */
@SuppressWarnings("UnusedDeclaration")
public class Page<T> implements Serializable {

    // 分页请求
    private PageRequest pageRequest;
    // 数据元素
    private List<T> elements;
    // 总记录数
    private long total;

    /**
     * 分页对象
     *
     * @param pageRequest 分页请求
     * @param elements    数据元素
     * @param total       总记录数
     */
    public Page(PageRequest pageRequest, List<T> elements, long total) {
        this.pageRequest = pageRequest;
        this.elements = elements;
        this.total = total;
    }

    /**
     * 获取当前页号
     *
     * @return 页号
     */
    public int getNumber() {
        return pageRequest == null ? 0 : pageRequest.getPageNumber();
    }

    /**
     * 获取每页的内容大小
     *
     * @return 内容数量
     */
    public int getSize() {
        return pageRequest == null ? 0 : pageRequest.getPageSize();
    }

    /**
     * 获取数据元素数量
     *
     * @return 数据元素数量
     */
    public int getNumberOfElements() {
        return elements.size();
    }

    /**
     * 判断是否存在上一页
     *
     * @return true 表示存在，否则 false
     */
    public boolean hasPrevious() {
        return getNumber() > 0;
    }

    /**
     * 判断是否存在下一页
     *
     * @return true 表示存在，否则 false
     */
    public boolean hasNext() {
        return getNumber() + 1 < getTotalPages();
    }

    /**
     * 判断是否为首页
     *
     * @return ture 表示首页，否则 false
     */
    public boolean isFirst() {
        return !hasPrevious();
    }

    /**
     * 判断是否为尾页
     *
     * @return true 表示尾页，否则 false
     */
    public boolean isLast() {
        return !hasNext();
    }

    /**
     * 判断是否存在数据元素
     *
     * @return true 表示存在，否则 false
     */
    public boolean hasContent() {
        return !elements.isEmpty();
    }

    /**
     * 获取数据元素
     *
     * @return 当前也的分页数据集合
     */
    public List<T> getContent() {
        return Collections.unmodifiableList(elements);
    }

    /**
     * 获取总页数
     *
     * @return 总页数
     */
    public int getTotalPages() {
        return getSize() == 0 ? 1 : (int) Math.ceil((double) total / (double) getSize());
    }

    /**
     * 获取总数据记录
     *
     * @return 总数据记录
     */
    public long getTotalElements() {
        return total;
    }
}
