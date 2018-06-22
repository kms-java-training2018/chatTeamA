function confirm() {
	if (window.confirm()) {
		return false;
	}
	return true;
};

flag = false;
function sendFlag(){
	if(flag){
		alert("送信済み");
		return false;
	}

	flag = true;
	return true;
}
