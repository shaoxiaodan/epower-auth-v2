package edu.nau.epower_auth.service;

import java.util.List;

import edu.nau.epower_auth.dao.Url;

/**
 * 资源路径服务接口
 * 
 * @ClassName: UrlService
 * @Description: TODO
 * @author Xiaodan Shao(xs94@nau.edu)
 * @date 2024-07-07 01:03:46
 */
public interface UrlService {

	// URL列表
	public List<Url> listUrl();

	// 获取URL
	public Url getUrl(int urlId);

	// 获取URL(by path)
	public Url getUrlByPath(String path);

	// 获取URL by menu id
	public List<Url> findUrlByMenuId(int menuId);

	// 添加URL
	public int addUrl(Url url);

	// 更新URL
	public int updateUrl(Url url);

	// 删除URL
	public int removeUrl(int urlId);
}
