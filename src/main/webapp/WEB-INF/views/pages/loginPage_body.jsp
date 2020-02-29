<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<div class="container">
	<div class="col-sm-3 well">
		<h3>Login with:</h3>
		<div class="list-group">
			 
                        <ul>
                        <c:forEach items="${urls}" var="url" varStatus="status">

                        <li><a  class="list-group-item active href="<c:out value="${url.value}"/>"><c:out value="${url.key}" /> Client</a></li>
                        </c:forEach>
                        </ul>
		</div>
	</div>
</div>

 