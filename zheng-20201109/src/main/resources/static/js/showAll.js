$(function() {
    $("#start").click(function () {
        console.log("开启定时更新");
        time=setInterval(function(){
            $.get("[[@{/findAllJson}]]",function (res) {
                //遍历
                $.each(res,function(index,file){
                    $("#"+file.id).text(file.downCounts);
                })
            });
        },3000);//3秒
    });

    $("#stop").click(function () {
        console.log("关闭定时更新");
        clearInterval(time);
    });
});