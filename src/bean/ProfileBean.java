package bean;

public class ProfileBean {

    /** 会員番号 */
    private String userNo;

    /** 表示名 */
    private String userName;

    /** 自己紹介文 */
    private String myPageText;

	/** エラーメッセージ */
	private String errorMessage="";

	/** エラーフラグ */
	private boolean errorFlag;

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMyPageText() {
        return myPageText;
    }

    public void setMyPageText(String myPageText) {
        this.myPageText = myPageText;
    }
	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public boolean getErrorFlag() {
		return errorFlag;
	}

	public void setErrorFlag(boolean errorFlag) {
		this.errorFlag = errorFlag;
	}
}


