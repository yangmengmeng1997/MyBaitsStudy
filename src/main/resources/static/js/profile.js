$(function(){
	$(".follow-btn").click(follow);
});

function follow() {
	var btn = this;
	if($(btn).hasClass("btn-info")) {
		//发送异步请求进行关注
		$.post(
			CONTEXT_PATH+"/fellow",
			{"entityType":3,"entityId":$(btn).prev().val()} , //获取当前组件的前一个组件的值
			function (data) {
				data = $.parseJSON(data);
				if(data.code==0){
					window.location.reload();
				}else{
					alert(data.msg);
				}
			}
		);

		// 关注TA
		//$(btn).text("已关注").removeClass("btn-info").addClass("btn-secondary");
	} else {
		// 取消关注
		//发送异步请求进行关注
		$.post(
			CONTEXT_PATH+"/unfellow",
			{"entityType":3,"entityId":$(btn).prev().val()} , //获取当前组件的前一个组件的值
			function (data) {
				data = $.parseJSON(data);
				if(data.code==0){
					window.location.reload();
				}else{
					alert(data.msg);
				}
			}
		);
		//$(btn).text("关注TA").removeClass("btn-secondary").addClass("btn-info");
	}
}