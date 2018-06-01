package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.DirectMessageBean;
import bean.SessionBean;
import model.DirectMessageModel;

public class DirectMessageServlet extends HttpServlet {

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {

        req.getRequestDispatcher("/WEB-INF/jsp/directMessage.jsp").forward(req, res);
    }

    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {

        //文字コード設定
        res.setContentType("text/html; charset=UTF-8");
        req.setCharacterEncoding("UTF-8");


        // 初期化
        SessionBean sessionBean = new SessionBean();
        DirectMessageBean bean = new DirectMessageBean();
        DirectMessageModel model = new DirectMessageModel();
        String direction = "/WEB-INF/jsp/directMessage.jsp";

        HttpSession session = req.getSession(false);

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

        sessionBean.setUserName("動けデブ");
        sessionBean.setUserNo("25");

        bean.setUserNo(sessionBean.getUserNo());

        // パラメータの取得
        String userId = (String) req.getParameter("userId");
        String password = (String) req.getParameter("password");

        bean.setUserId(userId);
        bean.setPassword(password);

        //--パラメータチェック--//

        //送信対象者番号
        String toSendUserNo = "";

        //--存在しなければエラー画面へ遷移--//
        if (toSendUserNo.equals(null)) {
            direction = "/error";
        }

        /*
         * 会話情報取得処理
         */

        //--セッション情報の会員番号と、送信対象者の会員番号を条件に会話情報取得する処理--//
        try {
            bean = model.authentication1(bean);
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
         * （1）パラメータチェック
         */

        //メッセージ画面で入力された情報を取得
        String message = req.getParameter("message");

        //--(1)-1 パラメータチェック--//
        //--(1)-2 チェックでエラーが発生した場合,エラーメッセージを設定して、//
        //メッセージ画面に遷移する。--//

        //未入力の場合
        if (message == null) {

            //エラーメッセージ設定
            bean.setErrorMessage("メッセージを入力してください");

            //メッセージ画面へ遷移

            //桁数チェック
        } else if (message.length() > 100) {

            //エラーメッセージ設定
            bean.setErrorMessage("メッセージが長すぎます");

            //メッセージ画面へ遷移

        }

        //--パラメータチェック完了--//
        bean.setMessage(message);

        /*
         * (2) 会話情報登録処理
         */

        //--(2)-1 セッション情報の会員番号を条件に、内容を登録する。--//
        try {

            //会話番号をカウントし次の番号へ
            bean = model.nextNumCheck(bean);

            //登録処理へ
            bean = model.authentication2(bean);

            //--(2)-2 エラーメッセージがセットされていた場合はエラー画面へ--//
            if (bean.getErrorMessage() != null) {
                direction = "/error";
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        //成功した場合、そのままメッセージ画面表示

        /**
        *
        *メッセージ削除処理
        *【処理概要】
        *メッセージ画面で削除ボタンを押したときに実行される処理。
        *ダイアログで論理削除確認後、会話情報を論理削除する。
        *
        */

        /*
         * 確認ダイアログ表示処理
         */

        //確認ダイアログをJSで表示する
        //OKが押下された場合以下の処理へ進む

        //String delete = req.getParameter("delete");

        if (req.getParameter("delete") != null) {

            /*
             * 会話情報論理削除処理
             */

            //セッション情報の会員番号を条件に会話情報を論理削除する
            try {
                bean = model.authentication3(bean);

                //レコードを論理削除できなかった場合
                if (bean.getErrorMessage() != null) {
                    direction = "/error";
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        req.getRequestDispatcher(direction).forward(req, res);
        return;
    }
}
