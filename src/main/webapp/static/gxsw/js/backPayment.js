
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


    var DiffStepInfo = {
        id: "infoTable",	//表格id
        seItem: null,	//选中的条目
        table: null,
        layerIndex: -1,
        pageSize:10,
        pageNumber:1
    };


    $('#expertFile').on('click',function () {
        window.location.href=Hussar.ctxPath+'/reconciliation/exportPaymentData?year='+year;
        /*$.ajax({
            type: "post",
            data: {year: year},
            url: Hussar.ctxPath+'/reconciliation/exportPaymentData',
            success: function(res){
                if (res.message==0){
                    layer.msg("导出失败", {icon: 5});
                }else {
                    layer.msg("导出成功", {icon: 6});
                }
            }
        })*/

    });
    /**
     * 返回
     * @type {HTMLElement}
     */
    var goBack = document.getElementById("goBack");
    goBack.onclick = function() {
        window.location.href = Hussar.ctxPath+'/reconciliation/goView';
    };


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

    //查询按钮
    $('#submit').bind('click',function(){
        infoTable();
    });


//获取下拉列表的年份

    $('#sel').val(nowyear);
    getYear();
    form.on('select(sel)', function(data){
        year = data.value;
        $('#sel').val(data.value);
        form.render();
    });
     //表格列
    function expertScoreColumn() {
        return [
            [
                {
                    title: '人社局补缴人员数据',
                    field: '',
                    width:'30%',
                    align: 'center',
                    colspan: 6,
                    rowspan: 1
                },
                {
                    title: '税务局数据',
                    field: '',
                    width:'30%',
                    align: 'center',
                    colspan: 6,
                    rowspan: 1
                }],
            [{title: '身份证件号码', field: 'social_security_number', align: 'center',width:'15%' ,halign:'center',
                formatter : function(params) {
                    if(params != null && params !== ""){
                        return "<div class='textOver'  title='" + params + "'>" + params + "</div>";
                    }else{
                        return "<div class='textOver'>-</div>";
                    }
                }
            },
                {title: '姓名', field: 'name', align: 'center',width:'8%' ,halign:'center',
                    formatter : function(params) {
                        if(params != null && params !== ""){
                            return "<div class='textOver'  title='" + params + "'>" + params + "</div>";
                        }else{
                            return "<div class='textOver'>-</div>";
                        }
                    }
                },
                {title: '起始年份', field: 'start_date', align: 'center',width:'6%' ,halign:'center',
                    formatter : function(params) {
                        if(params != null && params !== ""){
                            return "<div class='textOver'>"+params+"</div>";
                        }else{
                            return "<div class='textOver'>-</div>";
                        }
                    }
                },
                {title: '中止年份', field: 'end_date', align: 'center',width:'6%' ,halign:'center',
                    formatter : function(params) {
                        if(params != null && params !== ""){
                            return "<div class='textOver'>"+params+"</div>";
                        }else{
                            return "<div class='textOver'>-</div>";
                        }
                    }
                },
                {title: '缴费金额', field: 'personal_payment', align: 'center',width:'8%' ,halign:'center',
                    formatter : function(params) {
                        if(params != null && params !== ""){
                            return "<div class='textOver'  title='" + params + "'>" + params + "</div>";
                        }else{
                            return "<div class='textOver'>-</div>";
                        }
                    }
                },
                {title: '缴费年份', field: 'year', align: 'center',width:'6%' ,halign:'center',
                    formatter : function(params) {
                        if(params != null && params !== ""){
                            return "<div class='textOver'>"+params+"</div>";
                        }else{
                            return "<div class='textOver'>-</div>";
                        }
                    }
                },{title: '身份证件号码', field: 'social_credit_code', align: 'center',width:'15%' ,halign:'center',
                formatter : function(params) {
                    if(params != null && params !== ""){
                        return "<div class='textOver'  title='" + params + "'>" + params + "</div>";
                    }else{
                        return "<div class='textOver'>-</div>";
                    }
                }
            },
                {title: '姓名', field: 'taxpayer_name', align: 'center',width:'8%' ,halign:'center',
                    formatter : function(params) {
                        if(params != null && params !== ""){
                            return "<div class='textOver'  title='" + params + "'>" + params + "</div>";
                        }else{
                            return "<div class='textOver'>-</div>";
                        }
                    }
                },
                {title: '起始年份', field: 'startDate', align: 'center',width:'6%' ,halign:'center',
                    formatter : function(params) {
                        if(params != null && params !== ""){
                            return "<div class='textOver'>"+params+"</div>";
                        }else{
                            return "<div class='textOver'>-</div>";
                        }
                    }
                },
                {title: '中止年份', field: 'endDate', align: 'center',width:'6%' ,halign:'center',
                    formatter : function(params) {
                        if(params != null && params !== ""){
                            return "<div class='textOver'>"+params+"</div>";
                        }else{
                            return "<div class='textOver'>-</div>";
                        }
                    }
                },
                {title: '缴费金额', field: 'tax_payment', align: 'center',width:'8%' ,halign:'center',
                    formatter : function(params) {
                        if(params != null && params !== ""){
                            return "<div class='textOver'  title='" + params + "'>" + params + "</div>";
                        }else{
                            return "<div class='textOver'>-</div>";
                        }
                    }
                },
                {title: '缴费年份', field: 'year1', align: 'center',width:'6%' ,halign:'center',
                    formatter : function(params) {
                        if(params != null && params !== ""){
                            return "<div class='textOver'>"+params+"</div>";
                        }else{
                            return "<div class='textOver'>-</div>";
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
            url: Hussar.ctxPath +'/reconciliation/getBackPayment',
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
                    year: year,
                    pageSize : this.pageSize, // 页面大小
                    pageNumber : this.pageNumber// 页码
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

});