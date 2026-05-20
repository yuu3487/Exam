package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Subject;

public class SubjectDao extends Dao{

	public Subject get(String cd, School school) throws Exception {
	    Subject subject = new Subject();
	    subject.setSchool(school);
	    Connection connection = getConnection();
	    PreparedStatement statement = null;

	    try {
	        // ✅ is_true = true を追加して論理削除済みデータを除外
	        statement = connection.prepareStatement(
	            "select * from subject where school_cd=? and cd=? and is_true = true");
	        statement.setString(1, school.getCd());
	        statement.setString(2, cd);
	        ResultSet rSet = statement.executeQuery();

	        if (rSet.next()) {
	            subject.setCd(rSet.getString("cd"));
	            subject.setName(rSet.getString("name"));
	        } else {
	            subject = null;
	        }
	    } catch (Exception e) {
	        throw e;
	    } finally {
	        if (statement != null) {
	            try {
	                statement.close();
	            } catch (SQLException sqle) {
	                throw sqle;
	            }
	        }
	        if (connection != null) {
	            try {
	                connection.close();
	            } catch (SQLException sqle) {
	                throw sqle;
	            }
	        }
	    }
	    return subject;
	}

	public List<Subject> filter(School school,boolean filter) throws Exception{
		List<Subject> list = new ArrayList<>();
		Connection connection = getConnection();
		PreparedStatement statement = null;
		ResultSet rSet = null;

		try {
			statement = connection.prepareStatement(
			"select * from subject where school_cd=? and is_true = ?");
			statement.setString(1, school.getCd());
			statement.setBoolean(2, filter);
			rSet = statement.executeQuery();
			while(rSet.next()){
				Subject subject = new Subject();
				subject.setCd(rSet.getString("cd"));
				subject.setName(rSet.getString("name"));
				subject.setSchool(school);
				list.add(subject);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if (statement !=null) {
				try{
					statement.close();
				}catch (SQLException sqle){
					throw sqle;
				}
			}
			// コネクションを閉じる
			if (connection != null){
				try{
					connection.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
		}
		return list;
	}

	
	public boolean save(Subject subject) throws Exception{
		// コネクションを確立
		Connection connection = getConnection();
		// プリペアードステートメント
		PreparedStatement statement = null;
		// 実行件数
		int count = 0;

		try {
			// PostgreSQL用のINSERT ON CONFLICT構文（重複したらnameとis_trueを更新する）に修正
			statement = connection.prepareStatement(
					"insert into subject (school_cd, cd, name, is_true) values (?, ?, ?, ?) " +
					"on conflict (school_cd, cd) do update set name = excluded.name, is_true = excluded.is_true");
			
			statement.setString(1, subject.getSchool().getCd());
			statement.setString(2, subject.getCd());
			statement.setString(3, subject.getName());
			statement.setBoolean(4, true);
			
			// プリペアードステートメントを実行
			count = statement.executeUpdate();
		}catch (Exception e){
			e.printStackTrace();
			if (statement != null) { statement.close(); }
			if (connection != null) { connection.close(); }
			return false;
		} finally {
			// プリペアードステートメントを閉じる
			if (statement !=null) {
				try{
					statement.close();
				}catch (SQLException sqle){
					throw sqle;
				}
			}
			// コネクションを閉じる
			if (connection != null){
				try{
					connection.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
		}

		if (count > 0) {
			//実行件数が1件以上ある場合
			return true;
		} else {
			// 実行件数が0件の場合
			return false;
		}
	}

	public boolean delete(Subject subject) throws Exception{
		Connection connection = getConnection();
		PreparedStatement statement = null;
		int count = 0;
		try{
			statement = connection.prepareStatement(
				"update subject set is_true = ? where school_cd = ? and cd = ?");
			statement.setBoolean(1, false);
			statement.setString(2, subject.getSchool().getCd());
			statement.setString(3, subject.getCd());
			count = statement.executeUpdate();
		}catch (Exception e) {
			throw e;
		}finally {
			// プリペアードステートメントを閉じる
			if (statement !=null) {
				try{
					statement.close();
				}catch (SQLException sqle){
					throw sqle;
				}
			}
			// コネクションを閉じる
			if (connection != null){
				try{
					connection.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
		}
		if (count > 0) {
			//実行件数が1件以上ある場合
			return true;
		} else {
			// 実行件数が0件の場合
			return false;
		}
	}
	
	public boolean change(String cd,boolean back) throws Exception{
		Connection connection = getConnection();
		PreparedStatement statement = null;
		try{
			statement = connection.prepareStatement(
				    "select * from subject where school_cd=? and cd=? and is_true = true");
			statement.setString(1, cd);
			statement.executeUpdate();
		}catch (Exception e) {
			throw e;
		}finally {
			statement.close();
			connection.close();
		}
		
		return true;
	}
}