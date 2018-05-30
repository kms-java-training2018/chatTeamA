package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.MakeGroupBean;
import model.MakeGroupModel;

public class MakeGroupServlet extends HttpServlet {

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {

        MakeGroupBean bean = new MakeGroupBean();
        bean.setErrorMessage("");
        bean.setUserName("");
        bean.setUserNo("");

        req.setAttribute("makeGroupBean", bean);
        req.getRequestDispatcher("/WEB-INF/jsp/makeGroup.jsp").forward(req, res);
    }

    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        MakeGroupBean bean = new MakeGroupBean();
        MakeGroupModel model = new MakeGroupModel();









        req.getRequestDispatcher("/WEB-INF/jsp/makeGroup.jsp").forward(req, res);
    }

}
