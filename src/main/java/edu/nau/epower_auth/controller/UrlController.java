package edu.nau.epower_auth.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import edu.nau.epower_auth.common.ConstantUtils;
import edu.nau.epower_auth.common.HtmlUtils;
import edu.nau.epower_auth.common.SessionUtils;
import edu.nau.epower_auth.dao.Role;
import edu.nau.epower_auth.dao.Url;
import edu.nau.epower_auth.service.UrlService;
import jakarta.servlet.http.HttpServletRequest;

/**
 * URL资源控制器
 * 
 * @ClassName: UrlController
 * @Description: TODO
 * @author Xiaodan Shao(xs94@nau.edu)
 * @date 2024-07-13 03:19:49
 */
@Controller
@RequestMapping("/url")
public class UrlController {

	@Autowired
	private UrlService urlService;

	// logback日志
	private static final Logger logger = LoggerFactory.getLogger(UrlController.class);

	/*
	 * URL列表page
	 */
	@GetMapping("list")
	public String listUrl(HttpServletRequest request, @RequestParam(defaultValue = "1") int pageNum,
			ModelMap modelMap) {

		// 获取当前登录用户的默认角色
		Role defRole = (Role) SessionUtils.retrieveSession(request, ConstantUtils.SESSION_LOGIN_USER_DEF_ROLE);

		// 添加前端操作按钮的控制对象
		modelMap.addAttribute(ConstantUtils.PAGE_VERIFY_REQ, "/url");
		modelMap.addAttribute(ConstantUtils.PAGE_VERIFY_URLS, HtmlUtils.getUrlListByDefRole(defRole));

		// 列表 + 分页
		PageHelper.startPage(pageNum, ConstantUtils.PAGE_SIZE);
		List<Url> urls = urlService.listUrl();
		PageInfo<Url> pageInfo = new PageInfo<Url>(urls);

		modelMap.addAttribute("urls", urls);
		modelMap.addAttribute(ConstantUtils.PAGE_INFO, pageInfo);

		return "system/url/list";
	}

	/*
	 * URL添加page
	 */
	@GetMapping("add")
	public String addPage(HttpServletRequest request, ModelMap modelMap) {

		// 获取当前登录用户的默认角色
		Role defRole = (Role) SessionUtils.retrieveSession(request, ConstantUtils.SESSION_LOGIN_USER_DEF_ROLE);

		// 添加前端操作按钮的控制对象
		modelMap.addAttribute(ConstantUtils.PAGE_VERIFY_REQ, "/url");
		modelMap.addAttribute(ConstantUtils.PAGE_VERIFY_URLS, HtmlUtils.getUrlListByDefRole(defRole));

		// 设置boolean值的isEntrance属性
		Map<String, Object> optMaps = new HashMap<String, Object>();
		optMaps.put("是", 1);
		optMaps.put("否", 0);

		// 返回页面表单的绑定对象
		modelMap.addAttribute("addurl", new Url());
		modelMap.addAttribute("optmaps", optMaps);

		return "system/url/add";
	}

	/*
	 * URL添加
	 */
	@PostMapping("add")
	public String addUrl(Url url) {
		int add = urlService.addUrl(url);
		return "redirect:list";
	}

	/*
	 * URL更新page
	 */
	@GetMapping("update")
	public String updatePage(HttpServletRequest request, @RequestParam("id") int urlId, ModelMap modelMap) {

		// 获取当前登录用户的默认角色
		Role defRole = (Role) SessionUtils.retrieveSession(request, ConstantUtils.SESSION_LOGIN_USER_DEF_ROLE);

		// 添加前端操作按钮的控制对象
		modelMap.addAttribute(ConstantUtils.PAGE_VERIFY_REQ, "/url");
		modelMap.addAttribute(ConstantUtils.PAGE_VERIFY_URLS, HtmlUtils.getUrlListByDefRole(defRole));

		// 设置boolean值的isEntrance属性
		Map<String, Object> optMaps = new HashMap<String, Object>();
		optMaps.put("是", 1);
		optMaps.put("否", 0);

		// 获取URL列表
		Url url = urlService.getUrl(urlId);

		// 表单绑定对象
		modelMap.addAttribute("updateurl", url);
		modelMap.addAttribute("optmaps", optMaps);

		return "system/url/update";
	}

	/*
	 * URL更新
	 */
	@PostMapping("update")
	public String updateUrl(Url url) {
		int update = urlService.updateUrl(url);
		return "redirect:list";
	}

	/*
	 * URL删除
	 */
	@GetMapping("remove")
	public String removeUser(@RequestParam("id") int urlId) {
		int remove = urlService.removeUrl(urlId);
		return "redirect:list";
	}

}
