package edu.nau.epower_auth.common;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

@SpringBootTest
public class JwtTest {

	@Test
	public void testGen() {

		Map<String, Object> claims = new HashMap<>();
		claims.put("id", 1);
		claims.put("username", "aaa");

		// 生成jwt token的代码
		String token = JWT.create().withClaim("user", claims) // 添加载荷
				.withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 3)) // 设置过期时间，3小时
				.sign(Algorithm.HMAC256("epower-auth-v2")); // 设置签名（指定算法，配置密钥）

		System.out.println("token=" + token);
		// eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyIjp7ImlkIjoxLCJ1c2VybmFtZSI6ImFhYSJ9LCJleHAiOjE3MjI1NzYxMzZ9.Dq2_Uai4jiUh59UsnCrKKOvKc7tRzR7HX-cToA1N3Bk

	}

	@Test
	public void testParse() {
		// 待验证的token
		String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyIjp7ImlkIjoxLCJ1c2VybmFtZSI6ImFhYSJ9LCJleHAiOjE3MjI1NzYxMzZ9.Dq2_Uai4jiUh59UsnCrKKOvKc7tRzR7HX-cToA1N3Bk";

		// 请求一个JWT的验证器
		JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256("epower-auth-v2")).build(); // 指定算法，配置密钥(与生成token时保持一致)

		// 验证token，生成一个解析后的JWT对象
		DecodedJWT decodedJwt = jwtVerifier.verify(token);

		// 获取所有的载荷
		Map<String, Claim> claims = decodedJwt.getClaims();

		// 打印载荷为user的信息
		System.out.println(claims.get("user"));
		
		/*
		 * 验证失败的3种情况：
		 * 1，如果篡改了头部和载荷部分的数据，验证失败
		 * 2，如果篡改了密钥的数据，验证失败
		 * 3，token过期，验证失败
		 */
		
	}
}
