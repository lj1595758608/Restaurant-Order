package com.chinasofti.ordersys.servlets.waiter;

import com.chinasofti.ordersys.service.admin.UserService;
import com.chinasofti.ordersys.vo.UserInfo;
import com.chinasofti.util.web.upload.MultipartRequestParser;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ToModifyMyPassServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 创建用户管理服务对象
        UserService service = new UserService();
        // 创建表单请求解析器工具
        MultipartRequestParser parser = new MultipartRequestParser();
        // 解析获取UserInfo角色信息对象
        UserInfo info = (UserInfo) parser.parse(request, UserInfo.class);
        // 执行修改操作
        service.ModifyMyImf(info);
        // 跳转到用户管理界面

        request.getRequestDispatcher("/pages/waiter/waitermain.jsp").forward(
                request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
