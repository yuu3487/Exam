package dao;

import java.sql.ResultSet;
import java.util.List;

import bean.School;
import bean.Student;

public class StudentDao extends Dao{
	private String basesql;
	public Student get(String no) throws Exception{
		
	}
	private List<Student> postFilter(ResultSet rSet, School school)throws Exception{
		
	}
	public List<Student> filter(School school, int entYear, String classNum, boolean inAttend) throws Exception{
		
	}
	public List<Student> filter(School school, int entYear, boolean inAttend) throws Exception{
		
	}
	public List<Student> filter(School school, boolean inAttend) throws Exception{
		
	}
	public boolean save(Student student) throws Exception{
		
	}
}
