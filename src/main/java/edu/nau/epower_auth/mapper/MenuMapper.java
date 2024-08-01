package edu.nau.epower_auth.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.mapping.FetchType;

import edu.nau.epower_auth.dao.Menu;

/**
 * 菜单映射
 * 
 * @ClassName: MenuMapper
 * @Description: TODO
 * @author Xiaodan Shao(xs94@nau.edu)
 * @date 2024-07-06 09:24:18
 */
public interface MenuMapper {

	// 根据角色id，获取所有菜单，并装配资源路径url
	@Select("SELECT m.id as id, m.name as name, m.description as description, "
			+ " m.create_time as create_time, m.update_time as update_time" 
			+ "	FROM role_menu rm"
			+ "	LEFT JOIN menu m on rm.menu_id = m.id" 
			+ "	WHERE rm.role_id = #{roleId}")
	@Results(value = { @Result(id = true, property = "id", column = "id"), 
			@Result(property = "path", column = "path"),
			@Result(property = "description", column = "description"),
			@Result(property = "urlList", column = "id", 
				many = @Many(select = "edu.nau.epower_auth.mapper.UrlMapper.findUrlByMenuId", 
				fetchType = FetchType.DEFAULT)) })
	public List<Menu> findMenuByRoleId(@Param("roleId") int roleId);

	// 查询所有菜单
	@Select("SELECT * FROM menu")
	public List<Menu> listMenu();

	// 根据菜单id，查询菜单
	@Select("SELECT * FROM menu WHERE id = #{menuId}")
	public Menu findMenu(@Param("menuId") int menuId);

	// 添加新的菜单，并返回自增id
	@Insert("INSERT INTO menu(name, description) VALUES(#{name}, #{description})")
	@Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id") // 返回自增id
	public int insertMenu(Menu menu);

	// 根据菜单id，更新菜单
	@Update("UPDATE menu SET name = #{name}, description = #{description}, update_time = CURRENT_TIMESTAMP WHERE id = #{id}")
	public int updateMenu(Menu menu);

	// 工具菜单id，删除菜单
	@Delete("DELETE FROM menu WHERE id = #{menuId}")
	public int deleteMenu(@Param("menuId") int menuId);

}
