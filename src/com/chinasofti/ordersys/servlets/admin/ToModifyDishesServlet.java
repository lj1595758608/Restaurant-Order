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
        // �����û�����������
        DishesService service = new DishesService();
        // ��ȡҪ�޸ĵ��û�ID����ѯ��Ӧ���û���Ϣ
        DishesInfo info = service.getDishesById(new Integer(request
                .getParameter("dishesId")));
        // ���û���Ϣ����request������
        request.setAttribute("MODIFY_INFO", info);
        // ��ת���û���Ϣ�޸Ľ���
        request.getRequestDispatcher("/pages/admin/modifydishes.jsp").forward(
                request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
