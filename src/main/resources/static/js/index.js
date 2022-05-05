$(function(){
	$("#publishBtn").click(publish);
});

function publish() {
	$("#publishModal").modal("hide");

	//发送请求头之前，需要设置请求消息
	// var token = $("meta[name='_csrf']").attr("content");
	// var header = $("meta[name='_csrf_header']").attr("content");
	// $(document).ajaxSend(function (e,xhr,options) {
	// 	xhr.setRequestHeader(header,token);
	// });

	//获取标题和内容通过标题和正文发布
	var title = $("#recipient-name").val();
	var content = $("#message-text").val();
	//发送异步请求
	$.post(
		CONTEXT_PATH + "/discuss/addPost",
		{"title":title,"content":content},
		function (data) {
			data = $.parseJSON(data);
			//在提示框显示消息
			$("hintBody").text(data.msg);
			//显示提示框
			$("#hintModal").modal("show");
			//两秒后自动隐藏
			setTimeout(function(){
				$("#hintModal").modal("hide");
				//刷新页面
				if(data.code==0){
					window.location.reload();
				}
			}, 2000);
		}
	);
}