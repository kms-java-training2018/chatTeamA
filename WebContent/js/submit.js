//判定用フラグ
var isSubmit = false;

function doubleSubmit(){
  if(isSubmit){
    //フラグがtrueならアラートを表示してsubmitしない
    alert("処理中です。");
    return false;
  }else{
    //フラグがtrueでなければ、フラグをtrueにした上でsubmitする
    isSubmit = true;
    return true;
  }
}