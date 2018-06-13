package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import bean.UserListBean;

public class UserListModel {

	public ArrayList<UserListBean> getUserList(String authorUserNo) {
		/** 他会員一覧取得処理 */
		// bean入れるリスト宣言
		// beanそのものはSQLの結果を取得するところで初期化、宣言
		ArrayList<UserListBean> beanList = new ArrayList<>();
		StringBuilder sb = new StringBuilder();

		// DB
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
			Statement stmt = conn.createStatement();
			// SQL作成

			// ユーザ一覧取得
			sb.append("SELECT ");
			sb.append(" user_no ");
			sb.append(", user_id ");
			sb.append(", user_name ");
			sb.append("FROM ");
			sb.append(" m_user ");
			sb.append(" ORDER BY ");
			sb.append(" user_no ");

			// SQL実行
			ResultSet rs = stmt.executeQuery(sb.toString());

			// 結果をbeanに入れる
			while (rs.next()) {
				// beanを初期化
				UserListBean bean = new UserListBean();
				// 結果からログインしている自分自身は除く
				int check = rs.getInt("USER_NO");
				if (check == Integer.parseInt(authorUserNo)) {
					// 自分は追加しない
				} else {
					// 自分以外なのでbeanのフィールドにset
					bean.setUserNo(rs.getInt("USER_NO"));
					bean.setUserID(rs.getString("USER_ID"));
					bean.setUserName(rs.getString("USER_NAME"));
					// beanをリストに追加
					beanList.add(bean);
				}
			}
		} catch (SQLException e) {
			// SQLエラーはすべてここにくる
			e.printStackTrace();
			// beanList初期化
			beanList.clear();
			// エラー情報入れたbeanセット
			UserListBean bean = new UserListBean();
			bean.setErrorFlag(1);
			beanList.add(bean);

		} finally {
			// SQLの接続は絶対に切断
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		return beanList;
	}

/**	public ArrayList<UserListBean> getLatestMessage(ArrayList<UserListBean> beanList, String authorUserNo) {
		StringBuilder sb = new StringBuilder();

		// DB
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
			Statement stmt = conn.createStatement();

			// SQL作成
			for (int i = 0; i < beanList.size(); i++) {
				int uN = beanList.get(i).getUserNo();
				sb.append("SELECT ");
				sb.append(" message ");
				sb.append("FROM ");
				sb.append(" t_message_info ");
				sb.append("WHERE ");
				sb.append(" regist_date = ( ");
				sb.append(" SELECT ");
				sb.append(" MAX(regist_date) ");
				sb.append("FROM ");
				sb.append(" t_message_info ");
				sb.append("WHERE ");
				sb.append(" ( send_user_no = '" + authorUserNo + "' ");
				sb.append(" or send_user_no = '" + uN + "' ) ");
				sb.append(" AND ( to_send_user_no = '" + authorUserNo + "' ");
				sb.append(" or to_send_user_no = '" + uN + "' )");
				sb.append(" AND delete_flag = '0' )");
				sb.append(" ORDER BY REGIST_DATE ");

				// SQL実行
				ResultSet rs2 = stmt.executeQuery(sb.toString());
				if (rs2.next()) {
					// メッセージあり
					// そのまま対応するbeanに入れた後にArrayListに入れる
					beanList.get(i).setDirectMessage(rs2.getString("Message"));
				} else {
					// メッセージなし
					// 「会話を始めましょう」を入れる
					beanList.get(i).setDirectMessage("会話を始めましょう");
				}
				// 初期化
				sb.delete(0, sb.length());
			}
		} catch (SQLException e) {
			// SQLエラーはすべてここにくる
			e.printStackTrace();
			// beanList初期化
			beanList.clear();
			// エラー情報入れたbeanだけセット
			UserListBean bean = new UserListBean();
			bean.setErrorFlag(1);
			beanList.add(bean);
		} finally {
			// SQLの接続は絶対に切断
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

		return beanList;
	}
*/
}