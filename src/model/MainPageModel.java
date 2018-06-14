package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import bean.MainPageBean;

public class MainPageModel {

	//会員数取得
	public MainPageBean membership(MainPageBean bean) {
		// 初期化
		StringBuilder sb = new StringBuilder();
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
		try {
			conn = DriverManager.getConnection(url, user, dbPassword);

			//会員数取得処理
			sb.append("select count (*) ");
			sb.append("from m_user ");
			Statement stmtx = conn.createStatement();
			ResultSet num = stmtx.executeQuery(sb.toString());
			if (!num.next()) {
				bean.setErrorMessage("会員が取得できませんでした。");
			} else {
				bean.setNumber(num.getInt(1));
				bean.setErrorMessage("");
				conn.close();
			}
			// 初期化
			sb.delete(0, sb.length());
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return bean;

	}

	//会員名会員ナンバー取得
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
		ArrayList<MainPageBean> list = new ArrayList<MainPageBean>();
		try {
			conn = DriverManager.getConnection(url, user, dbPassword);

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

			// SQL実行
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sb.toString());

			while (rs.next()) {
				Statement stmt2 = conn.createStatement();

				MainPageBean myName = new MainPageBean();
				// Listに追加
				myName.setUserNo(rs.getString("user_no"));
				myName.setUserName(rs.getString("user_name"));

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
				sb2.append(" (to_send_user_no = '" + myName.getUserNo() + "'");
				sb2.append(" AND send_user_no = '" + userNo + "'");
				sb2.append(" AND delete_flag = 0)");
				sb2.append(" OR (to_send_user_no = '" + userNo + "'");
				sb2.append(" AND send_user_no = '" + myName.getUserNo() + "'");
				sb2.append(" AND delete_flag = 0))");

				// SQL実行
				ResultSet directMessage = stmt2.executeQuery(sb2.toString());

				if (directMessage.next()) {
					myName.setMessage(directMessage.getString(1));
				} else {
					// Listに追加
					myName.setMessage("会話を始めましょう!");
				}

				list.add(myName);

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

		return list;
	}

	//最新メッセージ取得
	public ArrayList<MainPageBean> latestMyTalk(MainPageBean bean) {
		// 初期化
		StringBuilder sb = new StringBuilder();
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

			/**最新メッセージ取得処理
			  上で取得した会員IDと自分のIDを条件に、
			  会話情報テーブルから二者間の最新メッセージを取得する
			  この処理を人数分繰り返す
			  */

			//最新メッセージ取得処理
			sb.append("SELECT ");
			sb.append("message ");
			sb.append("FROM ");
			sb.append("t_message_info ");
			sb.append("WHERE ");
			sb.append("(regist_date = (");
			sb.append("SELECT ");
			sb.append("MAX(regist_date) ");
			sb.append("FROM ");
			sb.append("t_message_info ");
			sb.append("WHERE ");
			sb.append("(send_user_no = " + userNo);
			sb.append("OR (to_send_user_no = " + userNo + "))))");

			// SQL実行
			Statement stmt2 = conn.createStatement();
			ResultSet rs2 = stmt2.executeQuery(sb.toString());

			while (rs2.next()) {
				MainPageBean myTalk = new MainPageBean();
				// Listに追加
				myTalk.setMessage(rs2.getString("message"));
				talkD.add(myTalk);

			}
			// 初期化
			sb.delete(0, sb.length());
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

	//グループ最新メッセージ取得
	public ArrayList<MainPageBean> latestGroupTalk(MainPageBean bean) {
		// 初期化
		StringBuilder sb = new StringBuilder();
		StringBuilder sb2 = new StringBuilder();
		Connection conn = null;
		String userNo = bean.getUserNo();
		//String groupNo = bean.getGroupNo();
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

			/**グループ一覧取得処理
			  自分の会員IDを条件に、参加しているグループと
			  そのグループ内での最新メッセージを取得する
			  */
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
			Statement stmt = conn.createStatement();
			ResultSet rs3 = stmt.executeQuery(sb.toString());

			while (rs3.next()) {
				Statement stmt2 = conn.createStatement();

				MainPageBean groupList = new MainPageBean();
				// Listに追加
				groupList.setGroupNo(rs3.getString("group_no"));
				groupList.setGroupName(rs3.getString("group_name"));

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
					// Listに追加
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
