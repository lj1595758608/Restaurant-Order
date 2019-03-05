package com.chinasofti.ordersys.servlets.admin;

import com.chinasofti.ordersys.service.admin.OrderService;
import com.chinasofti.ordersys.vo.OrderInfo;
import com.chinasofti.ordersys.vo.UserInfo;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.text.SimpleDateFormat;

public class ToOperateDataShowServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String startTime = request.getParameter("startTime");
        String endtime = request.getParameter("endTime");
        OrderService service = new OrderService();
        ArrayList<OrderInfo> list = service.getSearchOrder(startTime,endtime);

        response.setContentType("text/xml");
        // 尝试将结果结构化为xml文档
        try {
            // 创建XML DOM树
            Document doc = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder().newDocument();
            // 创建XML根节点
            Element root = doc.createElement("orders");
            // 将根节点加入DOM树
            doc.appendChild(root);
            // 循环遍历结果集合中的用户信息
            for (OrderInfo info : list) {
                // 每一个用户创建一个用户标签
                Element order = doc.createElement("order");

                // 创建用户ID标签
                Element orderId = doc.createElement("orderId");
                // 设置用户ID标签文本内容
                orderId.setTextContent(info.getOrderId() + "");
                // 将用户ID标签设置为用户标签子标签
                order.appendChild(orderId);

                // 创建用户名标签
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                Element orderBeginDate = doc.createElement("orderBeginDate");
                // 设置用户名标签文本内容
                orderBeginDate.setTextContent(sdf.format(info.getOrderBeginDate().getTime())  + "");
                // 将用户名标签设置为用户标签子标签
                order.appendChild(orderBeginDate);

                // 创建角色id标签
                Element orderEndDate = doc.createElement("orderEndDate");
                // 设置角色id标签文本内容
                orderEndDate.setTextContent(sdf.format(info.getOrderEndDate().getTime()) + "");
                // 将角色id标签设置为用户标签子标签
                order.appendChild(orderEndDate);

                // 创建角色名标签
                Element waiterId = doc.createElement("waiterId");
                String waiterName = service.getWaiterName(info.getWaiterId());
                // 设置角色名标签文本内容
                waiterId.setTextContent(waiterName + "");
                // 将角色名标签设置为用户标签子标签
                order.appendChild(waiterId);

                // 创建角色名标签
                Element orderState = doc.createElement("orderState");
                // 设置角色名标签文本内容
                orderState.setTextContent(info.getOrderState() + "");
                // 将角色名标签设置为用户标签子标签
                order.appendChild(orderState);

                // 创建角色名标签
                Element dishesState = doc.createElement("dishesState");
                // 设置角色名标签文本内容
                dishesState.setTextContent(info.getDishesState() + "");
                // 将角色名标签设置为用户标签子标签
                order.appendChild(dishesState);

                // 创建角色名标签
                Element tableId = doc.createElement("tableId");
                // 设置角色名标签文本内容
                tableId.setTextContent(info.getTableId() + "");
                // 将角色名标签设置为用户标签子标签
                order.appendChild(tableId);

                // 创建角色名标签
                Element totalPrice = doc.createElement("totalPrice");
                float totalprice = service.getTotalPrice(info.getOrderId());
                // 设置角色名标签文本内容
                totalPrice.setTextContent(totalprice + "");
                // 将角色名标签设置为用户标签子标签
                order.appendChild(totalPrice);

                // 设置用户标签为根标签子标签
                root.appendChild(order);
            }
            // 将完整的DOM树转换为XML文档结构字符串输出到客户端
            TransformerFactory
                    .newInstance()
                    .newTransformer()
                    .transform(new DOMSource(doc),
                            new StreamResult(response.getOutputStream()));
            // 捕获查询、转换过程中的异常信息
        } catch (Exception ex) {
            // 输出异常信息
            ex.printStackTrace();
        }

//        request.setAttribute("OrderDate",list);
//        request.getRequestDispatcher("/pages/admin/operatedataadmin.jsp").forward(request,response);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
