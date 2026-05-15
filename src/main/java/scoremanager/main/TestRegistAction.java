
package scoremanager.main;

import java.util.List;
import java.util.Map;

import bean.School;
import bean.Subject;
import bean.Test;
import dao.SubjectDao;
import dao.TestDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class TestRegistAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res)
            throws Exception {

        // パラメータ取得
        String f1 = req.getParameter("f1"); // 入学年度
        String f2 = req.getParameter("f2"); // クラス
        String f3 = req.getParameter("f3"); // 科目
        String f4 = req.getParameter("f4"); // 回数

        // JSPに戻す用
        req.setAttribute("f1", f1);
        req.setAttribute("f2", f2);
        req.setAttribute("f3", f3);
        req.setAttribute("f4", f4);

        // 学校（固定 or セッションから取得）
        School school = new School();
        school.setCd("S1");

        // --------------------------------
        // 科目一覧（プルダウン用）
        // --------------------------------
        SubjectDao subjectDao = new SubjectDao();
        List<Subject> subjectList = subjectDao.filter(school, true);
        req.setAttribute("subjectList", subjectList);

        // --------------------------------
        // 登録処理（POST）
        // --------------------------------
        if ("POST".equalsIgnoreCase(req.getMethod())) {

            TestDao dao = new TestDao();

            Map<String, String[]> params = req.getParameterMap();

            for (String key : params.keySet()) {

                // point_学籍番号 のみ処理
                if (key.startsWith("point_")) {

                    String studentNo = key.replace("point_", "");
                    String pointStr = req.getParameter(key);

                    if (pointStr == null || pointStr.equals("")) continue;

                    Test test = new Test();
                    test.setStudentNo(studentNo);
                    test.setSubjectCd(f3);
                    test.setSchoolCd(school.getCd());
                    test.setNo(Integer.parseInt(f4));
                    test.setPoint(Integer.parseInt(pointStr));

                    dao.save(test);
                }
            }
        }

        // --------------------------------
        // 検索処理（GET or POST後表示）
        // --------------------------------
        if (f2 != null && f3 != null && f4 != null &&
            !f2.equals("") && !f3.equals("") && !f4.equals("")) {

            TestDao dao = new TestDao();

            List<Test> tests = dao.find(
                    school.getCd(),
                    f2,
                    f3,
                    Integer.parseInt(f4)
            );

            req.setAttribute("tests", tests);
        }

        // 画面表示
        req.getRequestDispatcher("test_regist.jsp").forward(req, res);
	}
}
