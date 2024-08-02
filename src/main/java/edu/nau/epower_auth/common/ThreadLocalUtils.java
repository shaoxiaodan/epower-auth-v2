package edu.nau.epower_auth.common;

/**
 * ThreadLocal工具类
 * 
 * @ClassName: ThreadLocalUtils
 * @Description: TODO
 * @author Xiaodan Shao(xs94@nau.edu)
 * @date 2024-08-01 08:19:59
 */
public class ThreadLocalUtils {

	// 提供ThreadLocal对象
	private static final ThreadLocal THREAD_LOCAL = new ThreadLocal();

	// 根据键来获取值
	public static <T> T get() {
		return (T) THREAD_LOCAL.get();
	}

	// 存储键值对
	public static void set(Object value) {
		THREAD_LOCAL.set(value);
	}

	// 清除ThreadLocal，防止内存泄露
	public static void remove() {
		THREAD_LOCAL.remove();
	}

}
