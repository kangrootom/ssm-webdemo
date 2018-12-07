<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.*"%>
<%@ include file="/taglib/common.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>员工统计图</title>
<link href="${APP_PATH }/bootstrap3/css/bootstrap.css" rel="stylesheet">
<script type="text/javascript"
	src="${APP_PATH }/bootstrap3/js/jquery.js"></script>
<script src="${APP_PATH }/bootstrap3/js/bootstrap.js"></script>
<script type="text/javascript"
	src="${APP_PATH }/datepicker/WdatePicker.js"></script>
<script type="text/javascript"
	src="${APP_PATH }/fusioncharts/fusioncharts.js"></script>
<script type="text/javascript"
	src="${APP_PATH }/fusioncharts/fusioncharts.charts.js"></script>
<script type="text/javascript"
	src="${APP_PATH }/fusioncharts/themes/fusioncharts.theme.fint.js"></script>
</head>
<script type="text/javascript">
	//加载完dom元素后，执行
	$(document).ready(doEmpStatistic());

	//根据年份统计投诉数
	function doEmpStatistic() {
		//1、获取年份
		var dimensionality = $("#dimensionality option:selected").val();
		var title = $("#dimensionality option:selected").text();
		if (dimensionality == "" || dimensionality == undefined) {
			dimensionality = "sal";//默认月薪
			title = "月薪";
		}
		//2、根据年份统计
		$.ajax({
			url : "${APP_PATH }/emp/getEmpStatisticData",
			data : {
				dimensionality : dimensionality,
				datePara : new Date().getTime()
			},
			type : "POST",
			dataType : "json",
			success : function(data) {
				if (data.code == 1) {
					var revenueChart = new FusionCharts({
						"type" : "line",
						"renderAt" : "chartContainer",
						"width" : "800",
						"height" : "500",
						"dataFormat" : "json",
						"dataSource" : {
							"chart" : {
								"caption" : "员工"+title+"统计图",
								"xAxisName" : "员工名",
								"yAxisName" : title,
								"theme" : "fint"
							},
							"data" : data.list
						}

					});
					revenueChart.render();
				} else {
					alert("统计员工月薪失败！");
				}
			},
			error : function() {
				alert("统计员工月薪失败！");
			}
		});

	}
</script>
<body>
	<br>
	<div style="text-align: center; width: 100%;">
		<select id="dimensionality" onchange="doEmpStatistic()" name="dimensionality">
			<option value="sal">月薪</option>
			<option value="comm">奖金</option>
		</select>
	</div>
	<br>
	<div id="chartContainer" style="text-align: center; width: 100%;"></div>
</body>
</html>
