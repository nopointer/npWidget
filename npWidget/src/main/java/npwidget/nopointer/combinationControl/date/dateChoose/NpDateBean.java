package npwidget.nopointer.combinationControl.date.dateChoose;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import npwidget.nopointer.combinationControl.date.NpDateType;

public class NpDateBean {

    /**
     * 开始时间
     */
    private Date startDate;
    /**
     * 结束时间
     */
    private Date endDate;

    private String simpleTitle;

    private NpDateType dateType;


    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getSimpleTitle() {
        return simpleTitle;
    }

    public void setSimpleTitle(String simpleTitle) {
        this.simpleTitle = simpleTitle;
    }


    public NpDateType getDateType() {
        return dateType;
    }

    public void setDateType(NpDateType dateType) {
        this.dateType = dateType;
    }

    /**
     * 获取天
     *
     * @param index
     * @return
     */
    public static NpDateBean getDayDateBean(Date date, int index) {
        NpDateBean result = new NpDateBean();
        if (index == 0) {
            result.setStartDate(getDayStart(date));
            result.setEndDate(getDayEnd(date));
        } else {
            result.setStartDate(getDayStartByIndex(date, index));
            result.setEndDate(getDayEndByIndex(date, index));
        }
        result.setSimpleTitle(new SimpleDateFormat("yyyy-MM-dd").format(result.getStartDate()));
        result.setDateType(NpDateType.DAY);
        return result;
    }


    /**
     * 获取周
     *
     * @param date  当前日期
     * @param index 如果为0表示本周，-1为上周，1为下周
     * @return
     */
    public static NpDateBean getWeekDateBean(Date date, int index) {
        NpDateBean result = new NpDateBean();
        //拼标题
        result.setStartDate(getWeekStartDate(date, index));
        result.setEndDate(getWeekEndDate(date, index));
        String title = new SimpleDateFormat("yyyy-MM-dd").format(result.getStartDate());
        title += " ~ " + new SimpleDateFormat("MM-dd").format(result.getEndDate());
        result.setSimpleTitle(title);
        result.setDateType(NpDateType.WEEK);
        return result;
    }

    /**
     * 获取月
     *
     * @param date
     * @param index
     * @return
     */
    public static NpDateBean getMonthDateBean(Date date, int index) {
        NpDateBean result = new NpDateBean();
        result.setStartDate(getMonthStartDate(date, index));
        result.setEndDate(getMonthEndDate(date, index));
        result.setSimpleTitle(new SimpleDateFormat("yyyy-MM").format(result.getEndDate()));
        result.setDateType(NpDateType.MONTH);
        return result;
    }

    /**
     * 获取年
     *
     * @param date
     * @param index
     * @return
     */
    public static NpDateBean getYearDateBean(Date date, int index) {
        NpDateBean result = new NpDateBean();
        result.setStartDate(getYearStartDate(date, index));
        result.setEndDate(getYearEndStartDate(date, index));
        result.setSimpleTitle(new SimpleDateFormat("yyyy").format(result.getEndDate()));
        result.setDateType(NpDateType.YEAR);
        return result;
    }


    /**
     * 获取当天的开始时间
     */
    private static Date getDayStart(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取当天的结束时间
     */
    private static Date getDayEnd(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return calendar.getTime();
    }


    /**
     * 获取指定天数的开始时间
     *
     * @param date
     * @param dateIndex
     * @return
     */
    private static Date getDayStartByIndex(Date date, int dateIndex) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(getDayStart(date));
        calendar.add(Calendar.DAY_OF_MONTH, dateIndex);
        return calendar.getTime();
    }

    /**
     * 获取指定天数的开始时间
     *
     * @param date
     * @param dateIndex
     * @return
     */
    private static Date getDayEndByIndex(Date date, int dateIndex) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(getDayEnd(date));
        calendar.add(Calendar.DAY_OF_MONTH, dateIndex);
        return calendar.getTime();
    }

    /**
     * 获取本周一
     */
    private static Date getWeekStartDate(Date date, int index) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        // 获得当前日期是一个星期的第几天
        int day = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (day == 0) {
            day = 7;
        }
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.DATE, -day + 1 + index * 7);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        return calendar.getTime();
    }

    /**
     * 获取本周天
     *
     * @param date
     * @return
     */
    private static Date getWeekEndDate(Date date, int index) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        // 获得当前日期是一个星期的第几天
        int day = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (day == 0) {
            day = 7;
        }
        calendar.add(Calendar.DATE, -day + 7 + index * 7);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 23, 59, 59);
        return calendar.getTime();
    }

    /**
     * 获取指定月份开始时间
     *
     * @param date
     * @param index
     * @return
     */
    private static Date getMonthStartDate(Date date, int index) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(getNowYear(date), getNowMonth(date) + index, 1);
        return getDayStartTime(calendar.getTime());
    }

    /**
     * 获取指定月份结束时间
     *
     * @param date
     * @param index
     * @return
     */
    private static Date getMonthEndDate(Date date, int index) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(getNowYear(date), getNowMonth(date) + index, 1);
        int day = calendar.getActualMaximum(Calendar.DATE);
        calendar.set(getNowYear(date), getNowMonth(date) + index, day);
        return getDayEndTime(calendar.getTime());
    }

    /**
     * 获取本年的开始时间
     *
     * @param date
     * @param index
     * @return
     */
    public static Date getYearStartDate(Date date, int index) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, getNowYear(date) + index);
        calendar.set(Calendar.MONTH, Calendar.JANUARY);
        calendar.set(Calendar.DATE, 1);
        return getDayStartTime(calendar.getTime());
    }


    /**
     * 获取本年的结束时间
     *
     * @param date
     * @param index
     * @return
     */
    public static Date getYearEndStartDate(Date date, int index) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, getNowYear(date) + index);
        calendar.set(Calendar.MONTH, Calendar.DECEMBER);
        calendar.set(Calendar.DATE, 31);
        return getDayEndTime(calendar.getTime());
    }


    /**
     * 获取今年是哪一年
     *
     * @return
     */
    private static Integer getNowYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }

    /**
     * 获取本月是哪一月
     *
     * @param date
     * @return
     */
    private static int getNowMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH);
    }


    /**
     * 获取某个日期的开始时间
     *
     * @param date
     * @return
     */
    private static Date getDayStartTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        if (null != date) calendar.setTime(date);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取某个日期的结束时间
     *
     * @param date
     * @return
     */
    private static Date getDayEndTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        if (null != date) calendar.setTime(date);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 23, 59, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }

    /**
     * 获取指定日期中这个月的天数
     *
     * @param date
     * @return
     */
    public static int getDaysOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

}
