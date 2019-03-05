package com.chinasofti.ordersys.servlets.kitchen;

import com.chinasofti.ordersys.service.admin.UserService;
import com.chinasofti.ordersys.vo.UserInfo;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ToModifyMyPassForKitchenServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // �����û�����������
        UserService service = new UserService();
        // ��ȡҪ�޸ĵ��û�ID����ѯ��Ӧ���û���Ϣ
        UserInfo info = service.getUserById(new Integer(request
                .getParameter("userId")));
        // ���û���Ϣ����request������
        request.setAttribute("MODIFY_INFO", info);

        request.getRequestDispatcher("/pages/kitchen/modifymypass.jsp").forward(
                request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
