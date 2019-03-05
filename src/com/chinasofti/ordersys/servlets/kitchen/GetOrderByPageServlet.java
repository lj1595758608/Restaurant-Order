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
        // ���÷��ص�MIME����Ϊxml
        response.setContentType("text/xml");
        // ��ȡϣ����ʾ��ҳ����
        int page = Integer.parseInt(request.getParameter("page"));
        // �����û�����������
        OrderService service = new OrderService();
        // ��ȡ���ҳ����
        int maxPage = service.getMaxPage(10);
        // �Ե�ǰ��ҳ�������о������С��1����ֱ����ʾ��һҳ������
        page = page < 1 ? 1 : page;
        // �Ե�ǰ��ҳ�������о�������������ҳ�룬��ֱ����ʾ���һҳ������
        page = page > maxPage ? maxPage : page;
        // ���з�ҳ���ݲ�ѯ
        ArrayList<KitchenInfo> list = service.getByPage(page, 10);
        // ���Խ�����ṹ��Ϊxml�ĵ�
        try {
//            System.out.println("1");
            // ����XML DOM��
            Document doc = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder().newDocument();
            // ����XML���ڵ�
            Element root = doc.createElement("orders");
            // �����ڵ����DOM��
            doc.appendChild(root);
            // ѭ��������������е��û���Ϣ
            for (KitchenInfo info : list) {
                // ÿһ���û�����һ���û���ǩ
                Element order = doc.createElement("order");
                // �����û�ID��ǩ
                Element tableId = doc.createElement("tableId");
                // �����û�ID��ǩ�ı�����
                tableId.setTextContent(info.getTableId() + "");
                // ���û�ID��ǩ����Ϊ�û���ǩ�ӱ�ǩ
                order.appendChild(tableId);
                // �����û�����ǩ
                Element num = doc.createElement("num");
                // �����û�����ǩ�ı�����
                num.setTextContent(info.getNum()+"");
                // ���û�����ǩ����Ϊ�û���ǩ�ӱ�ǩ
                order.appendChild(num);
                // �����û�����ǩ
                Element odId = doc.createElement("odId");
                // �����û�����ǩ�ı�����
                odId.setTextContent(info.getOdId()+"");
                // ���û�����ǩ����Ϊ�û���ǩ�ӱ�ǩ
                order.appendChild(odId);
                // �����û�����ǩ
                Element dishesName= doc.createElement("dishesName");
                // �����û�����ǩ�ı�����
                dishesName.setTextContent(info.getDishesName()+"");
                // ���û�����ǩ����Ϊ�û���ǩ�ӱ�ǩ
                order.appendChild(dishesName);
                // �����û�����ǩ
                Element dishesState= doc.createElement("dishesState");
                // �����û�����ǩ�ı�����
                dishesState.setTextContent(info.getDishesState()+"");
                // ���û�����ǩ����Ϊ�û���ǩ�ӱ�ǩ
                order.appendChild(dishesState);
                // �����û���ǩΪ����ǩ�ӱ�ǩ
                root.appendChild(order);
            }
            // ������ǰҳ�����ı�ǩ
            Element pageNow = doc.createElement("page");
            // ���õ�ǰҳ������ǩ���ı�����
            pageNow.setTextContent(page + "");
            // ����ǰҳ������ǩ����Ϊ����ǩ���ӱ�ǩ
            root.appendChild(pageNow);
            // �������ҳ�����ı�ǩ
            Element maxPageElement = doc.createElement("maxPage");
            // �������ҳ������ǩ���ı�����
            maxPageElement.setTextContent(maxPage + "");
            // �����ҳ������ǩ����Ϊ����ǩ���ӱ�ǩ
            root.appendChild(maxPageElement);
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
