package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.SessionBean;
import bean.ShowProfileBean;
import model.ShowProfileModel;

public class ShowProfileServlet extends HttpServlet {

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        // Beanの初期化
        ShowProfileBean bean = new ShowProfileBean();
        bean.setUserNo("");
        bean.setErrorMessage("");
        bean.setUserName("");
        bean.setMyPageText("");

        req.setAttribute("ShowProfileBean", bean);
        req.getRequestDispatcher("/WEB-INF/jsp/showProfile.jsp").forward(req, res);
    }

    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {

        // 初期化
        ShowProfileBean bean = new ShowProfileBean();
        ShowProfileModel model = new ShowProfileModel();
        String direction = "/WEB-INF/jsp/showProfile.jsp";

        // パラメータの取得
        String userNo = (String) req.getParameter("userNo");
        String userName = (String) req.getParameter("userName");
        String myPageText = (String) req.getParameter("myPageText");

        /**
         * セッションの存在チェック

        HttpSession session = req.getSession();
        if(session != null) {}
        */

        //対象ユーザーの会員番号をパラメータに保持しているかチェック
        if(userNo == null) {
            bean.setErrorMessage("ユーザーを読み込めません。");
        }else {
            bean.setUserName(userName);
            bean.setMyPageText(myPageText);
        }

        //プロフィール取得処理
        try{
            bean = model.authentication(bean);
        } catch (Exception e) {
            e.printStackTrace();
        }
        req.setAttribute("showProfileBean", bean);

        // 取得に成功した場合セッション情報をセット
        if ("".equals(bean.getErrorMessage())) {
            SessionBean sessionBean = new SessionBean();
            sessionBean.setUserName(bean.getUserName());
            HttpSession session = req.getSession();
            session.setAttribute("session", sessionBean);
        }

        req.getRequestDispatcher(direction).forward(req, res);
    }
}
