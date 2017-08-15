package com.ay.framework.util;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.lowagie.text.pdf.codec.Base64.InputStream;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/**
 * 处理图片文件工具类实现对图片进行缩放功能
 * @author lyl
 *
 */
public class ImageMinCutUtil {

	/**
	 * 将图片按照指定宽度、高度、质量百分比按比例进行缩略,并绘制部分的图片类容适应大小并切除一部分
	 * @param fileName 源图片文件名称
	 * @param targetFileName 目标文件名称
	 * @param width 目标宽度
	 * @param height 目标调度
	 * @param per 缩略百分比
	 * @return Integer 处理结果：0失败,1成功
	 */
	public static Integer toSmallPic(String fileName, String targetFileName, Integer width, Integer height, Float per){
		File srcFile = new File(fileName);
		return toSmallPic(srcFile, targetFileName, width, height, per);
	}
	
	/**
	 * 将图片按照指定宽度、高度、质量百分比按比例进行缩略,并绘制部分的图片类容适应大小并切除一部分
	 * @param srcFile 源图片文件
	 * @param targetFileName 目标文件名称
	 * @param width 目标宽度
	 * @param height 目标调度
	 * @param per 缩略百分比
	 * @return Integer 处理结果：0失败,1成功
	 */
	public static Integer toSmallPic(File srcFile, String targetFileName, Integer width, Integer height, Float per){
		// 处理状态：0失败,1成功
		Integer status = 0;
		if(null == srcFile || null == targetFileName){
			return status;
		}
		try {
			BufferedImage src = ImageIO.read(srcFile);
			// 获取源图片的宽高
			int old_w = src.getWidth(null);
			int old_h = src.getHeight(null);
			int new_w = 0;
			int new_h = 0;
			// 获取源图宽高与指定宽高之间的倍数
			double w2 = (old_w * 1.00) / (width * 1.00);
			double h2 = (old_h * 1.00) / (height * 1.00);
			// 图片跟据长宽留白，成一个正方形图。
			BufferedImage oldpic;//= new BufferedImage(old_w,old_h, BufferedImage.TYPE_INT_RGB);
			if (old_w > old_h) {
				oldpic = new BufferedImage(old_h, old_h,
						BufferedImage.TYPE_INT_RGB);
			} else {
				if (old_w < old_h) {
					oldpic = new BufferedImage(old_w, old_w,
							BufferedImage.TYPE_INT_RGB);
				} else {
					oldpic = new BufferedImage(old_w, old_h,
							BufferedImage.TYPE_INT_RGB);
				}
			}
			Graphics2D g = oldpic.createGraphics();
			if (old_w > old_h) {
				g.fillRect(0, 0, old_h, old_h);//填充一个矩形的区域，从0,0的坐标开始绘制 并以图片短的一边设置矩形大小。
				g.drawImage(src, 0, 0, old_w, old_h, null, null);//绘制图片到制定的矩形，并从坐标位置x,y开始。
			} else {
				if (old_w < old_h) {
					g.fillRect(0, 0,old_w, old_w);
					g.drawImage(src,0, 0, old_w, old_h, null, null);
				} else {
					g.drawImage(src.getScaledInstance(old_w, old_h, Image.SCALE_SMOOTH), 0, 0, null);
				}
			}
			g.dispose();
			src = oldpic;
			// 图片调整为方形结束
			if (old_w > width){
				new_w = (int) Math.round(old_w / w2);
			} else {
				new_w = old_w;
			}
			// 计算新图长宽
			if (old_h > height){
				new_h = (int) Math.round(old_h / h2);
			} else {
				new_h = old_h;
			}
			BufferedImage tag = new BufferedImage(new_w, new_h, BufferedImage.TYPE_INT_RGB);
			// 绘制缩小后的图
			tag.getGraphics().drawImage(src.getScaledInstance(new_w, new_h, Image.SCALE_SMOOTH), 0, 0, null);
			File outFile = new File(targetFileName);
			if(!outFile.getParentFile().exists()){
				outFile.getParentFile().mkdirs();
			}
			
			// 输出到文件流
			FileOutputStream newimage = new FileOutputStream(outFile);
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(newimage);
			JPEGEncodeParam jep = JPEGCodec.getDefaultJPEGEncodeParam(tag);
			// 压缩质量
			jep.setQuality(per, true);
			encoder.encode(tag, jep);
			// 近JPEG编码
			newimage.close();
			// 处理成功
			status = 1;
		} catch (IOException e) {
			// TODO 暂时打印到控制台,若有日志记录器可记录到日志文件中
			e.printStackTrace();
		}
		return status;
	}
	/**
	 * 将图片按照指定宽度、高度、质量百分比按比例进行缩略,并绘制部分的图片类容适应大小并切除一部分
	 * @param srcFile 源图片文件
	 * @param targetFileName 目标文件名称
	 * @param width 目标宽度
	 * @param height 目标调度
	 * @param per 缩略百分比
	 * @return byte 处理结果二进制
	 */
	public static byte[] convertImg(File srcFile, String targetFileName, Integer width, Integer height, Float per){
		if(null == srcFile || null == targetFileName){
			return null;
		}
		byte[] newImg=null;
		try {
			BufferedImage src = ImageIO.read(srcFile);
			// 获取源图片的宽高
			int old_w = src.getWidth(null);
			int old_h = src.getHeight(null);
			int new_w = 0;
			int new_h = 0;
			// 获取源图宽高与指定宽高之间的倍数
			double w2 = (old_w * 1.00) / (width * 1.00);
			double h2 = (old_h * 1.00) / (height * 1.00);
			// 图片跟据长宽留白，成一个正方形图。
			BufferedImage oldpic;//= new BufferedImage(old_w,old_h, BufferedImage.TYPE_INT_RGB);
			if (old_w > old_h) {
				oldpic = new BufferedImage(old_h, old_h,
						BufferedImage.TYPE_INT_RGB);
			} else {
				if (old_w < old_h) {
					oldpic = new BufferedImage(old_w, old_w,
							BufferedImage.TYPE_INT_RGB);
				} else {
					oldpic = new BufferedImage(old_w, old_h,
							BufferedImage.TYPE_INT_RGB);
				}
			}
			Graphics2D g = oldpic.createGraphics();
			if (old_w > old_h) {
				g.fillRect(0, 0, old_h, old_h);//填充一个矩形的区域，从0,0的坐标开始绘制 并以图片短的一边设置矩形大小。
				g.drawImage(src, 0, 0, old_w, old_h, null, null);//绘制图片到制定的矩形，并从坐标位置x,y开始。
			} else {
				if (old_w < old_h) {
					g.fillRect(0, 0,old_w, old_w);
					g.drawImage(src,0, 0, old_w, old_h, null, null);
				} else {
					g.drawImage(src.getScaledInstance(old_w, old_h, Image.SCALE_SMOOTH), 0, 0, null);
				}
			}
			g.dispose();
			src = oldpic;
			// 图片调整为方形结束
			if (old_w > width){
				new_w = (int) Math.round(old_w / w2);
			} else {
				new_w = old_w;
			}
			// 计算新图长宽
			if (old_h > height){
				new_h = (int) Math.round(old_h / h2);
			} else {
				new_h = old_h;
			}
			BufferedImage tag = new BufferedImage(new_w, new_h, BufferedImage.TYPE_INT_RGB);
			// 绘制缩小后的图
			tag.getGraphics().drawImage(src.getScaledInstance(new_w, new_h, Image.SCALE_SMOOTH), 0, 0, null);
			File outFile = new File(targetFileName);
//			if(!outFile.getParentFile().exists()){
//				outFile.getParentFile().mkdirs();
//			}
			// 输出到文件流
			FileOutputStream newimage = new FileOutputStream(outFile);
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(newimage);
			JPEGEncodeParam jep = JPEGCodec.getDefaultJPEGEncodeParam(tag);
			// 压缩质量
			jep.setQuality(per, true);
			encoder.encode(tag, jep);
			// 近JPEG编码
			newImg=fileTobyte(outFile);
			newimage.close();
			// 处理成功
		} catch (IOException e) {
			// TODO 暂时打印到控制台,若有日志记录器可记录到日志文件中
			e.printStackTrace();
		}
		return newImg;
	}
	
	public static byte[] fileTobyte(File file) throws IOException{
		FileInputStream is = new FileInputStream(file);
		// 获取文件大小
		long length = file.length();
		if (length > Integer.MAX_VALUE) {
		    // 文件太大，无法读取
		    throw new IOException("File is to large "+file.getName());
		 }
		// 创建一个数据来保存文件数据
		byte[] bytes = new byte[(int)length];
		// 读取数据到byte数组中
		 int offset = 0;
		 int numRead = 0;
        while (offset < bytes.length
               && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
            offset += numRead;

        }
		// 确保所有数据均被读取
        if (offset < bytes.length) {
            throw new IOException("Could not completely read file "+file.getName());
        }
		is.close();
		return bytes;
	}
	/**
	 * 测试主函数
	 * @param args
	 */
	public static void main(String args[]) throws Exception{
		String srcImg = "E:\\home\\hhsoft\\webservice\\static_as\\njmu\\activeschool\\resources\\rss\\img\\src\\njmu\\";
		String tarDir = "E:\\image2\\";
		File file=new File(srcImg);
		if(file.exists()){
			File[] filelist=file.listFiles();
			for(int i=0;i<filelist.length;i++){
				String targetFileName = tarDir + filelist[i].getName();
				toSmallPic(filelist[i], targetFileName, 118, 118, (float)1.0);
			}
		}  
	}
}
