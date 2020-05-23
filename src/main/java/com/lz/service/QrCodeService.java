package com.lz.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Service;

import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.lz.config.QrConfig;
import com.lz.util.ImageUtil;
import com.lz.util.QrCodeUtil;
import com.lz.util.TargetImageConfig;

import cn.hutool.core.util.CharsetUtil;

@Service
public class QrCodeService {

	public BufferedImage createHaiBao() throws Exception {
		URL url = new URL("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1590053603778&di=1072d1f072fc9b8870442c11eb54d31a&imgtype=0&src=http%3A%2F%2Fpic.616pic.com%2Fbg_w1180%2F00%2F09%2F50%2FBOdceUq17L.jpg");
		BufferedImage haibao = ImageIO.read(url);
		
		QrConfig qrConfig = QrConfig.create();
		qrConfig.setErrorCorrection(ErrorCorrectionLevel.H);
		qrConfig.setCharset(CharsetUtil.CHARSET_UTF_8);
		qrConfig.setMargin(0);
		
		qrConfig.setWidth(500);
		qrConfig.setHeight(500);
		
		// 设置二维码logo
		BufferedImage logoImage = ImageIO.read(new URL("http://www.yqfml.com/uploads/article/4bd939081352c36081ec864fe3c57773.png"));
		qrConfig.setImg(logoImage);
		// 设置二维码logo相对于二维码的缩放比例
		qrConfig.setRatio(5);
		
		// 测试代码，扫描二维码跳转百度
		String content = "https://www.baidu.com";
		BufferedImage qrCodeImage = QrCodeUtil.generate(content, qrConfig);
		
		TargetImageConfig targetImageConfig = new TargetImageConfig(300, 900, true, 300, 300);
		ImageUtil.insertImage(haibao, qrCodeImage, targetImageConfig);
		
		URL urla = new URL("https://w-y-audio.oss-cn-beijing.aliyuncs.com/iosf541e095228a09ed30a2196f9e77644e1585899159992.jpeg");
		BufferedImage touxiang = ImageIO.read(urla);
		
		BufferedImage scaleByPercentage = ImageUtil.scaleByPercentage(touxiang, 200, 200);
		BufferedImage convertCircular = ImageUtil.convertCircular(scaleByPercentage, 1);
		
		
		TargetImageConfig targetImageConfiga = new TargetImageConfig(300, 100);
		ImageUtil.insertImage(haibao, convertCircular, targetImageConfiga);
		
		return haibao;
		
//		String file = System.currentTimeMillis() +".jpg";
//		java.text.SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
//		String path = "D:\\qrcode\\" + f.format(new Date());
//		ImageIO.write(haibao, "JPG", new File(path+"/"+file));
	}
	
}
