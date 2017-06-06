<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${message("admin.admin.list")} - Powered By CMSZ</title>
<meta name="author" content="teamsun" />
<link rel="stylesheet" type="text/css" href="${base}/resources/css/common.css"/>
<script type="text/javascript" src="${base}/resources/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/js/list.js"></script>
<script type="text/javascript">
$().ready(function() {

	[@flash_message /]

});
</script>
</head>
<body>
	<div class="path">
		${message("admin.main.home")} &raquo; ${message("admin.admin.list")} <span>(${message("admin.page.total", page.total)})</span>
	</div>
	<form id="listForm" action="list.do" method="get">
		<div class="bar">
			<a href="add.do" class="iconButton">
				<span class="addIcon">&nbsp;</span>${message("admin.common.add")}
			</a>
			<div class="buttonWrap">
				<a href="javascript:;" id="deleteButton" class="iconButton disabled">
					<span class="deleteIcon">&nbsp;</span>${message("admin.common.delete")}
				</a>
				<a href="javascript:;" id="refreshButton" class="iconButton">
					<span class="refreshIcon">&nbsp;</span>${message("admin.common.refresh")}
				</a>
				<div class="menuWrap">
					<a href="javascript:;" id="pageSizeSelect" class="button">
						${message("admin.page.pageSize")}<span class="arrow">&nbsp;</span>
					</a>
					<div class="popupMenu">
						<ul id="pageSizeOption">
							<li>
								<a href="javascript:;"[#if page.pageSize == 10] class="current"[/#if] val="10">10</a>
							</li>
							<li>
								<a href="javascript:;"[#if page.pageSize == 20] class="current"[/#if] val="20">20</a>
							</li>
							<li>
								<a href="javascript:;"[#if page.pageSize == 50] class="current"[/#if] val="50">50</a>
							</li>
							<li>
								<a href="javascript:;"[#if page.pageSize == 100] class="current"[/#if] val="100">100</a>
							</li>
						</ul>
					</div>
				</div>
			</div>
		</div>
		<table id="listTable" class="list">
			<tr>
				<th class="check">
					<input type="checkbox" id="selectAll" />
				</th>
				<th>
					<a href="javascript:;" class="sort" name="username">${message("Admin.username")}</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="email">${message("Admin.email")}</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="name">${message("Admin.name")}</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="department">${message("Admin.department")}</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="loginDate">${message("Admin.loginDate")}</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="loginIp">${message("Admin.loginIp")}</a>
				</th>
				<th>
					<span>${message("admin.admin.status")}</span>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="createDate">${message("admin.common.createDate")}</a>
				</th>
				<th>
					<span>${message("admin.common.handle")}</span>
				</th>
			</tr>
			[#list page.list as admin]
				<tr>
					<td>
						<input type="checkbox" name="ids" value="${admin.id}" />
					</td>
					<td>
						${admin.username}
					</td>
					<td>
						${admin.email}
					</td>
					<td>
						${admin.name}
					</td>
					<td>
						${admin.department}
					</td>
					<td>
						[#if admin.loginDate??]
							<span title="${admin.loginDate?string("yyyy-MM-dd HH:mm:ss")}">${admin.loginDate?string("yyyy-MM-dd HH:mm:ss")}</span>
						[#else]
							-
						[/#if]
					</td>
					<td>
						${(admin.loginIp)!"-"}
					</td>
					<td>
						[#if !admin.isEnabled]
							<span class="red">${message("admin.admin.disabled")}</span>
						[#elseif admin.isLocked]
							<span class="red"> ${message("admin.admin.locked")} </span>
						[#else]
							<span class="green">${message("admin.admin.normal")}</span>
						[/#if]
					</td>
					<td>
						<span title="${admin.createDate?string("yyyy-MM-dd HH:mm:ss")}">${admin.createDate?string("yyyy-MM-dd HH:mm:ss")}</span>
					</td>
					<td>
						<a href="edit.do?id=${admin.id}">[${message("admin.common.edit")}]</a>
					</td>
				</tr>
			[/#list]
		</table>
		[@pagination pageNum = page.pageNum pages = page.pages]
			[#include "/include/pagination.ftl"]
		[/@pagination]
	</form>
</body>
</html>