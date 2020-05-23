package com.lz.util;

import java.awt.image.BufferedImage;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.lz.config.QrConfig;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.extra.qrcode.QrCodeException;

/**
 * 二维码工具类
 * 
 */
public class QrCodeUtil {

	/**
	 * 生成二维码图片
	 * 
	 * @param content 文本内容
	 * @param width 宽度
	 * @param height 高度
	 * @return 二维码图片（黑白）
	 */
	public static BufferedImage generate(String content, Integer width, Integer height) {
		QrConfig qrConfig = QrConfig.create();
		if (width != null) {
			qrConfig.setWidth(width);
		}
		if (height != null) {
			qrConfig.setHeight(height);
		}
		qrConfig.setErrorCorrection(ErrorCorrectionLevel.H);
		qrConfig.setCharset(CharsetUtil.CHARSET_UTF_8);
		qrConfig.setMargin(1);
		
		return generate(content, qrConfig);
	}
	
	/**
	 * 生成二维码图片
	 * 
	 * @param content 文本内容
	 * @param config 二维码配置，包括长、宽、边距、颜色等
	 * @return 二维码图片
	 */
	public static BufferedImage generate(String content, QrConfig config) {
		final BitMatrix bitMatrix = encode(content, BarcodeFormat.QR_CODE, config);
		return toImage(bitMatrix, config.getForeColor(), config.getBackColor());
	}
	
	/**
	 * 将文本内容编码为条形码或二维码
	 * 
	 * @param content 文本内容
	 * @param format 格式枚举
	 * @param config 二维码配置，包括长、宽、边距、颜色等
	 * @return {@link BitMatrix}
	 */
	private static BitMatrix encode(String content, BarcodeFormat format, QrConfig config) {
		final MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
		if (null == config) {
			// 默认配置
			config = new QrConfig();
		}
		BitMatrix bitMatrix;
		try {
			bitMatrix = multiFormatWriter.encode(content, format, config.getWidth(), config.getHeight(), config.toHints());
		} catch (WriterException e) {
			throw new QrCodeException(e);
		}

		return bitMatrix;
	}
	
	/**
	 * BitMatrix转BufferedImage
	 * 
	 * @param matrix BitMatrix
	 * @param foreColor 前景色
	 * @param backColor 背景色
	 * @return BufferedImage
	 */
	private static BufferedImage toImage(BitMatrix matrix, int foreColor, int backColor) {
		final int width = matrix.getWidth();
		final int height = matrix.getHeight();
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				image.setRGB(x, y, matrix.get(x, y) ? foreColor : backColor);
			}
		}
		return image;
	}

}

