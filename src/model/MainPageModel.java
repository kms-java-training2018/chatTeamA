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
    public MainPageBean authentication(MainPageBean bean) {
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
                //この場合エラー画面へ遷移
            } else {
                //bean.setNumber(Integer.parseInt(num.getString("count (*)")));
                bean.setNumber(num.getInt(1));
                bean.setErrorMessage("");
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
                //この場合エラー画面へ遷移
            }
        }
        return bean;

    }

    //会員名会員ナンバー取得
    public ArrayList<MainPageBean> authentication2(MainPageBean bean) {
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


            // SQL実行
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sb.toString());


            while (rs.next()) {
                MainPageBean myName = new MainPageBean () ;
                // Listに追加
                myName.setUserNo(rs.getString("user_no"));
                myName.setUserName(rs.getString("user_name"));
                list.add(myName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
                //この場合エラー画面へ遷移
            }
        }



        return list;
    }

    //最新メッセージ取得
    public ArrayList<MainPageBean> authentication3(MainPageBean bean) {
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
        ArrayList<MainPageBean> list2 = new ArrayList<MainPageBean>();
        try {
            conn = DriverManager.getConnection(url, user, dbPassword);

            /**最新メッセージ取得処理
              上で取得した会員IDと自分のIDを条件に、
              会話情報テーブルから二者間の最新メッセージを取得する
              この処理を人数分繰り返す
              */
            //会員の人数分回処理を繰り返す
            //while (num.next()) {

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
			sb.append("(send_user_no = " + userNo );
			sb.append("OR (to_send_user_no = " + userNo + "))))" );


            // SQL実行
            Statement stmt2 = conn.createStatement();
            ResultSet rs2 = stmt2.executeQuery(sb.toString());

            while (rs2.next()) {
                MainPageBean myTalk = new MainPageBean () ;
                // Listに追加
                //myTalk.setUserName(rs2.getString("user_name"));
                myTalk.setMessage(rs2.getString("message"));
                list2.add(myTalk);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
                //この場合エラー画面へ遷移
            }
        }



        return list2;
    }

    //グループ最新メッセージ取得
    public ArrayList<MainPageBean> authentication4(MainPageBean bean) {
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
            e.printStackTrace();
        }
        // 接続作成
        ArrayList<MainPageBean> list3 = new ArrayList<MainPageBean>();
        try {
            conn = DriverManager.getConnection(url, user, dbPassword);
            /**グループ一覧取得処理
              自分の会員IDを条件に、参加しているグループと
              そのグループ内での最新メッセージを取得する
              */
            sb.append("select");
            sb.append(" info.to_send_group_no");
            sb.append(" , a.md");
            sb.append(" , info.MESSAGE");
            sb.append(" , gn.group_name");
            sb.append(" from");
            sb.append(" t_message_info info");
            sb.append(" , (");
            sb.append(" select");
            sb.append(" max(regist_date) as md ");
            sb.append(" from");
            sb.append(" t_message_info");
            sb.append(" group by");
            sb.append(" to_send_group_no");
            sb.append("  ) a");
            sb.append("  , (select group_name, group_no from m_group) gn");
            sb.append(" where");
            sb.append("  info.REGIST_DATE = a.md");
            sb.append(" and info.to_send_group_no is not null ");
            sb.append(" and info.TO_SEND_GROUP_NO = gn.group_no");
            sb.append(" order by");
            sb.append(" a.md desc");

         // SQL実行
            Statement stmt3 = conn.createStatement();
            ResultSet rs3 = stmt3.executeQuery(sb.toString());

            while (rs3.next()) {
                MainPageBean groupTalk = new MainPageBean () ;
                // Listに追加
                groupTalk.setUserNo(rs3.getString("to_send_group_no"));
                groupTalk.setGroupName(rs3.getString("group_name"));
                groupTalk.setMessage(rs3.getString("MESSAGE"));
                list3.add(groupTalk);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
                //この場合エラー画面へ遷移
            }
        }



        return list3;
    }
}
