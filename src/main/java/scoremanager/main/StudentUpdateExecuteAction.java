package scoremanager.main;

import bean.Student;
import dao.StudentDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class StudentUpdateExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res)
            throws Exception {

        String no = req.getParameter("no");
        String name = req.getParameter("name");
        String classNum = req.getParameter("class_num");
        String isAttendParam = req.getParameter("is_attend");

        boolean isAttend = (isAttendParam != null);

        StudentDao dao = new StudentDao();
        Student student = dao.get(no);

        if (student == null) {
            res.sendRedirect("StudentList.action");
            return;
        }

        student.setName(name);
        student.setClassNum(classNum);
        student.setAttend(isAttend);

        boolean result = dao.save(student);

        if (!result) {
            System.out.println("更新失敗");
        }

        res.sendRedirect("student_update_done.jsp");
    }
}