package edu.nau.epower_auth.dao;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 菜单-资源路径关联类
 * 
 * @ClassName: MenuUrl
 * @Description: TODO
 * @author Xiaodan Shao(xs94@nau.edu)
 * @date 2024-07-06 09:01:38
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenuUrl implements Serializable {

	private int id;

	private int menuId;

	private int urlId;

}
