<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ include file="/taglib/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript"
	src="${APP_PATH }/bootstrap3/js/jquery.js"></script>
</head>
<body>
	<center><h1><font color="green">${success }welcome to index.jsp</font></h1></center>
s	<hr/>
	<center>
	<form>
	
		<table align="center" border="1">
			<tr>
				<th>输入yonghuid</th>
				<td><input type="text" name="id" /></td>
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
			$("#b1").click(function(){
				 var url = "${APP_PATH }/user/getOne.do";
				var id = $("input[name='id']").val();
				var data = {"id":id};
				$.post(url,data,function(ajax,textStatus){
					var json = JSON.stringify(ajax);
					alert(json);
					$("span").text("用户id"+ajax.id+" 用户名:"+ajax.name).css("color","orange");
				}); 
			});
		
	</script>
</body>
</html>