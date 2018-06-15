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

}