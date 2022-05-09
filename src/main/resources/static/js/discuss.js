$(function () {
    $("#topBtn").click(setTop);
    $("#wonderfulBtn").click(setWonderful);
    $("#deleteBtn").click(setDelete);
});


function like(btn,entityType,entityId,entityUserId,postId) {
    $.post(
        CONTEXT_PATH+ "/like",   //送去响应的地址
        {"entityType":entityType,"entityId":entityId,"entityUserId":entityUserId,"postId":postId},   //需要处理的参数
        function (data) {  //处理返回的数据
            data = $.parseJSON(data) ;
            if(data.code==0){   //返回的code为0就表示成功
                //成功就需要动态更新页面的处理
                $(btn).children("i").text(data.likeCount);
                $(btn).children("b").text(data.likeStatus==1?"已赞":"赞");
            }else{
                alert(data.msg);
            }
        }
    );
}

//置顶方法
// 置顶
function setTop() {
    $.post(
        CONTEXT_PATH + "/discuss/top",
        {"id":$("#postId").val()},
        function(data) {
            data = $.parseJSON(data);
            if(data.code == 0) {
                $("#topBtn").attr("disabled", "disabled");
            } else {
                alert(data.msg);
            }
        }
    );
}

//加精
function setWonderful() {
    $.post(
        CONTEXT_PATH + "/discuss/wonderful",
        {"id":$("#postId").val()},
        function(data) {
            data = $.parseJSON(data);
            if(data.code==0){
                $("#wonderfulBtn").attr("disabled","disabled");
            }else{
                alert(data.msg);
            }
        }
    );
}

//删除
function setDelete() {
    $.post(
        CONTEXT_PATH + "/discuss/delete",
        {"id":$("#postId").val()},
        function(data) {
            data = $.parseJSON(data);
            if(data.code==0){
                location.href = CONTEXT_PATH + "/index";
            }else{
                alert(data.msg);
            }
        }
    );
}


