function confirm() {
	if (window.confirm()) {
		return false;
	}
	return true;
};

function getScrollTop(){
	 $("#result").text($(window).scrollTop() + $(window).height() + 'px');
	}
	$(window).on("load scroll resize", getScrollTop);
