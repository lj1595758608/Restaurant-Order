package com.chinasofti.ordersys.servlets.kitchen;

import com.chinasofti.ordersys.servlets.waiter.GetOrderMsgServlet;
import com.chinasofti.util.web.serverpush.MessageProducer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class SendOrderMsgServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // ������Ӧ�ַ���
        response.setCharacterEncoding("utf-8");
        // ��ȡ������Ϣ
        String tableId = request.getParameter("tableId");
        String dishesName = request.getParameter("dishesName");
        String num =request.getParameter("num");
        String ordermsg = tableId+"����"+num+"��"+dishesName+"����ɣ������Ա����";
        System.out.println("tableId:"+tableId);
        System.out.println("dishesName:"+dishesName);
        System.out.println("num:"+num);
        System.out.println(ordermsg);
        // ����ʵʩ��Ϣ������
        MessageProducer producer = new MessageProducer();
        // ��ȡʵʱ������Ϣ�ȴ��б�
        ArrayList<String> list = GetOrderMsgServlet.bords;
        // �����ȴ��б�
        for (int i = list.size() - 1; i >= 0; i--) {
            // ��ȡ�ȴ���Ϣ���û�sessionID
            String id = list.get(i);
            // ��Ը�sessionID����Ϣ���⡢����������Ϣ
            producer.sendMessage(id, "ordermsg", ordermsg);
            // ����sessionID�ӵȴ��б���ɾ��
            list.remove(id);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
