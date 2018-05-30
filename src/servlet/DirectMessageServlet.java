package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.DirectMessageBean;
import model.DirectMessageModel;

public class DirectMessageServlet extends HttpServlet {

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {

        req.getRequestDispatcher("/WEB-INF/jsp/directMessage.jsp").forward(req, res);
    }

    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {

        // 初期化
        DirectMessageBean bean = new DirectMessageBean();
        DirectMessageModel model = new DirectMessageModel();
        String direction = "/WEB-INF/jsp/directMessage.jsp";

        /**
         *
         *パラメータチェック画面表示処理
         *【処理概要】
         *メインメニュー画面で対象ユーザーリンクを押したときに実行される処理。
         *セッション情報チェック・会話情報取得処理が成功した場合、メッセージ画面に遷移する。
         *
         */

        /*
         * パラメータチェック
         */

        //--セッションの存在チェック--//

        //存在しなければエラー画面へ遷移
        //if (session = null) {
        //direction = "/error";
        //}

        // パラメータの取得
        String userId = (String) req.getParameter("userId");
        String password = (String) req.getParameter("password");

        bean.setUserId(userId);
        bean.setPassword(password);

        //--パラメータチェック--//

        //送信対象者番号
        String toSendUserNo = "";

        //存在しなければエラー画面へ遷移
        if (toSendUserNo.equals(null)) {
            direction = "/error";
        }

        /*
         * 会話情報取得処理
         */

        //--セッション情報の会員番号と、送信対象者の会員番号を条件に会話情報取得する処理--//
        try {
            bean = model.authentication(bean);
        } catch (Exception e) {
            e.printStackTrace();
        }

        /**
        *
        *メッセージ送信処理
        *【処理概要】
        *メッセージ画面で送信ボタンを押したときに実行される処理。
        *パラメータチェックに成功した場合、会話情報テーブルにメッセージを登録する。
        *
        */

        /*
         * パラメータチェック
         */

        //メッセージ画面で入力された情報を取得
        String message = req.getParameter("name1");

        //--パラメータチェック--//
        //未入力の場合
        if(message == null) {

            //エラーメッセージ設定
            bean.setErrorMessage("メッセージを入力してください");

            //メッセージ画面へ遷移



        //桁数チェック

        } else if(message.length() > 1) {

            //エラーメッセージ設定
            bean.setErrorMessage("メッセージが長すぎます");

            //メッセージ画面へ遷移


        }

        //--パラメータチェック完了--//



        //        // 取得に成功した場合セッション情報をセット
        //        if ("".equals(bean.getErrorMessage())) {
        //            SessionBean sessionBean = new SessionBean();
        //            sessionBean.setUserName(bean.getUserName());
        //            sessionBean.setUserNo(bean.getUserNo());
        //            HttpSession session = req.getSession();
        //            session.setAttribute("session", sessionBean);
        //
        //            // 行き先を次の画面に
        //            direction = "/directMessage";
        //        }
        req.getRequestDispatcher(direction).forward(req, res);
        return;
    }
}
