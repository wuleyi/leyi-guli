package org.guli.common.constant;

import java.math.BigDecimal;

/**
 * 金融系统中关于货币的定义
 * 存储精度：4位，根据金额的体量灵活定义，基金类的需要更大的精度
 * 运算精度：8位，一般是存储精度的两倍
 * 显示精度：2位
 */
public class PriceConstants {

	public static final int STORE_SCALE = 4; //存储精度
	public static final int CAL_SCALE = 8; //运算精度
	public static final int DISPLAY_SCALE = 2; //显示精度
	public static final BigDecimal ZERO = new BigDecimal("0.0000"); //系统级别的0

}