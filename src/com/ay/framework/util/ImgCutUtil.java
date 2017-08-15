package com.ay.framework.util;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
/**
 * 
 * @author lyl
 *
 */
@SuppressWarnings("all")
public class ImgCutUtil {

	
	public void readUsingImageReader(String src, String dest, int w, int h)
			throws Exception {

		// 取得图片读入器
		Iterator readers = ImageIO.getImageReadersByFormatName("jpg");

		ImageReader reader = (ImageReader) readers.next();

		// 取得图片读入流
		InputStream source = new FileInputStream(src);

		ImageInputStream iis = ImageIO.createImageInputStream(source);

		reader.setInput(iis, true);

//		File src1 = new File(src);
//		BufferedImage img = ImageIO.read(src1);
//		int oldwidth = img.getWidth();
//		int oldheight = img.getHeight();
		// 图片参数
		ImageReadParam param = reader.getDefaultReadParam();
		// 100，200是左上起始位置，300就是取宽度为300的，就是从100开始取300宽，就是横向100~400，同理纵向200~350的区域就取高度150
		
		Rectangle rect = new Rectangle(100, 200, 300, 150);
//		int hh = 0;
//		if (h > 100)
//			hh = (h - 100) / 3;
//		Rectangle rect = new Rectangle(0, hh, 227, 100);
		param.setSourceRegion(rect);

		BufferedImage bi = reader.read(0, param);

		ImageIO.write(bi, "jpg", new File(dest));

	}

	public static void main(String[] args) throws Exception { // main方法测试用
		ImgCutUtil t = new ImgCutUtil();
		t.readUsingImageReader("E:\\image2\\201405080946271795265.png", "E:\\image1\\3.jpg", 118, 118);
	}

}
