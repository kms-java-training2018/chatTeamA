package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import bean.MakeGroupBean;

public class MakeGroupModel {


        // 初期化
        //MakeGroupBeanの設定
        MakeGroupBean gb = new MakeGroupBean();

        //作成者の名前
        private String autherName;
        //作成者の番号
        private String autherNo;
        //グループの名前
        private String groupName;

        //グループビーンの引継ぎ
        public void setMakeGroupBean(MakeGroupBean MakeGroupBean) {
            this.gb = MakeGroupBean;
        }

        //指定されたグループネームの設定
        public void setGroupName(String name) {
            this.groupName = name;
        }

        //指定されたAutherNoの設定
        public void setAutherNo(String No) {
            this.autherNo = No;
        }

        public void checkinName(String name) {
        }

        //ユーザー一覧を出すメソッド
        public MakeGroupBean authentication(String name) {
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
                // 入れなかった場合
                e.printStackTrace();
            }
            // 接続作成
            try {
                conn = DriverManager.getConnection(url, user, dbPassword);

                // SQLから全会員情報を取得
                sb.append("SELECT ");
                sb.append(" user_no ");
                sb.append(" ,user_name ");
                sb.append("FROM ");
                sb.append(" m_user ");

                // SQL実行してrsにセット
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sb.toString());

                while (rs.next()) {

                    /* 行からデータを取得 */
                    gb.setUserNo(rs.getString("user_no"));
                    gb.setUserName(rs.getString("user_name"));
                    gb.setErrorMessage("");
                }

            } catch (SQLException e) {
                e.printStackTrace();
                // sqlの接続は絶対に切断
            } finally {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            //受け取った作成者userNameをBeanに渡して処理
            this.autherName = name;

            gb.setAuther(autherName);
            return gb;

        }

        //既存のグループnameか確認するメソッド

        //グループ登録を行うメソッド
        public String MakeGroup() {
            // 初期化
            StringBuilder sb = new StringBuilder();

            //戻り値として渡す成否メッセージを定義
            String makeCheck;

            //gbからautherNoを
            String autherNo = gb.getAutherNo();

            //DB接続
            Connection conn = null;
            String url = "jdbc:oracle:thin:@192.168.51.67:1521:XE";
            String user = "DEV_TEAM_A";
            String dbPassword = "A_DEV_TEAM";

            // JDBCドライバーのロード
            try {
                Class.forName("oracle.jdbc.driver.OracleDriver");
            } catch (ClassNotFoundException e) {
                // 入れなかった場合
                e.printStackTrace();
            }
            // 接続作成
            try {
                conn = DriverManager.getConnection(url, user, dbPassword);

                // SQLから全会員情報を取得
                sb.append("insert ");
                sb.append(" into ");
                sb.append(" M_GROUP( ");
                sb.append("GROUP_NO ");
                sb.append(", GROUP_NAME ");
                sb.append(", REGIST_USER_NO");
                sb.append(", REGIST_DATE");
                sb.append(")");
                sb.append("values");
                sb.append("(GROUP_SEQ.NEXTVAL");
                sb.append(",'" + groupName + "'");
                sb.append("," + autherNo);
                sb.append(", sysdate)");

                // SQL実行してrsにセット
                Statement stmt = conn.createStatement();
                int rs = stmt.executeUpdate(sb.toString());

                if (rs == 1) {
                    makeCheck = "Make ok";
                } else {
                    makeCheck = "Make no";
                }
            } catch (SQLException e) {
                e.printStackTrace();
                makeCheck = "Make no";
            } finally {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            return makeCheck;
        }

        //グループにメンバー登録を行うメソッド
        public String ResistGroup(String[] list) {
            // 初期化
            StringBuilder sb = new StringBuilder();

            //戻り値として渡す成否メッセージを定義
            String message = "0";

            //受け取ったStringリストからを登録者Listに設定
            String[] memberName = list;
            ArrayList<String> memberNo = new ArrayList<String>();
            for(String name : memberName) {
                int i = Integer.parseInt(name);
                memberNo.add(gb.getUserNo().get(i));
            }


            //作成者番号を入手
            this.autherNo = gb.getAutherNo();

            //DB接続
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

                // 作成者idとグループ名から、グループ番号を入手
                sb.append("select ");
                sb.append(" MAX(group_no) ");
                sb.append(" from ");
                sb.append("m_group ");
                sb.append(" where ");
                sb.append(" regist_user_no ");
                sb.append(" in ");
                sb.append(autherNo);
                sb.append(" and");
                sb.append(" group_name ");
                sb.append(" = ");
                sb.append("'" + groupName + "'");

                // SQL実行してrsにセット
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sb.toString());

                //結果をグループナンバーにセット

                String groupNo = "0";
                while (rs.next()) {
                    groupNo = rs.getString("MAX(group_no)");
                }

                //作成者のメンバー登録を行う文
                StringBuilder sb1 = new StringBuilder();
                sb1.append("insert into");
                sb1.append(" T_GROUP_INFO( ");
                sb1.append("GROUP_NO");
                sb1.append(", USER_NO ");
                sb1.append(", OUT_FLAG");
                sb1.append(", REGIST_DATE");
                sb1.append(")");
                sb1.append(" values ");
                sb1.append("(" + groupNo);
                sb1.append("," + autherNo);
                sb1.append(",0");
                sb1.append(", sysdate)");

                // 会員登録を行う

                for (int i = 0;i < memberNo.size(); i++) {
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("insert ");
                    sb2.append(" into ");
                    sb2.append(" T_GROUP_INFO( ");
                    sb2.append("GROUP_NO");
                    sb2.append(", USER_NO ");
                    sb2.append(", OUT_FLAG");
                    sb2.append(", REGIST_DATE");
                    sb2.append(")");
                    sb2.append(" values ");
                    sb2.append("(" + groupNo);
                    sb2.append("," + memberNo.get(i));
                    sb2.append(",0");
                    sb2.append(", sysdate)");


                }

            } catch (SQLException e) {
                e.printStackTrace();
                message = "Resist no";

                // sqlの接続の切断
            } finally {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            return message;
        }

    }

