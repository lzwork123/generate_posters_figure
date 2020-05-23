package com.lz.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Service;

import com.lz.util.ImageUtil;
import com.lz.util.QrCodeUtil;
import com.lz.util.TargetImageConfig;

@Service
public class QrCodeService {

	public BufferedImage createHaiBao() throws Exception {
		URL url = new URL("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1590053603778&di=1072d1f072fc9b8870442c11eb54d31a&imgtype=0&src=http%3A%2F%2Fpic.616pic.com%2Fbg_w1180%2F00%2F09%2F50%2FBOdceUq17L.jpg");
		BufferedImage haibao = ImageIO.read(url);
		
		BufferedImage qrCodeImage = QrCodeUtil.generate("https://www.baidu.com", 3000, 3000);
		
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
