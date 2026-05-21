package bean;

import java.util.HashMap;
import java.util.Map;

public class TestListSubject {

    private int entYear;
    private String classNum;
    private String studentNo;
    private String studentName;
    private Map<Integer, Integer> points = new HashMap<>();

    public int getEntYear() {
        return entYear;
    }
    public void setEntYear(int entYear) {
        this.entYear = entYear;
    }

    public String getClassNum() {
        return classNum;
    }
    public void setClassNum(String classNum) {
        this.classNum = classNum;
    }

    public String getStudentNo() {
        return studentNo;
    }
    public void setStudentNo(String studentNo) {
        this.studentNo = studentNo;
    }

    public String getStudentName() {
        return studentName;
    }
    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public Map<Integer, Integer> getPoints() {
        return points;
    }
    public void setPoints(Map<Integer, Integer> points) {
        this.points = points;
    }

    // ▼ 点数をセット（1回・2回など）
    public void putPoint(int no, int point) {
        if (this.points == null) {
            this.points = new HashMap<>();
        }
        this.points.put(no, point);
    }

    // ▼ JSP で安全に使える getter（EL が確実に読める）
    public Integer getPoint1() {
        return points.get(1);
    }

    public Integer getPoint2() {
        return points.get(2);
    }
}