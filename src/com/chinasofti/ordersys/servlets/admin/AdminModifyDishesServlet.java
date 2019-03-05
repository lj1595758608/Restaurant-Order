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
        // �������������������
        MultipartRequestParser parser = new MultipartRequestParser();
        // ������ȡUserInfo�û���Ϣ����
        DishesInfo info = (DishesInfo) parser.parse(request, DishesInfo.class);

        String dishesimg = (String) request.getSession().getAttribute("dishesimg");
        String dishesId = request.getParameter("dishesId");
        info.setDishesId(Integer.parseInt(dishesId));
        info.setDishesImg(dishesimg);

        // �����û�����������
        DishesService service = new DishesService();
        // ִ���޸Ĳ���
        service.dishesModify(info);
        // ��ת���û��������
        response.sendRedirect("/OrderSys/todishesadmin.order");
//        response.sendRedirect("/pages/admin/useradmin.jsp");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
