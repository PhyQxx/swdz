
var Evt = {
    callback:[],
    sendEvent:function (key,value) {
        var uuid = this.uuid(8,16);
        localStorage.setItem('evt_'+key,uuid+'_'+value);
    },
    addEvent:function (key,callback) {
        if(this.callback.length>0){
            var b = false;
            for(var i in this.callback){
                var item=this.callback[i];
                if(item.key===key){
                    this.callback[i].callback=callback;
                    b=true;
                }
            }
            if(!b){
                var data = {key:key,callback:callback};
                this.callback.push(data)
            }
        }else {
            var data = {key:key,callback:callback};
            this.callback.push(data)
        }
    },
    uuid:function (len,radix) {
        var chars = '0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqistuvwxyz'.split('');
        var uuid=[],i;
        radix=radix||chars.length;
        if(len){
            for(i=0;i<len;i++) uuid[i]=chars[0 | Math.random()*radix];
        }else{
            var r;
            uuid[8]=uuid[13]=uuid[18]=uuid[23]='-';
            uuid[14]='4';
            for (i=0;i<36;i++){
                if(uuid[i]){
                    r=0 | Math.random()*16;
                    uuid[i]=chars[(i===19)?(r & 0x3)|0x3:r];
                }
            }
        }
        return uuid.join('');
    }
};
window.addEventListener("storage",function (event) {
    if(event.key.indexOf('evt_')<0)return;
    var key = event.key.split('_');
    if(Evt.callback.length>0){
        for (var j in Evt.callback){
            if(key[1]===Evt.callback[j].key){
                var start = event.newValue.indexOf('_')+1;
                var end= event.newValue.length;
                var value=event.newValue.substring(start,end);
                Evt.callback[j].callback(Evt.callback[j].key,value);
                break;
            }
        }
    }
});


var DiffStepInfo = {
    id: "infoTable",	//表格id
    seItem: null,	//选中的条目
    table: null,
    layerIndex: -1,
    pageSize:10,
    pageNumber:1
};

layui.use(['layer','jquery','bootstrap_table_edit','Hussar','form','element','upload'], function() {
    var layer = layui.layer
        , Hussar = layui.Hussar
        , upload = layui.upload
        , form = layui.form
        , table = layui.table
        , element = layui.element
        , $ = layui.jquery;

    // //小计按钮
    // var subtotal = document.getElementById("subtotal");
    // subtotal.onclick = function() {
    //     layer.msg(subtotal.innerText,{icon: 1, area:['480px', '66px']});
    // }

    //表格列
    function expertScoreColumn() {
        return [
            {title: '身份证件号码', field: 'credit_code', align: 'center',width:'15%' ,halign:'center',
            formatter : function(params) {
                if(params != null && params !== ""){
                    return "<div class='textOver'  title='" + params + "'>" + params + "</div>";
                }else{
                    return "<div class='textOver'>-</div>";
                }
            },
                footerFormatter: function(value){
                    return "合 计";
                }
        },
            {title: '姓名', field: 'taxpayer_name', align: 'center',width:'10%' ,halign:'center',
                formatter : function(params) {
                    if(params != null && params !== ""){
                        return "<div class='textOver'  title='" + params + "'>" + params + "</div>";
                    }else{
                        return "<div class='textOver'>-</div>";
                    }
                }
            },
            {title: '征收子目', field: 'specific_item', align: 'center',width:'15%' ,halign:'center',
                formatter : function(params) {
                    if(params != null && params !== ""){
                        return "<div class='textOver' title='" + params + "'>" + params + "</div>";
                    }else{
                        return "<div class='textOver'>-</div>";
                    }
                }
            },
            {title: '实缴金额(元)', field: 'payment_amount', align: 'center',width:'10%' ,halign:'center',
                formatter : function(params) {
                    if(params != null && params !== ""){
                        return "<div class='textOver' title='" + params + "'>" + params + "</div>";
                    }else{
                        return "<div class='textOver'>-</div>";
                    }
                },
                footerFormatter: function(value){
                    return $("#allMoney").val();
                }
            },
            {title: '街道乡镇', field: 'town', align: 'center',width:'10%' ,halign:'center',
                formatter : function(params) {
                    if(params != null && params !== ""){

                        return "<div class='textOver'  title='" + params + "'>" +params + "</div>";
                    }else{
                        return "<div class='textOver'>-</div>";
                    }
                }
            },
            {title: '社区/村组', field: 'village', align: 'center',width:'10%' ,halign:'center',
                formatter : function(params,row) {
                    if(params != null && params !== ""){
                        return "<div class='textOver'>"+params+"</div>";
                    }else{
                        return "<div class='textOver'>-</div>";
                    }
                },
                footerFormatter: function(value){
                    return $("#allPeople").val();
                }
            }
    ];
    }

    var town = document.getElementById("town").value;
    var village = document.getElementById("village").value;
//表格信息
    function infoTable() {
        var h = $(window).height() - 150;
        var defaultColumns = expertScoreColumn();
        $("#infoTableList").bootstrapTable('destroy');
        $('#infoTableList').bootstrapTable({
            dataType: "json",
            contentType: "application/x-www-form-urlencoded",
            url: Hussar.ctxPath + '/payableInfo/getPayableInfoList',
            editable: false,//开启编辑模式
            striped: false,//隔行变色
            cache: false, // 是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
            sortable: false, // 是否启用排序
            pagination: true, // 是否显示分页（*）
            sortOrder: "desc", // 排序方式
            clickToSelect: true,//点击选中行
            pageNumber: 1, // 初始化加载第一页，默认第一页
            pageSize: 20, // 每页的记录行数（*）
            pageList: [5, 10, 15, 20, 50, 100], // 可供选择的每页的行数（*）
            showHeader: true,
            showFooter:true,
            columns: defaultColumns,
            minimumCountColumns: 2, // 最少允许的列数
            uniqueId: "id", // 每一行的唯一标识，一般为主键列
            height: h,
            queryParams: function (param) {
                var temp = {
                    pageSize: this.pageSize, // 页面大小
                    pageNumber: this.pageNumber,// 页码
                    town: town,
                    village:village,
                    year: $('#year').val() //年份
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

    $(document).ready(function () {
        layer.load(2);
        infoTable();
    });


    //导出
        // var experFile = document.getElementById("experFile")
        // experFile.onclick = function () {
        //     $.ajax({
        //             type: "post",
        //             url: Hussar.ctxPath + '/payableInfo/exportFile',
        //             data: {
        //                 town: town,
        //                 village:village
        //             },
        //             dataType: "json",
        //             success: function (res) {
        //                 layer.msg("数据成功导出!", {icon: 6});
        //             },
        //         })
        // }
    //导出一个村的详情
    $('#export').bind('click',function() {
        window.location.href = Hussar.ctxPath + '/payableInfo/exportVillageFile?year='+$('#year').val()+"&town="+town+"&village="+village;
        layer.msg("正在导出,请稍等···")
    });

       //返回按钮
    var reture = document.getElementById("return")
    reture.onclick = function () {
        window.history.back(-1);
    }
});
