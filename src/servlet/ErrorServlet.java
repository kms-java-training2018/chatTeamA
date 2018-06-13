package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErrorServlet extends HttpServlet{
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
    	HttpSession session = req.getSession();

    	//セッション切断
    	session.invalidate();

    	//エラー画面へ
        req.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(req, res);
    }

}
