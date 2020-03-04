<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
 <c:url value="/debug/getToken" var="getToken"/>
<div class="container">
<div class="row">
	<div class="col-sm-11 ">
                    <h3>  <a  class="label label-primary" href="<c:out value="${getToken}"/>">Return To Debug Page</a> </h3>
	</div>
 </div>
 


</div>

 
 
 