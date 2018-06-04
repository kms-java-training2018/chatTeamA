package servlet;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

        // Beanの初期化
        SessionBean bean1 = new SessionBean();
        ProfileBean bean2 = new ProfileBean();
        bean2.setUserNo("");
        bean2.setErrorMessage("");
        bean2.setUserName("");
        bean2.setMyPageText("");

        // 初期化
        MyPageModel model = new MyPageModel();

        // パラメータの取得
        String userNo = (String) req.getParameter("userNo");
        String userName = (String) req.getParameter("userName");
        String myPageText = (String) req.getParameter("myPageText");

        //セッション情報の取得
        HttpSession session = req.getSession();
        //session.getAttribute(userNo);
        bean1 = (SessionBean) session.getAttribute("session");
        bean2.setUserNo(bean1.getUserNo());

        //パラメータチェック(セッションの存在チェック)
        if(session != null) {
            //プロフィール情報をModelで取得
            try{
                bean2 = model.authentication(bean2);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        req.setAttribute("ProfileBean", bean2);
        req.getRequestDispatcher("/WEB-INF/jsp/myPage.jsp").forward(req, res);

    }


    public void doPost (HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {

        // 初期化
        SessionBean bean1 = new SessionBean();
        ProfileBean bean2 = new ProfileBean();
        MyPageModel model = new MyPageModel();

        // パラメータの取得
//        String userNo = (String) req.getParameter("userNo");
        String userName = (String) req.getParameter("userName");
        String myPageText = (String) req.getParameter("myPageText");
        Pattern p = Pattern.compile("^[0-9a-zA-Z]+$ || ^[^-~｡-ﾟ]*$ ");
        Matcher mUserName = p.matcher(userName);
        // ↑ここ変？
        Matcher mMyPageText = p.matcher(myPageText);

        //"プロフィールを更新"のリクエスト送信後、入力値チェック
        if (userName.length() > 30 ) {
            bean2.setErrorMessage("表示名は30文字以下で入力してください。");
        }else if (myPageText.length() > 100) {
            bean2.setErrorMessage("自己紹介は100文字以下で入力してください。");
        } else if (!mUserName.find()) {
            bean2.setErrorMessage("入力された値は正しくありません");
        } else if (!mMyPageText.find()) {
            bean2.setErrorMessage("入力された値は正しくありません");
        } else {
            bean2.setUserName(userName);
            bean2.setMyPageText(myPageText);
        }

        // 認証処理
        try {
            bean2 = model.authentication(bean2);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 取得に成功した場合セッション情報をセット
        if ("".equals(bean2.getErrorMessage())) {
            bean1.setUserName(bean2.getUserName());
            bean1.setMyPageText(bean2.getMyPageText()); //てらっちにSessionBeanにmyPageTextを追加してもらう
            HttpSession session = req.getSession();
            session.setAttribute("session", bean1);
        }

        req.setAttribute("Profile", bean1);
        req.getRequestDispatcher("/WEB-INF/jsp/myPage.jsp").forward(req, res);

    }
}