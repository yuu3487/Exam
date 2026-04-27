package scoremanager.main;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.Student;
import bean.Teacher;
import dao.StudentDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class StudentListAction extends Action{
	
	@Override
	public void execute(HttpServletRequest request,HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher)session.getAttribute("user");
		
		String entYeraStr="";
		String classNum="";
		String isAttendStr="";
		int entYear=0;
		boolean isAttend = false;
		List<Student> students = null;
		LocalDate todaysDate = LocalDate.now();
		int year = todaysDate.getYear();
		StudentDao sDao = new StudentDao();
		ClassNumDao cNumDao = new ClassNumDao();
		Map<String, String> errows = new HashMap<>();
	}

}
