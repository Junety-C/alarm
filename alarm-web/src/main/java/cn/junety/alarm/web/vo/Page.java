package cn.junety.alarm.web.vo;

/**
 * Created by caijt on 2017/4/3.
 */
public class Page {
    private int pageNo;
    private int pageSize;

    public Page(int pageNo, int pageSize) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
    }

    /**
     * 获取任一页第一条数据的位置,startIndex从0开始
     */
    public int getStart() {
        return (pageNo - 1) * pageSize;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public String toString() {
        return "{" +
                "pageNo=" + pageNo +
                ", pageSize=" + pageSize +
                '}';
    }
}
