package npwidget.nopointer.chart;

/**
 * 选中模式
 */
public enum NpSelectMode {

    /**
     * 选中第一个
     */
    SELECT_FIRST,
    /**
     * 选中最后一个
     */
    SELECT_LAST,

    /**
     * 选中最小的
     */
    SELECT_MIN,
    /**
     * 选中最大的
     */
    SELECT_MAX,

    /**
     * 选择第一个有效数据
     */
    SELECT_FIRST_NOT_NULL,

    /**
     * 选择最后一个有效数据
     */
    SELECT_LAST_NOT_NULL,

    /**
     * 不选中
     */
    NONE
}
