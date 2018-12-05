<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="func" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="APP_PATH"  value="${pageContext.request.contextPath }"/>
<% Date dt = new Date(); 
   pageContext.setAttribute("dt", dt);
%>
<c:set var="dt"  value="${dt}"/>