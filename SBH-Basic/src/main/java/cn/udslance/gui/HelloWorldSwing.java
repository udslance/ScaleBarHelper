package cn.udslance.gui;

import javax.swing.*;
import java.awt.*;

/**
 * @program: RulerHelper
 * @description:
 * @author: Udslance
 * @create: 2022-03-22 21:31
 **/
public class HelloWorldSwing {
    /**{
     * 创建并显示GUI。出于线程安全的考虑，
     * 这个方法在事件调用线程中调用。
     */
    private static void createAndShowGUI() {
        // 确保一个漂亮的外观风格
        JFrame.setDefaultLookAndFeelDecorated(true);

        // 创建及设置窗口
        JFrame frame = new JFrame("RulerHelper");
        //设置窗口大小和位置
        frame.setBounds(300, 100, 400, 200);
        //为Frame窗口设置布局为BorderLayout
        frame.setLayout(new BorderLayout());
        //设置窗口退出
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //创建一个JPanel对象
        JPanel jpWest=new JPanel();

        jpWest.setLayout(new BorderLayout());

        jpWest.add(new JLabel("预留图片预览位置"), BorderLayout.CENTER);
        jpWest.add(new Button("上传图片"), BorderLayout.SOUTH);

        frame.add(jpWest, BorderLayout.WEST);

        frame.add(new Button("West"), BorderLayout.EAST);


        // 显示窗口
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        // 显示应用 GUI
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
