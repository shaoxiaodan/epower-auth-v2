package edu.nau.epower_auth.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import edu.nau.epower_auth.dao.Url;

/**
 * 资源路径映射
 * 
 * @ClassName: UrlMapper
 * @Description: TODO
 * @author Xiaodan Shao(xs94@nau.edu)
 * @date 2024-07-06 09:22:06
 */
public interface UrlMapper {

	// 根据菜单id，获取所有资源路径url
	@Select("SELECT ul.id as id, ul.path as path,"
			+ " ul.static_path as static_path,"
			+ " ul.is_entrance as is_entrance,"
			+ " ul.create_time as create_time,"
			+ " ul.update_time as update_time,"
			+ " ul.description as description" 
			+ " FROM menu_url mu" 
			+ " LEFT JOIN url ul on mu.url_id = ul.id"
			+ " WHERE mu.menu_id = #{menuId}")
	public List<Url> findUrlByMenuId(@Param("menuId") int menuId);

	// 查询所有资源url
	@Select("SELECT * FROM url")
	public List<Url> listUrl();
	
	// 根据资源id,查询对应的资源url
	@Select("SELECT * FROM url WHERE id = #{urlId}")
	public Url findUrl(@Param("urlId") int urlId);
	
	// 根据路径,查询对应的资源url
	@Select("SELECT * FROM url WHERE path = #{path}")
	public Url findUrlByPath(@Param("path") String path);
	
	// 添加新的资源url
	@Insert("INSERT INTO url(path, static_path, is_entrance, description)"
			+ " VALUES (#{path}, #{staticPath}, #{isEntrance}, #{description})")
	@Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id") //返回自增id
	public int insertUrl(Url url);
	
	// 根据资源id,更新资源url
	@Update("UPDATE url SET path = #{path},"
			+ " static_path = #{staticPath},"
			+ " update_time = CURRENT_TIMESTAMP,"
			+ " is_entrance = #{isEntrance},"
			+ " description = #{description}"
			+ " WHERE id = #{id}")
	public int updateUrl(Url url);
	
	// 根据资源id,删除资源url
	@Delete("DELETE FROM url WHERE id = #{urlId}")
	public int deleteUrl(@Param("urlId") int urlId);
	
}
