package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.ProfileBean;
import model.MyPageModel;

public class MyPageServlet extends HttpServlet {

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {

        // Beanの初期化
        ProfileBean bean = new ProfileBean();
        bean.setUserNo("25");
        bean.setErrorMessage("");
        bean.setUserName("");
        bean.setMyPageText("");

        // 初期化
        MyPageModel model = new MyPageModel();

        // パラメータの取得
        String userNo = (String) req.getParameter("userNo");
        String userName = (String) req.getParameter("userName");
        String myPageText = (String) req.getParameter("myPageText");

        //パラメータチェック(セッションの存在チェック)
        HttpSession session = req.getSession();

        if(session != null) {
            //プロフィール情報をModelから取得
            try{
                bean = model.authentication(bean);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        req.setAttribute("ProfileBean", bean);
        req.getRequestDispatcher("/WEB-INF/jsp/myPage.jsp").forward(req, res);

    }


    /**public void doPost (HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {

        // 初期化
        ProfileBean bean = new ProfileBean();
        MyPageModel model = new MyPageModel();

        // パラメータの取得
        String userNo = (String) req.getParameter("userNo");
        String userName = (String) req.getParameter("userName");
        String myPageText = (String) req.getParameter("myPageText");
        Pattern p = Pattern.compile("^[0-9a-zA-Z]+$ || ^[^-~｡-ﾟ]*$ ");
        Matcher mUserName = p.matcher(userName);
        Matcher mMyPageText = p.matcher(myPageText);

        //"プロフィールを更新"のリクエスト送信後、入力値チェック
        if (userName.length() > 30 ) {
            bean.setErrorMessage("表示名は30文字以下で入力してください。");
        }else if (myPageText.length() > 100) {
            bean.setErrorMessage("自己紹介は100文字以下で入力してください。");
        } else if (!mUserName.find()) {
            bean.setErrorMessage("入力された値は正しくありません");
        } else if (!mMyPageText.find()) {
            bean.setErrorMessage("入力された値は正しくありません");
        } else {
            bean.setUserName(userName);
            bean.setMyPageText(myPageText);
        }

        // 認証処理
        try {
            bean = model.authentication(bean);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 取得に成功した場合セッション情報をセット
        if ("".equals(bean.getErrorMessage())) {
            SessionBean sessionBean = new SessionBean();
            sessionBean.setUserName(bean.getUserName());
            HttpSession session = req.getSession();
            session.setAttribute("session", sessionBean);
        }

        req.setAttribute("Profile", bean);
        req.getRequestDispatcher("/WEB-INF/jsp/mainPage.jsp").forward(req, res);

    }*/
}