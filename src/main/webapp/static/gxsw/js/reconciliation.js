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

layui.use(['layer','jquery','bootstrap_table_edit','Hussar','form','element','upload'], function(){
    var layer = layui.layer
        ,Hussar = layui.Hussar
        ,upload =layui.upload
        ,form = layui.form
        ,table = layui.table
        ,element=layui.element
        ,$ = layui.jquery;
    var year='2019';
    $(document).ready(function () {
        layer.load(1);
        infoTable();
    });

    //查询按钮
    $('#submit').bind('click',function(){
        infoTable();
    });


    var nowyear;
    function getYear(){
        nowyear = new Date().getFullYear(); //获取当前年份
        // var sel = document.getElementById ('sel');//获取select下拉列表
        var option = '';
        for ( var i = nowyear; i >= 2018; i--)//获取下拉框的值，从2018年到当前年
        {
            option += "<option value="+i+">"+i+"</option>";
        }
        $('#sel').html(option);
        form.render('select');
    }


//获取下拉列表的年份

    $('#sel').val(nowyear);
    getYear();
    form.on('select(sel)', function(data){
        year = data.value;
        $('#sel').val(data.value);
        form.render();
    });
    //导入实收数据
    upload.render({
        elem: '#uploadPaymentData' ,//绑定按钮
        url: Hussar.ctxPath +'/reconciliation/uploadPaymentData', //上传的地址
        accept: 'file' ,//普通文件
        //获取需要导入的年份的
        before: function(obj){
            layer.load(1);
            this.data={
               'data':  $('.layui-select-title input').val()
            }
        },
        done: function(res){
            infoTable();
            layer.close();
            layer.msg("成功导入"+res.count+"条数据! \n共耗时"+
                res.time+"秒!",{icon:6},2000);
        }
    });
    upload.render({
        elem: '#uploadAccountingData', //绑定按钮
        url: Hussar.ctxPath +'/reconciliation/uploadAccountingData', //上传的地址
        accept: 'file' ,//普通文件
        before: function(obj){
            layer.load(1);
            this.data={
                'data':  $('.layui-select-title input').val()
            }
        },
        done: function(res){
            infoTable();
            layer.close();
            layer.msg("成功导入"+res.count+"条数据! \n共耗时"+
                res.time+"秒!",{icon:6},2000);

        }
    });
    //获取征收子目下拉框数据
    var specificItem = '';
        form.on('select(specificItem)', function(data){
            specificItem = data.value; //得到被选中的值
            console.log(specificItem)

    });

    //以征收子目为单位导出数据
    $('#expertFile').on('click',function () {
        var pageData = $("#infoTable").bootstrapTable('getData','useCurrentPage');
        if (pageData.length!="") {
            window.location.href = Hussar.ctxPath + '/reconciliation/exportExceptionData?specificItem=' + specificItem + '&year=' + year;
            layer.msg('正在导出，请稍后...', {
                icon: 16
                ,shade: 0.01
            });
        }else {
            layer.msg("表中无异常数据，无需导出！",{
                icon: 5
            })
            return;
        }

        /*$.ajax({
            type: "post",
            url: Hussar.ctxPath+'/reconciliation/exportExceptionData',
            data:{specificItem: specificItem,year: year},
            success: function(res){
                if (res.message==0){
                    layer.msg("导出失败", {icon: 5});
                }else {
                    layer.msg("导出成功", {icon: 6});
                }
            }
        })*/
        // url: Hussar.ctxPath+'/reconciliation/exportExceptionData?specificItem='+specificItem;

    });




    /**
     * 补缴人员数据
     */
    var backPayment = document.getElementById("backPayment");
    backPayment.onclick = function() {
        window.location.href = Hussar.ctxPath+'/reconciliation/goBackPayment';
    };





    //表格列
    function expertScoreColumn() {
        return [
            [
                {
                    title: '人社局数据',
                    field: '',
                    width:'30%',
                    align: 'center',
                    colspan: 5,
                    rowspan: 1
                },
                {
                    title: '税务局数据',
                    field: '',
                    width:'30%',
                    align: 'center',
                    colspan: 5,
                    rowspan: 1
                },
                /*{title: '征收子目', field: 'category', align: 'center',width:'10%' ,halign:'center',rowspan: 2,
                    formatter : function(params,row) {
                        if(params != null && params !== ""){
                            return "<div class='textOver'>"+params+"</div>";
                        }else{
                            return "<div class='textOver'>-</div>";
                        }
                    }
                }*/
            ],
            [{title: '身份证件号码', field: 'social_security_number', align: 'center',width:'15%' ,halign:'center',
                formatter : function(params) {
                    if(params != null && params !== ""){
                        return "<div class='textOver'  title='" + params + "'>" + params + "</div>";
                    }else{
                        return "<div class='textOver'>-</div>";
                    }
                }
            },
                {title: '姓名', field: 'name', align: 'center',width:'10%' ,halign:'center',
                    formatter : function(params) {
                        if(params != null && params !== ""){
                            return "<div class='textOver'  title='" + params + "'>" + params + "</div>";
                        }else{
                            return "<div class='textOver'>-</div>";
                        }
                    }
                },
                {title: '缴费金额', field: 'personal_payment', align: 'center',width:'8%' ,halign:'center',
                    formatter : function(params) {
                        if(params != null && params !== ""){
                            return "<div class='textOver' title='" + params + "'>" + params + "</div>";
                        }else{
                            return "<div class='textOver'>-</div>";
                        }
                    }
                },
                {title: '征收子目', field: 'personnel_category', align: 'center',width:'10%' ,halign:'center',
                    formatter : function(params) {
                        if(params != null && params !== ""){
                            return "<div class='textOver'>"+params+"</div>";
                        }else{
                            return "<div class='textOver'>-</div>";
                        }
                    }
                },
                {title: '年份', field: 'year', align: 'center',width:'8%' ,halign:'center',
                    formatter : function(params) {
                        if(params != null && params !== ""){
                            return "<div class='textOver'>"+params+"</div>";
                        }else{
                            return "<div class='textOver'>-</div>";
                        }
                    }
                },
                {title: '身份证件号码', field: 'social_credit_code', align: 'center',width:'15%' ,halign:'center',
                    formatter : function(params) {
                        if(params != null && params !== ""){
                            return "<div class='textOver'  title='" + params + "'>" + params + "</div>";
                        }else{
                            return "<div class='textOver'>-</div>";
                        }
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
                {title: '缴费金额', field: 'tax_payment', align: 'center',width:'8%' ,halign:'center',
                    formatter : function(params) {
                        if(params != null && params !== ""){
                            return "<div class='textOver' title='" + params + "'>" + params + "</div>";
                        }else{
                            return "<div class='textOver'>-</div>";
                        }
                    }
                },
                {title: '征收子目', field: 'tax_type', align: 'center',width:'10%' ,halign:'center',
                    formatter : function(params) {
                        if(params != null && params !== ""){
                            return "<div class='textOver'>"+params+"</div>";
                        }else{
                            return "<div class='textOver'>-</div>";
                        }
                    }
                },

                {title: '年份', field: 'year1', align: 'center',width:'8%' ,halign:'center',
                    formatter : function(params,row) {

                        if( row.social_credit_code=='' || row.social_credit_code== undefined){
                            return "<div class='textOver'>-</div>";
                        }else{

                            return "<div class='textOver'>"+params+"</div>";
                        }
                    }
                }
            ]

        ];
    }

    //表格信息
    function infoTable() {
        var h = $(window).height() - 150;
        var defaultColumns = expertScoreColumn();
        $("#infoTable").bootstrapTable('destroy');
        $('#infoTable').bootstrapTable({
            dataType:"json",
            contentType : "application/x-www-form-urlencoded",
            url: Hussar.ctxPath +'/reconciliation/getCompareResultList',
            editable:false,//开启编辑模式
            striped:false,//隔行变色
            cache : false, // 是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
            sortable : false, // 是否启用排序
            pagination : true, // 是否显示分页（*）
            sortOrder : "desc", // 排序方式
            clickToSelect: true,//点击选中行
            pageNumber : 1, // 初始化加载第一页，默认第一页
            pageSize : 20, // 每页的记录行数（*）
            pageList : [ 5, 10, 15, 20, 50, 100 ], // 可供选择的每页的行数（*）
            showHeader:true,
            columns: defaultColumns,
            minimumCountColumns : 2, // 最少允许的列数
            uniqueId : "id", // 每一行的唯一标识，一般为主键列
            height:h,
            queryParams : function (param) {
                var temp = {
                    specificItem:specificItem,
                    pageSize : this.pageSize, // 页面大小
                    pageNumber : this.pageNumber,// 页码
                    year: year
                };// 传递参数（*）
                return temp;
            },
            sidePagination:"server",
            responseHandler:function (res) {
                var temp = {
                    total : res.total,
                    rows : res.rows
                };

                return temp;
            },
            onLoadSuccess:function(data){
                layer.closeAll("loading");
            }
        });
    }


    $('#dzBtn').on('click',function () {
        layer.load(1);
        $.ajax({
            type: "post",
            url: Hussar.ctxPath+'/reconciliation/saveErrorData',
            data:{year: year},
            success: function(res){
                layer.closeAll("loading");
                Hussar.success("对账后产生"+res.total+"条异常数据！");
                infoTable();
            }
        })
    });
});
