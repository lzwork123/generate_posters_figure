package com.lz.util;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import cn.hutool.core.img.ImgUtil;
import cn.hutool.core.util.NumberUtil;

/**
 * 图片工具类
 */
public class ImageUtil {
	
	/**
	 * 插入图片（要插入的图片支持缩放、可以指定缩放后的图片大小，以及要插入图片的位置坐标）
	 * 
	 * @param source 源图片
	 * @param target 要插入的图片
	 * @param targetImageConfig 要插入的图片配置，包括长、宽、坐标等信息
	 * @throws Exception
	 */
	public static void insertImage(BufferedImage source, BufferedImage target, TargetImageConfig targetImageConfig) throws Exception {
		if (source == null || target == null) {
			return;
		}
		
		Image zoomTarget = target;
		int targetWidth = target.getWidth();
		int targetHeight = target.getHeight();
		if (targetImageConfig.isNeedZoom()) {
			// 缩放插入的图片
			int targetImageConfigwidth = targetImageConfig.getWidth();
			int targetImageConfigheight = targetImageConfig.getHeight();
			if (targetWidth > targetImageConfigwidth) {
				targetWidth = targetImageConfigwidth;
			}
			if (targetHeight > targetImageConfigheight) {
				targetHeight = targetImageConfigheight;
			}
			Image image = target.getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);
			BufferedImage tag = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
			Graphics g = tag.getGraphics();
			g.drawImage(image, 0, 0, null); // 绘制缩小后的图
			g.dispose();
			zoomTarget = image;
		}
		// 插入图片
		Graphics2D graph = source.createGraphics();
		int x = targetImageConfig.getX();
		int y = targetImageConfig.getY();
		
		graph.drawImage(zoomTarget, x, y, targetWidth, targetHeight, null);
		
	}

	/**
	 * 缩小Image，此方法返回源图像按给定宽度、高度限制下缩放后的图像
	 * 
	 * @param inputImage
	 * @param newWidth：压缩后宽度
	 * @param newHeight：压缩后高度 
	 * @return 图片 {@link BufferedImage}
	 * @throws java.io.IOException
	 */
	public static BufferedImage scaleByPercentage(BufferedImage inputImage, int newWidth, int newHeight)
			throws Exception {
		if (inputImage == null) {
			return inputImage;
		}
		
		// 获取原始图像透明度类型
		int type = inputImage.getColorModel().getTransparency();
		int width = inputImage.getWidth();
		int height = inputImage.getHeight();
		// 开启抗锯齿
		RenderingHints renderingHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		// 使用高质量压缩
		renderingHints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		BufferedImage img = new BufferedImage(newWidth, newHeight, type);
		Graphics2D graphics2d = img.createGraphics();
		graphics2d.setRenderingHints(renderingHints);
		graphics2d.drawImage(inputImage, 0, 0, newWidth, newHeight, 0, 0, width, height, null);
		graphics2d.dispose();
		return img;
	}

	/**
	 * 传入的图像必须是正方形的 才会 圆形 如果是长方形的比例则会变成椭圆的
	 * 
	 * @param srcImage
	 * @param arc 圆角弧度，0~1，为长宽占比
	 * @return 图片 {@link BufferedImage}
	 * @throws IOException
	 */
	public static BufferedImage convertCircular(BufferedImage srcImage, double arc) throws IOException {
		if (srcImage == null) {
			return srcImage;
		}
		
		final int width = srcImage.getWidth(null);
		final int height = srcImage.getHeight(null);

		// 通过弧度占比计算弧度
		arc = NumberUtil.mul(arc, Math.min(width, height));

		final BufferedImage targetImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		final Graphics2D g2 = targetImage.createGraphics();
		g2.setComposite(AlphaComposite.Src);
		// 抗锯齿
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.fill(new RoundRectangle2D.Double(0, 0, width, height, arc, arc));
		g2.setComposite(AlphaComposite.SrcAtop);
		g2.drawImage(srcImage, 0, 0, null);
		g2.dispose();
		
		return targetImage;
	}
	
	/**
	 * 转换BufferedImage 数据为byte数组
	 * 
	 * @param image
	 * @param format 图片类型（图片扩展名），见{@link ImgUtil}
	 * @return byte数组
	 * @throws IOException
	 */
	public static byte[] imageToBytes(BufferedImage image, String format) throws IOException {
		if (image == null) {
			return null;
		}
		
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ImageIO.write(image, format, out);
		return out.toByteArray();
	}
	
	/**
	* 转换byte数组为Image
	*
	* @param bytes
	* @return Image
	*/
	public static BufferedImage bytesToImage(byte[] bytes) {
		ByteArrayInputStream in = new ByteArrayInputStream(bytes);
		BufferedImage image = null;
		try {
			image = ImageIO.read(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return image;
	}

}
