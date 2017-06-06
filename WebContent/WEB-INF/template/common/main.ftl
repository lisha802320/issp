[#assign shiro=JspTaglibs["/WEB-INF/tld/shiro.tld"] /]
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${message("admin.main.title")} - Powered By CMSZ</title>
<link rel="stylesheet" type="text/css" href="${base}/resources/css/common.css"/>
<link rel="stylesheet" type="text/css" href="${base}/resources/css/main.css"/>
<script type="text/javascript" src="${base}/resources/js/jquery.js"></script>
<style type="text/css">
*{
	font: 12px tahoma, Arial, Verdana, sans-serif;
}
html, body {
	width: 100%;
	height: 100%;
	overflow: hidden;
}
</style>
<script type="text/javascript">
$().ready(function() {
});
</script>
</head>
<body>
	<script type="text/javascript">
		if (self != top) {
			top.location = self.location;
		};
	</script>
	<table class="main">
		<tr>
			<th class="logo">
				<!--<a href="main.do">
					<img src="${base}/resources/images/header_logo.gif" alt="" />
				</a>-->
			</th>
			<th>
				<div id="nav" class="nav" style="width: 800px;">
					<ul>
						<li>
							<a href="javascript:void();" target="iframe">${message("admin.main.home")}</a>
						</li>
						[@shiro.hasPermission name="admin:businessAccepted"]
							<li>
								<a href="../admin/businessAccepted/list.do" target="iframe">${message("admin.main.businessAccepted")}</a>
							</li>
						[/@shiro.hasPermission]
						[@shiro.hasPermission name="admin:businessQuery"]
							<li>
								<a href="../admin/businessQuery/list.do" target="iframe">${message("admin.main.businessQuery")}</a>
							</li>
						[/@shiro.hasPermission]
						[@shiro.hasPermission name="admin:admin"]
							<li>
								<a href="../admin/admin/list.do" target="iframe">${message("admin.main.admin")}</a>
							</li>
						[/@shiro.hasPermission]
						[@shiro.hasPermission name="admin:role"]
							<li>
								<a href="../admin/role/list.do" target="iframe">${message("admin.main.role")}</a>
							</li>
						[/@shiro.hasPermission]
					</ul>
				</div>
				<div class="link">
					<strong>[@shiro.principal /]</strong>
					${message("admin.main.hello")}!
					<a href="../common/logout.jsp" target="_top">[${message("admin.main.logout")}]</a>
				</div>
			</th>
		</tr>
		<tr>
			<td colspan=2>
				<iframe id="iframe" name="iframe" frameborder="0"></iframe>
			</td>
		</tr>
	</table>
</body>
</html>