package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.ProfileBean;
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

        //セッションの存在チェック
        HttpSession session = req.getSession();
        if(session != null) {
            /**
             * 対象ユーザーの会員番号をパラメータに保持しているかチェック
             *   ・保持している場合→Modelでプロフィール情報を取得
             *   ・保持していない場合→エラー画面へ
             */
            if(userNo != null) {
            	//プロフィール取得処理
                try{
                    bean = model.authentication(bean);
                } catch (Exception e) {
                	direction = "/error";
                	e.printStackTrace();
                }
            }else {
            	direction = "/error";
            }
            //Modelにエラーがあった場合→エラー画面に遷移
            if(bean.isErrFlag()) {
                direction = "/error";
            }
        }
        req.setAttribute("ProfileBean", bean);
        req.getRequestDispatcher(direction).forward(req, res);
    }
}