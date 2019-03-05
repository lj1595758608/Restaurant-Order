package com.chinasofti.ordersys.servlets.admin;

import com.chinasofti.ordersys.service.admin.OrderService;
import com.chinasofti.ordersys.service.admin.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteOrderServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取试图删除的用户ID
        Integer orderId = new Integer(request.getParameter("orderId"));
        // 创建用户管理服务对象
        OrderService service = new OrderService();
        // 执行删除用户操作
        service.DeleteOrder(orderId);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
