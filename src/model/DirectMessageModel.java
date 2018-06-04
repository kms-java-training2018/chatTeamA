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
    public ArrayList<DirectMessageBean> getMessage(DirectMessageBean bean) {
        // 初期化
        StringBuilder sb = new StringBuilder();
        String toSendUserNo = bean.getToSendUserNo();
        String userNo = bean.getUserNo();

        Connection conn = null;
        String url = "jdbc:oracle:thin:@192.168.51.67:1521:XE";
        String user = "DEV_TEAM_A";
        String dbPassword = "A_DEV_TEAM";
        // JDBCドライバーのロード
        ArrayList<DirectMessageBean> list = new ArrayList<DirectMessageBean>();
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
            sb.append(" A.send_user_no AS sendUserNo");
            //sb.append(" ,message_no ");
            sb.append(" ,message");
            sb.append(" FROM");
            sb.append(" t_message_info A");
            sb.append(" inner join m_user B");
            sb.append(" on A.send_user_no = B.user_no");
            //sb.append(" inner join m_user B");
            //sb.append(" on A.to_send_user_no = B.user_no");
            sb.append(" WHERE");
            sb.append(" to_send_user_no = '" + toSendUserNo + "'");
            sb.append(" AND send_user_no = '" + userNo + "'");
            sb.append(" AND delete_flag = 0");

            // SQL実行
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sb.toString());

            //--メッセージの分だけ取得--//
            if (!rs.next()) {
                bean.setErrorMessage("メッセージの取得に失敗しました");

            } else {
                //bean型のlistにメッセージを格納
                while (rs.next()) {
                    DirectMessageBean directMessage = new DirectMessageBean () ;
                    directMessage.setMessage(rs.getString("message"));

                    if (rs.getString("sendUserNo").equals(userNo)) {
                        directMessage.setMyMessageFlag(true);
                    } else {
                        directMessage.setMyMessageFlag(false);
                    }
                    list.add(directMessage);
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

        return list;
    }

    /*
     * 会話情報登録処理
     */

 // 次の格納するメッセージの数字
    public DirectMessageBean nextNumCheck(DirectMessageBean bean) {
        // 初期化
        StringBuilder sb = new StringBuilder();
        //String userNo = bean.getUserNo();

        Connection conn = null;
        String url = "jdbc:oracle:thin:@192.168.51.67:1521:XE";
        String user = "DEV_TEAM_A";
        String dbPassword = "A_DEV_TEAM";
        // JDBCドライバーのロード
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            bean.setErrorMessage("エラーです");
            e.printStackTrace();
        }
        // 接続作成
        try {
            conn = DriverManager.getConnection(url, user, dbPassword);

            // SQL作成
            sb.append("SELECT ");
            sb.append("COUNT(*) ");
            sb.append("FROM ");
            sb.append("t_message_info");

            // SQL実行
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sb.toString());

            if (!rs.next()) {
                bean.setErrorMessage("エラーです");
            } else {
                bean.setMessageNo(String.valueOf(rs.getInt(1)+1));
                conn.close();

            }

            //conn.close();

        } catch (SQLException e) {
            bean.setErrorMessage("エラーです");
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



    //セッション情報の会員番号を条件に、内容を登録する。
    public DirectMessageBean messageRegi(DirectMessageBean bean) {
        // 初期化
        StringBuilder sb = new StringBuilder();
        String message = bean.getMessage();
        String messageNo = bean.getMessageNo();
        String toSendUserNo = bean.getToSendUserNo();
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
            sb.append(""+ Integer.valueOf(messageNo) +"");
            sb.append(", "+ Integer.valueOf(userNo) + "");
            sb.append(", '" + message + "' ");
            sb.append(", "+ Integer.valueOf(toSendUserNo) + "");
            sb.append(", 0");
            sb.append(", sysdate )");

            System.out.println(sb.toString());

            // SQL実行
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sb.toString());


            if (!rs.next()) {
                bean.setErrorMessage("メッセージの登録に失敗しました");

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

    /*
     * 会話情報論理削除処理
     */

    public DirectMessageBean DeleteMessage(DirectMessageBean bean3 ,String deleteMessageNo) {
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
            sb.append("update ");
            sb.append("t_message_info ");
            sb.append("set ");
            sb.append("delete_flag = 1");
            sb.append(", update_date = sysdate ");
            sb.append("where ");
            sb.append("message_no = " + deleteMessageNo);


            // SQL実行
            Statement stmt = conn.createStatement();
            stmt.executeQuery(sb.toString());

        } catch (SQLException e) {
            e.printStackTrace();
            bean3.setErrorMessage("削除に失敗しました");
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
