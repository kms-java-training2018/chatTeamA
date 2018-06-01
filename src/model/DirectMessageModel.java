package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import bean.DirectMessageBean;

/**
 * メッセージ画面ビジネスロジック
 */
public class DirectMessageModel {
    public DirectMessageBean authentication1(DirectMessageBean bean1) {
        // 初期化
        StringBuilder sb = new StringBuilder();
        String userId = bean1.getUserId();
        String toSendUserNo = bean1.getPassword();

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
            sb.append(" to_send_user_no = 2 "); //'" + toSendUserNo + "'
            sb.append(" AND send_user_no = 1 "); //'" + userId + "'
            sb.append(" AND delete_flag = 0");

            // SQL実行
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sb.toString());

            //--メッセージの分だけ取得--//
            if (!rs.next()) {
                bean1.setErrorMessage("メッセージの取得に失敗しました");

            } else {
                //beanにメッセージを格納
                while (rs.next()) {
                    bean1.setMessage(rs.getString("message"));
                }
                bean1.setErrorMessage("");
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

        return bean1;
    }

    /*
     * 会話情報登録処理
     */
    //セッション情報の会員番号を条件に、内容を登録する。
    public DirectMessageBean authentication2(DirectMessageBean bean2) {
        // 初期化
        StringBuilder sb = new StringBuilder();
        String message = bean2.getMessage();


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

            //--メッセージを確認した後、会話情報テーブルへ--//
            sb.append("INSERT");
            sb.append(" into");
            sb.append(" t_message_info(");
            sb.append("message_no");
            sb.append(", send_user_no");
            sb.append(", message");
            sb.append(", to_send_user_no");
            sb.append(", delete_flag");
            sb.append(", regist_date)");
            sb.append("values (");
            sb.append("10");
            sb.append(", 1");
            sb.append(", '" + message + "' ");
            sb.append(", 2");
            sb.append(", 0");
            sb.append(", sysdate )");

            // SQL実行
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sb.toString());

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

        return bean2;
    }

    /*
     * 会話情報論理削除処理
     */

    public DirectMessageBean authentication3(DirectMessageBean bean3) {
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
            // SQL作成

            //--会話情報テーブルの対象の会話のdelete_flagと更新日時を変更する--//
            sb.append("");
            sb.append("");
            sb.append("");
            sb.append("");
            sb.append("");
            sb.append("");
            sb.append("");
            sb.append("");
            sb.append("");
            sb.append("");
            sb.append(",");
            sb.append(",");
            sb.append(",");
            sb.append(", )");

            // SQL実行
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sb.toString());

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return bean3;
    }
}
