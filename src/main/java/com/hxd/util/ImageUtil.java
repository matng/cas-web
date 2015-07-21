package com.hxd.util;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.swing.ImageIcon;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class ImageUtil {

	/**
	 * 缩放计算
	 * 
	 * @param targetWidth
	 * @param targetHeight
	 * @param standardWidth
	 * @param standardHeight
	 * @return
	 */
	public static double getScaling(double targetWidth, double targetHeight,
			double standardWidth, double standardHeight) {
		double widthScaling = 0d;
		double heightScaling = 0d;
		if (targetWidth > standardWidth) {
			widthScaling = standardWidth / (targetWidth * 1.00d);
		} else {
			widthScaling = 1d;
		}
		if (targetHeight > standardHeight) {
			heightScaling = standardHeight / (targetHeight * 1.00d);
		} else {
			heightScaling = 1d;
		}
		return Math.min(widthScaling, heightScaling);
	}

	/**
	 * 指定大小 来缩放
	 * 
	 * @param width
	 * @param height
	 * @param image
	 * @return
	 */
	public static int[] getSize(int width, int height, Image image) {
		int targetWidth = image.getWidth(null);
		int targetHeight = image.getHeight(null);
		double scaling = getScaling(targetWidth, targetHeight, width, height);
		long standardWidth = Math.round(targetWidth * scaling);
		long standardHeight = Math.round(targetHeight * scaling);
		return new int[] { Integer.parseInt(Long.toString(standardWidth)),
				Integer.parseInt(String.valueOf(standardHeight)) };
	}

	/**
	 * 缩放比例
	 * 
	 * @param scale
	 * @param image
	 * @return
	 */
	public static int[] getSize(float scale, Image image) {
		int targetWidth = image.getWidth(null);
		int targetHeight = image.getHeight(null);
		long standardWidth = Math.round(targetWidth * scale);
		long standardHeight = Math.round(targetHeight * scale);
		return new int[] { Integer.parseInt(Long.toString(standardWidth)),
				Integer.parseInt(String.valueOf(standardHeight)) };
	}

	/**
	 * 生成缩略图
	 * 
	 * @param srcImagePath
	 *            原图路径
	 * @param WIDTH
	 *            缩略图宽度
	 * @param HEIGHT
	 *            缩略图高度
	 * @return BufferedImage
	 */
	public static BufferedImage zoom(String srcImagePath, int WIDTH, int HEIGHT) {
		// 使用源图像文件名创建ImageIcon对象。
		ImageIcon imgIcon = new ImageIcon(srcImagePath);
		// 得到Image对象。
		Image img = imgIcon.getImage();

		int[] size = getSize(WIDTH, HEIGHT, img);
		// 构造一个预定义的图像类型的BufferedImage对象。
		BufferedImage buffImg = new BufferedImage(size[0], size[1],
				BufferedImage.TYPE_INT_RGB);
		// 创建Graphics2D对象，用于在BufferedImage对象上绘图。
		Graphics2D g = buffImg.createGraphics();

		// 设置图形上下文的当前颜色为白色。
		g.setColor(Color.WHITE);
		// 用图形上下文的当前颜色填充指定的矩形区域。
		g.fillRect(0, 0, WIDTH, HEIGHT);
		// 按照缩放的大小在BufferedImage对象上绘制原始图像。
		g.drawImage(img, 0, 0, WIDTH, HEIGHT, null);
		// 释放图形上下文使用的系统资源。
		g.dispose();
		// 刷新此 Image 对象正在使用的所有可重构的资源.
		img.flush();

		// JPEGImageEncoder jpgEncoder = JPEGCodec.createJPEGDecoder();

		return buffImg;
	}

	public static BufferedImage zoom(String srcImagePath, float scale) {
		// 使用源图像文件名创建ImageIcon对象。
		ImageIcon imgIcon = new ImageIcon(srcImagePath);
		// 得到Image对象。
		Image img = imgIcon.getImage();

		int[] size = getSize(scale, img);
		// 构造一个预定义的图像类型的BufferedImage对象。
		BufferedImage buffImg = new BufferedImage(size[0], size[1],
				BufferedImage.TYPE_INT_RGB);
		// 创建Graphics2D对象，用于在BufferedImage对象上绘图。
		Graphics2D g = buffImg.createGraphics();

		// 设置图形上下文的当前颜色为白色。
		g.setColor(Color.WHITE);
		// 用图形上下文的当前颜色填充指定的矩形区域。
		g.fillRect(0, 0, size[0], size[1]);
		// 按照缩放的大小在BufferedImage对象上绘制原始图像。
		g.drawImage(img, 0, 0, size[0], size[1], null);
		// 释放图形上下文使用的系统资源。
		g.dispose();
		// 刷新此 Image 对象正在使用的所有可重构的资源.
		img.flush();

		// JPEGImageEncoder jpgEncoder = JPEGCodec.createJPEGDecoder();

		return buffImg;
	}

	public static void SZoom(String srcPicPath, String smallPicPath, float scale) {
		try {
			BufferedImage buffImg = zoom(srcPicPath, scale);

			// 开始生成缩略图
			FileOutputStream smallImage = new FileOutputStream(smallPicPath);
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(smallImage);
			JPEGEncodeParam jep = JPEGCodec.getDefaultJPEGEncodeParam(buffImg);

			/* 压缩质量 */
			jep.setQuality(0.7f, true);
			encoder.encode(buffImg, jep);

			smallImage.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 生成缩略图
	 * 
	 * @param srcPicPath
	 *            原图路径
	 * @param smallPicPath
	 *            生成缩略图路径
	 * @param WIDTH
	 *            缩略图宽度
	 * @param HEIGHT
	 *            缩略图高度
	 */
	public static void zoom(String srcPicPath, String smallPicPath, int WIDTH,
			int HEIGHT) {
		try {

			BufferedImage buffImg = zoom(srcPicPath, WIDTH, HEIGHT);

			// 开始生成缩略图
			FileOutputStream smallImage = new FileOutputStream(smallPicPath);
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(smallImage);
			JPEGEncodeParam jep = JPEGCodec.getDefaultJPEGEncodeParam(buffImg);

			/* 压缩质量 */
			jep.setQuality(0.7f, true);
			encoder.encode(buffImg, jep);

			smallImage.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 高保真缩放
	 * 
	 * @param srcPicPath
	 * @param smallPicPath
	 * @param WIDTH
	 * @param HEIGHT
	 */
	public static void HZoom(String srcPicPath, String smallPicPath, int WIDTH,
			int HEIGHT) {
		try {
			// 使用源图像文件名创建ImageIcon对象。
			ImageIcon imgIcon = new ImageIcon(srcPicPath);
			// 得到Image对象。
			Image img = imgIcon.getImage();

			// 构造一个预定义的图像类型的BufferedImage对象。
			BufferedImage buffImg = new BufferedImage(WIDTH, HEIGHT,
					BufferedImage.TYPE_INT_RGB);
			// 创建Graphics2D对象，用于在BufferedImage对象上绘图。
			Graphics2D g = buffImg.createGraphics();

			// 设置图形上下文的当前颜色为白色。
			g.setColor(Color.WHITE);
			// 用图形上下文的当前颜色填充指定的矩形区域。
			g.fillRect(0, 0, WIDTH, HEIGHT);
			// 按照缩放的大小在BufferedImage对象上绘制原始图像。
			g.drawImage(img, 0, 0, WIDTH, HEIGHT, null);
			// 释放图形上下文使用的系统资源。
			g.dispose();
			// 刷新此 Image 对象正在使用的所有可重构的资源.
			img.flush();

			// 开始生成缩略图
			FileOutputStream smallImage = new FileOutputStream(smallPicPath);
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(smallImage);
			JPEGEncodeParam jep = JPEGCodec.getDefaultJPEGEncodeParam(buffImg);

			/* 压缩质量 */
			jep.setQuality(0.7f, true);
			encoder.encode(buffImg, jep);

			smallImage.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * TODO:将byte数组以Base64方式编码为字符串
	 * 
	 * @param bytes
	 *            待编码的byte数组
	 * @return 编码后的字符串
	 * */
	public static String encode(byte[] bytes) {
		return new BASE64Encoder().encode(bytes);
	}

	/**
	 * TODO:将以Base64方式编码的字符串解码为byte数组
	 * 
	 * @param encodeStr
	 *            待解码的字符串
	 * @return 解码后的byte数组
	 * @throws IOException
	 * */
	public static byte[] decode(String encodeStr) throws IOException {
		byte[] bt = null;
		BASE64Decoder decoder = new BASE64Decoder();
		bt = decoder.decodeBuffer(encodeStr);
		return bt;
	}

	/**
	 * TODO:将两个byte数组连接起来后，返回连接后的Byte数组
	 * 
	 * @param front
	 *            拼接后在前面的数组
	 * @param after
	 *            拼接后在后面的数组
	 * @return 拼接后的数组
	 * */
	public static byte[] connectBytes(byte[] front, byte[] after) {
		byte[] result = new byte[front.length + after.length];
		System.arraycopy(front, 0, result, 0, after.length);
		System.arraycopy(after, 0, result, front.length, after.length);
		return result;
	}

	/**
	 * TODO:将图片以Base64方式编码为字符串
	 * 
	 * @param imgUrl
	 *            图片的绝对路径（例如：D:\\jsontest\\abc.jpg）
	 * @return 编码后的字符串
	 * @throws IOException
	 * */
	public static String encodeImage(String imgUrl) throws IOException {
		FileInputStream fis = new FileInputStream(imgUrl);
		byte[] rs = new byte[fis.available()];
		fis.read(rs);
		fis.close();
		return encode(rs);
	}

	// 对字节数组字符串进行Base64解码并生成图片
	public static boolean genBase64Image(String imgBase64Str, String imgSavePath) {
		
		if (imgBase64Str == null)
			return false;
		BASE64Decoder decoder = new BASE64Decoder();
		try {
			// Base64解码
			byte[] b = decoder.decodeBuffer(imgBase64Str);
			for (int i = 0; i < b.length; ++i) {
				if (b[i] < 0) {
					b[i] += 256;
				}
			}
			// 生成jpeg图片 
			OutputStream out = new FileOutputStream(imgSavePath);
			out.write(b);
			out.flush();
			out.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 体积小
	 * @param srcImg
	 * @param outImg
	 * @param w
	 * @param h
	 * @param per
	 */
	private static void Tosmallerpic(String srcImg,String outImg, int w, int h, float per) {  
        Image src;  
        try {  
            src = javax.imageio.ImageIO.read(new File(srcImg));  
            // 构造Image对象  
            int old_w = src.getWidth(null); // 得到源图宽  
            int old_h = src.getHeight(null);  
            int new_w = 0;  
            int new_h = 0; // 得到源图长  
            double w2 = (old_w * 1.00) / (w * 1.00);  
            double h2 = (old_h * 1.00) / (h * 1.00);  
            
            // 图片调整为方形结束  
            if (old_w > w)  
                new_w = (int) Math.round(old_w / w2);  
            else  
                new_w = old_w;  
            if (old_h > h)  
                new_h = (int) Math.round(old_h / h2);// 计算新图长宽  
            else  
                new_h = old_h;  
            
            BufferedImage tag = new BufferedImage(new_w, new_h,  
                    BufferedImage.TYPE_INT_RGB);  
            // 绘制缩小后的图  
            tag.getGraphics().drawImage(  
                    src.getScaledInstance(new_w, new_h, Image.SCALE_SMOOTH), 0,  
                    0, null);  
            FileOutputStream newimage = new FileOutputStream(outImg); // 输出到文件流  
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(newimage);  
            JPEGEncodeParam jep = JPEGCodec.getDefaultJPEGEncodeParam(tag);  
            /* 压缩质量 */  
            jep.setQuality(per, true);  
            encoder.encode(tag, jep);  
            // 近JPEG编码  
            newimage.close();  
        } catch (IOException ex) {  
            ex.printStackTrace(); 
        }  
    }  
	
	public static void main(String[] args) {

		try {
//			String src = "D:/软件项目宣传册/car.jpg";
//			String small = "D:/软件项目宣传册/car_s1.jpg";
//			HZoom(src,small,100,100);
//			
//			String str = encodeImage(small);
//			System.out.println(str.length());
//			System.out.println(str);
//			genBase64Image(str,"D:/软件项目宣传册/car_base64.jpg");
//			
//			String compressed = ZipUtil.GZipImg(small);
//			System.out.println("---------------------------------");
//			System.out.println(compressed.length());
//			System.out.println(compressed);
//			
//			ZipUtil.unGZipImg(compressed,"D:/软件项目宣传册/car_base64_unzip.jpg");
//			System.out.println("---------完成-----------");
			
			
//			String src = "D:/软件项目宣传册/main.png";
//			String small = "D:/软件项目宣传册/main_small1.png";
//			String small2 = "D:/软件项目宣传册/main_small2.png";
//			String small3 = "D:/软件项目宣传册/main_small3.png";
			
//			String src = "D:/软件项目宣传册/car.jpg";
//			String small = "D:/软件项目宣传册/car_small1.jpg";
//			String small2 = "D:/软件项目宣传册/car_small2.jpg";
//			String small3 = "D:/软件项目宣传册/car_small3.jpg";
//			String small4 = "D:/软件项目宣传册/car_small4.jpg";
			
			
			String src = "D:/软件项目宣传册/car2.jpg";
			String small = "D:/软件项目宣传册/car2_small1.jpg";
			String small2 = "D:/软件项目宣传册/car2_small2.jpg";
			String small3 = "D:/软件项目宣传册/car2_small3.jpg";
			String small4 = "D:/软件项目宣传册/car2_small4.jpg";
			
			Tosmallerpic(src,small,700,600,0.7f);    
 
			zoom(src,small2,700,600);
			
			HZoom(src,small3,700,600);
			
			SZoom(src,small4,0.3f);
			
			System.out.println("Done");
		} catch (Exception e) {

		}

	}

}