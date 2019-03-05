package com.chinasofti.ordersys.servlets.admin;

import com.chinasofti.ordersys.service.admin.OrderService;
import com.chinasofti.ordersys.vo.OrderDetailInfo;
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
import java.util.ArrayList;

public class ToOrderDetailServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 设置返回的MIME类型为xml
        response.setContentType("text/xml");
        // 获取希望显示的页码数
        int orderId = Integer.parseInt(request.getParameter("orderId"));
        // 创建用户管理服务对象
        OrderService service = new OrderService();
        // 进行分页数据查询
        ArrayList<OrderDetailInfo> list = service.getDishesDetail(orderId);
        // 尝试将结果结构化为xml文档
        try {
            // 创建XML DOM树
            Document doc = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder().newDocument();
            // 创建XML根节点
            Element root = doc.createElement("ordersDetail");
            // 将根节点加入DOM树
            doc.appendChild(root);
            // 循环遍历结果集合中的用户信息
            for (OrderDetailInfo info : list) {
                // 每一个用户创建一个用户标签
                Element orderDetail = doc.createElement("orderDetail");

                // 创建用户ID标签
                Element dishesName = doc.createElement("dishesName");
                // 设置用户ID标签文本内容
                dishesName.setTextContent(info.getDishesName() + "");
                // 将用户ID标签设置为用户标签子标签
                orderDetail.appendChild(dishesName);

                // 创建用户名标签
                Element dishesPrice = doc.createElement("dishesPrice");
                // 设置用户名标签文本内容
                dishesPrice.setTextContent(info.getDishesPrice() + "");
                // 将用户名标签设置为用户标签子标签
                orderDetail.appendChild(dishesPrice);

                // 创建角色id标签
                Element num = doc.createElement("num");
                // 设置角色id标签文本内容
                num.setTextContent(info.getNum() + "");
                // 将角色id标签设置为用户标签子标签
                orderDetail.appendChild(num);

                // 设置用户标签为根标签子标签
                root.appendChild(orderDetail);
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
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
