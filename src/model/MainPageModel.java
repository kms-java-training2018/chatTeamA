package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import bean.MainPageBean;

public class MainPageModel {

	/**
	 * 会員名一覧と会員ナンバー、
	 * 最新ダイレクトメッセージの取得
	  */
	public ArrayList<MainPageBean> member(MainPageBean bean) {
		// 初期化
		StringBuilder sb = new StringBuilder();
		StringBuilder sb2 = new StringBuilder();
		String userNo = bean.getUserNo();
		Connection conn = null;
		String url = "jdbc:oracle:thin:@192.168.51.67:1521:XE";
		String user = "DEV_TEAM_A";
		String dbPassword = "A_DEV_TEAM";
		// JDBCドライバーのロード
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		// 接続作成
		ArrayList<MainPageBean> talkD = new ArrayList<MainPageBean>();
		try {
			conn = DriverManager.getConnection(url, user, dbPassword);
			Statement stmt = conn.createStatement();

			// 会員一覧取得処理SQL作成
			sb.append("SELECT ");
			sb.append(" user_no ");
			sb.append(" ,user_name ");
			sb.append("FROM ");
			sb.append(" m_user ");
			sb.append("WHERE ");
			sb.append(" user_no <> '" + userNo + "' ");
			sb.append(" ORDER BY ");
			sb.append(" user_no ");
			sb.append(" DESC ");

			// SQL実行
			ResultSet rs = stmt.executeQuery(sb.toString());

			while (rs.next()) {
				Statement stmt2 = conn.createStatement();

				MainPageBean directList = new MainPageBean();
				// Listに追加
				directList.setUserNo(rs.getString("user_no"));
				directList.setUserName(rs.getString("user_name"));

				//ダイレクトメッセージ取得
				sb2.append(" SELECT ");
				sb2.append(" message ");
				sb2.append(" FROM ");
				sb2.append(" t_message_info ");
				sb2.append(" WHERE");
				sb2.append(" regist_date = ( ");
				sb2.append(" SELECT ");
				sb2.append(" MAX(regist_date) ");
				sb2.append(" FROM ");
				sb2.append(" t_message_info ");
				sb2.append(" WHERE ");
				sb2.append(" (to_send_user_no = '" + directList.getUserNo() + "'");
				sb2.append(" AND send_user_no = '" + userNo + "'");
				sb2.append(" AND delete_flag = 0)");
				sb2.append(" OR (to_send_user_no = '" + userNo + "'");
				sb2.append(" AND send_user_no = '" + directList.getUserNo() + "'");
				sb2.append(" AND delete_flag = 0))");

				// SQL実行
				ResultSet directMessage = stmt2.executeQuery(sb2.toString());

				if (directMessage.next()) {
					directList.setMessage(directMessage.getString(1));
				} else {
					directList.setMessage("会話を始めましょう!");
				}

				talkD.add(directList);

				// 初期化
				sb2.delete(0, sb2.length());

				stmt2.close();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return talkD;
	}

	/**
	 * グループ一覧取得処理
	 * 最新グループメッセージ取得処理
	  */
	public ArrayList<MainPageBean> latestGroupTalk(MainPageBean bean) {
		// 初期化
		StringBuilder sb = new StringBuilder();
		StringBuilder sb2 = new StringBuilder();
		Connection conn = null;
		String userNo = bean.getUserNo();
		String url = "jdbc:oracle:thin:@192.168.51.67:1521:XE";
		String user = "DEV_TEAM_A";
		String dbPassword = "A_DEV_TEAM";

		// JDBCドライバーのロード
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		// 接続作成
		ArrayList<MainPageBean> talkG = new ArrayList<MainPageBean>();
		try {
			conn = DriverManager.getConnection(url, user, dbPassword);
			Statement stmt = conn.createStatement();

			//自分が参加しているグループ一覧取得
			sb.append("SELECT");
			sb.append(" A.group_no , B.group_name ");
			sb.append("FROM");
			sb.append(" t_group_info A full outer join m_group B on A.group_no = B.group_no ");
			sb.append("WHERE");
			sb.append(" A.user_no = '" + userNo + "'");
			sb.append(" and A.out_flag = 0");
			sb.append(" ORDER BY ");
			sb.append(" group_no DESC");

			// SQL実行
			ResultSet rs = stmt.executeQuery(sb.toString());

			while (rs.next()) {
				Statement stmt2 = conn.createStatement();

				MainPageBean groupList = new MainPageBean();
				// Listに追加
				groupList.setGroupNo(rs.getString("group_no"));
				groupList.setGroupName(rs.getString("group_name"));

				//グループメッセージ取得処理
				sb2.append(" SELECT ");
				sb2.append(" message ");
				sb2.append(" FROM ");
				sb2.append(" t_message_info ");
				sb2.append(" WHERE");
				sb2.append(" regist_date = ( ");
				sb2.append(" SELECT ");
				sb2.append(" MAX(regist_date) ");
				sb2.append(" FROM ");
				sb2.append(" t_message_info ");
				sb2.append(" WHERE ");
				sb2.append(" to_send_group_no = '" + groupList.getGroupNo() + "'");
				sb2.append(" AND delete_flag =  0) ");

				// SQL実行
				ResultSet groupMessage = stmt2.executeQuery(sb2.toString());

				if (groupMessage.next()) {
					groupList.setMessage(groupMessage.getString(1));
				} else {
					groupList.setMessage("会話を始めましょう!");
				}

				talkG.add(groupList);

				sb2.delete(0, sb2.length());

				stmt2.close();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return talkG;
	}
}
