package com.ay.framework.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 文件的操作
 * 
 * @author zxy
 * 
 */
public class FileUtil {
	private static final int BUFFER_SIZE = 50 * 1024;// 缓冲区大小

	private static Log log = LogFactory.getLog(FileUtil.class);

	private static boolean copyFile(File from, File to) {
		boolean isSuccess = false;
		InputStream in = null;
		OutputStream out = null;
		try {
			in = new BufferedInputStream(new FileInputStream(from), BUFFER_SIZE);
			out = new BufferedOutputStream(new FileOutputStream(to),
					BUFFER_SIZE);
			byte[] buffer = new byte[BUFFER_SIZE];
			while (in.read(buffer) > 0) {
				out.write(buffer);
			}
			isSuccess = true;
		} catch (Exception e) {
			isSuccess = false;
			log.error(e);
			e.printStackTrace();
		} finally {
			try {
				if (null != in) {
					in.close();
				}
				if (null != out) {
					out.close();
				}
			} catch (Exception e) {
				log.error(e);
				e.printStackTrace();
			}

		}

		return isSuccess;
	}

	/**
	 * 复制文件到指定的地方
	 * 
	 * @param fromPathname
	 *            源文件
	 * @param toPathname
	 *            指定的文件
	 * @return
	 */
	public static boolean copyFile(String fromPathname, String toPathname) {
		File from = new File(fromPathname);
		File to = new File(toPathname);
		try {

			if (!from.exists()) {
				from.createNewFile();
			}
			if (!to.exists()) {
				to.createNewFile();
			}
			return copyFile(from, to);
		} catch (IOException e) {
			log.error(e);
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 删除文件夹下的所有文件
	 * 
	 * @param folderFullPath
	 *            = null 或 "" 都删除默认临时目录
	 * @return
	 * @throws Exception
	 */
	public static boolean deleteAllFile(String folderFullPath) throws Exception {
		boolean ret = false;
		if (folderFullPath == null || "".equals(folderFullPath)) {
			return ret;
		}
		try {
			File file = new File(folderFullPath);
			if (file.exists()) {
				if (file.isDirectory()) {
					File[] fileList = file.listFiles();
					for (int i = 0; i < fileList.length; i++) {
						String filePath = fileList[i].getPath();
						deleteAllFile(filePath);
					}
				}
				if (file.isFile()) {
					file.delete();
				}
			}
			ret = true;
		} catch (Exception e) {
			ret = false;
			e.printStackTrace();
		}

		return ret;
	}
}
