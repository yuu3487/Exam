package bean;

import java.io.Serializable;

public class Test implements Serializable {

    // --- オブジェクト参照（学生・科目・学校） ---
    private Student student;   // 学生オブジェクト
    private Subject subject;   // 科目オブジェクト
    private School school;     // 学校オブジェクト

    // --- DB の test テーブルのカラム ---
    private String student_no;   // 学籍番号
    private String subject_cd;   // 科目コード
    private String school_cd;    // 学校コード
    private String class_num;    // クラス番号
    private int entYear;         // 入学年度
    private String name;         // 学生名

    private int no;              // 回数（1回・2回…）
    private int point;           // 点数

    public Test() {}

    // --- オブジェクト参照 ---
    public Student getStudent() {
        return student;
    }
    public void setStudent(Student student) {
        this.student = student;
    }

    public Subject getSubject() {
        return subject;
    }
    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public School getSchool() {
        return school;
    }
    public void setSchool(School school) {
        this.school = school;
    }

    // --- DB カラム ---
    public String getStudentNo() {
        return student_no;
    }
    public void setStudentNo(String student_no) {
        this.student_no = student_no;
    }

    public String getSubjectCd() {
        return subject_cd;
    }
    public void setSubjectCd(String subject_cd) {
        this.subject_cd = subject_cd;
    }

    public String getSchoolCd() {
        return school_cd;
    }
    public void setSchoolCd(String school_cd) {
        this.school_cd = school_cd;
    }

    public String getClassNum() {
        return class_num;
    }
    public void setClassNum(String class_num) {
        this.class_num = class_num;
    }

    public int getEntYear() {
        return entYear;
    }
    public void setEntYear(int entYear) {
        this.entYear = entYear;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public int getNo() {
        return no;
    }
    public void setNo(int no) {
        this.no = no;
    }

    public int getPoint() {
        return point;
    }
    public void setPoint(int point) {
        this.point = point;
    }
}