package com.lz.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lz.service.QrCodeService;
import com.lz.util.ImageUtil;

import cn.hutool.core.img.ImgUtil;

@Controller
public class GenerateHaiBaoController {
	
	@Autowired
	private QrCodeService qrCodeService;

	@RequestMapping({"/generate/haiBao/{haiBaoId}"})
	public void generateHaiBao(HttpServletRequest request, HttpServletResponse response, @PathVariable("haiBaoId") String haiBaoId) throws IOException {
        response.setContentType("image/png");
        
        System.out.println(haiBaoId);
        
        ServletOutputStream out = null;
        try {
			BufferedImage image = qrCodeService.createHaiBao();
			out = response.getOutputStream();
			ImageIO.write(image, "png", out);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.close();
			}
		}
        
	}
	
	@GetMapping({"/generate/haiBao/string/{haiBaoId}"})
	@ResponseBody
	public void generateHaiBaoa(HttpServletRequest request, HttpServletResponse response, @PathVariable("haiBaoId") String haiBaoId) throws IOException {
		response.setContentType("image/png");
		byte[] data = null;
		try {
			BufferedImage image = qrCodeService.createHaiBao();
			data = ImageUtil.imageToBytes(image, ImgUtil.IMAGE_TYPE_JPG);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 加密
		String encodeToString = Base64Utils.encodeToString(data);
		byte[] decodeFromString = Base64Utils.decodeFromString(encodeToString);
		
		BufferedImage bytesToImage = ImageUtil.bytesToImage(decodeFromString);
		
		ServletOutputStream out = null;
        try {
			out = response.getOutputStream();
			ImageIO.write(bytesToImage, "png", out);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}
	
}
