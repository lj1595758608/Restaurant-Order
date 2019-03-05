package com.chinasofti.ordersys.servlets.admin;

import com.chinasofti.ordersys.service.admin.DishesService;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class CheckoutServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/xml");

        OrderService service = new OrderService();
        ArrayList<OrderInfo> list = service.getCheckoutInformation();
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

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                // �����û�����ǩ
                Element orderBeginDate = doc.createElement("orderBeginDate");
                // �����û�����ǩ�ı�����
                orderBeginDate.setTextContent(sdf.format(info.getOrderBeginDate().getTime()) + "");
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
                // ���ý�ɫ����ǩ�ı�����
                waiterId.setTextContent(info.getWaiterId() + "");
                // ����ɫ����ǩ����Ϊ�û���ǩ�ӱ�ǩ
                order.appendChild(waiterId);

                // ������ɫ������Ϣ��ǩ
                Element orderState = doc.createElement("orderState");
                // ���ý�ɫ������Ϣ��ǩ�ı�����
                orderState.setTextContent(info.getOrderState() + "");
                // ����ɫ������Ϣ��ǩ����Ϊ�û���ǩ�ӱ�ǩ
                order.appendChild(orderState);

                // ������ɫͷ���ǩ
                Element tableId = doc.createElement("tableId");
                // ���ý�ɫͷ���ǩ�ı�����
                tableId.setTextContent(info.getTableId() + "");
                // ����ͷ���ǩΪ�û���ǩ�ӱ�ǩ
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

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
