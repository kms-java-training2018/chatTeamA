package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import bean.ProfileBean;

public class MyPageModel {
    public ProfileBean authentication(ProfileBean bean2) {
        // 初期化
        //bean1 = SesstionBean, bean2 = ProfileBean
//        SessionBean bean1 = new SessionBean();
        StringBuilder sb = new StringBuilder();
        String userNo = bean2.getUserNo();
//        String userName;
//        String myPageText;

        // DB
        Connection conn = null;
        String url = "jdbc:oracle:thin:@192.168.51.67:1521:XE";
        String user = "DEV_TEAM_A";
        String dbPassword = "A_DEV_TEAM";

        // JDBCドライバーのロード
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
//            bean2.setErrorMessage("データベースに接続出来ませんでした");
            bean2.setErrFlag(true);
            e.printStackTrace();
        }

        // 接続作成
        try {
            conn = DriverManager.getConnection(url, user, dbPassword);

            // SQL作成(既存のプロフィール情報取得)
            sb.append("SELECT ");
            sb.append(" user_name");
            sb.append(" , my_page_text ");
            sb.append("FROM ");
            sb.append(" m_user ");
            sb.append("WHERE ");
            sb.append(" user_no = '" + userNo + "'");

            // SQL実行
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sb.toString());

            //取得したデータをbeanにセット
            if (!rs.next()) {
//                bean2.setErrorMessage("ユーザーが存在しません。");
                bean2.setErrFlag(true);
            } else {
                bean2.setUserName(rs.getString("user_name"));
                bean2.setMyPageText(rs.getString("my_page_text"));

            }
        } catch (SQLException e) {
//            bean2.setErrorMessage("ユーザー情報を取得できません。");
            bean2.setErrFlag(true);
            e.printStackTrace();
        //処理終了後、接続を切断
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return bean2;
    }

    public ProfileBean authentication2(ProfileBean bean2) {

        // 初期化
        StringBuilder sb = new StringBuilder();
        String userNo = bean2.getUserNo();
        String userName = bean2.getUserName();
        String myPageText = bean2.getMyPageText();

        // DB
        Connection conn = null;
        String url = "jdbc:oracle:thin:@192.168.51.67:1521:XE";
        String user = "DEV_TEAM_A";
        String dbPassword = "A_DEV_TEAM";

        // JDBCドライバーのロード
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
//            bean2.setErrorMessage("データベースに接続出来ませんでした");
            bean2.setErrFlag(true);
            e.printStackTrace();
        }

        // 接続作成
        try {
            conn = DriverManager.getConnection(url, user, dbPassword);

            // SQL作成(プロフィール情報更新)
            sb.append("UPDATE ");
            sb.append(" m_user ");
            sb.append("SET");
            sb.append(" user_name='"+ userName + "' ");
            sb.append(" , my_page_text='"+ myPageText + "' ");
            sb.append("WHERE ");
            sb.append(" user_no = '" + userNo + "' ");

            // SQL実行
            Statement stmt = conn.createStatement();
            int resultCount = stmt.executeUpdate(sb.toString());

            //更新に失敗した場合
            if (resultCount == 0) {
//                bean2.setErrorMessage("更新処理に失敗しました。");
                bean2.setErrFlag(true);
            }
        } catch (SQLException e) {
//            bean2.setErrorMessage("更新処理に失敗しました。");
            bean2.setErrFlag(true);
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                bean2.setErrFlag(true);
                e.printStackTrace();
            }
        }
        return bean2;
    }
}
