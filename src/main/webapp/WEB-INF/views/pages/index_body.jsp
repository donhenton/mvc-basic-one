<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<div class="container">
	<div class="col-sm-11 well">
		<h3>Index Page</h3>
		 
                 <p>Principal: ${principal} </p>
                <p>SecObject: ${secObject}</p>
	</div>
</div>

 
 

<h3<Secure Page</h3>

<div class="well">
The class for principal in tags <sec:authentication property="principal.class.name"/>
</div