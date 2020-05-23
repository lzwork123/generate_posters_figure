package com.lz.util;

public class TargetImageConfig {
	
	/** 插入图片左上角横轴x像素点位置  */
	private int x;
	/** 插入图片左上角纵轴y像素点位置  */
	private int y;
	/** 图片是否需要缩放 */
	private boolean needZoom;
	/** 图片缩放后的大小（宽） */
	private int width;
	/** 图片缩放后的大小（高） */
	private int height;
	
	public TargetImageConfig() {}
	
	/**
	 * 指定插入的图片左上角的像素坐标
	 * 
	 * @param x 横轴x像素点位置
	 * @param y 纵轴y像素点位置
	 */
	public TargetImageConfig(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * 要插入的图片配置，包括长、宽、坐标、像素等信息
	 * 
	 * @param x 插入图片左上角横轴x像素点位置
	 * @param y 插入图片左上角纵轴y像素点位置
	 * @param needZoom 图片是否需要缩放
	 * @param width 图片缩放后的大小（宽）
	 * @param height 图片缩放后的大小（高）
	 */
	public TargetImageConfig(int x, int y, boolean needZoom, int width, int height) {
		this.x = x;
		this.y = y;
		this.needZoom = needZoom;
		this.width = width;
		this.height = height;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public boolean isNeedZoom() {
		return needZoom;
	}

	public void setNeedZoom(boolean needZoom) {
		this.needZoom = needZoom;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

}
