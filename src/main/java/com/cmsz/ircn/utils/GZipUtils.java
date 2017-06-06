package com.cmsz.ircn.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.compress.utils.IOUtils;

public class GZipUtils {

	/**
	 * 解压tar.gz到指定文件夹
	 * 
	 * @param gzFile 待解压gz文件
	 * @param outPath  解压输出目录
	 */
	public static void uncompress(File gzFile, String outPath) {
		GZIPInputStream gis = null;
		ArchiveInputStream ais = null;
		OutputStream out = null;
		try {
			gis = new GZIPInputStream(new BufferedInputStream(new FileInputStream(gzFile)));
			ais = new ArchiveStreamFactory().createArchiveInputStream("tar", gis);
			TarArchiveEntry entry = null;
			// 循环处理压缩包中的项目
			while ((entry = (TarArchiveEntry) ais.getNextEntry()) != null) {
				// 不是目录时才需要进行处理
				if (!entry.isDirectory()) {
					File path = new File(outPath);
					if (!path.exists()) {
						path.mkdirs();
					}
					File enteyFile = new File(outPath + "/" + entry.getName());
					out = new FileOutputStream(enteyFile);
					int length = 0;
					byte[] b = new byte[2048];
					while ((length = ais.read(b)) != -1) {
						out.write(b, 0, length);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ArchiveException e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(ais);
			IOUtils.closeQuietly(gis);
			IOUtils.closeQuietly(out);
		}
	}

	/**
	 * 把多个文件打包为tar文件
	 * @param sources 多个小文件数组
	 * @param target 目标tar文件
	 * @return
	 */
	public static File pack(File[] sources, File target) {
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(target);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		TarArchiveOutputStream os = new TarArchiveOutputStream(out);
		for (File file : sources) {
			try {
				os.putArchiveEntry(new TarArchiveEntry(file));
				IOUtils.copy(new FileInputStream(file), os);
				os.closeArchiveEntry();

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		IOUtils.closeQuietly(os);
		return target;
	}

	/**
	 * 压缩多个文件为gz文件(归档类型为tar)
	 * @param sources  多个文件
	 * @param path 输出路径(最后以斜杠结尾的路径)
	 * @param name 输出文件名(不带扩展名，只要文件名，如：xxx,不要写成xxx.tar或xxx.tar.gz)
	 * @return
	 */
	public static File compress(File[] sources, String path, String name) {
		FileInputStream in = null;
		GZIPOutputStream out = null;
		File outFile = null;
		File tarFile = null;
		try {
			tarFile = new File(path + name + ".tar");
			in = new FileInputStream(pack(sources, tarFile));
			outFile = new File(path + name + ".gz");
			out = new GZIPOutputStream(new FileOutputStream(outFile));
			byte[] array = new byte[1024];
			int number = -1;
			while ((number = in.read(array, 0, array.length)) != -1) {
				out.write(array, 0, number);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} finally {
			IOUtils.closeQuietly(out);
			IOUtils.closeQuietly(in);
			if (tarFile != null && tarFile.exists()) {
				tarFile.delete();
			}
		}
		return outFile;
	}

	public static void main(String[] args) {
		// 压缩
		File[] sources = new File[] { new File("c:/config.ini"), new File("c:/spark-data.log") };
		File target = new File("c:/temp.tar");
		compress(sources, "c:/", "temp");

		// 解压
		uncompress(new File("c:/temp.tar.gz"), "c:/temp/2");
	}
}
