package scoremanager.main;

import java.util.List;

import bean.School;
import bean.Student;
import bean.Teacher;
import bean.TestListStudent;
import dao.ClassNumDao;
import dao.StudentDao;
import dao.SubjectDao;
import dao.TestListStudentDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class TestListStudentExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");
        School school = teacher.getSchool();

        // ▼ 入力された学生番号を取得
        String studentNo = request.getParameter("student_no");

        // ▼ 入力値を保持（JSP の value="${student_no}" 用）
        request.setAttribute("student_no", studentNo);

        // ▼ セレクトボックスの値を常にセット（科目検索用）
        setSelectBoxData(request, school);

        // ▼ 入力チェック
        if (studentNo == null || studentNo.isEmpty()) {
            request.setAttribute("student_message", "学生番号を入力してください。");
            request.getRequestDispatcher("test_list.jsp").forward(request, response);
            return;
        }

        // ▼ 学生取得
        StudentDao studentDao = new StudentDao();
        Student student = studentDao.get(studentNo, school);

        if (student == null) {
            request.setAttribute("student_message", "該当する学生が存在しません。");
            request.getRequestDispatcher("test_list.jsp").forward(request, response);
            return;
        }

        // ▼ 成績取得
        TestListStudentDao dao = new TestListStudentDao();
        List<TestListStudent> list = dao.filter(student);

        // ▼ 成績が 0 件のとき（写真の動作）
        if (list.size() == 0) {

            // 氏名を表示するためにセット
            request.setAttribute("student_name", student.getName());
            request.setAttribute("student_no", student.getNo());

            // エラー文（写真と同じ）
            request.setAttribute("student_message", "成績情報が存在しませんでした。");

            request.getRequestDispatcher("test_list.jsp").forward(request, response);
            return;
        }

        // ▼ 成績一覧を JSP に渡す
        request.setAttribute("student_test_list", list);
        request.setAttribute("student_name", student.getName());
        request.setAttribute("student_no", student.getNo()); // 再セット（保持用）

        request.getRequestDispatcher("test_list.jsp").forward(request, response);
    }

    // ▼ TestListAction と同じ処理（科目検索用セレクトボックスの値をセット）
    private void setSelectBoxData(HttpServletRequest request, School school) throws Exception {

        ClassNumDao classDao = new ClassNumDao();
        request.setAttribute("class_num_set", classDao.filter(school));

        SubjectDao subjectDao = new SubjectDao();
        request.setAttribute("subject_set", subjectDao.filter(school, true));

        StudentDao studentDao = new StudentDao();
        request.setAttribute("ent_year_set", studentDao.getEntYearSet(school));
    }
}