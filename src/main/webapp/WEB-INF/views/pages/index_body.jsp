<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<div class="container">
	<div class="col-sm-11 well">
		<h3>Index Page</h3>
		 <ul>
                    <li>Principal: ${principal} </li>
                    <li>SecObject: ${secObject}</li>
                    <li>The class for principal in Sec Tags <sec:authentication property="principal.class.name"/></li>
                    <li>UserName from Sec Tags Principal: <sec:authentication property="principal.username"/></li>

                 </ul>
	</div>
</div>

 
 

<h3>Security Tag</h3>

<div class="well">

</div