package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Subject;

public class SubjectDao extends Dao {

    // ============================================================
    // ① 1件取得（論理削除済みは除外）
    // ============================================================
    public Subject get(String cd, School school) throws Exception {

        Subject subject = null;

        Connection con = getConnection();
        PreparedStatement ps = null;

        try {
            String sql = """
                SELECT *
                FROM subject
                WHERE school_cd = ? AND cd = ? AND is_true = true
            """;

            ps = con.prepareStatement(sql);
            ps.setString(1, school.getCd());
            ps.setString(2, cd);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                subject = new Subject();
                subject.setCd(rs.getString("cd"));
                subject.setName(rs.getString("name"));
                subject.setSchool(school);
            }

            rs.close();

        } finally {
            if (ps != null) ps.close();
            if (con != null) con.close();
        }

        return subject;
    }

    // ============================================================
    // ② 一覧取得（is_true を指定可能）
    // ============================================================
    public List<Subject> filter(School school, boolean filter) throws Exception {

        List<Subject> list = new ArrayList<>();

        Connection con = getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            String sql = """
                SELECT *
                FROM subject
                WHERE school_cd = ? AND is_true = ?
                ORDER BY cd
            """;

            ps = con.prepareStatement(sql);
            ps.setString(1, school.getCd());
            ps.setBoolean(2, filter);

            rs = ps.executeQuery();

            while (rs.next()) {
                Subject subject = new Subject();
                subject.setCd(rs.getString("cd"));
                subject.setName(rs.getString("name"));
                subject.setSchool(school);
                list.add(subject);
            }

        } finally {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (con != null) con.close();
        }

        return list;
    }

    // ============================================================
    // ③ 保存（INSERT or UPDATE）
    // ============================================================
    public boolean save(Subject subject) throws Exception {

        Connection con = getConnection();
        PreparedStatement ps = null;
        int count = 0;

        try {
            // PostgreSQL の ON CONFLICT を使用
            String sql = """
                INSERT INTO subject (school_cd, cd, name, is_true)
                VALUES (?, ?, ?, true)
                ON CONFLICT (school_cd, cd)
                DO UPDATE SET name = EXCLUDED.name, is_true = EXCLUDED.is_true
            """;

            ps = con.prepareStatement(sql);
            ps.setString(1, subject.getSchool().getCd());
            ps.setString(2, subject.getCd());
            ps.setString(3, subject.getName());

            count = ps.executeUpdate();

        } finally {
            if (ps != null) ps.close();
            if (con != null) con.close();
        }

        return count > 0;
    }

    // ============================================================
    // ④ 論理削除（is_true = false）
    // ============================================================
    public boolean delete(Subject subject) throws Exception {

        Connection con = getConnection();
        PreparedStatement ps = null;
        int count = 0;

        try {
            String sql = """
                UPDATE subject
                SET is_true = false
                WHERE school_cd = ? AND cd = ?
            """;

            ps = con.prepareStatement(sql);
            ps.setString(1, subject.getSchool().getCd());
            ps.setString(2, subject.getCd());

            count = ps.executeUpdate();

        } finally {
            if (ps != null) ps.close();
            if (con != null) con.close();
        }

        return count > 0;
    }

    // ============================================================
    // ⑤ 復元（is_true = true）
    // ============================================================
    public boolean change(String cd, School school) throws Exception {

        Connection con = getConnection();
        PreparedStatement ps = null;

        try {
            String sql = """
                UPDATE subject
                SET is_true = true
                WHERE school_cd = ? AND cd = ?
            """;

            ps = con.prepareStatement(sql);
            ps.setString(1, school.getCd());
            ps.setString(2, cd);

            ps.executeUpdate();

        } finally {
            if (ps != null) ps.close();
            if (con != null) con.close();
        }

        return true;
    }
}