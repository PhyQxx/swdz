/**
 * @Description: 组织机构新增
 * @Author: sunZengXin
 * @Date: 2018/2/6.
 */
var EmployeeAdd={ id:"employeeAdd"};
layui.use(['jquery','layer','Hussar','form', 'laydate'], function(){
	var form=layui.form,
		laydate = layui.laydate,
		$ = layui.jquery,
		Hussar = layui.Hussar;


	EmployeeAdd.initButtonEvent=function(){
		  //监听提交
		  form.on('submit(employeeAdd)', function(data){
			  var formData = data.field;
			  var isEmployee = "1";
			  //是否为人员
			  formData.isEmployee=isEmployee;
			  if(formData.beginDate>formData.endDate){
				  Hussar.info("失效日期不能早于生效日期！");
				  return false;
			  }
			  $.ajax({
					type:"POST",
					contentType:"application/x-www-form-urlencoded",
					url:Hussar.ctxPath+"/employee/emplInfoSave",
					data:formData,
					async:false,
					success:function(data){
						if (data.code == 200){
							Hussar.success("保存成功!");
							//刷新父页面
							parent.location.reload();
						} else {
							Hussar.error(data.message);
						}
					},
					error:function(){
						Hussar.error("保存失败!");			
					}
				});
		    return false;
		  });
	};

    /**
	 * 初始化日期控件
     */
    EmployeeAdd.initLaydate = function () {
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
	 * 页面加载完成后
     */
	$(function(){
		EmployeeAdd.initLaydate();
        EmployeeAdd.initButtonEvent();
	});
});