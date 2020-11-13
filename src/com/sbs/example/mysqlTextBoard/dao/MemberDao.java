package com.sbs.example.mysqlTextBoard.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.sbs.example.mysqlTextBoard.dto.Member;

public class MemberDao {

	List<Member> members;

	public MemberDao() {
		members = new ArrayList<>();
	}

	public int saveMemberData(String loginId, String loginPw, String name) {
		int id = 0;
		Connection con = null;

		try {

			String driver = "com.mysql.cj.jdbc.Driver";

			String url = "jdbc:mysql://127.0.0.1:3306/textBoard?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull&connectTimeout=60000&socketTimeout=60000";
			String user = "sbsst";
			String pw = "sbs123414";

			// MySQL 드라이버 등록
			try {
				Class.forName(driver);
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			}

			// 연결 생성
			try {
				con = DriverManager.getConnection(url, user, pw);
			} catch (SQLException e) {
				e.printStackTrace();
			}

			String sql = "INSERT INTO member SET regDate = NOW(), loginId = ?, loginPw = ?, name = ?";

			try {
				PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

				pstmt.setString(1, loginId);
				pstmt.setString(2, loginPw);
				pstmt.setString(3, name);
				pstmt.executeUpdate();

				ResultSet rs = pstmt.getGeneratedKeys();
				rs.next();
				id = rs.getInt(1);

			} catch (SQLException e) {
				e.printStackTrace();
			}

		} finally {
			try {
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return id;
	}

	public Member getMemberByLoginId(String id) {
		Connection con = null;
		Member member = null;

		try {

			String driver = "com.mysql.cj.jdbc.Driver";

			String url = "jdbc:mysql://127.0.0.1:3306/textBoard?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull&connectTimeout=60000&socketTimeout=60000";
			String user = "sbsst";
			String pw = "sbs123414";

			// MySQL 드라이버 등록
			try {
				Class.forName(driver);
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			}

			// 연결 생성
			try {
				con = DriverManager.getConnection(url, user, pw);
			} catch (SQLException e) {
				e.printStackTrace();
			}

			String sql = "SELECT * FROM member WHERE loginId = ?";

			try {
				PreparedStatement pstmt = con.prepareStatement(sql);

				pstmt.setString(1, id);

				ResultSet rs = pstmt.executeQuery();

				if (rs.next()) {
					int memberId = rs.getInt("id");
					String regDate = rs.getString("regDate");
					String loginId = rs.getString("loginId");
					String loginPw = rs.getString("loginPw");
					String name = rs.getString("name");

					member = new Member(memberId, regDate, loginId, loginPw, name);
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}

		} finally {
			try {
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return member;
	}

	public Member getMemberByMemberId(int id) {
		Connection con = null;
		Member member = null;

		try {

			String driver = "com.mysql.cj.jdbc.Driver";

			String url = "jdbc:mysql://127.0.0.1:3306/textBoard?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull&connectTimeout=60000&socketTimeout=60000";
			String user = "sbsst";
			String pw = "sbs123414";

			// MySQL 드라이버 등록
			try {
				Class.forName(driver);
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			}

			// 연결 생성
			try {
				con = DriverManager.getConnection(url, user, pw);
			} catch (SQLException e) {
				e.printStackTrace();
			}

			String sql = "SELECT * FROM member WHERE id = ?";

			try {
				PreparedStatement pstmt = con.prepareStatement(sql);

				pstmt.setInt(1, id);

				ResultSet rs = pstmt.executeQuery();

				if (rs.next()) {
					int memberId = rs.getInt("id");
					String regDate = rs.getString("regDate");
					String loginId = rs.getString("loginId");
					String loginPw = rs.getString("loginPw");
					String name = rs.getString("name");

					member = new Member(memberId, regDate, loginId, loginPw, name);
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}

		} finally {
			try {
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return member;
	}

}
