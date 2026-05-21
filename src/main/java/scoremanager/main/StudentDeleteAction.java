package scoremanager.main;

import dao.StudentDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class StudentDeleteAction extends Action {

    public void execute(HttpServletRequest req, HttpServletResponse res)
            throws Exception {

        String no = req.getParameter("no");
        String schoolCd = req.getParameter("school_cd");

        StudentDao dao = new StudentDao();
        dao.delete(no, schoolCd);

        res.sendRedirect("StudentList.action");
    }
}

