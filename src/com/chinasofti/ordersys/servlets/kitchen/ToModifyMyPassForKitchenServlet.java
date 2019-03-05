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
        // 创建用户管理服务对象
        UserService service = new UserService();
        // 获取要修改的用户ID并查询对应的用户信息
        UserInfo info = service.getUserById(new Integer(request
                .getParameter("userId")));
        // 将用户信息加入request作用域
        request.setAttribute("MODIFY_INFO", info);

        request.getRequestDispatcher("/pages/kitchen/modifymypass.jsp").forward(
                request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
