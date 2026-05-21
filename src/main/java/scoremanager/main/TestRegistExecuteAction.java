package scoremanager.main;

import java.util.HashMap;
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

        School school = teacher.getSchool();

        String f3 = req.getParameter("f3");
        String f4 = req.getParameter("f4");

        int no = Integer.parseInt(f4);

        TestDao dao = new TestDao();

        Map<String, String> errors = new HashMap<>();
        Map<String, String[]> params = req.getParameterMap();

        // ✅ 入力チェック
        for (String key : params.keySet()) {

            if (key.startsWith("point_")) {

                String studentNo = key.replace("point_", "");
                String pointStr = req.getParameter(key);

                // 空欄チェック
                if (pointStr == null || pointStr.equals("")) {
                    errors.put(studentNo, "点数を入力してください");
                    continue;
                }

                int point;

                try {
                    point = Integer.parseInt(pointStr);
                } catch (Exception e) {
                    errors.put(studentNo, "数値で入力してください");
                    continue;
                }

                // 範囲チェック
                if (point < 0 || point > 100) {
                    errors.put(studentNo, "0～100の範囲で入力してください");
                }
            }
        }

        // ✅ エラーがある場合
        if (!errors.isEmpty()) {

            req.setAttribute("errors", errors);

            // 元の画面に戻す
            TestDao findDao = new TestDao();
            String f2 = req.getParameter("f2");

            req.setAttribute("tests",
                findDao.find(school.getCd(), f2, f3, no)
            );

            // 条件保持
            req.setAttribute("f1", req.getParameter("f1"));
            req.setAttribute("f2", f2);
            req.setAttribute("f3", f3);
            req.setAttribute("f4", f4);

            req.getRequestDispatcher("test_regist.jsp")
               .forward(req, res);

            return;
        }

        // ✅ 正常登録
        for (String key : params.keySet()) {

            if (key.startsWith("point_")) {

                String studentNo = key.replace("point_", "");
                int point = Integer.parseInt(req.getParameter(key));

                Test test = new Test();
                test.setStudentNo(studentNo);
                test.setSubjectCd(f3);
                test.setSchoolCd(school.getCd());
                test.setNo(no);
                test.setPoint(point);

                dao.save(test);
            }
        }

        // ✅ 完了画面
        req.getRequestDispatcher("test_regist_done.jsp")
           .forward(req, res);
    }
}
