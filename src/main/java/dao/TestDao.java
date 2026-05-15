package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.Test;

public class TestDao extends Dao {

    // --------------------------------
    // 検索（一覧表示）
    // --------------------------------
    public List<Test> find(String schoolCd, String classNum,
                           String subjectCd, int no) throws Exception {

        List<Test> list = new ArrayList<>();

        Connection con = getConnection();
        PreparedStatement ps = null;

        try {
            String sql = """
                SELECT
                    s.student_no,
                    s.name,
                    s.class_num,
                    s.ent_year,
                    t.subject_cd,
                    t.school_cd,
                    t.no,
                    t.point
                FROM student s
                LEFT JOIN test t
                    ON s.student_no = t.student_no
                    AND t.subject_cd = ?
                    AND t.school_cd = ?
                    AND t.no = ?
                WHERE s.class_num = ?
                  AND s.school_cd = ?
                ORDER BY s.student_no
            """;

            ps = con.prepareStatement(sql);
            ps.setString(1, subjectCd);
            ps.setString(2, schoolCd);
            ps.setInt(3, no);
            ps.setString(4, classNum);
            ps.setString(5, schoolCd);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Test t = new Test();

                t.setStudentNo(rs.getString("student_no"));
                t.setClassNum(rs.getString("class_num"));
                t.setSubjectCd(rs.getString("subject_cd"));
                t.setSchoolCd(rs.getString("school_cd"));
                t.setNo(rs.getInt("no"));
                t.setPoint(rs.getInt("point"));

                // 👇 追加項目（Testにフィールド追加して使う）
                t.setName(rs.getString("name"));
                t.setEntYear(rs.getInt("ent_year"));

                list.add(t);
            }

            rs.close();

        } finally {
            if (ps != null) ps.close();
            if (con != null) con.close();
        }

        return list;
    }

    // --------------------------------
    // 登録（INSERT or UPDATE）
    // --------------------------------
    public void save(Test test) throws Exception {

        Connection con = getConnection();

        try {
            // データ存在チェック
            String checkSql = """
                SELECT COUNT(*)
                FROM test
                WHERE student_no=? AND subject_cd=? AND school_cd=? AND no=?
            """;

            PreparedStatement ps1 = con.prepareStatement(checkSql);
            ps1.setString(1, test.getStudentNo());
            ps1.setString(2, test.getSubjectCd());
            ps1.setString(3, test.getSchoolCd());
            ps1.setInt(4, test.getNo());

            ResultSet rs = ps1.executeQuery();
            rs.next();
            int count = rs.getInt(1);

            rs.close();
            ps1.close();

            if (count > 0) {
                // UPDATE
                String updateSql = """
                    UPDATE test
                    SET point=?
                    WHERE student_no=? AND subject_cd=? AND school_cd=? AND no=?
                """;

                PreparedStatement ps2 = con.prepareStatement(updateSql);
                ps2.setInt(1, test.getPoint());
                ps2.setString(2, test.getStudentNo());
                ps2.setString(3, test.getSubjectCd());
                ps2.setString(4, test.getSchoolCd());
                ps2.setInt(5, test.getNo());

                ps2.executeUpdate();
                ps2.close();

            } else {
                // INSERT
                String insertSql = """
                    INSERT INTO test(student_no, subject_cd, school_cd, no, point)
                    VALUES (?, ?, ?, ?, ?)
                """;

                PreparedStatement ps2 = con.prepareStatement(insertSql);
                ps2.setString(1, test.getStudentNo());
                ps2.setString(2, test.getSubjectCd());
                ps2.setString(3, test.getSchoolCd());
                ps2.setInt(4, test.getNo());
                ps2.setInt(5, test.getPoint());

                ps2.executeUpdate();
                ps2.close();
            }

        } finally {
            if (con != null) con.close();
        }
    }
}
