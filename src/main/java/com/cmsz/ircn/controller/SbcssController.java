package com.cmsz.ircn.controller;

import java.util.concurrent.Callable;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cmsz.ircn.bean.Admin;
import com.cmsz.ircn.bean.Setting;
import com.cmsz.ircn.log.monitor.MonitorableLog;
import com.cmsz.ircn.service.AdminService;

@Controller
@RequestMapping("/cs")
public class SbcssController {
	@Resource
	private Setting setting;
	@Resource
	private MonitorableLog log;
	@Resource
	private AdminService adminService;
	

	/**
	 * 接收第三方平台请求
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/Receiver", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public @ResponseBody Callable<String> receive(HttpServletRequest request, HttpServletResponse response) {
		return new Callable<String>() {
			@Override
			public String call() {
				Admin admin = adminService.find(Long.valueOf(1));
				System.out.println("=="+admin.getUsername());
				admin = adminService.findByUsername("admin");
				System.out.println(admin.getUsername());
				return "----------";
			}
		};
	}

}
