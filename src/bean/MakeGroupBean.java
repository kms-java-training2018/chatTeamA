package bean;

public class MakeGroupBean {

	/** エラーメッセージ */
	private String errorMessage="";

	/** エラーフラグ */
	private int errorFlag;
	/** メソッド */
	
	public String getErrorMessage() {
		return errorMessage;
	}
	
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	public int getErrorFlag() {
		return errorFlag;
	}
	
	public void setErrorFlag(int errorFlag) {
		this.errorFlag = errorFlag;
	}
}