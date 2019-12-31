/**
 * 管理初始化
 */
var GxswJcsjInfo = {
    id: "GxswJcsjInfoTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1,
    pageSize:20,
    pageNumber:1
};
layui.use(['layer','bootstrap_table_edit','Hussar', 'HussarAjax','upload','form'], function(){
	var layer = layui.layer
	    ,table = layui.table
        ,Hussar = layui.Hussar
	    ,$ = layui.jquery
        ,upload =layui.upload
	    ,$ajax = layui.HussarAjax
        ,form=layui.form;



    var village = '';
    var town = '';

/**
 * 初始化表格的列
 */

GxswJcsjInfo.initColumn = function () {
    return [
    /*    {checkbox:true, halign:'center',align:"center",width: 50},*/
        {title: '序号',align:"center" ,halign:'center',width:50 ,formatter: function (value, row, index) {
            var para =  (GxswJcsjInfo.pageNumber-1)*GxswJcsjInfo.pageSize +1 +index ;
                return "<div class='textOver'  title='" + para + "'>" + para + "</div>";
        }},
            {title: '身份证号', field: 'identity_card', align: 'center',halign:'center',
                formatter : function(params) {
                    if(params != null && params !== ""){
                        return "<div class='textOver'  title='" + params + "'>" + params + "</div>";
                    }else{
                        return "<div class='textOver'>-</div>";
                    }
                }},
            {title: '纳税人名称', field: 'taxpayer_name', align: 'center',halign:'center',
                formatter : function(params) {
                    if(params != null && params !== ""){
                        return "<div class='textOver'  title='" + params + "'>" + params + "</div>";
                    }else{
                        return "<div class='textOver'>-</div>";
                    }
                }},
            {title: '征收项目', field: 'taxpayer_item', align: 'center',halign:'center',
                formatter : function(params) {
                    if(params != null && params !== ""){
                        return "<div class='textOver'  title='" + params + "'>" + params + "</div>";
                    }else{
                        return "<div class='textOver'>-</div>";
                    }
                }},
            {title: '实缴金额', field: 'taxpayer_money', align: 'center',halign:'center',
                formatter : function(params) {
                    if(params != null && params !== ""){
                        return "<div class='textOver'  title='" + params + "'>" + params + "</div>";
                    }else{
                        return "<div class='textOver'>-</div>";
                    }
                }},
            {title: '税款所属期起', field: 'start_time', align: 'center',halign:'center',
                formatter : function(params) {
                    if(params != null && params !== ""){
                        return "<div class='textOver'  title='" + params + "'>" + params + "</div>";
                    }else{
                        return "<div class='textOver'>-</div>";
                    }
                }},
            {title: '税款所属期止', field: 'end_time', align: 'center',halign:'center',
                formatter : function(params) {
                    if(params != null && params !== ""){
                        return "<div class='textOver'  title='" + params + "'>" + params + "</div>";
                    }else{
                        return "<div class='textOver'>-</div>";
                    }
                }},
            {title: '征收子目', field: 'taxpayer_type', align: 'center',halign:'center',
                formatter : function(params) {
                    if(params != null && params !== ""){
                        return "<div class='textOver'  title='" + params + "'>" + params + "</div>";
                    }else{
                        return "<div class='textOver'>-</div>";
                    }
                }},
            {title: '街道乡镇', field: 'town', align: 'center',halign:'center',
                formatter : function(params) {
                    if(params != null && params !== ""){
                        return "<div class='textOver'  title='" + params + "'>" + params + "</div>";
                    }else{
                        return "<div class='textOver'>-</div>";
                    }
                }},
            {title: '社区/村组', field: 'village', align: 'center',halign:'center',
                formatter : function(params) {
                    if(params != null && params !== ""){
                        return "<div class='textOver'  title='" + params + "'>" + params + "</div>";
                    }else{
                        return "<div class='textOver'>-</div>";
                    }
                }}

    ];
};

/**
 * 检查是否选中
 */
GxswJcsjInfo.check = function () {
    var selected = $('#GxswJcsjInfoTable').bootstrapTable('getSelections');
    if(selected.length == 0){
        Hussar.info("请先选中表格中的某一记录！");
        return false;
    }else{
        GxswJcsjInfo.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加
 */
GxswJcsjInfo.openAddGxswJcsjInfo = function () {
    var index = layer.open({
        type: 2,
        title: '添加',
        area: ['400px', '420px'], //宽高
        fix: false, //不固定
        maxmin: false,
        content: Hussar.ctxPath + '/gxswJcsjInfo/gxswJcsjInfo_add'
    });
    this.layerIndex = index;
};

/**
 * 点击修改
 */
GxswJcsjInfo.openGxswJcsjInfoDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '详情',
            area: ['400px', '420px'], //宽高
            fix: false, //不固定
            maxmin: false,
            content: Hussar.ctxPath + '/gxswJcsjInfo/gxswJcsjInfo_update/' +  GxswJcsjInfo.seItem.identityCard
                });
        this.layerIndex = index;
    }
};

/**
 * 删除
 */
GxswJcsjInfo.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Hussar.ctxPath + "/gxswJcsjInfo/delete", function (data) {
            Hussar.success("删除成功!");
            $('#GxswJcsjInfoTable').bootstrapTable('refresh');
        }, function (data) {
            Hussar.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("gxswJcsjInfoId", GxswJcsjInfo.seItem.identityCard);
        ajax.start();
    }
};
    /**
     * 原始数据上传
     */
    upload.render({
        elem: '#uploadjcsj' //绑定按钮
        ,url: Hussar.ctxPath +'/gxswJcsjInfo/uploadOriginalData' //上传的地址
        ,accept: 'file' //普通文件
        ,before:function () {
            //打开加载框
            /*layer.load(1);*/
            layer.msg('正在导入，请稍后...', {
                icon: 16
                ,shade: 0.01
            });
        }
        ,done: function(res){
           /* layer.closeAll("loading");*/
            layer.msg("成功导入"+res.count+"条数据! \n共耗时"+
                res.time/1000+"秒!",{icon: 6},5000);
            infoTable();
        }
    });

/*每次点击菜单加载一遍*/
$(function () {
    infoTable();
});


    //查询按钮
    $('#submit').bind('click',function(){
        infoTable();

    });
    //获取下拉框的数据

    /*监听获取所得到的的值*/
    form.on('select(village)', function (data) {
        console.log(data.value)
        village = data.value;
    });
   /*获取到值加监听 所有的村*/
    form.on('select(town)', function (data) {
        town = data.value;

        $.ajax({
            async: false,
            type: "post",
            traditional : true,// 这使json格式的字符不会被转码
            contentType:'application/x-www-form-urlencoded',
            url:Hussar.ctxPath+"/gxswJcsjInfo/getAllVillage",
            data: {
                town:town
            },
            dataType: "json",
            success:function(data){
                $('#allVillage').empty();/*清空*/
                $('#allVillage').append(new Option("",""));/*拼空白*/
                $.each(data, function (index, item) {
                    $('#allVillage').append(new Option(item.village, item.village));// 下拉菜单里添加元素
                });
                //重新渲染
                layui.form.render("select");
            },
            error:function(data){
            }
        });
    })

    /*重置*/
   var revert = document.getElementById("revert");
    revert.onclick = function() {
        /*文本框重置*/
        document.getElementById("idcard").value="";
        document.getElementById("username").value="";
        /*下拉框重置*/
            $("#town").val("");
            $("#allVillage").val("");
            form.render();
            village='';
            town='';
        infoTable();
    }




    function infoTable() {
        var idcard =$("#idcard").val();
        var username =$("#username").val();
        var h = $(window).height() - 100;
        var defaultColumns =  GxswJcsjInfo.initColumn();
        $("#GxswJcsjInfoTable").bootstrapTable('destroy');
        $('#GxswJcsjInfoTable').bootstrapTable({
            dataType: "json",
            contentType: "application/x-www-form-urlencoded",
            url: Hussar.ctxPath + '/gxswJcsjInfo/getList',
            editable: false,//开启编辑模式
            striped: true,//隔行变色
            cache: false, // 是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
            sortable: false, // 是否启用排序
            pagination: true, // 是否显示分页（*）
            sortOrder: "desc", // 排序方式
            clickToSelect: true,//点击选中行
            pageNumber: 1, // 初始化加载第一页，默认第一页
            pageSize: 20, // 每页的记录行数（*）
            pageList: [5, 10, 15, 20, 50, 100], // 可供选择的每页的行数（*）
            showHeader: true,
            columns: defaultColumns,
            minimumCountColumns: 2, // 最少允许的列数
            uniqueId: "id", // 每一行的唯一标识，一般为主键列
            height: h,
            queryParams: function (param) {
                var temp = {
                    pageSize: this.pageSize, // 页面大小
                    pageNumber: this.pageNumber,// 页码
                    town: town,
                    village: village,
                    idcard:idcard,
                    username:username

                };// 传递参数（*）
                return temp;
            },
            sidePagination: "server",
            responseHandler: function (res) {
                var temp = {
                    total: res.total,
                    rows: res.rows
                };

                return temp;
            },
            onLoadSuccess: function () {

                layer.closeAll("loading");
            }
        });
    }

});