package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.ProfileBean;
import bean.SessionBean;
import model.ShowProfileModel;

public class ShowProfileServlet extends HttpServlet {

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        // 初期化
    	String userNo = (String) req.getParameter("user_no"); //from direct or group link
        ProfileBean bean = new ProfileBean();
        bean.setUserNo(userNo);
        bean.setErrorMessage("");
        bean.setUserName("");
        bean.setMyPageText("");
        ShowProfileModel model = new ShowProfileModel();
        String direction = "/WEB-INF/jsp/showProfile.jsp";

        //セッション情報の取得
        HttpSession session = req.getSession();
        SessionBean sessionBean = new SessionBean();
        sessionBean = (SessionBean) session.getAttribute("session");

        if(sessionBean == null) {
			req.getRequestDispatcher("/error").forward(req, res);
			return;
		}

        if(session != null) {
            /**
             * パラメータチェック
             * 対象ユーザーの会員番号をパラメータに、
             *   ・保持している場合→Modelでプロフィール情報を取得
             *   ・保持していない場合→エラー画面へ
             */
            if(userNo != null) {
                try{
                    bean = model.authentication(bean);
                } catch (Exception e) {
                	direction = "/error";
                	e.printStackTrace();
                }
            }else {
            	direction = "/error";
            }
        }
        req.setAttribute("ProfileBean", bean);
        req.getRequestDispatcher(direction).forward(req, res);
    }

}