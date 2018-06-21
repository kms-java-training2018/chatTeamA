package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import bean.ProfileBean;

public class MyPageModel {

	public ProfileBean getMyProfile(ProfileBean bean) {
		/**
		 * ログインユーザーのプロフィール情報を取得
		 */

		// 初期化
		StringBuilder sb = new StringBuilder();
		String userNo = bean.getUserNo();

		// DB
		Connection conn = null;
		String url = "jdbc:oracle:thin:@192.168.51.67:1521:XE";
		String user = "DEV_TEAM_A";
		String dbPassword = "A_DEV_TEAM";

		// JDBCドライバーのロード
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			bean.setErrorFlag(true);
			e.printStackTrace();
		}

		// 接続作成
		try {
			conn = DriverManager.getConnection(url, user, dbPassword);

			// SQL作成
			//ログインユーザーの表示名と自己紹介文を取得
			sb.append("SELECT ");
			sb.append(" user_name");
			sb.append(" , my_page_text ");
			sb.append("FROM ");
			sb.append(" m_user ");
			sb.append("WHERE ");
			sb.append(" user_no = '" + userNo + "'");

			// SQL実行
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sb.toString());

			//取得したデータをProfileBeanにセット
			if (!rs.next()) {
				bean.setErrorFlag(true);
			} else {
				bean.setUserName(rs.getString("user_name"));
				bean.setMyPageText(rs.getString("my_page_text"));
			}
		} catch (SQLException e) {
			bean.setErrorFlag(true);
			e.printStackTrace();
		//処理終了後、接続を切断
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return bean;
	}


	public ProfileBean updateMyProfile(ProfileBean bean) {
		/**
		 * ログインユーザーのプロフィール情報を更新
		 */

		// 初期化
		StringBuilder sb = new StringBuilder();
		String userNo = bean.getUserNo();
		String userName = bean.getUserName();
		String myPageText = bean.getMyPageText();

		// DB
		Connection conn = null;
		String url = "jdbc:oracle:thin:@192.168.51.67:1521:XE";
		String user = "DEV_TEAM_A";
		String dbPassword = "A_DEV_TEAM";

		// JDBCドライバーのロード
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			bean.setErrorFlag(true);
			e.printStackTrace();
		}

		// 接続作成
		try {
			conn = DriverManager.getConnection(url, user, dbPassword);

			// SQL作成
			//ログインユーザーの表示名と自己紹介文を更新
			sb.append("UPDATE ");
			sb.append(" m_user ");
			sb.append("SET");
			sb.append(" user_name='" + userName + "' ");
			sb.append(" , my_page_text='" + myPageText + "' ");
			sb.append(" , update_date = sysdate ");
			sb.append("WHERE ");
			sb.append(" user_no = '" + userNo + "' ");

			// SQL実行
			Statement stmt = conn.createStatement();
			int resultCount = stmt.executeUpdate(sb.toString());

			//更新に失敗した場合
			if (resultCount == 0) {
				bean.setErrorFlag(true);
			}
		} catch (SQLException e) {
			bean.setErrorFlag(true);
			e.printStackTrace();
		//処理終了後、接続を切断
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				bean.setErrorFlag(true);
				e.printStackTrace();
			}
		}
		return bean;
	}

}