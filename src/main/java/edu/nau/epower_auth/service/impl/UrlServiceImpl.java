package edu.nau.epower_auth.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import edu.nau.epower_auth.dao.Url;
import edu.nau.epower_auth.mapper.UrlMapper;
import edu.nau.epower_auth.service.UrlService;

/**
 * 资源路径服务实现
 * 
 * @ClassName: UrlServiceImpl
 * @Description: TODO
 * @author Xiaodan Shao(xs94@nau.edu)
 * @date 2024-07-07 01:04:15
 */
@Service
public class UrlServiceImpl implements UrlService {

	@Autowired
	private UrlMapper urlMapper;

	/*
	 * 资源路径列表
	 */
	@Override
	public List<Url> listUrl() {
		return urlMapper.listUrl();
	}

	/*
	 * 获取资源路径
	 */
	@Override
	public Url getUrl(int urlId) {
		return urlMapper.findUrl(urlId);
	}

	/*
	 * 获取资源路径(by path)
	 */
	@Override
	public Url getUrlByPath(String path) {
		return urlMapper.findUrlByPath(path);
	}

	/*
	 * 获取资源路径 by menu id
	 */
	@Override
	public List<Url> findUrlByMenuId(int menuId) {
		return urlMapper.findUrlByMenuId(menuId);
	}

	/*
	 * 添加资源路径
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public int addUrl(Url url) {
		return urlMapper.insertUrl(url);
	}

	/*
	 * 更新资源路径
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public int updateUrl(Url url) {
		return urlMapper.updateUrl(url);
	}

	/*
	 * 删除资源路径
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public int removeUrl(int urlId) {
		return urlMapper.deleteUrl(urlId);
	}

}
