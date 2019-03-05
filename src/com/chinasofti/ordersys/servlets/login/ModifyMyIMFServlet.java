package com.chinasofti.ordersys.servlets.login;

import com.chinasofti.ordersys.service.admin.UserService;
import com.chinasofti.ordersys.vo.UserInfo;
import com.chinasofti.util.web.upload.MultipartRequestParser;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ModifyMyIMFServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // �����û�����������
        UserService service = new UserService();
        // �������������������
        MultipartRequestParser parser = new MultipartRequestParser();
        // ������ȡUserInfo��ɫ��Ϣ����
        UserInfo info = (UserInfo) parser.parse(request, UserInfo.class);
        // ִ���޸Ĳ���
        service.ModifyMyImf(info);
        // ��ת���û��������

        response.sendRedirect("/OrderSys/touseradmin.order");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
