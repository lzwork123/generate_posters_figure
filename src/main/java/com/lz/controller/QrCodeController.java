package com.lz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lz.service.QrCodeService;

@RestController
public class QrCodeController {
	
	@Autowired
	private QrCodeService qrCodeService;
	
	@RequestMapping("/haibao/create")
	public String createHaiBao(){
		try {
			qrCodeService.createHaiBao();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "create qrcode success!";
	}

}
