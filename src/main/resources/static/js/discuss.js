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