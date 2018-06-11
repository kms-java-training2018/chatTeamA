package servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.MakeGroupBean;
import bean.SessionBean;
import bean.UserListBean;
import model.MakeGroupModel;
import model.UserListModel;

public class MakeGroupServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		// グループ作成ページに移動する処理
		// 初期化

		UserListModel model = new UserListModel();
		SessionBean sessionBean = new SessionBean();
		ArrayList<UserListBean> userListBeanList = new ArrayList<>();
		String direction = "/WEB-INF/jsp/makeGroup.jsp";

		/** パラメータチェック */
		HttpSession session = req.getSession();
		sessionBean = (SessionBean) session.getAttribute("session");
		String authorUserNo = sessionBean.getUserNo();

		if (authorUserNo == null) {

			// セッションチェック

			direction = "/error";
			req.setAttribute("errorMessage", "セッションがありません");
		} else {

			/** 会員一覧取得処理 */
			try {
				userListBeanList = model.getUserList(authorUserNo);
			} catch (Exception e) {
				e.printStackTrace();
				userListBeanList.clear();

				// エラー情報入れたbeanだけセット
				UserListBean ulBean = new UserListBean();
				ulBean.setErrorFlag(1);
				userListBeanList.add(ulBean);
			}
			if (userListBeanList.isEmpty() || userListBeanList.get(0).getErrorFlag() == 1) {
				//エラーページに遷移
				direction = "/error";
			} else {
				// 正常に一覧取得できた場合、リクエストに送る
				req.setAttribute("bean", userListBeanList);
			}
		}
		req.getRequestDispatcher(direction).forward(req, res);

	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		// グループ作成を行いメインページに移動する処理
		// 初期化
		MakeGroupBean bean = new MakeGroupBean();
		MakeGroupModel model = new MakeGroupModel();
		String direction = "/WEB-INF/jsp/makeGroup.jsp";

		/** パラメータチェック */
		req.setCharacterEncoding("UTF-8");

		// セッション情報確認
		HttpSession session = req.getSession();
		// 入力値を変数に渡す
		String groupName = req.getParameter("groupName");
		String[] groupMemberNo = req.getParameterValues("userNo");

		if (session == null) {

			// セッション情報なしの場合
			direction = "/error";
			req.setAttribute("errorMessage", "セッションがありません");
		} else {
			// それ以外の場合処理続ける
			// ログインユーザー情報をセッションから取得
			SessionBean sessionBean = (SessionBean) session.getAttribute("session");
			String authorUserNo = sessionBean.getUserNo();

			try {
				/** グループ登録処理とグループ会員登録処理 */
				bean = model.makeGroup(groupName, groupMemberNo, authorUserNo);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (bean.getErrorFlag() == 1) {
				// SQLでのエラー
				req.setAttribute("errorMessage", bean.getErrorMessage());
				direction = "/error";
				req.setAttribute("bean", bean);
			} else if (bean.getErrorFlag() == 2) {

				// グループ名が不正だった場合
				/** 会員一覧取得処理 */
				UserListModel ulModel = new UserListModel();
				ArrayList<UserListBean> userListBeanList = new ArrayList<>();
				try {
					userListBeanList = ulModel.getUserList(authorUserNo);
				} catch (Exception e) {
					e.printStackTrace();

					// エラーはいてるのでuserListBeanList初期化してエラー情報入れる
					userListBeanList.clear();
					// エラー情報入れたbeanだけセット
					UserListBean ulBean = new UserListBean();
					ulBean.setErrorFlag(1);
					userListBeanList.add(ulBean);
				}
				if (userListBeanList.isEmpty() || userListBeanList.get(0).getErrorFlag() == 1) {
					// 途中でエラーはいている場合
					direction = "/error";
				} else {
					// 正常に一覧取得できた場合
					req.setAttribute("bean", userListBeanList);
					// 行き先をグループ作成ページに
					direction = "/WEB-INF/jsp/makeGroup.jsp";
				}

			} else {
				// 正常にグループ作成できた場合
				// 行き先をメインページに
				direction = "/main";
				req.setAttribute("bean", bean);
			}

			req.setAttribute("errorMessage", bean.getErrorMessage());
			req.getRequestDispatcher(direction).forward(req, res);

		}
	}
}