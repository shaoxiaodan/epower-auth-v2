package edu.nau.epower_auth.dao;

import java.io.Serializable;

/**
 * 菜单-资源路径关联类
 * 
 * @ClassName: MenuUrl
 * @Description: TODO
 * @author Xiaodan Shao(xs94@nau.edu)
 * @date 2024-07-06 09:01:38
 */
public class MenuUrl implements Serializable {

	private int id;

	private int menuId;

	private int urlId;

	public MenuUrl() {
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getMenuId() {
		return menuId;
	}

	public void setMenuId(int menuId) {
		this.menuId = menuId;
	}

	public int getUrlId() {
		return urlId;
	}

	public void setUrlId(int urlId) {
		this.urlId = urlId;
	}

}
