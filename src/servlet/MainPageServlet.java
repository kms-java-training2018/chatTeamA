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
                sessionBean = (SessionBean) session.getAttribute("session");

        // 初期化
        MainPageBean bean = new MainPageBean();
        MainPageModel model = new MainPageModel();
        String direction = "/WEB-INF/jsp/main.jsp";
        String userNo = sessionBean.getUserNo();

        bean.setUserNo(userNo);
        ArrayList<MainPageBean> list = new ArrayList<MainPageBean>();
        ArrayList<MainPageBean> talkD = new ArrayList<MainPageBean>();
        ArrayList<MainPageBean> talkG = new ArrayList<MainPageBean>();

        //modelの会員番号会員名処理をbean経由で取る
        try {
            list = model.member(bean);
        } catch (Exception e) {
            e.printStackTrace();
            direction = "/error";
        }

        //jspに飛ばす
        req.setAttribute("list", list);


        //1対1最新会話情報取得
        try {
        	talkD = model.latestMyTalk(bean);
        } catch (Exception e) {
            e.printStackTrace();
            direction = "/error";
        }

      //jspに飛ばす
        req.setAttribute("talkD", talkD);


      //グループ一覧情報取得
        try {
        	talkG = model.latestGroupTalk(bean);
        } catch (Exception e) {
            e.printStackTrace();
            direction = "/error";
        }

      //jspに飛ばす
        req.setAttribute("talkG", talkG);



        // 取得に成功した場合セッション情報をセット
        if ("".equals(bean.getErrorMessage())) {
            sessionBean.setUserName(bean.getUserName());
            sessionBean.setUserNo(bean.getUserNo());
            session.setAttribute("session", sessionBean);
        }

        req.getRequestDispatcher(direction).forward(req, res);

    }

}