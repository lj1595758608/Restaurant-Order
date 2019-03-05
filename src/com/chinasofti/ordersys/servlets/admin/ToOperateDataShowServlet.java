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
        // ���Խ�����ṹ��Ϊxml�ĵ�
        try {
            // ����XML DOM��
            Document doc = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder().newDocument();
            // ����XML���ڵ�
            Element root = doc.createElement("orders");
            // �����ڵ����DOM��
            doc.appendChild(root);
            // ѭ��������������е��û���Ϣ
            for (OrderInfo info : list) {
                // ÿһ���û�����һ���û���ǩ
                Element order = doc.createElement("order");

                // �����û�ID��ǩ
                Element orderId = doc.createElement("orderId");
                // �����û�ID��ǩ�ı�����
                orderId.setTextContent(info.getOrderId() + "");
                // ���û�ID��ǩ����Ϊ�û���ǩ�ӱ�ǩ
                order.appendChild(orderId);

                // �����û�����ǩ
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                Element orderBeginDate = doc.createElement("orderBeginDate");
                // �����û�����ǩ�ı�����
                orderBeginDate.setTextContent(sdf.format(info.getOrderBeginDate().getTime())  + "");
                // ���û�����ǩ����Ϊ�û���ǩ�ӱ�ǩ
                order.appendChild(orderBeginDate);

                // ������ɫid��ǩ
                Element orderEndDate = doc.createElement("orderEndDate");
                // ���ý�ɫid��ǩ�ı�����
                orderEndDate.setTextContent(sdf.format(info.getOrderEndDate().getTime()) + "");
                // ����ɫid��ǩ����Ϊ�û���ǩ�ӱ�ǩ
                order.appendChild(orderEndDate);

                // ������ɫ����ǩ
                Element waiterId = doc.createElement("waiterId");
                String waiterName = service.getWaiterName(info.getWaiterId());
                // ���ý�ɫ����ǩ�ı�����
                waiterId.setTextContent(waiterName + "");
                // ����ɫ����ǩ����Ϊ�û���ǩ�ӱ�ǩ
                order.appendChild(waiterId);

                // ������ɫ����ǩ
                Element orderState = doc.createElement("orderState");
                // ���ý�ɫ����ǩ�ı�����
                orderState.setTextContent(info.getOrderState() + "");
                // ����ɫ����ǩ����Ϊ�û���ǩ�ӱ�ǩ
                order.appendChild(orderState);

                // ������ɫ����ǩ
                Element dishesState = doc.createElement("dishesState");
                // ���ý�ɫ����ǩ�ı�����
                dishesState.setTextContent(info.getDishesState() + "");
                // ����ɫ����ǩ����Ϊ�û���ǩ�ӱ�ǩ
                order.appendChild(dishesState);

                // ������ɫ����ǩ
                Element tableId = doc.createElement("tableId");
                // ���ý�ɫ����ǩ�ı�����
                tableId.setTextContent(info.getTableId() + "");
                // ����ɫ����ǩ����Ϊ�û���ǩ�ӱ�ǩ
                order.appendChild(tableId);

                // ������ɫ����ǩ
                Element totalPrice = doc.createElement("totalPrice");
                float totalprice = service.getTotalPrice(info.getOrderId());
                // ���ý�ɫ����ǩ�ı�����
                totalPrice.setTextContent(totalprice + "");
                // ����ɫ����ǩ����Ϊ�û���ǩ�ӱ�ǩ
                order.appendChild(totalPrice);

                // �����û���ǩΪ����ǩ�ӱ�ǩ
                root.appendChild(order);
            }
            // ��������DOM��ת��ΪXML�ĵ��ṹ�ַ���������ͻ���
            TransformerFactory
                    .newInstance()
                    .newTransformer()
                    .transform(new DOMSource(doc),
                            new StreamResult(response.getOutputStream()));
            // �����ѯ��ת�������е��쳣��Ϣ
        } catch (Exception ex) {
            // ����쳣��Ϣ
            ex.printStackTrace();
        }

//        request.setAttribute("OrderDate",list);
//        request.getRequestDispatcher("/pages/admin/operatedataadmin.jsp").forward(request,response);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
