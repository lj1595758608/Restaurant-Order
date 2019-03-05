package com.chinasofti.ordersys.servlets.admin;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class OperateSearchServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
         String startTime = request.getParameter("starttime");
         String endTime = request.getParameter("endtime");
         request.setAttribute("startTime",startTime);
         request.setAttribute("endTime",endTime);

         request.getRequestDispatcher("tooperatedatashow.order").forward(request,response);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
