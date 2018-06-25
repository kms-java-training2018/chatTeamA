package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.ProfileBean;
import bean.SessionBean;
import model.MyPageModel;

public class MyPageServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		/**
		 * プロフィール情報の取得
		 */

		// 初期化
		MyPageModel model = new MyPageModel();
		String direction = "/WEB-INF/jsp/myPage.jsp";

		// Beanの初期化
		SessionBean sessionBean = new SessionBean();
		ProfileBean bean = new ProfileBean();
		bean.setUserNo("");
		bean.setUserName("");
		bean.setMyPageText("");
		bean.setErrorMessage("");

		//セッション情報の取得
		HttpSession session = req.getSession();
		sessionBean = (SessionBean) session.getAttribute("session");

		//セッション情報が無い場合→エラー画面へ
		if (sessionBean == null) {
			req.getRequestDispatcher("/error").forward(req, res);
			return;
		}

		//パラメータチェック
		//会員番号をパラメータに保持している場合→ModelでDBからプロフィール情報を取得→ProfileBeanにセット
		if (session != null) {
			bean.setUserNo(sessionBean.getUserNo());
			try {
				bean = model.getMyProfile(bean);
			//会員番号をパラメータに保持していない場合→エラー画面へ
			} catch (Exception e) {
				e.printStackTrace();
				direction = "/error";
			}
		}
		req.setAttribute("ProfileBean", bean);
		req.getRequestDispatcher(direction).forward(req, res);
	}

	public static boolean spaceCheck(String input) {
		/**
		 * スペースを空白に置き換える処理
		 */

		boolean result;
		String str = input.replaceAll(" ", "");
		str = str.replaceAll("　", "");
		result = !(str.isEmpty());
		return result;
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		/**
		 * プロフィール情報の変更・更新
		 */

		//文字コード設定
		res.setContentType("text/html; charset=UTF-8");
		req.setCharacterEncoding("UTF-8");

		// 初期化
		SessionBean sessionBean = new SessionBean();
		ProfileBean bean = new ProfileBean();
		MyPageModel model = new MyPageModel();
		String direction = "/WEB-INF/jsp/myPage.jsp";

		//セッションから会員番号を取得
		HttpSession session = req.getSession();
		sessionBean = (SessionBean) session.getAttribute("session");
		bean.setUserNo(sessionBean.getUserNo());

		// パラメータ(テキストボックスに入力された値)を取得
		String userName = (String) req.getParameter("userName");
		String myPageText = (String) req.getParameter("myPageText");

		//"プロフィールを更新"のリクエスト送信後、入力値チェック
		if ("userName" != null && "myPageText" != null) {
			bean.setUserNo(bean.getUserNo());
			bean.setUserName(userName);
			bean.setMyPageText(myPageText);
			bean.setErrorMessage("");
			//桁数チェック
			if (userName.getBytes().length > 30 || myPageText.getBytes().length > 100) {
				bean.setErrorMessage("表示名は30桁以内、自己紹介文は100桁以内で入力してください。");
			} else if (!MyPageServlet.spaceCheck(userName) || !MyPageServlet.spaceCheck(myPageText)) {
				bean.setErrorMessage("メッセージを入力してください。");
			}
		}

		//更新処理が成功した場合→セッションに情報をセット→メインメニューへ
		if ("".equals(bean.getErrorMessage())) {
			try {
				bean = model.updateMyProfile(bean);
			} catch (Exception e) {
				e.printStackTrace();
			}
			sessionBean.setUserNo(bean.getUserNo());
			sessionBean.setUserName(bean.getUserName());
			session.setAttribute("session", sessionBean);
			direction = "/main";
		//更新に失敗した場合→ModelでDBからプロフィール情報を再取得
		} else {
			bean = model.getMyProfile(bean);
			req.setAttribute("ProfileBean", bean);
		}
		req.setAttribute("session", sessionBean);
		req.getRequestDispatcher(direction).forward(req, res);
	}

}