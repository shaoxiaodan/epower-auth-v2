package edu.nau.epower_auth.dao;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 角色类
 * 
 * @ClassName: Role
 * @Description: TODO
 * @author Xiaodan Shao(xs94@nau.edu)
 * @date 2024-07-06 09:53:55
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role implements Serializable {

	private int id;

	private String name;

	private String description;

	private Date createTime;

	private Date updateTime;

	private boolean isRoot;

	private int level;

	private List<Menu> menuList;

}
