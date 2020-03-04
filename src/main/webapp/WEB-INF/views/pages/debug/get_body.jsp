<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<c:url var="baseURL" value="/" />

<div class="container">
<div class="row">
	<div class="col-sm-11">
                    <form:form method="post" modelAttribute="tokenRequest"  action="${baseURL}debug/processRequest">
                                <div class="form-group">
                                    <form:label path="state">State</form:label>
                                    <form:input   class="form-control" path="state" />
                                </div>
			       <div class="form-group">
                                    <form:label path="authorizeURI">Authorize URI</form:label> 
                                    <form:input   class="form-control"  path="authorizeURI" />
                                </div>
			       <div class="form-group">
                                    <form:label path="clientId">Client ID</form:label>
                                    <form:input  class="form-control" path="clientId" />
                                </div>
			       <div class="form-group">
				<form:label path="scope">Scope</form:label>
				<form:input   class="form-control"  path="scope" />
                                </div>
                                <div class="col-md-4">
                                <fieldset>
                                    <legend>Response Type</legend>
                                        <div class="checkbox">
                                            <form:checkbox id="responseType_code"  path="responseType" value="code" /> 
                                            <label for="responseType_code">Code</label>
                                        </div>
                                        <div class="checkbox">
                                            <form:checkbox     path="responseType" value="token" />
                                            <label for="responseType_code">Token</label>
                                        </div>
                                        <div class="checkbox">
                                            <form:checkbox    path="responseType" value="id_token" />  
                                            <label for="responseType_code">Id Token</label>
                                        </div>

                                </fieldset>
                                </div>



                                <div class="col-md-8">
                                <fieldset>
                                    <legend>Response Mode</legend>
                                        <div class="checkbox">
                                            <form:radiobutton   path="responseMode" value="query" />
                                            <label for="responseType_code">Query</label>
                                        </div>
                                        <div class="checkbox">
                                            <form:radiobutton    path="responseMode" value="fragment" /> 
                                            <label for="responseType_code">Fragment</label>
                                        </div>
                                        <div class="checkbox">
                                            <form:radiobutton    path="responseMode" value="form_post" /> 
                                            <label for="responseType_code">Form Post</label>
                                        </div>

                                </fieldset>
                                </div>


 
                              

                                <div class="form-group">
			       <div class="col-md-4">
				<input class="btn btn-primary" type="submit" value="Get Token" />
                               </div>
                               </div>
	 
</form:form>
	</div>
 </div>
 


</div>

 
 
 