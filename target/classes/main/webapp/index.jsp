<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ include file="/taglib/common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style type="text/css">
	a:HOVER {
	text-decoration: none;
}
</style>
<script type="text/javascript"
	src="${APP_PATH }/bootstrap3/js/jquery.js"></script>
</head>
<body>
	<center><h3>this is index.jsp</h3></center>
	<center><font color="green"><a href="${APP_PATH }/WelcomeServlet">go to welcome.jsp</a></font></center>
	<center><font color="green"><a href="${APP_PATH }/user/test.do">go to useraction</a></font></center>
	<center><font color="green"><a href="${APP_PATH }/emp/emps.do">selectAll</a></font></center>
	<center><font color="green"><a href="${APP_PATH }/emp/empUI.do">empUI</a></font></center>
	<center>
	<form>
	
		<table align="center" border="1">
			<tr>
				<th>输入yonghuid</th>
				<td><input type="text" name="id" id="userid"/></td>
			</tr>
			<tr>
				<td colspan="2">
					<input type="button" id="b1" value="查询"/><span></span>
				</td>
			</tr>
		</table>
	</form>
	</center>
	<script type="text/javascript">
			document.getElementById("b1").onclick = function(){
				alert("wha");
				var url = "${APP_PATH }/user/getOne?id=";
				var id = $("#userid").val();
				if(id == ""|| id == undefined){
					id = "1";
				}
				alert(id);
				window.location.href=url+id;
			}
		
	</script>
</body>
</html>