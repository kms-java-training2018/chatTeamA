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
import model.MakeGroupModel;

public class GroupMessageServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {

		String direction = "/WEB-INF/jsp/groupMessage.jsp";

		GroupMessageBean bean = new GroupMessageBean();
		SessionBean sessionBean = new SessionBean();
		GroupMessageModel model = new GroupMessageModel();
		ArrayList<GroupMessageBean> list = new ArrayList<GroupMessageBean>();
		ArrayList<String> groupMemberList = new ArrayList<String>();

		HttpSession session = req.getSession();
		sessionBean = (SessionBean) session.getAttribute("session");

		if (sessionBean == null) {
			req.getRequestDispatcher("/error").forward(req, res);
			return;
		}

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

		// グループメッセージ取得
		try {
			list = model.messageCheck(bean, sessionBean.getUserNo());
		} catch (Exception e) {
			e.printStackTrace();
		}

		// グループ名取得
		try {
			bean = model.getGroupName(bean);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// グループメンバー取得
		try {
			groupMemberList = model.getGroupMember(bean.getGroupNo());
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (bean.isErrFlag()) {
			direction = "/error";
		}

		session.setAttribute("session", sessionBean);

		//jspに飛ばす
		req.setAttribute("list", list);
		req.setAttribute("group_name", bean.getGroupName());
		req.setAttribute("groupMemberList", groupMemberList);
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
		ArrayList<String> groupMemberList = new ArrayList<String>();

		HttpSession session = req.getSession();

		sessionBean = (SessionBean) session.getAttribute("session");

		bean.setUserNo(sessionBean.getUserNo());
		bean.setUserName(sessionBean.getUserName());
		bean.setGroupNo(sessionBean.getGroupNo());

		String errorMessage = "";

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
			//String message = req.getParameter("message");
			String message = new String(req.getParameter("message").getBytes("ISO-8859-1"));
			//req.getParameter("userId").getBytes("ISO-8859-1"));
			//コメント変換
			message = MakeGroupModel.commentCheck(message);

			bean.setMessage(message);
			if (message.getBytes().length > 100) {
				errorMessage = "メッセージは100桁以内にしてください。";
			} else if (!MakeGroupModel.spaceCheck(message)) {
				errorMessage = "メッセージを入力してください。";
			} else {
				// 次の数字の確認
				try {
					bean = model.nextNumCheck(bean);
				} catch (Exception e) {
					e.printStackTrace();
				}

				// メッセージ送信
				try {
					bean = model.sendMessage(bean);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		// グループメッセージ取得
		try {
			list = model.messageCheck(bean, sessionBean.getUserNo());
		} catch (Exception e) {
			e.printStackTrace();
		}

		// グループ名取得
		try {
			bean = model.getGroupName(bean);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// グループメンバー取得
		try {
			groupMemberList = model.getGroupMember(bean.getGroupNo());
		} catch (Exception e) {
			e.printStackTrace();
		}

		//jspに飛ばす
		req.setAttribute("list", list);
		req.setAttribute("groupMemberList", groupMemberList);
		req.setAttribute("errorMessage", errorMessage);
		req.setAttribute("group_name", bean.getGroupName());

		// 退会ボタンが押されたとき
		if (req.getParameter("escape") != null) {
			// グループ作成者取得
			try {
				bean = model.registCheck(bean, sessionBean.getUserNo());
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (!bean.isErrFlag()) {
				try {
					bean = model.escapeGroup(bean);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			direction = "/main";
		}

		// グループ消去ボタンが押されたとき
		if (req.getParameter("deleteGroup") != null) {
			// グループ作成者取得
			try {
				bean = model.registCheck(bean, sessionBean.getUserNo());
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (bean.isErrFlag()) {
				try {
					bean = model.deleteGroup(bean);
				} catch (Exception e) {
					e.printStackTrace();
				}
				bean.setErrFlag(false);
				direction = "/main";
			}else {
				bean.setErrFlag(true);
			}
		}

		if (bean.isErrFlag()) {
			bean.setErrFlag(false);

			direction = "/error";
		}

		req.getRequestDispatcher(direction).forward(req, res);
		return;
	}
}