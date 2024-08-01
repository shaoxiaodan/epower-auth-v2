package edu.nau.epower_auth.dao;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户-角色关联类
 * 
 * @ClassName: UserRole
 * @Description: TODO
 * @author Xiaodan Shao(xs94@nau.edu)
 * @date 2024-07-06 10:19:55
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRole implements Serializable {

	private int id;

	private int userId;

	private int roleId;

}
