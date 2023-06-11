<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<% if(request.getSession().getAttribute("user") == null){
	response.sendRedirect("/Ps24565_TranMinhDuy/login");
}
	
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<title>User Management</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65"
	crossorigin="anonymous">
	<style>
		body {
			margin: 0;
			padding: 0;
			text-align: center;
			background: #FFFBEB;
		}
		
		h1 {
			margin-top: 5%;
			margin-bottom: 2%;
			color: #251749;
		}
		
		.div_form {
			width: 40%;
			margin-left: auto;
			margin-right: auto;
			color: #263159;
		}
		
		.input-group-text, .input-group mb-3 {
			color: #263159;
		}
		
		.div_role {
			display: flex;
		}
		
		.div_role>input, .div_role>label {
			margin-left: 0.3rem;
		}
		
		.table {
			width: 60%;
			margin-left: auto;
			margin-right: auto;
		}
		
		.alert {
			width: 60%;
			margin-left: auto;
			margin-right: auto;
		}
		a {
			color: #263159;
			margin-left: 0.2rem;
		}
		
		.delete {
			color: #DC3535;
		}
		.col-3{
			margin-left: 15%;
		}
	</style>
</head>
<body>

<input type="hidden" id="status" value="${status}">
	<h1>User Manager</h1>
	<div class="alert">
		<c:if test="${not empty message}">
			<div class="alert alert-success">${message}</div>
		</c:if>
		<c:if test="${not empty error}">
			<div class="alert alert-danger">${error}</div>
		</c:if>
	</div>
	<form class="div_form" action="/Ps24565_TranMinhDuy/user/index" method="post">
		<div class="input-group mb-3">
			<span class="input-group-text" id="inputGroup-sizing-default">User ID</span>
			<input name="username" type="text" class="form-control" value="${user.username}"
				aria-label="Sizing example input"
				aria-describedby="inputGroup-sizing-default">
		</div>
		<div class="input-group mb-3">
			<span class="input-group-text" id="inputGroup-sizing-default">Password</span>
			<input name="password" type="password" class="form-control" value="${user.password}"
				aria-label="Sizing example input"
				aria-describedby="inputGroup-sizing-default">
		</div>
		<div class="input-group mb-3">
			<span class="input-group-text" id="inputGroup-sizing-default">Full-name</span>
			<input name="fullname" type="text" class="form-control" value="${user.fullname}"
				aria-label="Sizing example input"
				aria-describedby="inputGroup-sizing-default">
		</div>
		<div class="input-group mb-3">
			<span class="input-group-text" id="inputGroup-sizing-default">Email</span>
			<input name="email" type="email" class="form-control" value="${user.email}"
				aria-label="Sizing example input"
				aria-describedby="inputGroup-sizing-default">
		</div>
		<div class="div_role mb-3">
			<label>Role:</label>
			<input id="isAdmin" type="radio" name="isAdmin" ${user.isAdmin?'checked':''}
				aria-label="Sizing example input"> <label for="admin">Admin</label>
			<input id="user" type="radio" name="isAdmin" ${user.isAdmin?'':'checked'}
				aria-label="Sizing example input"> <label for="user">User</label>
		</div>
		<button formaction="/Ps24565_TranMinhDuy/user/create" class="btn btn-outline-success">Create</button>
		<button formaction="/Ps24565_TranMinhDuy/user/update/?id=${user.id}" class="btn btn-outline-warning">Update</button>
		<button formaction="/Ps24565_TranMinhDuy/user/delete/?id=${user.id}" class="btn btn-outline-danger">Delete</button>
		<button formaction="/Ps24565_TranMinhDuy/user/reset" class="btn btn-outline-info">Reset</button>
	</form>
	
	<div class="container mb-3">
	<form method="get" action="/Ps24565_TranMinhDuy/user/search">
		<div class="row">
			<div class="col-3">
				<div class="form-group">
					 <input type="text" class="form-control" 
					 id="search-input" name="keyword" 
					 placeholder="Nhập tên nhân viên...">
				</div>
			</div>
			 <div class="col-3">
				<button type="submit" class="btn btn-primary" formaction="/Ps24565_TranMinhDuy/user/search">Tìm kiếm</button>
			 </div>
		</div>
	</form>
</div>
	<table class="table">
		<thead>
			<tr>
				<th scope="col">No</th>
				<th scope="col">User ID</th>
				<th scope="col">Password</th>
				<th scope="col">Full-name</th>
				<th scope="col">Email</th>
				<th scope="col">Role</th>
				<th scope="col">Action</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="user" items="${users}">
				<tr>
					<td>${user.id}</td>
					<td>${user.username}</td>
					<td>${user.password}</td>
					<td>${user.fullname}</td>
					<td>${user.email}</td>
					<td>${user.isAdmin?'Admin':'User'}</td>
					<td><a href="/Ps24565_TranMinhDuy/user/edit/?id=${user.id}">
						edit
						</a>
						<a class="delete" href="/Ps24565_TranMinhDuy/user/delete/?id=${user.id}">
						delete
						</a>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4"
		crossorigin="anonymous"></script>
		<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
		<script type="text/javascript">
		    window.onload = function() {
		        var status = document.getElementById("status").value;
		        if(status == "Delete success"){
		            swal("Congrats","Delete success!","success");
		        }
		        if(status == "Insert succsesfully!"){
		            swal("Congrats","Insert succsesfully!","success");
		        }
		        if(status == "Update success"){
		            swal("Congrats","Update success!","success");
		        }
		    };
		</script>
</body>
</html>