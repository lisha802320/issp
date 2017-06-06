<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${message("admin.error.title")}</title>
<link href="${base}/resources/css/common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/js/list.js"></script>
<link href="${base}/resources/css/error.css" rel="stylesheet" type="text/css" />
</head>
<script type="text/javascript">
	$().ready(function() {
		[@flash_message /]
	});
</script>
<body>
	<div class="wrap">
		<div class="error">
			<dl>
				<dt>${message("admin.error.message")}</dt>
				[#if content??]
					<dd>${content}</dd>
				[/#if]
				[#if constraintViolations?has_content]
					[#list constraintViolations as constraintViolation]
						<dd>[${constraintViolation.propertyPath}] ${constraintViolation.message}</dd>
					[/#list]
				[/#if]
				<dd>
					<a href="javascript:;" onclick="window.history.back(); return false;">${message("admin.error.back")}</a>
				</dd>
			</dl>
		</div>
	</div>
</body>
</html>