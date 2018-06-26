package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import bean.NewInsertBean;



public class NewInsertModel {
	/**
	 * 認証処理
	 * @param bean
	 * @return
	 */
    public NewInsertBean signUp(NewInsertBean bean) {
        // 初期化
        StringBuilder sb = new StringBuilder();
        String userId = bean.getUserId();
        String password = bean.getPassword();
        String userName = bean.getUserName();
        bean.setErrorMessage("");

        Connection conn = null;
        String url = "jdbc:oracle:thin:@192.168.51.67:1521:XE";
        String user = "DEV_TEAM_A";
        String dbPassword = "A_DEV_TEAM";
        // JDBCドライバーのロード
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            bean.setErrorMessage("データベースと接続出来ませんでした。");
            e.printStackTrace();
        }
        // 接続作成
        try {
            conn = DriverManager.getConnection(url, user, dbPassword);

         // 最大値取得
			sb.append("SELECT ");
			sb.append(" MAX(user_no) ");
			sb.append("FROM ");
			sb.append(" m_user ");

			// SQL実行
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sb.toString());

			int userNo = 0;
			while (rs.next()) {
				// 値を取得、その次の番号に
				userNo = rs.getInt("MAX(USER_NO)") + 1;
			}
			// 初期化
			sb.delete(0, sb.length());


            // SQL作成
            sb.append("INSERT ");
            sb.append("INTO ");
            sb.append(" m_user( ");
            sb.append(" user_no ");
            sb.append(" , user_id ");
            sb.append(" , password ");
            sb.append(" , user_name ");
            sb.append(" , regist_date) ");
            sb.append(" values( ");
            sb.append(" " + userNo + " ");
            sb.append(" ,'" + userId + "' ");
            sb.append(" ,'" + password + "' ");
            sb.append(" ,'" + userName + "' ");
            sb.append(" , sysdate)");

            // SQL実行
            stmt = conn.createStatement();
            stmt.executeUpdate(sb.toString());

        } catch (SQLException e) {
            bean.setErrorMessage("データベースと接続出来ませんでした。");
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
}
