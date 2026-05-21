package scoremanager.main;

import java.util.List;

import bean.School;
import bean.Subject;
import bean.Teacher;
import dao.ClassNumDao;
import dao.StudentDao;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class TestListAction extends Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	
    	request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");
        School school = teacher.getSchool();

        ClassNumDao classDao = new ClassNumDao();
        List<String> classNumSet = classDao.filter(school);

        SubjectDao subjectDao = new SubjectDao();
        List<Subject> subjectSet = subjectDao.filter(school, true);

        StudentDao studentDao = new StudentDao();
        List<Integer> entYearSet = studentDao.getEntYearSet(school);

        request.setAttribute("class_num_set", classNumSet);
        request.setAttribute("subject_set", subjectSet);
        request.setAttribute("ent_year_set", entYearSet);

        request.getRequestDispatcher("test_list.jsp").forward(request, response);
    }
}