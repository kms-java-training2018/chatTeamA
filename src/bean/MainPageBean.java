package bean;

public class MainPageBean {
    /** 会員ID */
    private String userId;

    /** パスワード */
    private String password;

    /** 表示名 */
    private String userName;

    /** 会員番号 */
    private String userNo;

    /** グループ名 */
    private String groupName;

    /** エラーメッセージ */
    private String errorMessage;

    /** メッセージ */
    private String message;

    /** 会員数 */
    private int number;

    /** 会員番号カウント用 */
    private int countUN;

    /** グループ番号 */
    private String groupNo;

    /** エラーフラグ */
    private int errFlag;



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


    public String getGroupName() {
        return groupName;
    }
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }


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

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    public int getNumber() {
        return number;
    }
    public void setNumber(int number) {
        this.number = number;
    }

    public int getCountUN() {
        return countUN;
    }
    public void setCountUN(int countUN) {
        this.countUN = countUN;
    }

    public String getGroupNo() {
        return groupNo;
    }
    public void setGroupNo(String groupNo) {
        this.groupNo = groupNo;
    }

    public int getErrFlag() {
        return errFlag;
    }

    public void setErrFlag(int errFlag) {
        this.errFlag = errFlag;
    }

}
