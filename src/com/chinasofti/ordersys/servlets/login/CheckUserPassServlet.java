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
        // 获取请求参数中的用户名信息
        String oldPass = request.getParameter("oldPass");
        String userAccount = request.getParameter("userAccount");
        // 由于是ajax请求，因此需要转码
        oldPass = new String(oldPass.getBytes("iso8859-1"), "utf-8");
        // 获取带有连接池的数据库模版操作工具对象
        JDBCTemplateWithDS helper = JDBCTemplateWithDS.getJDBCHelper();
        // 查询对应用户名的用户信息
        String password = (String) helper.preparedQueryForObject(
                "select userPass from userinfo where userAccount=?",
                new Object[] { userAccount });

        Passport passport = new Passport();
        // 获取针对客户端的文字输出流
        PrintWriter pw = response.getWriter();
        // 如果数据库中无数据
        if (passport.md5(oldPass).equals(password)) {
            // 输出可以添加标识
            pw.print("OK");
            // 如果数据库中有数据
        } else {
            // 输出不能添加标识
            pw.print("FAIL");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
