package edu.nau.epower_auth.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.jdbc.SQL;
import org.apache.ibatis.mapping.FetchType;

import edu.nau.epower_auth.dao.Role;

/**
 * 角色映射
 * 
 * @ClassName: RoleMapper
 * @Description: TODO
 * @author Xiaodan Shao(xs94@nau.edu)
 * @date 2024-07-06 09:56:26
 */
public interface RoleMapper {

	// 根据用户id，查出所有角色，并装配菜单menu
	@Select("SELECT r.id as id, r.name as name, r.description as description, "
			+ " r.create_time as create_time, r.update_time as update_time, "
			+ " r.is_root as is_root, r.level as level FROM user_role ur" 
			+ " LEFT JOIN role r on ur.role_id = r.id" 
			+ " WHERE ur.user_id = #{userId}")
	@Results(value = { 
			@Result(id = true, property = "id", column = "id"), 
			@Result(property = "name", column = "name"),
			@Result(property = "description", column = "description"),
			@Result(property = "menuList", column = "id", 
			many = @Many(select = "edu.nau.epower_auth.mapper.MenuMapper.findMenuByRoleId", 
					fetchType = FetchType.DEFAULT)) })
	public List<Role> findRoleByUserId(@Param("userId") int userId);

	// 查询所有角色
	@Select("SELECT * FROM role")
	public List<Role> listRole();

	// 根据角色id，查询所有排除自己和低于自己角色级别的数据
	@Select("select * from role where id != #{id} and level >= #{level}")
	public List<Role> listRoleNotRoot(Role defRole);

	// 根据角色id，查询角色
	@Select("SELECT * FROM role where id = #{roleId}")
	public Role findRole(@Param("roleId") int roleId);

	// 添加新的角色
	@Insert("INSERT INTO role(name, description, level) VALUES (#{name}, #{description}, #{level})")
	@Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id") // 返回自增id
	public int addRole(Role role);

	// 更具角色id，更新对应角色
	@Update("UPDATE role SET name = #{name}, description = #{description}, update_time = CURRENT_TIMESTAMP WHERE id = #{id}")
	public int updateRole(Role role);

	// 根据角色id，删除对应角色
	@Delete("DELETE FROM role WHERE id = #{roleId}")
	public int removeRole(@Param("roleId") int roleId);

//	@SelectProvider(type = SqlProvider.class, method = "selectNotInBatch")
//	public List<Role> listRoleListByExcludingBatch(List<Role> excludeRoleList);

	/*
	 * 批量SQL提供者
	 */
	class SqlProvider {
		// 批量查询
		public String selectNotInBatch(Map map) {
			List<Role> roleList = (List<Role>) map.get("list");
			StringBuilder sb = new StringBuilder();
			sb.append("SELECT * FROM role WHERE id NOT IN (");
			for (int i = 0; i < roleList.size(); i++) {
				sb.append("'").append(roleList.get(i).getId()).append("'");
				if (i < roleList.size() - 1)
					sb.append(",");
			}
			sb.append(")");
			return sb.toString();
		}

	}
}
