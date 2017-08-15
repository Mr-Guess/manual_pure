package com.ay.framework.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;

import com.ay.framework.bean.OperateInfo;
import com.ay.framework.shiro.SystemParameter;

/**
 * 随机认证码的Util工具类
 * 
 * @author PS
 * 
 */
@SuppressWarnings("all")
public class RandomNumUtil {

	/** 图像 */
	private ByteArrayInputStream image;
	/** 验证码 */
	private String str="";

	private RandomNumUtil() {
		init(); // 初始化属性
	}

	/**
	 * 取得randomNumUtil 新的实例
	 * 
	 * @return
	 */
	public static RandomNumUtil Instance() {
		return new RandomNumUtil();
	}

    /**
     * 创建方法
     */
	private void init() {
		String numberStr=SystemParameter.getLogin_authcode_digit();
		String typeStr=SystemParameter.getLogin_authcode_type();
		int number = Integer.parseInt(numberStr);
		int type = Integer.parseInt(typeStr);
		// 在内存中创建图像
		int width = 85, height = 20;
		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		// 获取图形上下文
		Graphics g = image.getGraphics();
		// 生成随机类
		Random random = new Random();
		// 设定背景色
		g.setColor(getRandColor(200, 250));
		g.fillRect(0, 0, width, height);
		// 设定字体
		g.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		// 随机产生155条干扰线，使图象中的认证码不易被其它程序探测到
		g.setColor(getRandColor(160, 200));

		for (int i = 0; i < 155; i++) {
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			int xl = random.nextInt(12);
			int yl = random.nextInt(12);
			g.drawLine(x, y, x + xl, y + yl);
		}
		// 取随机产生的认证码(6位数字)
		String sRand = "";
		for (int i = 0; i < number; i++) {
			String rand = null;
			switch (type) {
			case 0://数字
				rand = String.valueOf(random.nextInt(10));
				break;
			case 1://字母
				char ca1;
				if(random.nextInt(2)==0){
					//大写字母
					ca1 = (char)(random.nextInt(26)+65);
				}else{
					//小写字母
					ca1 = (char)(random.nextInt(26)+97);
				}
				rand = String.valueOf(ca1);
				break;
			case 2://混合				
				if(random.nextInt(2)==0){
					char ca;
					if(random.nextInt(2)==0){
						//大写字母
						ca = (char)(random.nextInt(26)+65);
					}else{
						//小写字母
						ca = (char)(random.nextInt(26)+97);
					}
					rand = String.valueOf(ca);
				}else
					rand = String.valueOf(random.nextInt(10));
				break;
			default:
				rand = String.valueOf(random.nextInt(10));
				break;
			}
			
			sRand += rand;
			// 将认证码显示到图象中
			g.setColor(new Color(20 + random.nextInt(110), 20 + random
					.nextInt(110), 20 + random.nextInt(110)));
			// 调用函数出来的颜色相同，可能是因为种子太接近，所以只能直接生成
			g.drawString(rand, 13 * i + 6, 16);
			/** 赋值验证码 */
			this.str += rand;
		}
		// 图象生效
		g.dispose();
		ByteArrayInputStream input = null;
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		OperateInfo operateInfo = new OperateInfo();
		try {
			ImageOutputStream imageOut = ImageIO
					.createImageOutputStream(output);
			ImageIO.write(image, "JPEG", imageOut);
			imageOut.close();
			input = new ByteArrayInputStream(output.toByteArray());
		} catch (Exception e) {
			System.out.println("验证码图片产生出现错误：" + e.toString());
			operateInfo.setOperateSuccess(false);
			operateInfo.setOperateMessage("验证码错误");
		}
		/** 赋值图像 */
		this.image = input;
	}

	/**
	 * 取得验证码图片
	 * 
	 * @return
	 */
	public ByteArrayInputStream getImage() {
		return this.image;
	}

	/**
	 * 取得图片的验证码
	 * 
	 * @return
	 */
	public String getString() {
		return this.str;
	}

	private Color getRandColor(int fc, int bc) {
		Random random = new Random();
		if (fc > 255)
			fc = 255;
		if (bc > 255)
			bc = 255;
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}
}
