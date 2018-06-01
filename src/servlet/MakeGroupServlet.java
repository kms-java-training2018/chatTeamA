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

        //グループビーンの設定(jspに送る用の空ビーン)
        MakeGroupBean MakeGroupBean = new MakeGroupBean();
        //今から処理させるモデル
        MakeGroupModel groupMake = new MakeGroupModel();

        //セッションに値があるかどうか
        if (session.getAttribute("session") != null) {

            //グループ作成画面から来たかどうかの判断if
            if(req.getParameter("userNo") != null) {

                //groupcreateにsessionのbean引き継がせる
                groupMake.setMakeGroupBean((MakeGroupBean)session.getAttribute("MakeGroupBean"));


                //指定されたグループ名をもらう
                String name = new String(req.getParameter("groupName").getBytes("ISO-8859-1"));


                //入力チェックの返答
                int bytecheck = 0;
                bytecheck = MakeGroupBean.stringLengthCheck(name);
                if(bytecheck == 1) {
                    String message = "文字数が多すぎます";

                    req.setAttribute("error", message);
                    direction = "/WEB-INF/jsp/makeGroup.jsp";

                    req.getRequestDispatcher(direction).forward(req, res);
                }else {



                //チェック用
                System.out.println("受け取ったグループ名"+ name);




                //モデルにセット
                groupMake.setGroupName(name);


                //グループへ登録
                String sucsess =groupMake.MakeGroup();

                System.out.println(sucsess);

                //選択されたユーザーをreqからもらう

                String SelectNo[];

                SelectNo = req.getParameterValues("userNo");

                //抜き取った配列をMakeGroupBeanへ送ってグループ作成
                String message = groupMake.ResistGroup(SelectNo);

                System.out.println(message);

                direction = "/WEB-INF/jsp/main.jsp";

                }

            }else {


            // ログインデータ取得
            sessionBean = (SessionBean)session.getAttribute("session");
            String autherName = sessionBean.getUserName();



            //空のビーンにつめる
            MakeGroupBean = groupMake.authentication(autherName);

            //セッションにセットしてjspに送る
            session.setAttribute("MakeGroupBean", MakeGroupBean);

            direction = "/WEB-INF/jsp/makeGroup.jsp";
            }


        }
        req.getRequestDispatcher(direction).forward(req, res);


    }

}
