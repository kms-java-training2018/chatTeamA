package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import bean.GroupMessageBean;

public class GroupMessageModel {
    // 次の格納するメッセージの数字
    public GroupMessageBean nextNumCheck(GroupMessageBean bean) {
        // 初期化
        StringBuilder sb = new StringBuilder();
        String groupNo = bean.getGroupNo();
        String userNo = bean.getUserNo();

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
            sb.append("COUNT(*) ");
            sb.append("FROM ");
            sb.append("t_message_info");

            // SQL実行
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sb.toString());

            if (!rs.next()) {
                bean.setErrFlag(true);
            } else {
                bean.setNextNo(String.valueOf(rs.getInt(1)+1));
                conn.close();

            }

            //conn.close();

        } catch (SQLException e) {
            bean.setErrFlag(true);
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


    // グループに入っているか
    public GroupMessageBean groupCheck(GroupMessageBean bean) {
        // 初期化
        StringBuilder sb = new StringBuilder();
        String groupNo = bean.getGroupNo();
        String userNo = bean.getUserNo();

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
            sb.append(" user_no ");
            sb.append("FROM ");
            sb.append(" t_group_info ");
            sb.append("WHERE ");
            sb.append(" group_no = '" + groupNo +"'");
            sb.append(" AND user_no = '" + userNo + "'");

            // SQL実行
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sb.toString());

            // グループに入ってない
            if (!rs.next()) {
                bean.setErrFlag(true);

            }

            conn.close();

        } catch (SQLException e) {
            bean.setErrFlag(true);
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

    /**
     *	メッセージ送信処理
     * @param bean
     * @return
     */
    public GroupMessageBean sendMessage(GroupMessageBean bean) {
        // 初期化
        StringBuilder sb = new StringBuilder();
        //String groupNo = bean.getGroupNo();
        String userNo = bean.getUserNo();
        String nextNo = bean.getNextNo();
        String message = bean.getMessage();

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
            sb.append("INSERT ");
            sb.append("INTO ");
            sb.append(" t_message_info (");
            sb.append(" message_no ");
            sb.append(" ,send_user_no  ");
            sb.append(" ,message");
            sb.append(" ,to_send_group_no");
            sb.append(" ,delete_flag");
            sb.append(" ,regist_date");
            sb.append(") values (");
            sb.append("'"+ nextNo +"'");
            sb.append(" ,'"+ userNo +"'");
            sb.append(" ,'"+ message +"'");
            sb.append(" ,12");
            sb.append(" ,0");
            sb.append(" ,sysdate)");


            // SQL実行
            Statement stmt = conn.createStatement();
            stmt.executeQuery(sb.toString());

            conn.close();

        } catch (SQLException e) {
            bean.setErrFlag(true);
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



    /**	グループ退会処理
     * @param bean
     * @return
     */
    public GroupMessageBean escapeGroup(GroupMessageBean bean) {

        // 初期化
        StringBuilder sb = new StringBuilder();
        String groupNo = bean.getGroupNo();
        String userNo = bean.getUserNo();

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
            sb.append("UPDATE ");
            sb.append(" t_group_info ");
            sb.append("SET ");
            sb.append(" out_flag ='" + 1 + "'");
            sb.append("WHERE  ");
            sb.append(" group_no = '" + groupNo + "'");
            sb.append(" user_no = '" + userNo + "' ");

            // SQL実行
            Statement stmt = conn.createStatement();
            stmt.executeQuery(sb.toString());

            conn.close();

        } catch (SQLException e) {
            bean.setErrFlag(true);
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
