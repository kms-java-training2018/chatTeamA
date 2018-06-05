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
        /**
         * プロフィール情報の取得
         */

        // Beanの初期化
        SessionBean bean1 = new SessionBean();
        ProfileBean bean2 = new ProfileBean();
        bean2.setUserNo("");
        bean2.setErrorMessage("");
        bean2.setUserName("");
        bean2.setMyPageText("");

        // 初期化
        MyPageModel model = new MyPageModel();
        String direction = "/WEB-INF/jsp/myPage.jsp";

        // パラメータの取得
//        String userNo = (String) req.getParameter("userNo");
//        String userName = (String) req.getParameter("userName");
//        String myPageText = (String) req.getParameter("myPageText");

        //セッション情報の取得
        HttpSession session = req.getSession();
        //session.getAttribute(userNo);
        bean1 = (SessionBean) session.getAttribute("session");
        bean2.setUserNo(bean1.getUserNo());

        /**
         * パラメータチェック
         *   ・セッションが存在する場合→Modelでプロフィール情報を取得
         *   ・セッションが存在しない場合→エラー画面へ
         */
        if(session != null) {
            try{
                bean2 = model.authentication(bean2);
            } catch (Exception e) {
                e.printStackTrace();
                direction = "/error";
            }
        }
        req.setAttribute("ProfileBean", bean2);
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
        SessionBean bean1 = new SessionBean();
        ProfileBean bean2 = new ProfileBean();
        MyPageModel model = new MyPageModel();
        String direction = "/WEB-INF/jsp/myPage.jsp";

        // パラメータの取得
        String userNo = (String) req.getParameter("userNo");
        String userName = (String) req.getParameter("userName");
        String myPageText = (String) req.getParameter("myPageText");
        //Pattern p1 = Pattern.compile("^[0-9a-zA-Z]*$");    //半角英数or空白
        Pattern p = Pattern.compile("^[^-~｡-ﾟ]*$");    //全角
        Matcher mUserName = p.matcher(userName);    //←エラーここ
        Matcher mMyPageText = p.matcher(myPageText);

        //"プロフィールを更新"のリクエスト送信後、入力値チェック
        if (userName.length() > 30 ) {
            if(myPageText.length() > 100) {
                if(!mUserName.find()) {
                    if(!mMyPageText.find()) {
                        bean2.setErrorMessage("表示名は30文字以下、自己紹介は100文字以下の全角で入力してください。");
                    }
                }
                bean2.setErrorMessage("表示名は30文字以下で入力してください。");
            }

        }else if (myPageText.length() > 100) {
            bean2.setErrorMessage("自己紹介は100文字以下で入力してください。");
        } else if (!mUserName.find()) {
            bean2.setErrorMessage("入力された値は正しくありません");
        } else if (!mMyPageText.find()) {
            bean2.setErrorMessage("入力された値は正しくありません");
        } else {
            bean2.setUserName(userName);
            bean2.setMyPageText(myPageText);
            bean2.setErrorMessage("");
        }

        // 認証処理
//        try {
//            bean2 = model.authentication(bean2);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        // プロフィール更新処理
//      try {
//          bean2 = model.authentication2(bean2);
//      } catch (Exception e) {
//          e.printStackTrace();
//      }

        // 更新処理が成功した場合セッションに情報をセット
        bean2.setUserNo(userNo);
        if ("".equals(bean2.getErrorMessage())) {
            try {
                bean2 = model.authentication2(bean2);
            } catch (Exception e) {
                e.printStackTrace();
            }
            bean1.setUserName(bean2.getUserName());
            bean1.setMyPageText(bean2.getMyPageText());
            HttpSession session = req.getSession();
            session.setAttribute("session", bean1);
            direction = "/main";
        }else {
            //更新に失敗した場合→プロフィール情報を再度DBから取得
            bean2 = model.authentication(bean2);
            req.setAttribute("ProfileBean", bean2);
        }
        req.setAttribute("session", bean1);
        req.getRequestDispatcher(direction).forward(req, res);

    }
}