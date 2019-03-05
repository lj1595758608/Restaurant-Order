package com.chinasofti.ordersys.servlets.admin;

import com.chinasofti.ordersys.service.admin.DishesService;
import com.chinasofti.ordersys.vo.DishesInfo;
import com.chinasofti.util.web.upload.MultipartRequestParser;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AdminModifyDishesServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 创建表单请求解析器工具
        MultipartRequestParser parser = new MultipartRequestParser();
        // 解析获取UserInfo用户信息对象
        DishesInfo info = (DishesInfo) parser.parse(request, DishesInfo.class);

        String dishesimg = (String) request.getSession().getAttribute("dishesimg");
        String dishesId = request.getParameter("dishesId");
        info.setDishesId(Integer.parseInt(dishesId));
        info.setDishesImg(dishesimg);

        // 创建用户管理服务对象
        DishesService service = new DishesService();
        // 执行修改操作
        service.dishesModify(info);
        // 跳转到用户管理界面
        response.sendRedirect("/OrderSys/todishesadmin.order");
//        response.sendRedirect("/pages/admin/useradmin.jsp");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
