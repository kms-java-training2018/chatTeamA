package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import bean.ProfileBean;

public class ShowProfileModel {

    public ProfileBean authentication(ProfileBean bean) {

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
            bean.setErrFlag(true);
            e.printStackTrace();
        }

        // 接続作成
        try {
            conn = DriverManager.getConnection(url, user, dbPassword);

            // SQL作成
            sb.append("SELECT ");
            sb.append(" user_name");
            sb.append(" , my_page_text ");
            sb.append("FROM ");
            sb.append(" m_user ");
            sb.append("WHERE ");
            sb.append(" user_no = '" + userNo + "' ");

            // SQL実行
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sb.toString());

            if (!rs.next()) {
            	bean.setErrFlag(true);
            } else {
            	bean.setUserName(rs.getString("user_name"));
                bean.setMyPageText(rs.getString("my_page_text"));
                bean.setErrorMessage("");
                conn.close();
            }
        } catch (SQLException e) {
            bean.setErrFlag(true);
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                bean.setErrFlag(true);
                e.printStackTrace();
            }
        }
        return bean;
    }

}
