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

	public static boolean spaceCheck(String input) {
		boolean result;

		String str = input.replaceAll(" ", "");
		str = str.replaceAll("　", "");

		result = !(str.isEmpty());

		return result;

	}

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        /**
         * プロフィール情報の取得
         */

        // Beanの初期化
        SessionBean sessionBean = new SessionBean();
        ProfileBean bean = new ProfileBean();
        bean.setUserNo("");
        bean.setUserName("");
        bean.setMyPageText("");
        bean.setErrorMessage("");

        // 初期化
        MyPageModel model = new MyPageModel();
        String direction = "/WEB-INF/jsp/myPage.jsp";

        //セッション情報の取得
        HttpSession session = req.getSession();
        sessionBean = (SessionBean) session.getAttribute("session");

        if(sessionBean == null) {
			req.getRequestDispatcher("/error").forward(req, res);
			return;
		}

        bean.setUserNo(sessionBean.getUserNo());

        /**
         * パラメータチェック
         *   ログインユーザーの会員番号をパラメータに、
         *   ・保持している場合→Modelでプロフィール情報を取得
         *   ・保持していない場合→エラー画面へ
         */
        if(session != null) {
            try{
                bean = model.authentication(bean);
            } catch (Exception e) {
                e.printStackTrace();
                direction = "/error";
            }
        }
        req.setAttribute("ProfileBean", bean);
        req.getRequestDispatcher(direction).forward(req, res);
    }


    public void doPost (HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
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

        // パラメータの取得
        String userNo = (String) req.getParameter("userNo");


        //"プロフィールを更新"のリクエスト送信後、入力値チェック
        if("userName" != null || "myPageText" != null) {
        	String userName = (String) req.getParameter("userName");
            String myPageText = (String) req.getParameter("myPageText");
            bean.setUserNo(userNo);
            bean.setUserName(userName);
            bean.setMyPageText(myPageText);
            bean.setErrorMessage("");



        if (userName.getBytes().length > 30 || myPageText.getBytes().length > 100) {
        	bean.setErrorMessage("表示名は30桁以下、自己紹介文は100桁以下で入力してください。");
        } else if (!MyPageServlet.spaceCheck(userName) || !MyPageServlet.spaceCheck(myPageText)) {
        	bean.setErrorMessage("メッセージを入力してください。");
        }

        }

        // 更新処理が成功した場合→セッションに情報をセット
        if ("".equals(bean.getErrorMessage())) {
            try {
                bean = model.authentication2(bean);
            } catch (Exception e) {
                e.printStackTrace();
            }
            sessionBean.setUserNo(userNo);
            sessionBean.setUserName(bean.getUserName());
            HttpSession session = req.getSession();
            session.setAttribute("session", sessionBean);
            direction = "/main";
        //更新に失敗した場合→プロフィール情報を再度DBから取得
        }else {
            bean = model.authentication(bean);
            req.setAttribute("ProfileBean", bean);
        }
        req.setAttribute("session", sessionBean);
        req.getRequestDispatcher(direction).forward(req, res);

    }

}