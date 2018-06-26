function ShowLength( str ) {

	if(str.length < 5 || str == "password"){
		level = 1;
	}else if(str.length >=5&&str.length < 10){
		level = 2;
	}else{
		level = 3;
	}

	if(str.match(/^[0-9]+$/)){
		level = 1;
	}else if(str.match(/^[a-z]+$/)){
		level = 1;
	}


	if(level == 1){
		document.getElementById("inputlength").innerHTML = "パスワード強度：弱い";
	}else if(level == 2){
		document.getElementById("inputlength").innerHTML = "パスワード強度：普通";
	}else{
		document.getElementById("inputlength").innerHTML = "パスワード強度：強い";
	}
}