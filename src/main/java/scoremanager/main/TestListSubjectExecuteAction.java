package scoremanager.main;

import java.util.List;

import bean.School;
import bean.Subject;
import bean.Teacher;
import bean.TestListSubject;
import dao.ClassNumDao;
import dao.StudentDao;
import dao.SubjectDao;
import dao.TestListSubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;
public class TestListSubjectExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");
        School school = teacher.getSchool();

        // ▼ パラメータ取得
        String entYearStr = request.getParameter("ent_year");
        String classNum = request.getParameter("class_num");
        String subjectCd = request.getParameter("subject_cd");

        // ▼ 選択状態を保持（JSP の selected 判定用）
        request.setAttribute("ent_year", entYearStr);
        request.setAttribute("class_num", classNum);
        request.setAttribute("subject_cd", subjectCd);

        // ▼ セレクトボックスの値を常にセット（エラー時も成功時も）
        setSelectBoxData(request, school);

        // ▼ 未選択チェック
        if (entYearStr == null || entYearStr.isEmpty()
                || classNum == null || classNum.isEmpty()
                || subjectCd == null || subjectCd.isEmpty()) {

            request.setAttribute("message", "入学年度とクラスと科目を選択してください");
            request.getRequestDispatcher("test_list.jsp").forward(request, response);
            return;
        }

        int entYear = Integer.parseInt(entYearStr);

        // ▼ 科目取得
        SubjectDao subjectDao = new SubjectDao();
        Subject subject = subjectDao.get(subjectCd, school);

        // ▼ 成績一覧取得
        TestListSubjectDao dao = new TestListSubjectDao();
        List<TestListSubject> list = dao.filter(entYear, classNum, subject, school);

        if (list.size() == 0) {
            request.setAttribute("message", "該当する成績が見つかりませんでした。");
            request.getRequestDispatcher("test_list.jsp").forward(request, response);
            return;
        }

        // ▼ 成績一覧を JSP に渡す
        request.setAttribute("subject_test_list", list);

        request.getRequestDispatcher("test_list.jsp").forward(request, response);
    }

    // ▼ TestListAction と同じ処理（セレクトボックスの値をセット）
    private void setSelectBoxData(HttpServletRequest request, School school) throws Exception {

        ClassNumDao classDao = new ClassNumDao();
        request.setAttribute("class_num_set", classDao.filter(school));

        SubjectDao subjectDao = new SubjectDao();
        request.setAttribute("subject_set", subjectDao.filter(school, true));

        StudentDao studentDao = new StudentDao();
        request.setAttribute("ent_year_set", studentDao.getEntYearSet(school));
    }
}