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
        // �������������������
        MultipartRequestParser parser = new MultipartRequestParser();
        // ������ȡUserInfo�û���Ϣ����
        DishesInfo info = (DishesInfo) parser.parse(request, DishesInfo.class);

        String dishesimg = (String) request.getSession().getAttribute("dishesimg");

        info.setDishesImg(dishesimg);

        // �����û�����������
        DishesService service = new DishesService();
        // ����û�
        service.addDishes(info);
        // �ͷű��ύ����
        // ��ת���û��������
        response.sendRedirect("/OrderSys/todishesadmin.order");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
