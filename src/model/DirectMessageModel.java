package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import bean.DirectMessageBean;

/**
 * メッセージ画面ビジネスロジック
 */
public class DirectMessageModel {
    public DirectMessageBean authentication(DirectMessageBean bean) {
        // 初期化
        StringBuilder sb = new StringBuilder();
        String userId = bean.getUserId();
        String toSendUserNo = bean.getPassword();

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

            // SQL作成

            //--会員番号と送信者番号、送信対象者番号を紐付けてメッセージ一覧取得--//
            //--該当する送信者と送信対象者の一覧のみを表示--//
            sb.append("SELECT");
            sb.append("message");
            sb.append("FROM");
            sb.append("t_message_info A");
            sb.append("inner join m_user B");
            sb.append("on A.send_user_no = B.user_no");
            sb.append("inner join m_user B");
            sb.append("on A.to_send_user_no = B.user_no");
            sb.append("WHERE ");
            sb.append(" to_send_user_no = '" + toSendUserNo + "' ");
            sb.append(" AND send_user_no = '" + userId + "'");

            // SQL実行
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sb.toString());

            ArrayList<String> list = new ArrayList<String>();

            //--メッセージの分だけ取得--//
            if (!rs.next()) {
                bean.setErrorMessage("メッセージの取得に失敗しました");

            } else {
                while (rs.next()) {
                bean.setMessage(rs.getString("message"));
                list.add("message");
                }
                bean.setErrorMessage("");
            }
            

            conn.close();


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
}
