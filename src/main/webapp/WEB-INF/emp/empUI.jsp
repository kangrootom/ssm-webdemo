<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.*"%>
<%@ include file="/taglib/common.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>empUI</title>
<style type="text/css">
.container {
	margin-top: 20px;
}

.query {
	margin-bottom: 20px;
}
</style>
<script type="text/javascript"
	src="${APP_PATH }/bootstrap3/js/jquery.js"></script>
<link href="${APP_PATH }/bootstrap3/css/bootstrap.css" rel="stylesheet">
<script src="${APP_PATH }/bootstrap3/js/bootstrap.js"></script>
<script type="text/javascript"
	src="${APP_PATH }/datepicker/WdatePicker.js"></script>
<script type="text/javascript">
	$(function() {

		$("#query_btn").click(function() {
			var flag = validate_date();
			if (!flag) {
				return false;
			}
			;
			$.ajax({
				url : "${APP_PATH}/emp/queryByWhere",
				type : "POST",
				data : $("#query_form").serialize(),
				success : function(result) {
					if (result.code == 1) {
						build_emps_table(result);
					} else {
						alert("查询查询查询查询查询查询查询查询查询查询查询查询查询查询查询查询查询查询查询错误");
					}
				},
				error : function() {
					alert("服务器错误");
				}
			});
		});

		//单个删除
		$(document).on("click", ".delete_btn", function() {
			//1、弹出是否确认删除对话框
			var ename = $(this).parents("tr").find("td:eq(1)").text();
			var empno = $(this).attr("para");
			//alert($(this).parents("tr").find("td:eq(1)").text());
			if (confirm("确认删除【" + ename + "】吗？")) {
				//确认，发送ajax请求删除即可
				$.ajax({
					url : "${APP_PATH}/emp/" + empno,
					type : "DELETE",
					data : {
						dateStr : new Date().getTime()
					},
					success : function(result) {
						if (result.code == 1) {
							build_emps_table(result);
						} else {
							alert("无法删除");
						}
					},
					error : function() {
						alert("服务器错误");
					}
				});
			}
		});
		//1、我们是按钮创建之前就绑定了click，所以绑定不上。
		//1）、可以在创建按钮的时候绑定。    2）、绑定点击.live()
		//jquery新版没有live，使用on进行替代
		$(document).on("click", ".edit_btn", function() {
			//alert("edit");

			//1、查出部门信息，并显示部门列表
			getDepts("#empUpdateModal #dname_edit_sel");
			//发送ajax请求，查出员工信息，显示在下拉列表中
			getMgrs("#empUpdateModal #mgr_edit_sel");
			//2、查出员工信息，显示员工信息
			getEmp($(this).attr("para"));

			//3、把员工的id传递给模态框的更新按钮
			$("#emp_update_btn").attr("para", $(this).attr("para"));
			$("#empUpdateModal").modal({
				backdrop : "static"
			});
		});

		//点击新增按钮弹出模态框。
		$("#emp_add_modal_btn").click(function() {
			//清除表单数据（表单完整重置（表单的数据，表单的样式））
			reset_form("#empAddModal form");
			//s$("")[0].reset();
			//发送ajax请求，查出部门信息，显示在下拉列表中
			getDepts("#empAddModal #dname_add_sel");
			//发送ajax请求，查出员工信息，显示在下拉列表中
			getEmps("#empAddModal #mgr_add_sel")
			//弹出模态框
			$("#empAddModal").modal({
				backdrop : "static"
			});
		});
		//校验用户名是否可用
		$("#ename_add_input").change(
				function() {
					//发送ajax请求校验用户名是否可用
					var ename = this.value;
					$.ajax({
						url : "${APP_PATH}/emp/check",
						data : "ename=" + ename,
						type : "POST",
						success : function(result) {
							if (result.code == 1) {
								show_validate_msg("#ename_add_input",
										"success", "用户名可用");
								$("#emp_save_btn").attr("ajax-va", "success");
							} else {
								show_validate_msg("#ename_add_input", "error",
										"用户名不可用");
								$("#emp_save_btn").attr("ajax-va", "error");
							}
						}
					});
				});
		//点击保存，保存员工。
		$("#emp_save_btn").click(function() {
			//1、模态框中填写的表单数据提交给服务器进行保存
			//1、先对要提交给服务器的数据进行校验
			if (!validate_add_form()) {
				return false;
			}
			;
			//1、判断之前的ajax用户名校验是否成功。如果成功。
			if ($(this).attr("ajax-va") == "error") {
				return false;
			}

			//2、发送ajax请求保存员工
			$.ajax({
				url : "${APP_PATH}/emp/save",
				type : "POST",
				data : $("#empAddModal form").serialize(),
				success : function(result) {
					//alert(result.msg);
					if (result.code == 1) {
						//员工保存成功；
						//1、关闭模态框
						$("#empAddModal").modal('hide');
						build_emps_table(result);
					} else {
						alert("添加失败");
					}
				}
			});
		});

		//点击更新，更新员工信息
		$("#emp_update_btn").click(function() {
			$.ajax({
				url : "${APP_PATH}/emp/update",
				type : "PUT",
				data : $("#empUpdateModal form").serialize(),
				success : function(result) {
					if (result.code == 1) {
						//员工保存成功；
						//1、关闭模态框
						$("#empUpdateModal").modal("hide");
						//2、回到本页面
						build_emps_table(result);

					} else {
						alert("更新失败");
					}
				}
			});
		});

		//完成全选/全不选功能
		$("#check_all").click(function() {
			//attr获取checked是undefined;
			//我们这些dom原生的属性；attr获取自定义属性的值；
			//prop修改和读取dom原生属性的值
			$(".check_item").prop("checked", $(this).prop("checked"));
		});

		//check_item
		$(document)
				.on(
						"click",
						".check_item",
						function() {
							//判断当前选择中的元素是否5个
							var flag = $(".check_item:checked").length == $(".check_item").length;
							$("#check_all").prop("checked", flag);
						});

		//点击全部删除，就批量删除
		$("#emp_delete_all_btn").click(
				function() {
					//
					var enames = "";
					var del_idstr = "";
					$.each($(".check_item:checked"), function() {
						//this
						enames += $(this).parents("tr").find("td:eq(2)").text()
								+ ",";
						//组装员工id字符串
						del_idstr += $(this).parents("tr").find("td:eq(1)")
								.text()
								+ "-";
					});
					//去除empNames多余的,
					enames = enames.substring(0, enames.length - 1);
					//去除删除的id多余的-
					del_idstr = del_idstr.substring(0, del_idstr.length - 1);
					if (confirm("确认删除【" + enames + "】吗？")) {
						//发送ajax请求删除
						$.ajax({
							url : "${APP_PATH}/emp/" + del_idstr,
							type : "DELETE",
							success : function(result) {
								if (result.code == 1) {
									//员工保存成功；
									//2、回到本页面
									build_emps_table(result);

								} else {
									alert("删除失败");
								}
							}
						});
					}
				});

	});
	function build_emps_table(result) {
		//清空table表格
		$("#emps_table tbody").empty();
		var emps = result.list;
		if (emps.length == 0) {
			var tipTd = $("<td></td>").append("暂无数据!").addClass(
					"bg-primary text-center").attr("colspan", "10");
			$("<tr></tr>").append(tipTd).appendTo("#emps_table tbody");
			return;
		}
		$
				.each(
						emps,
						function(index, item) {
							var checkBoxTd = $("<td><input type='checkbox' class='check_item'/></td>");
							var empnoTd = $("<td></td>").append(item.empno);
							var enameTd = $("<td></td>").append(item.ename);
							var jobTd = $("<td></td>").append(item.job);
							var hiredateTd = $("<td></td>").append(
									item.hiredate);
							var salTd = $("<td></td>").append(formatMoney(item.sal,2,'￥'));
							var commTd = $("<td></td>").append(formatMoney(item.comm,2,'￥'));
							var mgr = $("<td></td>").append(item.mgrName);
							var dname = $("<td></td>").append(item.dept.dname);
							/**
							<button class="">
												<span class="" aria-hidden="true"></span>
												编辑
											</button>
							 */
							var editBtn = $("<button></button>").addClass(
									"btn btn-primary btn-sm edit_btn").append(
									$("<span></span>").addClass(
											"glyphicon glyphicon-pencil"))
									.append("编辑");
							//为编辑按钮添加一个自定义的属性，来表示当前员工id
							editBtn.attr("para", item.empno);
							var delBtn = $("<button></button>").addClass(
									"btn btn-danger btn-sm delete_btn").append(
									$("<span></span>").addClass(
											"glyphicon glyphicon-trash"))
									.append("删除");
							//为删除按钮添加一个自定义的属性来表示当前删除的员工id
							delBtn.attr("para", item.empno);
							var btnTd = $("<td></td>").append(editBtn).append(
									" ").append(delBtn);
							//var delBtn = 
							//append方法执行完成以后还是返回原来的元素
							$("<tr></tr>").append(checkBoxTd).append(empnoTd)
									.append(enameTd).append(jobTd).append(
											hiredateTd).append(salTd).append(
											commTd).append(mgr).append(dname)
									.append(btnTd)
									.appendTo("#emps_table tbody");
						});
	}
	//清空表单样式及内容
	function reset_form(ele) {
		$(ele)[0].reset();
		//清空表单样式
		$(ele).find("*").removeClass("has-error has-success");
		$(ele).find(".help-block").text("");
	}
	//查出所有的部门信息并显示在下拉列表中
	function getDepts(ele) {
		//清空之前下拉列表的值
		$(ele).empty();
		$.ajax({
			url : "${APP_PATH}/dept/depts",
			type : "GET",
			success : function(result) {
				//{"code":100,"msg":"处理成功！",
				//"extend":{"depts":[{"deptId":1,"deptName":"开发部"},{"deptId":2,"deptName":"测试部"}]}}
				//console.log(result);
				//显示部门信息在下拉列表中
				//$("#empAddModal select").append("")
				$.each(result.list, function() {
					var optionEle = $("<option></option>").append(this.dname)
							.attr("value", this.deptno);
					optionEle.appendTo(ele);
				});
			}
		});

	}
	//查出所有的部门信息并显示在下拉列表中
	function getMgrs(ele) {
		//清空之前下拉列表的值
		$(ele).empty();
		$.ajax({
			url : "${APP_PATH}/emp/mgrs",
			type : "GET",
			success : function(result) {
				//{"code":100,"msg":"处理成功！",
				//"extend":{"depts":[{"deptId":1,"deptName":"开发部"},{"deptId":2,"deptName":"测试部"}]}}
				//console.log(result);
				//显示部门信息在下拉列表中
				//$("#empAddModal select").append("")
				var tipEle = $("<option></option>").append("==请选择==").attr(
						"value", "");
				tipEle.appendTo(ele);
				$.each(result.list, function() {
					var optionEle = $("<option></option>").append(this.mgrName)
							.attr("value", this.mgr);
					optionEle.appendTo(ele);
				});
			}
		});

	}
	function getEmps(ele) {
		//清空之前下拉列表的值
		$(ele).empty();
		$.ajax({
			url : "${APP_PATH}/emp/emps",
			type : "GET",
			success : function(result) {
				//{"code":100,"msg":"处理成功！",
				//"extend":{"depts":[{"deptId":1,"deptName":"开发部"},{"deptId":2,"deptName":"测试部"}]}}
				//console.log(result);
				//显示部门信息在下拉列表中
				//$("#empAddModal select").append("")
				$.each(result.list, function() {
					var optionEle = $("<option></option>").append(this.ename)
							.attr("value", this.empno);
					optionEle.appendTo(ele);
				});
			}
		});

	}
	function getEmp(id) {
		$.ajax({
			url : "${APP_PATH}/emp/" + id,
			type : "GET",
			success : function(result) {
				//console.log(result);
				var emp = result.list[0];
				$("#empno_edit_static").val(emp.empno);
				$("#ename_edit_static").val(emp.ename);
				$("#job_edit_input").val(emp.job);
				$("#hiredate_edit_input").val(emp.hiredate);
				$("#sal_edit_input").val(emp.sal);
				$("#comm_edit_input").val(emp.comm);
				if (emp.mgr != null) {

					$("#empUpdateModal #mgr_edit_sel").val([ emp.mgr ]);
				} else {
					$("#empUpdateModal #mgr_edit_sel").val("==请选择==");
				}
				$("#empUpdateModal #dname_edit_sel").val([ emp.deptno ]);
			}
		});
	}
	//校验表单数据
	function validate_add_form() {
		//1、拿到要校验的数据，使用正则表达式
		var ename = $("#ename_add_input").val();
		var regName = /(^[a-zA-Z0-9_-]{6,16}$)|(^[\u2E80-\u9FFF]{2,5})/;
		if (!regName.test(ename)) {
			//alert("用户名可以是2-5位中文或者6-16位英文和数字的组合");
			show_validate_msg("#empName_add_input", "error",
					"用户名可以是2-5位中文或者6-16位英文和数字的组合");
			return false;
		} else {
			show_validate_msg("#empName_add_input", "success", "");
		}

		return true;
	}
	//显示校验结果的提示信息
	function show_validate_msg(ele, status, msg) {
		//清除当前元素的校验状态
		$(ele).parent().removeClass("has-success has-error");
		$(ele).next("span").text("");
		if ("success" == status) {
			$(ele).parent().addClass("has-success");
			$(ele).next("span").text(msg);
		} else if ("error" == status) {
			$(ele).parent().addClass("has-error");
			$(ele).next("span").text(msg);
		}
	}

	function validate_date() {
		var begindate_input_val = $("#begindate_input").val();
		var enddate_input_val = $("#enddate_input").val();
		if (begindate_input_val == '' && enddate_input_val == '') {
			return true;
		} else if (begindate_input_val != '' && enddate_input_val == '') {
			alert("请输入结束时间");
			return false;
		} else if (begindate_input_val == '' && enddate_input_val != '') {
			alert("请输入开始时间");
			return false;
		} else if (begindate_input_val != '' && enddate_input_val != '') {
			if (toDate(begindate_input_val) >= toDate(enddate_input_val)) {
				alert("开始时间不能大于结束时间");
				 $("#begindate_input").focus();
				return false;
			}
		}
		return true;

	}
	function toDate(str) {
		var dateStr = str.split('-');
		return new Date(dateStr[0], dateStr[1], dateStr[2]);
	}
	// 价格，小数点后几位，替换$，千分位显示什么符号默认'，'  ，  小数点'.'
	function formatMoney(number, places, symbol, thousand, decimal) {
		number = number || 0;
		places = !isNaN(places = Math.abs(places)) ? places : 2;
		symbol = symbol !== undefined ? symbol : "$";
		thousand = thousand || ",";
		decimal = decimal || ".";
		var negative = number < 0 ? "-" : "", i = parseInt(number = Math.abs(
				+number || 0).toFixed(places), 10)
				+ "", j = (a = i.length) > 3 ? a % 3 : 0;
		return symbol
				+ negative
				+ (j ? i.substr(0, j) + thousand : "")
				+ i.substr(j).replace(/(\d{3})(?=\d)/g, "$1" + thousand)
				+ (places ? decimal
						+ Math.abs(number - i).toFixed(places).slice(2) : "");
	}
</script>
</head>
<body>
	<!-- 搭建显示页面 -->
	<div class="container">
		<!-- 标题 -->
		<div class="row" class="query">

			<form class="form-inline" id="query_form">
				<div class="col-md-12 ">
					<div class="col-md-3">
						<div class="form-group">
							<label for="ename_input" class="text-primary">ename:</label> <input
								type="text" name="ename" class="form-control" id="ename_input"
								placeholder="ename">
						</div>
					</div>
					<div class="col-md-3 ">
						<div class="form-group">
							<label for="comm_input" class="text-primary">comm:</label> <input
								type="text" class="form-control" name="comm" id="comm_input"
								placeholder="comm">
						</div>
					</div>
					<div class="col-md-6 ">
						<div class="form-group">
							<label for="begindate_input" class="text-primary">起止日期：</label> <input
								type="text" class="form-control" name="begindate"
								id="begindate_input" placeholder="begindate" readonly="readonly"
								onfocus="WdatePicker({'skin':'whyGreen','dateFmt':'yyyy-MM-dd'});">
						</div>
						<div class="form-group">
							<label for="begindate_input">-</label> <input type="text"
								class="form-control" name="enddate" id="enddate_input"
								placeholder="enddate" readonly="readonly"
								onfocus="WdatePicker({'skin':'whyGreen','dateFmt':'yyyy-MM-dd'});">
						</div>
						<button type="button" class="btn btn-success" id="query_btn">查询</button>
					</div>
				</div>
			</form>


		</div>
		<!-- 显示表格数据 -->
		<div class="row ">
			<div class="col-md-12">
				<table class="table table-hover" id="emps_table">
					<thead>
						<tr>
							<th><input type="checkbox" id="check_all" /></th>
							<th>empno</th>
							<th>ename</th>
							<th>job</th>
							<th>hiredate</th>
							<th>sal</th>
							<th>comm</th>
							<th>mgr</th>
							<th>dname</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${result.list }" var="emp">
							<tr>
								<td><input type='checkbox' class='check_item' /></td>
								<td>${emp.empno }</td>
								<td>${emp.ename }</td>
								<td>${emp.job }</td>
								<td><fmt:formatDate value="${emp.hiredate }"
										pattern="yyyy年MM月dd日" /></td>
								<td><fmt:setLocale value="en_US" /> <fmt:formatNumber
										value="${emp.sal }" type="currency"></fmt:formatNumber></td>
								<td><fmt:formatNumber value="${emp.comm }" type="currency"></fmt:formatNumber></td>
								<td>${emp.mgrName }</td>
								<td>${emp.dept.dname }</td>
								<td>
									<button class="btn btn-primary btn-sm edit_btn"
										para="${emp.empno }">
										<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
										编辑
									</button>
									<button class="btn btn-danger btn-sm delete_btn"
										para="${emp.empno }">
										<span class="glyphicon glyphicon-trash" aria-hidden="true"></span>
										删除
									</button>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
		<!-- 按钮 -->
		<div class="row bg-info">
			<div class="col-md-7 col-md-offset-5">
				<button class="btn btn-primary" id="emp_add_modal_btn">新增</button>
				<button class="btn btn-danger" id="emp_delete_all_btn">删除</button>
			</div>
		</div>
	</div>
	<!-- 员工添加的模态框 -->
	<div class="modal fade" id="empAddModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">员工添加</h4>
				</div>
				<div class="modal-body">
					<form class="form-horizontal">
						<div class="form-group">
							<label class="col-sm-2 control-label">ename</label>
							<div class="col-sm-10">
								<input type="text" name="ename" class="form-control"
									id="ename_add_input" placeholder="ename"> <span
									class="help-block"></span>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">job</label>
							<div class="col-sm-10">
								<input type="text" name="job" class="form-control"
									id="job_add_input" placeholder="job"> <span
									class="help-block"></span>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">hiredate</label>
							<div class="col-sm-4">
								<input id="hiredate_add_input" placeholder="hiredate"
									name="hiredate" type="text" class="form-control"
									value="<fmt:formatDate value='${dt}' pattern='yyyy年MM月dd日' />"
									readonly="readonly"
									onfocus="WdatePicker({'skin':'whyGreen','dateFmt':'yyyy年MM月dd日'});"></input><span
									class="help-block"></span>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">sal</label>
							<div class="col-sm-10">
								<input type="text" name="sal" class="form-control"
									id="sal_add_input" placeholder="sal"> <span
									class="help-block"></span>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">comm</label>
							<div class="col-sm-10">
								<input type="text" name="comm" class="form-control"
									id="comm_add_input" placeholder="comm"> <span
									class="help-block"></span>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">mgr</label>
							<div class="col-sm-4">
								<select class="form-control" name="mgr" id="mgr_add_sel">
								</select>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">dname</label>
							<div class="col-sm-4">
								<!-- 部门提交部门id即可 -->
								<select class="form-control" name="deptno" id="dname_add_sel">
								</select>
							</div>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" id="emp_save_btn">保存</button>
				</div>
			</div>
		</div>
	</div>
	<!-- 员工修改的模态框 -->
	<div class="modal fade" id="empUpdateModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">员工修改</h4>
				</div>
				<div class="modal-body">
					<form class="form-horizontal">
						<input type="hidden" name="empno" class="form-control"
							id="empno_edit_static" placeholder="empno">
						<div class="form-group">
							<label class="col-sm-2 control-label">ename</label>
							<div class="col-sm-10">
								<input type="text" name="ename" class="form-control"
									id="ename_edit_static" placeholder="ename" readonly="readonly">
								<span class="help-block"></span>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">job</label>
							<div class="col-sm-10">
								<input type="text" name="job" class="form-control"
									id="job_edit_input" placeholder="job"> <span
									class="help-block"></span>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">hiredate</label>
							<div class="col-sm-4">
								<input id="hiredate_edit_input" placeholder="hiredate"
									name="hiredate" type="text" class="form-control"
									value="<fmt:formatDate value='${dt}' pattern='yyyy年MM月dd日' />"
									readonly="readonly"></input> <span class="help-block"></span>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">sal</label>
							<div class="col-sm-10">
								<input type="text" name="sal" class="form-control"
									id="sal_edit_input" placeholder="sal"> <span
									class="help-block"></span>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">comm</label>
							<div class="col-sm-10">
								<input type="text" name="comm" class="form-control"
									id="comm_edit_input" placeholder="comm"> <span
									class="help-block"></span>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">mgr</label>
							<div class="col-sm-4">
								<select class="form-control" name="mgr" id="mgr_edit_sel">
								</select>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">dname</label>
							<div class="col-sm-4">
								<!-- 部门提交部门id即可 -->
								<select class="form-control" name="deptno" id="dname_edit_sel">
								</select>
							</div>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" id="emp_update_btn">更新</button>
				</div>
			</div>
		</div>
	</div>
</body>
</html>