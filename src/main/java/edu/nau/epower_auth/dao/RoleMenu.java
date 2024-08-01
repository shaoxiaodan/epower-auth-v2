package edu.nau.epower_auth.dao;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 角色-菜单关联类
 * 
 * @ClassName: RoleMenu
 * @Description: TODO
 * @author Xiaodan Shao(xs94@nau.edu)
 * @date 2024-07-06 10:21:45
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleMenu implements Serializable {

	private int id;

	private int roleId;

	private int menuId;

}
