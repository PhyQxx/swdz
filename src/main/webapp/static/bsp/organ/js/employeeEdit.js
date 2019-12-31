/**
 * @Description: 组织机构修改
 * @Author: sunZengXin
 * @Date: 2018/2/6.
 */
var EmployeeEdit = {};
layui.use(['jquery','layer','Hussar','form', 'laydate'], function(){
	var form=layui.form,
		laydate = layui.laydate,
		$ = layui.jquery,
		Hussar = layui.Hussar;

    /**
	 * 初始化时间控件
     */
    EmployeeEdit.initLaydate = function () {
        laydate.render({
            elem: '#beginDate'
        });
        laydate.render({
            elem: '#endDate'
        });
        laydate.render({
            elem: '#birthday'
        });
        laydate.render({
            elem: '#workDate'
        });
        laydate.render({
            elem: '#graduateDate'
        });
    };

    /**
	 * 监听表单提交
     */
    EmployeeEdit.formSubmit = function () {
        form.on('submit(orgInfoEdit)', function(data){
            var formData = data.field;
            if(formData.beginDate>formData.endDate){
                Hussar.info("失效日期不能早于生效日期！");
                return false;
            }
            $.ajax({
                type:"POST",
                contentType:"application/x-www-form-urlencoded",
                url:Hussar.ctxPath+"/employee/emplInfoEditSave",
                data:formData,
                async:false,
                success:function(data){
                    Hussar.success("保存成功!");
                    //刷新父页面
                    parent.location.reload();
                },
                error:function(){
                    Hussar.error("保存失败!");
                }
            });
            return false;
        });
    };

    /**
	 * 表单元素渲染
     */
    EmployeeEdit.renderForm = function () {
		//性别
        var sex = $("input[name='sex']");
        for(var i=0; i<sex.length; i++){
            if(sex[i].value == staff_sex){
                sex[i].checked = true;
            }
        }

        form.render();	//设置完成后渲染表单
    };

    

    /**
	 * 页面加载完成后
     */
    $(function(){
        EmployeeEdit.renderForm();
        EmployeeEdit.initLaydate();
        EmployeeEdit.formSubmit();
    });
	  
});