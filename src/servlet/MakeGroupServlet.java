package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.LoginBean;
import bean.MakeGroupBean;
import bean.SessionBean;
import model.MakeGroupModel;

public class MakeGroupServlet extends HttpServlet {

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {

        req.getRequestDispatcher("/WEB-INF/jsp/makeGroup.jsp").forward(req, res);
        LoginBean bean = new LoginBean();
        bean.setErrorMessage("");
        bean.setUserId("");
        bean.setPassword("");

        req.setAttribute("loginBean", bean);
        req.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(req, res);
    }

    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {

        String direction = "/WEB-INF/jsp/login.jsp";

        //セッション設定
        HttpSession session = req.getSession();
        SessionBean sessionBean = new SessionBean();

        //グループビーン、モデルの設定
        MakeGroupBean makeGroupBean = new MakeGroupBean();
        MakeGroupModel groupMake = new MakeGroupModel();

        //セッション判定
        if (session.getAttribute("session") != null) {

            //正しい画面遷移か
            if (req.getParameter("userNo") != null) {

                //groupmakeにsessionのbean引き継がせる
                groupMake.setMakeGroupBean((MakeGroupBean) session.getAttribute("MakeGroupBean"));

                //グループ名をもらう
                String name = new String(req.getParameter("groupName").getBytes("ISO-8859-1"));

                //入力チェック
                int bytecheck = 0;
                bytecheck = makeGroupBean.stringLengthCheck(name);
                if (bytecheck == 1) {
                    String message = "文字数がオーバーしています";

                    req.setAttribute("error", message);
                    direction = "/WEB-INF/jsp/makeGroup.jsp";

                    req.getRequestDispatcher(direction).forward(req, res);
                } else {

                    //モデルにセット
                    groupMake.setGroupName(name);

                    //グループへ登録
                    String success = groupMake.MakeGroup();
                    System.out.println(success);



                    //選択されたユーザーをreqからもらう

                    String SelectNo[];

                    SelectNo = req.getParameterValues("userNo");

                    //抜き取った配列をMakeGroupBeanへ送ってグループ作成
                    String message = groupMake.ResistGroup(SelectNo);


                    direction = "/WEB-INF/jsp/main.jsp";

                }

            } else {

                // ログインデータ取得
                sessionBean = (SessionBean) session.getAttribute("session");
                String autherName = sessionBean.getUserName();

                //空のビーンにつめる
                makeGroupBean = groupMake.authentication(autherName);

                //jspに送る
                session.setAttribute("MakeGroupBean", makeGroupBean);
                direction = "/WEB-INF/jsp/makeGroup.jsp";
            }

        }
        req.getRequestDispatcher(direction).forward(req, res);

    }

}
