package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import bean.LoginBean;

/**
 * ログイン画面ビジネスロジック
 */
public class LoginModel {

    public LoginBean authentication(LoginBean bean) {
        // 初期化
        StringBuilder sb = new StringBuilder();
        String userId = bean.getUserId();
        String password = bean.getPassword();

        Connection conn = null;
        String url = "jdbc:oracle:thin:@192.168.51.67:1521:XE";
        String user = "DEV_TEAM_A";
        String dbPassword = "A_DEV_TEAM";
        // JDBCドライバーのロード
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            bean.setErrorMessage("データベースと接続出来ませんでした");
            e.printStackTrace();
        }
        // 接続作成
        try {
            conn = DriverManager.getConnection(url, user, dbPassword);

            // SQL作成
            sb.append("SELECT ");
            sb.append(" user_no ");
            sb.append(" ,user_name ");
            sb.append("FROM ");
            sb.append(" m_user ");
            sb.append("WHERE ");
            sb.append(" user_id = '" + userId + "' ");
            sb.append(" AND password = '" + password + "'");

            // SQL実行
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sb.toString());

            if (!rs.next()) {
                bean.setErrorMessage("会員IDもしくはパスワードが間違っています。");
            } else {
                bean.setUserNo(rs.getString("user_no"));
                bean.setUserName(rs.getString("user_name"));
                bean.setErrorMessage("");
                conn.close();

            }
        } catch (SQLException e) {
            bean.setErrorMessage("データがありませんでした。");
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
