package cn.udslance.controller;

import cn.udslance.service.RulerService;
import cn.udslance.service.impl.RulerMakerServiceImpl;
import java.util.Scanner;

/**
 * @program: RulerHelper
 * @description:
 * @author: Udslance
 * @create: 2022-03-23 10:36
 **/
public class RulerController {
    private static final RulerService rulerService = new RulerMakerServiceImpl();
    private static final Scanner SCANNER = new Scanner(System.in);

    public void mainMenu(){
        int flag = -1;
        while (flag != 2) {
            System.out.println("这是一个为图片添加标尺的软件 version: 0.1");
            System.out.println("1. 修改图片");
            System.out.println("2. 退出");
            System.out.print("请输入：");
            flag = SCANNER.nextInt();
            SCANNER.nextLine();
            if (flag == 1) {
                multiEdit();
            }
        }
        System.out.println("Bye～");
    }

    public void multiEdit() {
        System.out.println("-----------------------------------------请输入路径-----------------------------------------");
        System.out.println("------------------如果你指向一个文件夹，那么整个文件夹内的jpg图片都将会被加上标尺；-------------------");
        System.out.println("-----------------------------如果你指向图片，那么仅此图片会被加上标尺-----------------------------");
        String foldPath = SCANNER.nextLine();
        rulerService.multiMaker(foldPath);
    }

}
