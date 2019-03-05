package com.chinasofti.ordersys.servlets.login;

import com.chinasofti.ordersys.service.admin.DishesService;
import com.chinasofti.ordersys.vo.DishesInfo;
import com.chinasofti.util.web.upload.MultipartRequestParser;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AddDishesServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 创建表单请求解析器工具
        MultipartRequestParser parser = new MultipartRequestParser();
        // 解析获取UserInfo用户信息对象
        DishesInfo info = (DishesInfo) parser.parse(request, DishesInfo.class);

        String dishesimg = (String) request.getSession().getAttribute("dishesimg");

        info.setDishesImg(dishesimg);

        // 创建用户管理服务对象
        DishesService service = new DishesService();
        // 添加用户
        service.addDishes(info);
        // 释放表单提交令牌
        // 跳转到用户管理界面
        response.sendRedirect("/OrderSys/todishesadmin.order");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
