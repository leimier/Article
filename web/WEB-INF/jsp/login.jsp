<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include  file="common/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <script src="<c:url value="/static/js/jQuery.js" />"></script>
    <link rel="stylesheet" type="text/css" href="<c:url value='/static/css/public.css'/> "/>
    <link rel="stylesheet" type="text/css" href="<c:url value='/static/css/login.css'/> "/>
    <title>登陆页面</title>

    <style type="text/css">
        .content .logo {
            text-align: center;
            line-height: 90px;
            color: #fff;
            font-size:30px;
            position: relative;
            top: 26px;
        }
    </style>
</head>
<body>
	<!-- 头部页面 -->
	<%@include file="common/header.jsp" %>

	<!-- 展示信息 -->
	<div class='show'>
		<img src = "<c:url value="/static/img/msg.png"/>" />
	</div>
	
	<!-- 登陆框 -->
	<div class='content'>
		<div class='logo'>用户登录</div>
		<div class='inputBox mt50 ml32'>
			<input type="text" id="username" autofocus="autofocus" autocomplete="off" maxlength="60" placeholder="请输入账号/邮箱/手机号">
		</div>
		<div class='inputBox mt50 ml32'>
			<input type="password" id="password" autofocus="autofocus" autocomplete="off" maxlength="60" placeholder="请输入密码">
		</div>
		
		<div class='mt50 ml32'>
			<button class="login_btn" onclick="login()">登陆</button>
		</div>
	</div>
	
	<script>
		function login(){
			var username = $('#username').val();
			var password = $('#password').val();
			$.ajax({
				type:"post",//请求方式
				url:"<c:url value="login"/>",//请求地址
				data:{"username":username,"password":password},//传递给controller的json数据
				error:function(){//如果出错了，将事件重新绑定
					alert("登陆出错！");
				},
				success:function(data){ //返回成功执行回调函数。
					if(data == -1){
						alert('用户名和密码不能为空！');
					}else if(data == -2){
						alert('用户名不存在！');
					}else if(data == -3){
						alert('用户名或密码错误！');
					}else{
						//登录成功后返回首页
						window.location.href = "<c:url value='/index.jsp'/>";
					}
				}
			});
		}
	</script>
</body>
</html>