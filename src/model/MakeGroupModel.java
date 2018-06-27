package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import bean.MakeGroupBean;

public class MakeGroupModel {

	public static boolean spaceCheck(String input) {
		boolean result;

		String str = input.replaceAll(" ", "");
		str = str.replaceAll("　", "");

		result = !(str.isEmpty());

		return result;

	}

	public static String commentCheck(String str) {

		String result = str.replaceAll("<", "&lt;");
		result = result.replaceAll("-", "&#45;");

		return result;
	}

	//グループ名の長さの指定
	final int GN_LENGTH = 30;

	public MakeGroupBean makeGroup(String groupName, String[] groupMemberNo, String authorUserNo) {
		// エラー結果入力用のbean作成
		MakeGroupBean bean = new MakeGroupBean();

		// 一人グループ判定用
		int soloGroupFlag = 0;

		// jspの入力値分析
		if (MakeGroupModel.spaceCheck(groupName) == false) {

			// グループ名が空
			bean.setErrorMessage("グループ名を入力してください");
			bean.setErrorFlag(2);

		} else if ((groupName.getBytes()).length > GN_LENGTH) {
			// グループ名が所定の文字数より長い
			bean.setErrorMessage("グループ名は30桁以内で入力してください");
			bean.setErrorFlag(2);
		} else if (groupMemberNo == null) {
			// グループメンバーがいない
			soloGroupFlag = 1;
		}
		if (bean.getErrorFlag() == 2) {
			// グループ名不正、登録処理を行わなずbeanを返す

		} else {
			/** グループ登録処理 */
			StringBuilder sb = new StringBuilder();
			//DB
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

				// 最大値取得
				sb.append("SELECT ");
				sb.append(" MAX(group_no) ");
				sb.append("FROM ");
				sb.append(" m_group ");

				// SQL実行
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sb.toString());

				int maxGroupNo = 0;
				while (rs.next()) {
					// 値を取得、その次の番号に
					maxGroupNo = rs.getInt("MAX(GROUP_NO)") + 1;
				}
				// 初期化
				sb.delete(0, sb.length());

				/** グループ作成処理 */
				sb.append("INSERT INTO ");
				sb.append(" m_group ");
				sb.append(" ( ");
				sb.append(" group_no ");
				sb.append(", group_name ");
				sb.append(", regist_user_no ");
				sb.append(", regist_date ");
				sb.append(" ) ");
				sb.append("VALUES ");
				sb.append(" ( ");
				sb.append(" '" + maxGroupNo + "' , ");
				sb.append(" '" + groupName + "' , ");
				sb.append(" '" + authorUserNo + "' , ");
				sb.append(" sysdate ");
				sb.append(" ) ");

				// SQL実行
				stmt.executeUpdate(sb.toString());

				// 初期化
				sb.delete(0, sb.length());

				/** グループ会員登録処理 */
				// 自分
				sb.append("INSERT INTO ");
				sb.append(" t_group_info ");
				sb.append(" ( ");
				sb.append(" group_no ");
				sb.append(", user_no ");
				sb.append(", out_flag ");
				sb.append(", regist_date ");
				sb.append(" ) ");
				sb.append("VALUES ");
				sb.append(" ( ");
				sb.append(" '" + maxGroupNo + "' , ");
				sb.append(" '" + authorUserNo + "' , ");
				sb.append(" 0 ,");
				sb.append(" sysdate ");
				sb.append(" ) ");

				// SQL実行
				stmt.executeUpdate(sb.toString());

				// 初期化
				sb.delete(0, sb.length());

				// 自分以外
				if (soloGroupFlag != 1) {

					// 一人グループじゃなければ他メンバーも追加
					for (int i = 0; i < groupMemberNo.length; i++) {
						sb.append("INSERT INTO ");
						sb.append(" t_group_info ");
						sb.append(" ( ");
						sb.append(" group_no ");
						sb.append(", user_no ");
						sb.append(", out_flag ");
						sb.append(", regist_date ");
						sb.append(" ) ");
						sb.append("VALUES ");
						sb.append(" ( ");
						sb.append(" '" + maxGroupNo + "' , ");
						sb.append(" '" + groupMemberNo[i] + "' , ");
						sb.append(" 0 ,");
						sb.append(" sysdate ");
						sb.append(" ) ");
						// SQL実行
						stmt.executeUpdate(sb.toString());

						// 初期化
						sb.delete(0, sb.length());

					}
				}

			} catch (SQLException e) {
				// SQLエラー
				e.printStackTrace();
				bean.setErrorMessage("予期せぬエラーが発生しました");
				bean.setErrorFlag(1);

				// SQLの接続は絶対に切断
			} finally {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return bean;
	}
}