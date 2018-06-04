package servlet;

import java.io.IOException;
import java.util.ArrayList;

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
        //MainPageBean bean = new MainPageBean();
        //bean.setErrorMessage("");
        //bean.setUserId("");
        //bean.setPassword("");

        //req.setAttribute("mainPageBean", bean);
        req.getRequestDispatcher("/WEB-INF/jsp/main.jsp").forward(req, res);
        }



    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        // セッション情報取得
                HttpSession session = req.getSession();
                SessionBean sessionBean = new SessionBean();
        // 初期化
        MainPageBean bean = new MainPageBean();
        MainPageModel model = new MainPageModel();
        String direction = "/WEB-INF/jsp/main.jsp";
        ArrayList<MainPageBean> list = new ArrayList<MainPageBean>();
        //ArrayList<String> list2 = new ArrayList<String>();

        sessionBean = (SessionBean) session.getAttribute("session");

        //modelの会員番号会員名処理をbean経由で取る
        try {
            list = model.authentication2(bean);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //jspに飛ばす
        req.setAttribute("list", list);
        req.getRequestDispatcher(direction).forward(req, res);



        // 取得に成功した場合セッション情報をセット
        if ("".equals(bean.getErrorMessage())) {
            sessionBean.setUserName(bean.getUserName());
            sessionBean.setUserNo(bean.getUserNo());
            session.setAttribute("session", sessionBean);
        }

        req.getRequestDispatcher(direction).forward(req, res);

    }

}