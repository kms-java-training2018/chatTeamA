package servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.GroupMessageBean;
import bean.SessionBean;
import model.GroupMessageModel;

public class GroupMessageServlet extends HttpServlet {

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {

        String direction = "/WEB-INF/jsp/groupMessage.jsp";

        GroupMessageBean bean = new GroupMessageBean();
        SessionBean sessionBean = new SessionBean();
        GroupMessageModel model = new GroupMessageModel();
        ArrayList<GroupMessageBean> list = new ArrayList<GroupMessageBean>();
        HttpSession session = req.getSession();
        sessionBean = (SessionBean) session.getAttribute("session");

        bean.setUserNo(sessionBean.getUserNo());
        bean.setUserName(sessionBean.getUserName());
        bean.setGroupNo(req.getParameter("group_no"));
        sessionBean.setGroupNo(bean.getGroupNo());

        // グループ番号チェック
        try {
            bean = model.groupCheck(bean);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //modelの会員番号会員名処理をbean経由で取る
        try {
            list = model.messageCheck(bean, sessionBean.getUserNo());
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (bean.isErrFlag()) {
            direction = "/error";
        }
        bean = model.getGroupName(bean);

        session.setAttribute("session", sessionBean);

        //jspに飛ばす
        req.setAttribute("list", list);
        req.setAttribute("group_name", bean.getGroupName());

        req.getRequestDispatcher(direction).forward(req, res);
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

        HttpSession session = req.getSession();

        sessionBean = (SessionBean) session.getAttribute("session");

        bean.setUserNo(sessionBean.getUserNo());
        bean.setUserName(sessionBean.getUserName());
        bean.setGroupNo(sessionBean.getGroupNo());

        // グループ番号チェック
        try {
            bean = model.groupCheck(bean);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // メッセージ削除ボタンが押されたとき
        if (req.getParameter("delete") != null) {
            String deleteMessageNo = req.getParameter("message_no");
            bean.setErrFlag(model.DeleteMessage(bean.isErrFlag(), deleteMessageNo));
        }

        // メッセージ送信機能
        if (req.getParameter("sendMessage") != null) {
            String message = req.getParameter("message");
            bean.setMessage(message);
            if (message.length() > 100 || message == null) {
                // エラー画面へ遷移
                bean.setErrFlag(true);
            } else {

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

        //modelの会員番号会員名処理をbean経由で取る
        try {
            list = model.messageCheck(bean, sessionBean.getUserNo());
        } catch (Exception e) {
            e.printStackTrace();
        }

        bean = model.getGroupName(bean);

        //jspに飛ばす
        req.setAttribute("list", list);
        req.setAttribute("group_name", bean.getGroupName());

        // 退会ボタンが押されたとき
        if (req.getParameter("escape") != null) {

            bean = model.registCheck(bean, sessionBean.getUserNo());
            if (!bean.isErrFlag()) {
                bean = model.escapeGroup(bean);
            }
            direction = "/main";
        }

        if (bean.isErrFlag()) {
            bean.setErrFlag(false);

            direction = "/error";
        }

        req.getRequestDispatcher(direction).forward(req, res);
    }
}