package bean;
 
import java.io.Serializable;
 
public class Test implements Serializable {
 
    private String student_no;   // 学籍番号
    private String subject_cd;   // 科目コード
    private String school_cd;    // 学校コード
    private int no;             // 回数
    private int point;          // 点数
    private String class_num;    // クラス番号
    private String name;
    private int entYear;
    
    
    public Test() {}
 
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
 
    public String getClassNum() {
        return class_num;
    }
 
    public void setClassNum(String class_num) {
        this.class_num = class_num;
    }

	public String getName() {
	    return name;
	}
	
	public void setName(String name) {
	    this.name = name;
	}
	
	public int getEntYear() {
	    return entYear;
	}
	
	public void setEntYear(int entYear) {
	    this.entYear = entYear;
	}


}