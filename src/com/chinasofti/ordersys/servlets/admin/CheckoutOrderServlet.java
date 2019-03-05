package com.chinasofti.ordersys.servlets.admin;

import com.chinasofti.ordersys.service.admin.OrderService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CheckoutOrderServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // ��ȡ��ͼɾ�����û�ID
        Integer orderId = new Integer(request.getParameter("orderId"));
        // �����û�����������
        OrderService service = new OrderService();
        // ִ��ɾ���û�����
        service.CheckoutOrder(orderId);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
