package com.funcas.pboot.common.enumeration.entity;

/**
 * 头像尺寸枚举
 *
 */
public enum PortraitSize {

    /**
     * 80 * 80 尺寸
     */
    BIG(80, 80, "big.jpeg"),
    /**
     * 60 * 60 尺寸
     */
    MIDDLE(60, 60, "middle.jpeg"),
    /**
     * 40 * 40 尺寸
     */
    SMALL(40, 40, "small.jpeg");

    /**
     * 构造方法
     *
     * @param width  宽度
     * @param height 高度
     * @param name   名称
     */
    PortraitSize(int width, int height, String name) {
        this.width = width;
        this.height = height;
        this.name = name;
    }

    // 宽度
    private int width;
    // 高度
    private int height;
    // 名称
    private String name;

    /**
     * 获取宽度
     *
     * @return 宽度
     */
    public int getWidth() {
        return width;
    }

    /**
     * 获取高度
     *
     * @return 高度
     */
    public int getHeight() {
        return height;
    }

    /**
     * 获取名称
     *
     * @return 名称
     */
    public String getName() {
        return name;
    }

    /**
     * 通过名称获取头像尺寸
     *
     * @param name 　头像名称
     * @return 头像尺寸枚举
     */
    public static PortraitSize getPortraitSize(String name) {
        for (PortraitSize size : PortraitSize.values()) {
            if (size.getName().equals(name)) {
                return size;
            }
        }

        return null;
    }
}
