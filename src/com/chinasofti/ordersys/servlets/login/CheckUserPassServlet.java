package com.chinasofti.ordersys.servlets.login;

import com.chinasofti.ordersys.vo.DishesInfo;
import com.chinasofti.util.jdbc.template.JDBCTemplateWithDS;
import com.chinasofti.util.sec.Passport;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class CheckUserPassServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // ��ȡ��������е��û�����Ϣ
        String oldPass = request.getParameter("oldPass");
        String userAccount = request.getParameter("userAccount");
        // ������ajax���������Ҫת��
        oldPass = new String(oldPass.getBytes("iso8859-1"), "utf-8");
        // ��ȡ�������ӳص����ݿ�ģ��������߶���
        JDBCTemplateWithDS helper = JDBCTemplateWithDS.getJDBCHelper();
        // ��ѯ��Ӧ�û������û���Ϣ
        String password = (String) helper.preparedQueryForObject(
                "select userPass from userinfo where userAccount=?",
                new Object[] { userAccount });

        Passport passport = new Passport();
        // ��ȡ��Կͻ��˵����������
        PrintWriter pw = response.getWriter();
        // ������ݿ���������
        if (passport.md5(oldPass).equals(password)) {
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
