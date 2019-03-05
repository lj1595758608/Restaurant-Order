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
        // 设置响应字符集
        response.setCharacterEncoding("utf-8");
        // 获取公告信息
        String tableId = request.getParameter("tableId");
        System.out.println("tableId:"+tableId);
        // 创建实施消息生产者
        MessageProducer producer = new MessageProducer();
        // 获取实时公告信息等待列表
        ArrayList<String> list = GetOrderMsg2Servlet.bords;
        // 遍历等待列表
        for (int i = list.size() - 1; i >= 0; i--) {
            // 获取等待信息的用户sessionID
            String id = list.get(i);
            // 针对该sessionID和消息标题、内容生产消息
            producer.sendMessage(id, "tableId", tableId);
            // 将该sessionID从等待列表中删除
            list.remove(id);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
