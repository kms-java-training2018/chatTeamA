package bean;

public class GroupListBean {
	/**
	 * フィールド
	 */
	/**	表示するグループNo */
	private int groupNo;

	/**	表示するグループ名 */
	private String groupName;

	/**	グループの最新メッセージ */
	private String groupMessage;

	/** グループに参加していない場合の文言 */
	private String groupNullMessage = "";

	/** エラーフラグ */
	private int errorFlag;

	public int getGroupNo() {
		return groupNo;
	}

	public void setGroupNo(int groupNo) {
		this.groupNo = groupNo;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getGroupMessage() {
		return groupMessage;
	}

	public void setGroupMessage(String groupMessage) {
		this.groupMessage = groupMessage;
	}


	public String getGroupNullMessage() {
		return groupNullMessage;
	}

	public void setGroupNullMessage(String groupNullMessage) {
		this.groupNullMessage = groupNullMessage;
	}

	public int getErrorFlag() {
		return errorFlag;
	}

	public void setErrorFlag(int errorFlag) {
		this.errorFlag = errorFlag;
	}

}