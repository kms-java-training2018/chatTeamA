package bean;

public class DirectMessageBean {
    /** 会員ID */
    private String userId;

    /** パスワード */
    private String password;

    /** エラーメッセージ */
    private String errorMessage;

    /** 表示名 */
    private String userName;

    /** 送ったユーザー名 */
    private String sendUserName;

    /** 会員番号 */
    private String userNo;

    /** 送信対象者番号 */
    private String toSendUserNo;

    /** 送信者番号 */
    private String sendUserNo;

    /**メッセージ内容 */
    private String message;

    /** 次の数字 */
    private String messageNo;

    /** 自分のメッセージかどうか */
    private boolean myMessageFlag;

    /** 自分の名前かどうか */
    private boolean myNameFlag;

    /** 削除する会話番号 */
    private String deleteMessageNo;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public String getToSendUserNo() {
        return toSendUserNo;
    }

    public void setToSendUserNo(String toSendUserNo) {
        this.toSendUserNo = toSendUserNo;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessageNo() {
        return messageNo;
    }

    public void setMessageNo(String messageNo) {
        this.messageNo = messageNo;
    }

    public boolean isMyMessageFlag() {
        return myMessageFlag;
    }

    public void setMyMessageFlag(boolean myMessageFlag) {
        this.myMessageFlag = myMessageFlag;
    }

    public String getDeleteMessageNo() {
        return deleteMessageNo;
    }

    public void setDeleteMessageNo(String deleteMessageNo) {
        this.deleteMessageNo = deleteMessageNo;
    }

	public String getSendUserName() {
		return sendUserName;
	}

	public void setSendUserName(String sendUserName) {
		this.sendUserName = sendUserName;
	}

	public String getSendUserNo() {
		return sendUserNo;
	}

	public void setSendUserNo(String sendUserNo) {
		this.sendUserNo = sendUserNo;
	}

	public boolean isMyNameFlag() {
		return myNameFlag;
	}

	public void setMyNameFlag(boolean myNameFlag) {
		this.myNameFlag = myNameFlag;
	}

}


