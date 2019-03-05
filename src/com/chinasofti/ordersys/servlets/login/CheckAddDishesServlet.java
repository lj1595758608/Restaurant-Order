package com.chinasofti.ordersys.servlets.login;

import com.chinasofti.ordersys.vo.DishesInfo;
import com.chinasofti.util.jdbc.template.JDBCTemplateWithDS;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class CheckAddDishesServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // ��ȡ��������е��û�����Ϣ
        String dishesName = request.getParameter("name");
        // ������ajax���������Ҫת��
        dishesName = new String(dishesName.getBytes("iso8859-1"), "utf-8");
        // ��ȡ�������ӳص����ݿ�ģ��������߶���
        JDBCTemplateWithDS helper = JDBCTemplateWithDS.getJDBCHelper();
        // ��ѯ��Ӧ�û������û���Ϣ
        ArrayList<DishesInfo> list = helper.preparedQueryForList(
                "select dishesName from dishesinfo where dishesName=?",
                new Object[] { dishesName }, DishesInfo.class);
        // ��ȡ��Կͻ��˵����������
        PrintWriter pw = response.getWriter();
        // ������ݿ���������
        if (list.size() == 0) {
            // ���������ӱ�ʶ
            pw.print("OK");
            // ������ݿ���������
        } else {
            // ���������ӱ�ʶ
            pw.print("FAIL");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
