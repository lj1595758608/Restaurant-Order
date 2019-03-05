package com.chinasofti.ordersys.servlets.admin;

import com.chinasofti.ordersys.service.admin.DishesService;
import com.chinasofti.ordersys.vo.DishesInfo;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ToModifyDishesServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 创建用户管理服务对象
        DishesService service = new DishesService();
        // 获取要修改的用户ID并查询对应的用户信息
        DishesInfo info = service.getDishesById(new Integer(request
                .getParameter("dishesId")));
        // 将用户信息加入request作用域
        request.setAttribute("MODIFY_INFO", info);
        // 跳转到用户信息修改界面
        request.getRequestDispatcher("/pages/admin/modifydishes.jsp").forward(
                request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
