<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<c:url var="baseURL" value="/" />

<div class="container">
<div class="row">
	<div class="col-sm-11">
                    <form:form method="post" modelAttribute="tokenRequest"  action="${baseURL}debug/addContact">
	<table>
		<tr>
			<td>
				<form:label path="state">State</form:label>
			</td>
			<td>
				<form:input path="state" />
			</td>
		</tr>
		<tr>
			<td>
				<form:label path="authorizeURI">Author</form:label>
			</td>
			<td>
				<form:input path="authorizeURI" />
			</td>
		</tr>
		<tr>
			<td>
				<form:label path="clientId">Client ID</form:label>
			</td>
			<td>
				<form:input path="clientId" />
			</td>
		</tr>
		<tr>
			<td>
				<form:label path="scope">Scope</form:label>
			</td>
			<td>
				<form:input path="scope" />
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<input class="btn btn-primary" type="submit" value="Get Token" />
			</td>
		</tr>
	</table>
</form:form>
	</div>
 </div>
 


</div>

 
 
 