package bean;

public class SessionBean {
    /** 会員番号 */
    private String userNo;

    /** 表示名 */
    private String userName;

    /** 自己紹介文 */
    private String myPageText;

    /** グループ番号 */
    private String groupNo;

    /** 送信対象者番号 */
    private String toSendUserNo;

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

    public String getGroupNo() {
        return groupNo;
    }

    public void setGroupNo(String groupNo) {
        this.groupNo = groupNo;
    }

	public String getToSendUserNo() {
		return toSendUserNo;
	}

	public void setToSendUserNo(String toSendUserNo) {
		this.toSendUserNo = toSendUserNo;
	}

}
