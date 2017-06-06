<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@page import="java.util.UUID"%>
<%@page import="java.security.interfaces.RSAPublicKey"%>
<%@page import="org.apache.commons.lang3.ArrayUtils"%>
<%@page import="org.apache.commons.codec.binary.Base64"%>
<%@page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@page import="org.springframework.context.ApplicationContext"%>
<%@page import="com.cmsz.ircn.bean.Setting"%>
<%@page import="com.cmsz.ircn.utils.SpringUtils"%>
<%@page import="com.cmsz.ircn.bean.Setting.CaptchaType"%>
<%@page import="com.cmsz.ircn.bean.Setting.AccountLockType"%>
<%@page import="com.cmsz.ircn.service.RSAService"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%
String base = request.getContextPath();
String captchaId = UUID.randomUUID().toString();
ApplicationContext applicationContext = SpringUtils.getApplicationContext();
Setting setting = SpringUtils.getBean("setting", Setting.class);
if (applicationContext != null) {
%>
<shiro:authenticated>
<%
response.sendRedirect(base + "/common/main.do");
%>
</shiro:authenticated>
<%
}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>南方基地客服系统</title>
<%
if (applicationContext != null) {
	RSAService rsaService = SpringUtils.getBean("rsaServiceImpl", RSAService.class);
	RSAPublicKey publicKey = rsaService.generateKey(request);
	String modulus = Base64.encodeBase64String(publicKey.getModulus().toByteArray());
	String exponent = Base64.encodeBase64String(publicKey.getPublicExponent().toByteArray());
	
	String message = null;
	String loginFailure = (String) request.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
	if (loginFailure != null) {
		if (loginFailure.equals("org.apache.shiro.authc.pam.UnsupportedTokenException")) {
			message = "admin.captcha.invalid";
		} else if (loginFailure.equals("org.apache.shiro.authc.UnknownAccountException")) {
			message = "admin.login.unknownAccount";
		} else if (loginFailure.equals("org.apache.shiro.authc.DisabledAccountException")) {
			message = "admin.login.disabledAccount";
		} else if (loginFailure.equals("org.apache.shiro.authc.LockedAccountException")) {
			message = "admin.login.lockedAccount";
		} else if (loginFailure.equals("org.apache.shiro.authc.IncorrectCredentialsException")) {
			message = "admin.login.accountLockCount";
		} else if (loginFailure.equals("org.apache.shiro.authc.AuthenticationException")) {
			message = "admin.login.authentication";
		}
	}
%>
<link rel="stylesheet" type="text/css" href="<%=base%>/resources/css/common.css"/>
<link rel="stylesheet" type="text/css" href="<%=base%>/resources/css/global2.css"/>
<link rel="stylesheet" type="text/css" href="<%=base%>/resources/css/css2.css"/>
<link rel="stylesheet" type="text/css" href="<%=base%>/resources/css/nivo-slider.css"/>	
<script type="text/javascript" src="<%=base%>/resources/js/jquery.js"></script>
<script type="text/javascript" src="<%=base%>/resources/js/jsbn.js"></script>
<script type="text/javascript" src="<%=base%>/resources/js/prng4.js"></script>
<script type="text/javascript" src="<%=base%>/resources/js/rng.js"></script>
<script type="text/javascript" src="<%=base%>/resources/js/rsa.js"></script>
<script type="text/javascript" src="<%=base%>/resources/js/base64.js"></script>
<script type="text/javascript" src="<%=base%>/resources/js/common.js"></script>
<script type="text/javascript" src="<%=base%>/resources/js/jquery.nivo.slider.js"></script>
<style type="text/css">
.login .input0 .input1{ 
	height:36px; 
	line-height:36px; 
	margin-bottom:20px;
	margin-top:-15px;
	margin-left:60px;
}
.login .input0 .input1 p{ 
	width:60px; 
	margin-right:8px; 
	text-align:right;
	margin-top:-15px;	
}

.login .input0 .input3 { 
	margin-right:8px;
	text-align:left;
	margin-bottom:25px;	
}


.login .input0 .input3 .submit { 
	margin-top:50px;
	margin-left:-120px;
}

a{
	font-size:12px;
}
body,td,table{
	font-size:12px;
}
.user_input{
	height:19px;
	width:175px;
	border:1px solid #b2c9f6;
	font-size:14px;
}
.user_input_over{
	height:19px;
	width:175px;
	border:1px solid #faa72e;
	font-size:14px;
}
.input2 a.org {
	background: #F28529;
}
.input2 a {
	font-size: 14px;
	margin-top: -1px;
	float: left;
	display: block;
	width: 81px;
	height: 25px;
	line-height: 26px;
	color: #535352;
	border-radius: 5px;
	margin-right: 12px;
	font-weight: bold;
}
.textc {
	text-align: center;
}
.font {
	font-family: "微软雅黑";
}
/**turnshow样式 Begin**/
#slideshow {
	height: 300px;
	width: 300px;
	position: relative;
	left: -5px;
}
/**设置白框背景的大小**/
#slideshow .bg {
	height: 500px;
	width: 550px;
	background-size: 550px 475px;
}
/**设置左右移动的箭头的属性**/
#sliderturnshow {
	width: 550px;
	position: relative;
	top: 30px;
	margin: 0 20px 20px -35px;
}
#sliderturnshow .holder { 
	width: 480px; 
	height: 78px;
	float: center;
}
body{
	background: #ECFAFF url(../resources/images/login_bg.jpg) no-repeat center top;
}
</style>


<script type="text/javascript">
	$().ready( function() {
		var $loginForm = $("#loginForm");
		var $enPassword = $("#enPassword");
		var $username = $("#username");
		var $password = $("#password");
		var $captcha = $("#captcha");
		var $captchaImage = $("#captchaImage");
		var $isRememberUsername = $("#isRememberUsername");
		
		// 记住用户名
		if(getCookie("adminUsername") != null) {
			$isRememberUsername.prop("checked", true);
			$username.val(getCookie("adminUsername"));
			$password.focus();
		} else {
			$isRememberUsername.prop("checked", false);
			$username.focus();
		}
		
		// 更换验证码
		$captchaImage.click( function() {
			$captchaImage.attr("src", "<%=base%>/common/captcha.do?captchaId=<%=captchaId%>&timestamp=" + (new Date()).valueOf());
		});
		
		// 表单验证、记住用户名
		$loginForm.submit( function() {
			if ($username.val() == "") {
				$.message("warn", "<%=SpringUtils.getMessage("admin.login.usernameRequired")%>");
				return false;
			}
			if ($password.val() == "") {
				$.message("warn", "<%=SpringUtils.getMessage("admin.login.passwordRequired")%>");
				return false;
			}
			if ($captcha.val() == "") {
				$.message("warn", "<%=SpringUtils.getMessage("admin.login.captchaRequired")%>");
				return false;
			}
			
			if ($isRememberUsername.prop("checked")) {
				addCookie("adminUsername", $username.val(), {expires: 7 * 24 * 60 * 60});
			} else {
				removeCookie("adminUsername");
			}
			
			var rsaKey = new RSAKey();
			rsaKey.setPublic(b64tohex("<%=modulus%>"), b64tohex("<%=exponent%>"));
			var enPassword = hex2b64(rsaKey.encrypt($password.val()));
			$enPassword.val(enPassword);
		});
		
		<%if (message != null) {%>
			$.message("error", "<%=SpringUtils.getMessage(message, setting.getAccountLockCount())%>");
		<%}%>
	});		
	$(window).load(function() {
		$('#slider').nivoSlider({
			effect:'random',
			slices:15,
			animSpeed:500,
			pauseTime:5000,
			startSlide:0, // Set starting Slide (0 index)
			directionNav: false, // Next and Prev
			directionNavHide:false, // Only show on hover
			controlNav:false, // 1,2,3...
			controlNavThumbs:false, //Use thumbnails for Control Nav
			pauseOnHover:true, //Stop animation while hovering
			manualAdvance:false, //Force manual transitions
			captionOpacity:0.8, //Universal caption opacity
			beforeChange: function(){},
			afterChange: function(){},
			slideshowEnd: function(){} //Triggers after all slides have been shown
		});
		$("#showusername").val($("#username").val());
	});
</script>
<%} else {%>
<title>南方基地客服系统 | 登陆</title>
<meta http-equiv="expires" content="0" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Cache-Control" content="no-cache" />
<meta name="author" content="cmsz" />
<link rel="stylesheet" type="text/css" href="<%=base%>/resources/css/common.css"/>
<link rel="stylesheet" type="text/css" href="<%=base%>/resources/css/login.css"/>
<link rel="stylesheet" type="text/css" href="<%=base%>/resources/css/global2.css"/>
<link rel="stylesheet" type="text/css" href="<%=base%>/resources/css/css2.css"/>
<link rel="stylesheet" type="text/css" href="<%=base%>/resources/css/nivo-slider.css"/>	
<%}%>
</head>
<body>
<div id = "maskid" margin-left:auto;margin-right:auto; class="Loign_Mainwaap">
	<div id="login" style="width:1000px; margin:115px auto;">
		<!-- <div class="logo"><img src="<%=base%>/resources/images/login_logo.png" width="241" height="37" style="display: block;float:left;" /></div> -->
	    <div class="main">
	    	<div class="l_ban">
	           	<!-- 代码 开始 -->
			    <div class="comiis_wrapad" id="slideContainer">
			        <div id="frameHlicAe" class="frame cl">
			            <div class="temp"></div>
			            <div class="block">
							<div id="slider">
						        <img src="<%=base%>/resources/images/login_bg_1.jpg" width="968" height="348" alt="1" />
						        <img src="<%=base%>/resources/images/login_bg_2.jpg" width="968" height="348" alt="2" />
						        <img src="<%=base%>/resources/images/login_bg_3.jpg" width="968" height="348" alt="3" />
						        <img src="<%=base%>/resources/images/login_bg_4.jpg" width="968" height="348" alt="4" />
						        <img src="<%=base%>/resources/images/login_bg_5.jpg" width="968" height="348" alt="5" />
						    </div> 
			            </div>
			        </div>
			    </div>
			</div>  
			<div class="loginbar">
				<form id="loginForm" action="login.jsp" method="post" style="padding: 24px 0 0 36px;display: block;">
					<input type="hidden" id="enPassword" name="enPassword" />
					<input type="hidden" name="captchaId" value="<%=captchaId%>" />
					<table border="0" cellpadding="0" cellspacing="0" style="margin-top: 10px;">
						<tr>
							<td class="login_title">用户名：</td>
							<td>
								<div class="input1">
									<input type="text" id="username" name="username" maxlength="20" style="margin-top: 1px;"/>
								</div>
							</td>
						</tr>
						<tr>
							<td class="login_title">密 码：</td>
							<td>
								<div class="input1">
									<input type="password" id="password" maxlength="20" autocomplete="off" style="margin-top: 1px;"/>
								</div>
							</td>
						</tr>
						<tr>
							<td class="login_title">验证码：</td>
							<td>
								<div class="input2">
									<div style="float: left;">
										<input type="text" id="captcha" name="captcha" class="text captcha" maxlength="4" autocomplete="off" style="width: 88px; margin-top: 1px;"/>
									</div>
									<div style="height: 23px; padding-top: 0px; float: left; padding-right: 3px; width: 60px; margin-top: 0px;">
										<img id="captchaImage" class="captchaImage" src="<%=base%>/common/captcha.do?captchaId=<%=captchaId%>" title="<%=SpringUtils.getMessage("admin.captcha.imageTitle")%>" style="width:70px;height:25px;padding-left:3px;"/>
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<td colspan="3">
								<div class="input3">
									<div style="float: left; width: 50px; height: 31px; padding-left: 20px; margin: 0px 20px 0px 0px;">
										<input type="image" src="<%=base%>/resources/images/dl_btn.jpg" id="sub" name="sub" style="cursor: pointer;"/>
									</div>
									<div style="float: left; width: 50px; height: 31px; padding-left: 20px;  margin-top: 0px 20px 0px 0px;">
										<img src="<%=base%>/resources/images/cz_btn.jpg" style="cursor: pointer;" onClick="javascript:clearEnter()" />
									</div>
								</div>
							</td>
						</tr>
					</table>
				</form>
			</div>
		</div>
	</div>
</div>
</body>							
</html>