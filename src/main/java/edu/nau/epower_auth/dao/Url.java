package edu.nau.epower_auth.dao;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 资源路径类
 * 
 * @ClassName: Url
 * @Description: TODO
 * @author Xiaodan Shao(xs94@nau.edu)
 * @date 2024-07-06 10:22:20
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Url implements Serializable {

	private int id;

	private String path;

	private String staticPath;

	private String description;

	private Date createTime;

	private Date updateTime;

	private boolean isEntrance;

}
