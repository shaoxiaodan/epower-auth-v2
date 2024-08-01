package edu.nau.epower_auth.common;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.thymeleaf.util.ListUtils;
import org.thymeleaf.util.MapUtils;

import edu.nau.epower_auth.dao.Menu;
import edu.nau.epower_auth.dao.Role;
import edu.nau.epower_auth.dao.Url;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletResponse;

public class HtmlUtils {

	/*
	 * 渲染前端HTML页面的提示窗口
	 */
	public static void getWebPageAlert(HttpServletResponse response, String msgStr, String urlStr) throws IOException {
		response.setContentType("text/html; charset=utf-8");
		PrintWriter writer = response.getWriter();
		writer.println("<script language='javascript'>");
		if (StringUtils.isEmpty(urlStr)) {
			writer.println("history.go(-1)"); // 返回（历史）上一页
		} else {
			writer.println("window.location.href = '" + urlStr + "';"); // 直接跳转url
		}
		writer.println("alert('" + msgStr + "');");
		writer.println("</script>");
	}

	/*
	 * 根据当前用户的def role来重新装配角色下的url，并用户前端按钮是否显示。
	 */
	public static List<Url> getUrlListByDefRole(Role defRole) {

		List<Url> newUrlList = new ArrayList<Url>();
		Map<String, Url> tempUrlMap = new LinkedHashMap<String, Url>();

		// 1，获取当前用户默认角色下的url，并封装在map中（排除重复path）
		if (defRole != null) {
			if (!ListUtils.isEmpty(defRole.getMenuList())) {
				for (Menu menu : defRole.getMenuList()) {
					if (!ListUtils.isEmpty(menu.getUrlList())) {
						for (int i = 0; i < menu.getUrlList().size(); i++) {
							tempUrlMap.put(menu.getUrlList().get(i).getPath(), menu.getUrlList().get(i));
						}
					}
				}
			}
		}

		// 2，从map中再次遍历重来，装入newUrlList后返回
		if (!MapUtils.isEmpty(tempUrlMap)) {
			// 3，通过迭代器，获取value（URL对象）
			Iterator<Map.Entry<String, Url>> itr = tempUrlMap.entrySet().iterator();
			while (itr.hasNext()) {
				Map.Entry<String, Url> entry = itr.next();
				newUrlList.add(entry.getValue());
			}
		}

		return newUrlList;
	}

}
