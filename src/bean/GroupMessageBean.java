package bean;

public class GroupMessageBean {
    /** 会員ID */
    private String userId;

    /** パスワード */
    private String password;

    /** 表示名 */
    private String userName;

    /** 会員番号 */
    private String userNo;

    /** グループ番号 */
    private String groupNo;

    /** エラーフラグ */
    private boolean errFlag;

    /** メッセージ番号 */
    private String messageNo;

    /** メッセージ*/
    private String message;

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

    public String getGroupNo() {
        return groupNo;
    }

    public void setGroupNo(String groupNo) {
        this.groupNo = groupNo;
    }

    public boolean isErrFlag() {
        return errFlag;
    }

    public void setErrFlag(boolean errFlag) {
        this.errFlag = errFlag;
    }

    public String getMessageNo() {
        return messageNo;
    }

    public void setMessageNo(String messageNo) {
        this.messageNo = messageNo;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
