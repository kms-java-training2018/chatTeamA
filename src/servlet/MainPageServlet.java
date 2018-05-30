package servlet;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.MainPageBean;
import bean.SessionBean;
import model.MainPageModel;

/**
 * メインページ用サーブレット
 */

public class MainPageServlet extends HttpServlet {
    /**
    * 初期表示
    */
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        // Beanの初期化
        MainPageBean bean = new MainPageBean();
        bean.setErrorMessage("");
        bean.setUserId("");
        bean.setPassword("");

        req.setAttribute("mainPageBean", bean);
        req.getRequestDispatcher("/WEB-INF/jsp/main.jsp").forward(req, res);
    }


    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        // 初期化
        MainPageBean bean = new MainPageBean();
        MainPageModel model = new MainPageModel();
        String direction = "/WEB-INF/jsp/main.jsp";

     // パラメータの取得
        String userId = (String) req.getParameter("userId");
        String password = (String) req.getParameter("password");
        Pattern p = Pattern.compile("^[0-9a-zA-Z]+$");
        Matcher mUserId = p.matcher(userId);

        Matcher mPassword = p.matcher(password);

     // パラメータの判定
        if (userId.length() > 20 || password.length() > 20 || !mUserId.find() || !mPassword.find()) {
            bean.setErrorMessage("入力された値は正しくありません");
        } else {
            bean.setUserId(userId);
            bean.setPassword(password);

         // 認証処理
            try {
                bean = model.authentication(bean);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        req.setAttribute("MainPageBean", bean);

        // 取得に成功した場合セッション情報をセット
        if ("".equals(bean.getErrorMessage())) {
            SessionBean sessionBean = new SessionBean();
            sessionBean.setUserName(bean.getUserName());
            sessionBean.setUserNo(bean.getUserNo());
            HttpSession session = req.getSession();
            session.setAttribute("session", sessionBean);

            // 行き先を次の画面に
            direction = "/main";
        }

        req.getRequestDispatcher(direction).forward(req, res);

    }

}