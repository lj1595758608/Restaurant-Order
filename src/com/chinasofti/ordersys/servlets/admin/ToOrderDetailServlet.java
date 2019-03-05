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
        // ���÷��ص�MIME����Ϊxml
        response.setContentType("text/xml");
        // ��ȡϣ����ʾ��ҳ����
        int orderId = Integer.parseInt(request.getParameter("orderId"));
        // �����û�����������
        OrderService service = new OrderService();
        // ���з�ҳ���ݲ�ѯ
        ArrayList<OrderDetailInfo> list = service.getDishesDetail(orderId);
        // ���Խ�����ṹ��Ϊxml�ĵ�
        try {
            // ����XML DOM��
            Document doc = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder().newDocument();
            // ����XML���ڵ�
            Element root = doc.createElement("ordersDetail");
            // �����ڵ����DOM��
            doc.appendChild(root);
            // ѭ��������������е��û���Ϣ
            for (OrderDetailInfo info : list) {
                // ÿһ���û�����һ���û���ǩ
                Element orderDetail = doc.createElement("orderDetail");

                // �����û�ID��ǩ
                Element dishesName = doc.createElement("dishesName");
                // �����û�ID��ǩ�ı�����
                dishesName.setTextContent(info.getDishesName() + "");
                // ���û�ID��ǩ����Ϊ�û���ǩ�ӱ�ǩ
                orderDetail.appendChild(dishesName);

                // �����û�����ǩ
                Element dishesPrice = doc.createElement("dishesPrice");
                // �����û�����ǩ�ı�����
                dishesPrice.setTextContent(info.getDishesPrice() + "");
                // ���û�����ǩ����Ϊ�û���ǩ�ӱ�ǩ
                orderDetail.appendChild(dishesPrice);

                // ������ɫid��ǩ
                Element num = doc.createElement("num");
                // ���ý�ɫid��ǩ�ı�����
                num.setTextContent(info.getNum() + "");
                // ����ɫid��ǩ����Ϊ�û���ǩ�ӱ�ǩ
                orderDetail.appendChild(num);

                // �����û���ǩΪ����ǩ�ӱ�ǩ
                root.appendChild(orderDetail);
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
