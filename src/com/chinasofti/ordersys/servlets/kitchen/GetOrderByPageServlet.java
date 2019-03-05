package com.chinasofti.ordersys.servlets.kitchen;

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

import com.chinasofti.ordersys.service.admin.OrderService;
import com.chinasofti.ordersys.vo.KitchenInfo;
import com.chinasofti.ordersys.vo.OrderInfo;
import org.w3c.dom.Document;
import org.w3c.dom.Element;


import com.chinasofti.ordersys.service.admin.UserService;
import com.chinasofti.ordersys.vo.UserInfo;


public class GetOrderByPageServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 设置返回的MIME类型为xml
        response.setContentType("text/xml");
        // 获取希望显示的页码数
        int page = Integer.parseInt(request.getParameter("page"));
        // 创建用户管理服务对象
        OrderService service = new OrderService();
        // 获取最大页码数
        int maxPage = service.getMaxPage(10);
        // 对当前的页码数进行纠错，如果小于1，则直接显示第一页的内容
        page = page < 1 ? 1 : page;
        // 对当前的页码数进行纠错，如果大于最大页码，则直接显示最后一页的内容
        page = page > maxPage ? maxPage : page;
        // 进行分页数据查询
        ArrayList<KitchenInfo> list = service.getByPage(page, 10);
        // 尝试将结果结构化为xml文档
        try {
//            System.out.println("1");
            // 创建XML DOM树
            Document doc = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder().newDocument();
            // 创建XML根节点
            Element root = doc.createElement("orders");
            // 将根节点加入DOM树
            doc.appendChild(root);
            // 循环遍历结果集合中的用户信息
            for (KitchenInfo info : list) {
                // 每一个用户创建一个用户标签
                Element order = doc.createElement("order");
                // 创建用户ID标签
                Element tableId = doc.createElement("tableId");
                // 设置用户ID标签文本内容
                tableId.setTextContent(info.getTableId() + "");
                // 将用户ID标签设置为用户标签子标签
                order.appendChild(tableId);
                // 创建用户名标签
                Element num = doc.createElement("num");
                // 设置用户名标签文本内容
                num.setTextContent(info.getNum()+"");
                // 将用户名标签设置为用户标签子标签
                order.appendChild(num);
                // 创建用户名标签
                Element odId = doc.createElement("odId");
                // 设置用户名标签文本内容
                odId.setTextContent(info.getOdId()+"");
                // 将用户名标签设置为用户标签子标签
                order.appendChild(odId);
                // 创建用户名标签
                Element dishesName= doc.createElement("dishesName");
                // 设置用户名标签文本内容
                dishesName.setTextContent(info.getDishesName()+"");
                // 将用户名标签设置为用户标签子标签
                order.appendChild(dishesName);
                // 创建用户名标签
                Element dishesState= doc.createElement("dishesState");
                // 设置用户名标签文本内容
                dishesState.setTextContent(info.getDishesState()+"");
                // 将用户名标签设置为用户标签子标签
                order.appendChild(dishesState);
                // 设置用户标签为根标签子标签
                root.appendChild(order);
            }
            // 创建当前页码数的标签
            Element pageNow = doc.createElement("page");
            // 设置当前页码数标签的文本内容
            pageNow.setTextContent(page + "");
            // 将当前页码数标签设置为根标签的子标签
            root.appendChild(pageNow);
            // 创建最大页码数的标签
            Element maxPageElement = doc.createElement("maxPage");
            // 设置最大页码数标签的文本内容
            maxPageElement.setTextContent(maxPage + "");
            // 将最大页码数标签设置为根标签的子标签
            root.appendChild(maxPageElement);
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
