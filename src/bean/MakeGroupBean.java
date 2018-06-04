package bean;

import java.util.ArrayList;

public class MakeGroupBean {
    /** 会員名 */
    private ArrayList<String> userNameList = new ArrayList<String>();

    /** 会員番号 */
    private ArrayList<String> userNumList = new ArrayList<String>();

    /** エラーメッセージ */
    private String errorMessage;

    /**	グループ作成者のデータ */
    private String autherUserName;
    private String autherUserNo;



    //作成者のユーザー名を設定、ArrayListから検索し、そのindexからuserNameとuserNoを新しいListに追加
    public void setAuther(String userName) {
        this.autherUserName = userName;
        int autherIndex = userNameList.indexOf(autherUserName);
        //usernameが一応あるかでif
        if (autherIndex >= 0) {

            //作成者の名前から出したindexから、作成者のNoを全会員Noの値を検索してautherNoリストに設定
            autherUserNo = userNumList.get(autherIndex);

            //作成者のデータを全会員一覧から削除
            userNameList.remove(autherIndex);
            userNumList.remove(autherIndex);
        }
    }

    //グループ名入力チェック
        public int stringLengthCheck(String input) {
            //返すメッセージを設定
            int judgeByte =0;

            // 何バイト分の長さであるかを取得
            int length = input.getBytes().length;
            // 最大バイト数の設定
            int max = 30;

            if ((int) length > max) { // 最大文字数よりも多かった場合
                judgeByte = 1;
                return judgeByte;
            }
            return judgeByte; // 許容内であった場合
        }



    //作成者の取得
    public String getAutherName() {
        return autherUserName;
    }

    //作成者Noの取得
    public String getAutherNo() {
        return autherUserNo;
    }


    //ユーザーの取得
    public ArrayList<String> getUserName() {
        return userNameList;
    }

    //ユーザー名の設定
    public void setUserName(String userName) {
        userNameList.add(userName);
    }

    //会員番号の取得
    public ArrayList<String> getUserNo() {
        return userNumList;
    }

    //会員番号の設定
    public void setUserNo(String userNo) {
        userNumList.add(userNo);
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}