package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import bean.School;
import bean.Subject;
import bean.TestListSubject;

public class TestListSubjectDao extends Dao {

    private static final String SQL = 
            "SELECT st.ent_year, st.class_num, st.no AS student_no, st.name, "
          + "       t.no AS test_no, t.point "
          + "FROM student st "
          + "INNER JOIN test t "
          + "        ON st.no = t.student_no "
          + "       AND st.school_cd = t.school_cd "
          + "       AND t.subject_cd = ? "
          + "WHERE st.ent_year = ? "
          + "  AND st.class_num = ? "
          + "  AND st.school_cd = ? "
          + "ORDER BY st.no, t.no";

    public List<TestListSubject> filter(int entYear, String classNum,
            Subject subject, School school) throws Exception {

        List<TestListSubject> list = new ArrayList<>();

        Connection con = getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = con.prepareStatement(SQL);
            st.setString(1, subject.getCd());
            st.setInt(2, entYear);
            st.setString(3, classNum);
            st.setString(4, school.getCd());

            rs = st.executeQuery();

            list = postfilter(rs);

        } finally {
            if (rs != null) try { rs.close(); } catch (SQLException e) {}
            if (st != null) try { st.close(); } catch (SQLException e) {}
            if (con != null) try { con.close(); } catch (SQLException e) {}
        }

        return list;
    }

    private List<TestListSubject> postfilter(ResultSet rs) throws Exception {

        List<TestListSubject> list = new ArrayList<>();
        TestListSubject current = null;
        String lastStudentNo = null;

        while (rs.next()) {

            String studentNo = rs.getString("student_no");

            // ▼ 学生が変わったら新しいオブジェクトを作る
            if (!studentNo.equals(lastStudentNo)) {

                if (current != null) {
                    list.add(current);
                }

                current = new TestListSubject();
                current.setEntYear(rs.getInt("ent_year"));
                current.setClassNum(rs.getString("class_num"));
                current.setStudentNo(studentNo);
                current.setStudentName(rs.getString("name"));
                current.setPoints(new HashMap<>());

                lastStudentNo = studentNo;
            }

            // ▼ 点数がある場合だけセット
            int testNo = rs.getInt("test_no");
            int point = rs.getInt("point");

            if (testNo > 0) {
                current.putPoint(testNo, point);
            }
        }

        // 最後の学生を追加
        if (current != null) {
            list.add(current);
        }

        return list;
    }
}