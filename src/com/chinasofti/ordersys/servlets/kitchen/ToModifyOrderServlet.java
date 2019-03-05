package com.chinasofti.ordersys.servlets.kitchen;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import com.chinasofti.ordersys.service.admin.OrderService;

public class ToModifyOrderServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int odId =  Integer.parseInt(request.getParameter("odId"));
        int dishesState =  Integer.parseInt(request.getParameter("dishesState"));

        dishesState++;
        // 创建用户管理服务对象
        OrderService service = new OrderService();
        service.modify(dishesState,odId);
//        // 跳转到用户管理界面
//        response.sendRedirect("tokitchenmain.order");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
