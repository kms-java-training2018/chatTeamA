package servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.JOptionPane;

import bean.GroupMessageBean;
import bean.SessionBean;
import model.GroupMessageModel;

public class GroupMessageServlet extends HttpServlet {

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {

        req.getRequestDispatcher("/WEB-INF/jsp/groupMessage.jsp").forward(req, res);
    }

    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {

        //文字コード設定
        req.setCharacterEncoding("UTF-8");
        res.setContentType("text/html; charset=UTF-8");

        SessionBean sessionBean = new SessionBean();
        GroupMessageBean bean = new GroupMessageBean();
        GroupMessageModel model = new GroupMessageModel();
        String direction = "/WEB-INF/jsp/groupMessage.jsp";

        ArrayList<GroupMessageBean> list = new ArrayList<GroupMessageBean>();

        HttpSession session = req.getSession(false);

        // セッションがあるかどうか
        /*if(session.getAttribute("session") == null) {
            req.getRequestDispatcher("/WEB-INF/jsp/Login.jsp").forward(req, res);
        }*/

        //sessionBean = (SessionBean) session.getAttribute("session");
        sessionBean.setUserName("動けデブ");
        sessionBean.setUserNo("25");

        bean.setUserNo(sessionBean.getUserNo());
        bean.setGroupNo("12");

        // グループ番号チェック
        /*if("".equals(sessionBean.getGroupNo())) {
            req.getRequestDispatcher("/WEB-INF/jsp/Login.jsp").forward(req, res);
        }*/

        //modelの会員番号会員名処理をbean経由で取る
        try {
            list = model.messageCheck(bean);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //jspに飛ばす
        req.setAttribute("list", list);
        //req.getRequestDispatcher(direction).forward(req, res);


        // メッセージ送信機能
        if (req.getParameter("sendMessage") != null) {
            String message = req.getParameter("message");
            bean.setMessage(message);
            if (message.length() > 100 || message == null) {
                // エラー画面へ遷移
                bean.setErrFlag(true);
            } else {

                bean.setGroupNo("12"); // せっしょんからグループ

                try {
                    bean = model.groupCheck(bean);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    bean = model.nextNumCheck(bean);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                // 認証処理
                try {

                    bean = model.sendMessage(bean);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        // メッセージ削除ボタンが押されたとき
        /*if(req.getParameter("")) != null) {
            req.getParameter("");
            bean = model.DeleteMessage(bean);
        }*/

        // 退会ボタンが押されたとき
        if(req.getParameter("escape") != null) {
            // ダイアログ表示
            int dialogButton = JOptionPane.YES_NO_OPTION;
            int dialogResult = JOptionPane.showConfirmDialog(null, "Your Message", "Title on Box", dialogButton);
            // Yes,Noの判定
            if(dialogResult == 0) {
                bean.setGroupNo("12"); // せっしょんからグループ
              model.escapeGroup(bean);
            }

        }

        if (bean.isErrFlag()) {
            bean.setErrFlag(false);
            //direction = "/WEB-INF/jsp/error.jsp";
            direction = "/error";
        }

        req.getRequestDispatcher(direction).forward(req, res);
    }
}