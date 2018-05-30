package bean;

public class ShowProfileBean {

    /** 会員番号 */
    private String userNo;

    /** エラーメッセージ */
    private String errorMessage;

    /** 表示名 */
    private String userName;

    /** 自己紹介文 */
    private String myPageText;


    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
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

    public String getMyPageText() {
        return myPageText;
    }

    public void setMyPageText(String myPageText) {
        this.myPageText = myPageText;
    }

}

