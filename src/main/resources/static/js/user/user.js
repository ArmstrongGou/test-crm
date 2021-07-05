layui.use(['form','jquery','jquery_cookie','table'], function () {
    var form = layui.form,
        table=layui.table,
        layer = layui.layer,
        $ = layui.jquery,
        $ = layui.jquery_cookie($);
    /**
     * 渲染列表
     */

    var  tableIns = table.render({
        elem: '#userList',
        url : ctx+'/user/list',
        cellMinWidth : 95,
        page : true,
        height : "full-125",
        limits : [10,15,20,25],
        limit : 10,
        toolbar: "#toolbarDemo",
        id : "userListTable",
        cols : [[
            {type: "checkbox", fixed:"left", width:50},
            {field: "id", title:'编号',fixed:"true", width:80},
            {field: 'userName', title: '用户名', minWidth:50, align:"center"},
            {field: 'email', title: '用户邮箱', minWidth:100, align:'center'},
            {field: 'phone', title: '用户电话', minWidth:100, align:'center'},
            {field: 'trueName', title: '真实姓名', align:'center'},
            {field: 'createDate', title: '创建时间', align:'center',minWidth:150},
            {field: 'updateDate', title: '更新时间', align:'center',minWidth:150},
            {title: '操作', minWidth:150, templet:'#userListBar',fixed:"right",align:"center"}
        ]]
    });
    /*
    * 绑定搜索按钮的点击事件
    * */
    $(".search_btn").click(function (){
        tableIns.reload({
            where:{
                userName:$("input[name=userName]").val(),
                email:$("input[name=email]").val(),
                phone:$("input[name=phone]").val()
            },
            page:{
                curr:1
            }
        });

    });
    /**
     * 头部⼯具栏事件
     */
    table.on("toolbar(users)", function (obj) {
        table.checkStatus(obj.config.id);
        switch (obj.event) {
            case "add":
                openAndOrUpdateUserDialog();
                break;
        }
    });
    /*
    * 行监听事件
    * */
    table.on("tool(users)", function (obj) {
        var layEvent = obj.event;
        //监听编辑事件
        if (layEvent==="edit") {
            openAndOrUpdateUserDialog(obj.data.id);
            return;
        }
    });

    /*
    * 头部工具栏事件
    * */
    table.on("toolbar(users)", function (obj) {
      var checkStatus=  table.checkStatus(obj.config.id);
        switch (obj.event) {
            case "add":
                //openAndOrUpdateUserDialog
                openAndOrUpdateUserDialog();
                break;
            case "del":
                deleteUser(checkStatus.data);
                break;
        }
    });
    /**
     * 批量删除⽤户
     * @param datas
     */
    function deleteUser(datas) {
        if(datas.length == 0){
            layer.msg("请选择删除记录!", {icon: 5});
            return;
        }
        layer.confirm('确定删除选中的⽤户记录？', {
            btn: ['确定','取消'] //按钮
        }, function(index){
            layer.close(index);
            var ids= "ids=";
            for(var i=0;i<datas.length;i++){
                if(i<datas.length-1){
                    ids=ids+datas[i].id+"&ids=";
                }else {
                    ids=ids+datas[i].id
                }
            }
            $.ajax({
                type:"post",
                url:ctx+"/user/delete",
                data:ids,
                dataType:"json",
                success: function (data) {
                    if (data.code == 200) {
                        tableIns.reload();
                    }else {
                        layer.msg(data.msg,{icon:5});
                    }
                }
            })
          /*
          * 行监听事件
          * */
            table.on("tool(users)", function(obj){
                var layEvent = obj.event;
            // 监听编辑事件
                if(layEvent === "edit") {
                    //openAndOrUpdateUserDialog
                    openAndOrUpdateUserDialog(obj.data.id);
                    return;
                } else if(layEvent === "del") {
// 监听删除事件
                    layer.confirm('确定删除当前⽤户？', {icon: 3, title: "⽤户管理"}, function
                        (index) {
                        $.post(ctx + "/user/delete",{ids:obj.data.id},function (data) {
                            if(data.code==200){
                                layer.msg("操作成功！");
                                tableIns.reload();
                            }else{
                                layer.msg(data.msg, {icon: 5});
                            }
                        });
                    });
                }
            });
        });
    }
    /*
       *打开用户添加或更新对话
       * */
    function openAndOrUpdateUserDialog(userId) {
        var url=ctx+"/user/addOrUpdateUserPage";
        var title="用户管理-用户添加";
        if (userId) {
            url=url+"?id="+userId;
            title="用户管理-用户更新"
        }
        layui.layer.open({
            title : title,
            type : 2,
            area:["650px","400px"],
            maxmin:true,
            content : url
        });
    }
});