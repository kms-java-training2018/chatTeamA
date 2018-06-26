package servlet;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.NewInsertBean;
import model.NewInsertModel;

public class NewInsertServlet extends HttpServlet{
	 /**
     * 初期表示
     */
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        // Beanの初期化
        NewInsertBean bean = new NewInsertBean();
        bean.setErrorMessage("");
        bean.setUserId("");
        bean.setPassword("");
        bean.setUserName("");

        req.setAttribute("newInsesrtBean", bean);
        req.getRequestDispatcher("/WEB-INF/jsp/newInsert.jsp").forward(req, res);
    }

    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

    	//文字コード設定
    	res.setContentType("text/html; charset=UTF-8");
    	req.setCharacterEncoding("UTF-8");

    	// 初期化
        NewInsertBean bean = new NewInsertBean();
        NewInsertModel model = new NewInsertModel();
        String direction = "/WEB-INF/jsp/newInsert.jsp";

        // パラメータの取得
        String userName = (String) req.getParameter("name");
        String userId = (String) req.getParameter("userId");
        String password1 = (String) req.getParameter("password1");
        String password2 = (String) req.getParameter("password2");
        Pattern p = Pattern.compile("^[0-9a-zA-Z]+$");
        Matcher mUserId = p.matcher(userId);

        Matcher mPassword = p.matcher(password1);

        // パラメータの判定
        if (userId.length() > 20 || password1.length() > 20 || !mUserId.find() || !mPassword.find()
        		|| userName.getBytes().length > 30 || password2.length() > 20
        		|| !password1.equals(password2)) {
            bean.setErrorMessage("入力された値は正しくありません");
        } else {
        	bean.setUserName(userName);
            bean.setUserId(userId);
            bean.setPassword(password1);

            // 認証処理
            try {
                bean = model.signUp(bean);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if("".equals(bean.getErrorMessage())) {
        	direction = "/WEB-INF/jsp/login.jsp";
        }

        req.setAttribute("newInsertBean", bean);
        req.getRequestDispatcher(direction).forward(req, res);
    }

}
