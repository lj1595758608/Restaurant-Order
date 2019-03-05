package com.chinasofti.ordersys.servlets.waiter;

import com.chinasofti.util.web.serverpush.MessageProducer;
import com.chinasofti.ordersys.servlets.kitchen.GetOrderMsg2Servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class SendOrderMsg2Servlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // ������Ӧ�ַ���
        response.setCharacterEncoding("utf-8");
        // ��ȡ������Ϣ
        String tableId = request.getParameter("tableId");
        System.out.println("tableId:"+tableId);
        // ����ʵʩ��Ϣ������
        MessageProducer producer = new MessageProducer();
        // ��ȡʵʱ������Ϣ�ȴ��б�
        ArrayList<String> list = GetOrderMsg2Servlet.bords;
        // �����ȴ��б�
        for (int i = list.size() - 1; i >= 0; i--) {
            // ��ȡ�ȴ���Ϣ���û�sessionID
            String id = list.get(i);
            // ��Ը�sessionID����Ϣ���⡢����������Ϣ
            producer.sendMessage(id, "tableId", tableId);
            // ����sessionID�ӵȴ��б���ɾ��
            list.remove(id);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
