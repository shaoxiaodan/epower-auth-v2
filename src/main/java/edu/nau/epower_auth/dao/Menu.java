package edu.nau.epower_auth.dao;

import java.io.Serializable;
import java.util.Date;
/**
 * 
 */
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 菜单类
 * 
 * @ClassName: Menu
 * @Description: TODO
 * @author Xiaodan Shao(xs94@nau.edu)
 * @date 2024-07-06 09:01:13
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Menu implements Serializable {

	private int id;

	private String name;

	private String description;

	private Date createTime;

	private Date updateTime;

	private List<Url> urlList;

}
