package scoremanager.main;

import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Subject;
import bean.Teacher;
import bean.Test;
import dao.ClassNumDao;
import dao.SubjectDao;
import dao.TestDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class TestRegistAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res)
            throws Exception {

        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        // --------------------------
        // パラメータ
        // --------------------------
        String f1 = req.getParameter("f1"); // 入学年度
        String f2 = req.getParameter("f2"); // クラス
        String f3 = req.getParameter("f3"); // 科目
        String f4 = req.getParameter("f4"); // 回数

        int no = 0;
        if (f4 != null && !f4.equals("")) {
            no = Integer.parseInt(f4);
        }

        // --------------------------
        // 学校
        // --------------------------
        School school = teacher.getSchool();

        // --------------------------
        // プルダウン
        // --------------------------

        // 入学年度
        List<Integer> entYearSet = new ArrayList<>();
        for (int i = 2020; i <= 2025; i++) {
            entYearSet.add(i);
        }
        

        // クラス ← ★追加
        ClassNumDao classNumDao = new ClassNumDao();
        List<String> classNumSet = classNumDao.filter(school);


        // 科目
        SubjectDao subjectDao = new SubjectDao();
        List<Subject> subjectList = subjectDao.filter(school, true);

        // 回数
        List<Integer> noSet = new ArrayList<>();
        noSet.add(1);
        noSet.add(2);
        noSet.add(3);

        // --------------------------
        // 登録処理（POST）
        // --------------------------
        if ("POST".equalsIgnoreCase(req.getMethod())) {

            TestDao dao = new TestDao();

            var params = req.getParameterMap();

            for (String key : params.keySet()) {

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
        }

        // --------------------------
        // 検索処理
        // --------------------------
        List<Test> tests = new ArrayList<>();

        if (f2 != null && !f2.equals("0") &&
            f3 != null && !f3.equals("0") &&
            no != 0) {

            TestDao dao = new TestDao();

            tests = dao.find(
                    school.getCd(),
                    f2,
                    f3,
                    no
            );
        }

        // --------------------------
        // リクエストにセット
        // --------------------------
        req.setAttribute("f1", f1);
        req.setAttribute("f2", f2);
        req.setAttribute("f3", f3);
        req.setAttribute("f4", f4);

        req.setAttribute("tests", tests);
        req.setAttribute("ent_year_set", entYearSet);
        req.setAttribute("class_num_set", classNumSet);
        req.setAttribute("subjectList", subjectList);
        req.setAttribute("no_set", noSet);

        // --------------------------
        // JSPへ
        // --------------------------
        req.getRequestDispatcher("test_regist.jsp").forward(req, res);
    }
}