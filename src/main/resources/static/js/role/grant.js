var zTreeObj;
$(function(){
    loadModuleInfo();
});


function loadModuleInfo(){
    //发送ajax
    $.ajax({
        type:"post",
        url:ctx+"/role/queryModule",
        dataType:"json",
        data:{},
        success:function (data){
            // zTree 的参数配置，深入使用请参考 API 文档（setting 配置详解）
            var setting = {
                data: {
                    simpleData: {
                        enable: true
                    }
                },
                view:{
                    showLine: false
                    // showIcon: false
                },
                check: {
                    enable: true,
                    chkboxType: { "Y": "ps", "N": "ps" }
                }
            };
            var zNodes =data;
            zTreeObj=$.fn.zTree.init($("#test1"), setting, zNodes);
        }
    });

}