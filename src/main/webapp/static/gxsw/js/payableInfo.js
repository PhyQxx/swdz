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
    // $(document).ready(function () {
    //     //给年份赋初值
    //     var thisYear = new Date().getFullYear();
    //     $('#year').val(thisYear);
    //     getYear();
    //     layer.load(1);
    //     aggregatedData();
    //     infoTable();
    //
    // });
    $(function () {
        //给年份赋初值
        var thisYear = new Date().getFullYear();
        $('#year').val(thisYear);
        getYear();
        layer.load(1);
        infoTable();
    });

    //查询按钮
    $('#submit').bind('click',function(){
        infoTable();
    });

    //下拉列表-年份
    var nowyear;
    function getYear()
    {
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
    $('#year').val(nowyear);
    form.on('select(sel)', function(data){
        $('#year').val(data.value);
    });
        //导入
        var upmsg;
        upload.render({
            elem: '#uploadDetailData' //绑定按钮
            , url: Hussar.ctxPath + '/payableInfo/uploadDetailData'
            , accept: 'file' //普通文件
            , before: function (obj) {
                upmsg = layer.load(1);
                this.data = {
                    "year": $('#year').val()
                }///携带额外的数据
            }
            , done: function (res) {
                if (res.judge == 1) {
                    layer.close(upmsg);
                    layer.msg("上传文件类型错误,请转换成.xlsx文件", {icon: 5});
                } else {
                    if (res.count != 0) {
                        layer.close(upmsg);
                        layer.msg("上传成功! \n成功上传"+res.count+"条数据 \n耗时"+res.time+"毫秒", {icon: 6});
                        infoTable();
                    } else {
                        layer.close(upmsg);
                        layer.msg("上传失败!", {icon: 5});
                    }
                }
                }

        });

        /*//全局变量合计数据
        var allpeople;
        var allmoney;
        var lastyearallpeople;
        var lastyearallmoney;
*/
    //表格列
    function expertScoreColumn() {
            return [
                {title: '街道乡镇', field: 'town', align: 'center',width:'15%' ,halign:'center',
                    formatter : function(params) {
                        if(params != null && params !== ""){
                            return "<div class='textOver' title='" + params + "'>" + params + "</div>";
                        }else{
                            return "<div class='textOver'>-</div>";
                        }
                    },
                    footerFormatter: function (value) {
                        return "合 计";
                    }
                },
                {title: '总金额(元)', field: 'allmoney', align: 'center',width:'15%' ,halign:'center',
                    formatter : function(params) {
                        if(params != null && params !== ""){
                            return "<div class='textOver'  title='" + params + "'>" + params + "</div>";
                        }else{
                            return "<div class='textOver'>-</div>";
                        }
                    },
                    footerFormatter: function(value){
                        var count = 0;
                        for (var i in value) {
                            count += value[i].allmoney;
                        }
                        return count;
                    }
                },
                {title: '去年总金额(元)', field: 'lastyearallmoney', align: 'center',width:'10%' ,halign:'center',
                    formatter : function(params) {
                        if(params != null && params !==         ""){
                            return "<div class='textOver' title='" + params + "'>" + params + "</div>";
                        }else{
                            return "<div class='textOver'>-</div>";
                        }
                    },
                    footerFormatter: function(value){
                        var count = 0;
                        for (var i in value) {
                            count += value[i].lastyearallmoney;
                        }
                        return count;
                    }
                },
                {title: '总人数(人)', field: 'allpeople', align: 'center',width:'10%' ,halign:'center',
                    formatter : function(params) {
                        if(params != null && params !==         ""){
                            return "<div class='textOver' title='" + params + "'>" + params + "</div>";
                        }else{
                            return "<div class='textOver'>-</div>";
                        }
                    },
                    footerFormatter: function(value){
                        var count = 0;
                        for (var i in value) {
                            count += parseInt(value[i].allpeople);
                        }
                        return count;
                    }
                },
                {title: '去年总人数(人)', field: 'lastyearallpeople', align: 'center',width:'10%' ,halign:'center',
                    formatter : function(params) {
                        if(params != null && params !==         ""){
                            return "<div class='textOver' title='" + params + "'>" + params + "</div>";
                        }else{
                            return "<div class='textOver'>-</div>";
                        }
                    },
                    footerFormatter: function(value){
                        var count = 0;
                        for (var i in value) {
                            count += parseInt(value[i].lastyearallpeople);
                        }
                        return count;
                    }
                },
            ];
        }

    //表格信息
    function infoTable() {
        //接受合计数据

           /* $.ajax({
                async: false,
                type: "post",
                traditional: true,// 这使json格式的字符不会被转码
                contentType: 'application/x-www-form-urlencoded',
                url: Hussar.ctxPath +'/payableInfo/aggregatedData',
                data: {
                    year: $('#year').val(),
                },
                dataType: "json",
                success: function (data) {
                    allpeople = data.allpeople;
                    allmoney = data.allmoney;
                    lastyearallpeople = data.lastyearallpeople;
                    lastyearallmoney = data.lastyearallmoney;
                    //合计数据校验
                    if (allpeople == " " || allpeople == "undefined" || allpeople == 0 || allpeople == null) {
                        allpeople = "-";
                    }
                    if (allmoney == " " || allmoney == "undefined" || allmoney == 0 || allmoney == null) {
                        allmoney = "-";
                    }
                    if (lastyearallpeople == " " || lastyearallpeople == "undefined" || lastyearallpeople == 0 || lastyearallpeople == null) {
                        lastyearallpeople = "-";
                    }
                    if (lastyearallmoney == " " || lastyearallmoney == "undefined" || lastyearallmoney == 0 || lastyearallmoney == null) {
                        lastyearallmoney = "-";
                    }
                },
                error: function (data) {
                }
            })*/
        var h = $(window).height() - 150;
        var defaultColumns = expertScoreColumn();
        $("#infoTable").bootstrapTable('destroy');
        $('#infoTable').bootstrapTable({
            dataType:"json",
            contentType : "application/x-www-form-urlencoded",
            url: Hussar.ctxPath +'/payableInfo/getTownPayableList',
            editable:false,//开启编辑模式
            striped:false,//隔行变色
            cache : false, // 是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
            sortable : false, // 是否启用排序
            pagination : true, // 是否显示分页（*）
            sortOrder : "desc", // 排序方式
            clickToSelect: true,//点击选中行
            pageNumber : 1, // 初始化加载第一页，默认第一页
            pageSize : 20, // 每页的记录行数（*）
            pageList : [ 20 ], // 可供选择的每页的行数（*）
            showHeader:true,
            showFooter:true,
            columns: defaultColumns,
            minimumCountColumns : 2, // 最少允许的列数
            uniqueId : "id", // 每一行的唯一标识，一般为主键列
            height:h,
            queryParams : function (param) {
                var temp = {
                    pageSize : this.pageSize, // 页面大小
                    pageNumber : this.pageNumber,// 页码
                    year: $('#year').val() //年份
                };// 传递参数（*）
                return temp;
            },
            sidePagination:"server",
            responseHandler:function (res) {
                var temp = {
                    total : res.total,
                    rows : res.rows,
                };

                return temp;
            },
            onClickCell:function(field, value, row, $element) {
                var town = row.town;
                var allMoney = row.allmoney;
                var allPeople = row.allpeople;
                var lastyearallMoney = row.lastyearallmoney;
                var lastyearallPeople = row.lastyearallpeople;
                window.location.href = Hussar.ctxPath+'/payableInfo/getVillagePayableList?town='+
                    town+'&allMoney='+allMoney+'&allPeople='+allPeople+'&year='+$('#year').val()+
                    "&lastyearallMoney="+lastyearallMoney+"&lastyearallPeople="+lastyearallPeople;
            },
            onLoadSuccess:function(data){
                layer.closeAll("loading");
            }

        });
    };

    //未缴人员导出
    $('#export').bind('click',function() {
        if ($('#year').val()>2018) {
            window.location.href = Hussar.ctxPath + '/payableInfo/exportFile?year='+$('#year').val();
            layer.msg("正在导出,请稍等···")
        } else {
            layer.msg("对比年份必须大于2018" , {icon: 5});
        }
    });

    //统计信息导出
    $('#exportInfo').bind('click',function() {
        if ($('#year').val()>2018) {
            window.location.href = Hussar.ctxPath + '/payableInfo/exportstatistics?year='+$('#year').val();
            layer.msg("正在导出,请稍等···")
        } else {
            layer.msg("对比年份必须大于2018" , {icon: 5});
        }
    });

});