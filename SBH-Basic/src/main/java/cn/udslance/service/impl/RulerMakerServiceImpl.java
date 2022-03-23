package cn.udslance.service.impl;

import cn.udslance.service.RulerService;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * 在图片上绘制标尺
 * @author H
 */
public class RulerMakerServiceImpl implements RulerService {

	private static final Scanner SCANNER = new Scanner(System.in);

	private boolean flag = true;
	private int backWidth = 0;
	private int backHeight = 0;
	private int rulerWidth = 0;
	private int rulerHeight = 0;
	private int backDis = 0;
	private int rulerDis = 0;
	private int fontSize = 0;
	private String textToDraw;
	private static final double INDEX = 0.0182;

	/**
	 * 为文件夹内的所有图片添加标尺
	 * @param orgPath 文件夹路径
	 */
	@Override
	public void multiMaker(String orgPath) {
		flag = true;
		File orgFile = new File(orgPath);
		ArrayList<BufferedImage> imagesToSave = new ArrayList<>();
		if (orgFile.isDirectory()) {
			//如果路径指向文件夹
			ArrayList<String> imagesToEdit = new ArrayList<>();
			File[] tempList = orgFile.listFiles();
			//如果文件夹为空
			if(tempList == null){
				System.out.println("文件夹为空");
				return;
			}
			for (File value : tempList) {
				// 将文件夹内所有jpg格式图片加入待修改队列
				if (value.toString().endsWith(".jpg")) {
					imagesToEdit.add(value.toString());
				}
			}
			//如果文件夹有图片
			if (!imagesToEdit.isEmpty()) {
				for (String orgImagePath : imagesToEdit) {
					File orgImage = new File(orgImagePath);
					//将修改好的图片加入待保存队列
					imagesToSave.add(rulerMaker(orgImage));
					flag = false;
				}
			}else{
				System.out.println("文件夹内无图片");
			}
		}else if (orgPath.endsWith(".jpg")) {
			//如果路径指向图片
			//将修改好的图片加入待保存队列
			imagesToSave.add(rulerMaker(orgFile));
		}

		System.out.println("图片已修改完毕，请输入保存文件名前缀，请勿携带格式：");
		String outputNameF = SCANNER.nextLine();
		System.out.println("请输入保存文件夹的绝对路径，末尾请勿携带斜杠【/】：");
		String outputPath = SCANNER.nextLine();
		//写出
		try {
			int num = 0;
			for (BufferedImage bufferedImage : imagesToSave) {
				ImageIO.write(bufferedImage, "jpg", new File(outputPath + "/" + outputNameF + num + ".jpg"));
				num++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 绘制标尺
	 * @param orgImagePath 原图地址
	 * @return BufferedImage
	 */
	private BufferedImage rulerMaker(File orgImagePath) {
		//1 读入临时图片，用以
		Image orgImage = null;
		try {
			orgImage = ImageIO.read(orgImagePath);
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 获取原图以及原图的长和宽
		int orgWidth = orgImage.getWidth(null);
		int orgHeight = orgImage.getHeight(null);
		BufferedImage bufferedImage = new BufferedImage(orgWidth,orgHeight,BufferedImage.TYPE_INT_RGB);
		//2 以原图大小为画纸，创建Java绘图工具
		Graphics2D graphics2D = bufferedImage.createGraphics();
		//3 使用绘图工具将原图绘制到画纸上
		graphics2D.drawImage(orgImage, 0, 0, orgWidth, orgHeight, null);
		//4 采集绘图参数
		if(flag){
			getDetail(orgWidth);
		}
		//5 标尺背景绘制
		backgroundDrawer(graphics2D,orgWidth,orgHeight);
		//6 标尺绘制
		rulerDrawer(graphics2D,orgWidth,orgHeight);
		//7 标尺文字绘制
		textDrawer(graphics2D,orgWidth,orgHeight);
		//8 释放工具
		graphics2D.dispose();

		return bufferedImage;

		//创建文件输出流，指向最终的目标文件
		//最终目标文件地址

	}

	/**
	 * 采集绘图参数
	 * @param orgWidth 原图宽度
	 */
	private void getDetail(int orgWidth){
		System.out.print("请输入背景宽度（pixel）：");
		backWidth = SCANNER.nextInt();
		System.out.print("请输入背景高度（pixel）：");
		backHeight = SCANNER.nextInt();
		backDis = (int)(orgWidth * INDEX);

		System.out.print("请输入标尺宽度（pixel）：");
		rulerWidth = SCANNER.nextInt();
		System.out.print("请输入标尺高度（pixel）：");
		rulerHeight = SCANNER.nextInt();
		SCANNER.nextLine();
		rulerDis = (int)(backHeight * 0.15);

		System.out.print("请输入标尺文字（如：50mm）：");
		textToDraw = SCANNER.nextLine();
		System.out.print("请输入字体大小：");
		fontSize = SCANNER.nextInt();
		SCANNER.nextLine();
	}

	/**
	 * 标尺背景绘制
	 * @param graphics2D 绘图工具
	 * @param orgWidth 原图宽度
	 * @param orgHeight 原图高度
	 */
	private void backgroundDrawer(Graphics2D graphics2D, int orgWidth, int orgHeight){
		//左上角横坐标 = 原图宽度 - 背景与左边距离 - 标尺背景宽度
		int x = orgWidth - backDis - backWidth;
		//左上角纵坐标 = 原图宽度 - 背景与底边边距离 - 标尺背景高度
		int y = orgHeight - backDis - backHeight;
		//创建背景长方形
		Shape rulerBack = new Rectangle(x, y, backWidth, backHeight);
		//背景颜色设置
		graphics2D.setColor(Color.white);
		//绘制背景
		graphics2D.fill(rulerBack);
	}

	/**
	 * 标尺绘制
	 * @param graphics2D 绘图工具
	 * @param orgWidth 原图宽度
	 * @param orgHeight 原图高度
	 */
	private void rulerDrawer(Graphics2D graphics2D, int orgWidth, int orgHeight){
		//左上角横坐标 = 原图宽度 - 背景与左边距离 - 背景宽度/2 - 标尺宽度/2
		int x = orgWidth - backDis - backWidth/2 - rulerWidth/2;
		//左上角纵坐标 = 原图高度 - 背景与底边距离 - 标尺与背景底边距离 - 标尺高度
		int y = orgHeight - backDis - rulerDis - rulerHeight;
		//创建标尺方形
		Shape ruler = new Rectangle(x, y, rulerWidth, rulerHeight);
		//标尺颜色设置
		graphics2D.setColor(Color.black);
		//绘制标尺
		graphics2D.fill(ruler);
	}

	/**
	 * 文字绘制
	 * @param graphics2D 绘图工具
	 * @param orgWidth 原图宽度
	 * @param orgHeight 原图高度
	 */
	private void textDrawer(Graphics2D graphics2D, int orgWidth, int orgHeight){
		//字体设置
		Font rulerFont = new Font("Times New Roman", Font.PLAIN, fontSize);
		//基于FontMetrics 我们就能获取字体高度、asent、decent，结合具体的字符串得到宽度
		FontMetrics metrics = graphics2D.getFontMetrics(rulerFont);

		//指定要绘制的字符串

		//得到字符串绘制宽度和高度
		int textWidth = metrics.stringWidth(textToDraw);
		int x = orgWidth - backDis - backWidth / 2 - textWidth / 2;
		int y = orgHeight - backDis - rulerDis - rulerHeight - metrics.getDescent();

		//设置工具字体
		graphics2D.setFont(rulerFont);
		//文字颜色设置
		graphics2D.setColor(Color.black);
		//绘制字体
		graphics2D.drawString(textToDraw,x,y);
	}

}
