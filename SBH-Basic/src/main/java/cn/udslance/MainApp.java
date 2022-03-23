package cn.udslance;

import cn.udslance.controller.RulerController;

/**
 * @program: RulerHelper
 * @description:
 * @author: Udslance
 * @create: 2022-03-22 19:07
 **/
public class MainApp {
    public static void main(String[] args) {

        RulerController rulerController = new RulerController();

        rulerController.mainMenu();
    }
}
