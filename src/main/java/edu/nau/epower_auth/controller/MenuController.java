package edu.nau.epower_auth.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.util.ListUtils;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import edu.nau.epower_auth.common.ConstantUtils;
import edu.nau.epower_auth.common.HtmlUtils;
import edu.nau.epower_auth.common.SessionUtils;
import edu.nau.epower_auth.dao.Menu;
import edu.nau.epower_auth.dao.MenuUrl;
import edu.nau.epower_auth.dao.Role;
import edu.nau.epower_auth.dao.Url;
import edu.nau.epower_auth.service.MenuService;
import edu.nau.epower_auth.service.UrlService;
import jakarta.servlet.http.HttpServletRequest;

/**
 * 菜单控制器
 * 
 * @ClassName: MenuController
 * @Description: TODO
 * @author Xiaodan Shao(xs94@nau.edu)
 * @date 2024-07-13 03:05:12
 */
@Controller
@RequestMapping("/menu")
public class MenuController {

	@Autowired
	private MenuService menuService;

	@Autowired
	private UrlService urlService;

	// logback日志
	private static final Logger logger = LoggerFactory.getLogger(MenuController.class);

	/*
	 * 菜单列表page
	 */
	@GetMapping("list")
	public String listMenu(HttpServletRequest request, @RequestParam(defaultValue = "1") int pageNum,
			ModelMap modelMap) {

		// 获取当前登录用户的默认角色
		Role defRole = (Role) SessionUtils.retrieveSession(request, ConstantUtils.SESSION_LOGIN_USER_DEF_ROLE);

		// 添加前端操作按钮的控制对象
		modelMap.addAttribute(ConstantUtils.PAGE_VERIFY_REQ, "/menu");
		modelMap.addAttribute(ConstantUtils.PAGE_VERIFY_URLS, HtmlUtils.getUrlListByDefRole(defRole));

		// 列表 + 分页
		PageHelper.startPage(pageNum, ConstantUtils.PAGE_SIZE);
		List<Menu> menus = menuService.listMenu();
		PageInfo<Menu> pageInfo = new PageInfo<Menu>(menus);

		modelMap.addAttribute("menus", menus);
		modelMap.addAttribute(ConstantUtils.PAGE_INFO, pageInfo);

		return "system/menu/list";
	}

	/*
	 * 添加菜单page
	 */
	@GetMapping("add")
	public String addPage(HttpServletRequest request, ModelMap modelMap) {

		// 获取当前登录用户的默认角色
		Role defRole = (Role) SessionUtils.retrieveSession(request, ConstantUtils.SESSION_LOGIN_USER_DEF_ROLE);

		// 添加前端操作按钮的控制对象
		modelMap.addAttribute(ConstantUtils.PAGE_VERIFY_REQ, "/menu");
		modelMap.addAttribute(ConstantUtils.PAGE_VERIFY_URLS, HtmlUtils.getUrlListByDefRole(defRole));

		// 表单绑定对象
		modelMap.addAttribute("addmenu", new Menu());
		return "system/menu/add";
	}

	/*
	 * 添加菜单
	 */
	@PostMapping("add")
	public String addMenu(Menu menu) {
		int add = menuService.addMenu(menu);
		return "redirect:list";
	}

	/*
	 * 更新菜单page
	 */
	@GetMapping("update")
	public String updatePage(HttpServletRequest request, @RequestParam("mid") int menuId, ModelMap modelMap) {

		// 获取当前登录用户的默认角色
		Role defRole = (Role) SessionUtils.retrieveSession(request, ConstantUtils.SESSION_LOGIN_USER_DEF_ROLE);

		// 添加前端操作按钮的控制对象
		modelMap.addAttribute(ConstantUtils.PAGE_VERIFY_REQ, "/menu");
		modelMap.addAttribute(ConstantUtils.PAGE_VERIFY_URLS, HtmlUtils.getUrlListByDefRole(defRole));

		// 表单绑定对象
		Menu menu = menuService.getMenu(menuId);
		modelMap.addAttribute("updatemenu", menu);

		return "system/menu/update";
	}

	/*
	 * 更新菜单
	 */
	@PostMapping("update")
	public String updateMenu(Menu menu) {
		int update = menuService.updateMenu(menu);
		return "redirect:list";
	}

	/*
	 * 删除菜单
	 */
	@GetMapping("remove")
	public String removeMenu(@RequestParam("mid") int menuId) {

		int remove = menuService.removeMenu(menuId);
		return "redirect:list";
	}

	/*
	 * 菜单授权page
	 */
	@GetMapping("auth")
	public String authPage(HttpServletRequest request, @RequestParam("mid") int menuId, ModelMap modelMap) {

		// 获取当前登录用户的默认角色
		Role defRole = (Role) SessionUtils.retrieveSession(request, ConstantUtils.SESSION_LOGIN_USER_DEF_ROLE);

		// 添加前端操作按钮的控制对象
		modelMap.addAttribute(ConstantUtils.PAGE_VERIFY_REQ, "/menu");
		modelMap.addAttribute(ConstantUtils.PAGE_VERIFY_URLS, HtmlUtils.getUrlListByDefRole(defRole));

		// 根据menu id获取菜单
		Menu menu = menuService.getMenu(menuId);

		// 获取URL列表 - left url
		List<Url> leftUrls = urlService.listUrl();

		// 根据menu id获取菜单的所有URL - right url
		List<Url> rightUrls = urlService.findUrlByMenuId(menuId);

		// 如果menu下面存在有URL数据，先获取URL的id
		List<Integer> dupUrlList = null;

		// 转换需要去重的URL数据
		if (!ListUtils.isEmpty(rightUrls)) {
			dupUrlList = new ArrayList<Integer>();
			for (Url rightUrl : rightUrls) {
				dupUrlList.add(rightUrl.getId());
			}
		}

		// 开始将left url去重
		if (!ListUtils.isEmpty(dupUrlList)) {
			Url urlTmp = null;
			for (int i = 0; i < dupUrlList.size(); i++) {

				for (int j = 0; j < leftUrls.size(); j++) {
					urlTmp = leftUrls.get(j);
					if (dupUrlList.get(i) == urlTmp.getId()) {
						leftUrls.remove(j);
						break;
					}
				}
			}
		}

		// 表单绑定对象
		modelMap.addAttribute("menu", menu);
		modelMap.addAttribute("lefturls", leftUrls);
		modelMap.addAttribute("righturls", rightUrls);

		return "system/menu/auth";
	}

	/*
	 * 菜单授权(URL配置)
	 */
	@PostMapping("auth")
	public String addAuth(@RequestParam("newurls") int[] urlidsArry, @RequestParam("mid") int menuId) {

		// 1，先检查菜单URL是否存在
//		System.out.println("1，检查菜单URL是否存在。。。");
		List<MenuUrl> muList = menuService.getMenuUrl(menuId);

		// 2，已存在菜单URL，先删除该菜单URL
		if (!ListUtils.isEmpty(muList)) {
//			System.out.println("2，先删除该菜单已经存在的URL。。。");
			MenuUrl rmUrl = new MenuUrl();
			rmUrl.setMenuId(menuId);
			int remove = menuService.removeMenuUrlAuth(rmUrl);
		} else {
			// 菜单不存在
//			System.out.println("3，菜单不存在。。。");
		}

		// 3，如果URL的id数组不为空，进入逻辑处理流程；
		if (urlidsArry != null && urlidsArry.length > 0) {
//			System.out.println("3，URL的id数组不为空。。。");
			muList = new ArrayList<MenuUrl>();
			for (int i = 0; i < urlidsArry.length; i++) {
				MenuUrl mUrl = new MenuUrl();
				mUrl.setMenuId(menuId);
				mUrl.setUrlId(urlidsArry[i]);
				muList.add(mUrl);
			}
			// 4，再添加新的角色菜单
//			System.out.println("4，再添加新的角色菜单。。。");
			int auth = menuService.addMenuUrlAuthBatch(muList);
		} else {
			// 5，URL的id数组为空，不做处理
			System.out.println("5，URL的id数组为空，不做任何东西。。。");
		}

		// 6，重定向返回auth列表
		System.out.println("6，重定向返回。。。");
		return "redirect:auth?mid=" + menuId;
	}

}
