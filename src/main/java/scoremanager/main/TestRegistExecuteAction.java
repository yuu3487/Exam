package scoremanager.main;

import java.util.Map;

import bean.School;
import bean.Teacher;
import bean.Test;
import dao.TestDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class TestRegistExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res)
            throws Exception {

        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        // 学校取得
        School school = teacher.getSchool();

        // パラメータ取得
        String f3 = req.getParameter("f3"); // 科目
        String f4 = req.getParameter("f4"); // 回数

        int no = 0;
        if (f4 != null && !f4.equals("")) {
            no = Integer.parseInt(f4);
        }

        // DAO
        TestDao dao = new TestDao();

        // フォームから全パラメータ取得
        Map<String, String[]> params = req.getParameterMap();

        // 点数登録処理
        for (String key : params.keySet()) {

            // point_学生番号 の形式だけ処理
            if (key.startsWith("point_")) {

                String studentNo = key.replace("point_", "");
                String pointStr = req.getParameter(key);

                if (pointStr == null || pointStr.equals("")) continue;

                Test test = new Test();
                test.setStudentNo(studentNo);
                test.setSubjectCd(f3);
                test.setSchoolCd(school.getCd());
                test.setNo(no);
                test.setPoint(Integer.parseInt(pointStr));

                dao.save(test);
            }
        }

        // 完了画面へ
        req.getRequestDispatcher("test_regist_done.jsp")
           .forward(req, res);
    }
}