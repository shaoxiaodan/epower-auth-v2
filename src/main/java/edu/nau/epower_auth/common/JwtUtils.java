package edu.nau.epower_auth.common;

import java.util.Date;
import java.util.Map;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

/**
 * JWT token生成工具类
 * 
 * @ClassName: JwtUtils
 * @Description: TODO
 * @author Xiaodan Shao(xs94@nau.edu)
 * @date 2024-08-01 07:47:07
 */
public class JwtUtils {

	// 密钥
	private static final String KEY = "epower-auth-v2";

	/*
	 * 生成token
	 */
	public static String genToken(Map<String, Object> claims) {
		return JWT.create() // 创建JWT对象
				.withClaim("claims", claims) // 添加载荷claims
				.withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 3)) // 设置过期时间，3小时
				.sign(Algorithm.HMAC256(KEY)); // 设置签名（指定算法，配置密钥）
	}

	/*
	 * 解析token
	 */
	public static Map<String, Object> parseToken(String token) {

		return JWT.require(Algorithm.HMAC256(KEY)) // 申请验证器（与生成token时的算法、密钥要一致）
				.build() // 生成JwtVerfier验证器
				.verify(token) // 开始验证token
				.getClaim("claims") // 获取载荷claims
				.asMap(); // 返回map
	}
}
