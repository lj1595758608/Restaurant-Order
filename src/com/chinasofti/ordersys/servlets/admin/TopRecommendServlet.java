package com.chinasofti.ordersys.servlets.admin;

import com.chinasofti.ordersys.service.admin.DishesService;
import com.chinasofti.ordersys.vo.DishesInfo;
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

public class TopRecommendServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DishesService service = new DishesService();
        ArrayList<DishesInfo> list = service.getRecommendDishes();

        response.setContentType("text/xml");
        try {
            // ����XML DOM��
            Document doc = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder().newDocument();
            // ����XML���ڵ�
            Element root = doc.createElement("dishes");
            // �����ڵ����DOM��
            doc.appendChild(root);
            // ѭ��������������е��û���Ϣ
            for (DishesInfo info : list) {
                // ÿһ���û�����һ���û���ǩ
                Element dishes = doc.createElement("dish");

                // �����û�ID��ǩ
                Element dishesId = doc.createElement("dishesId");
                // �����û�ID��ǩ�ı�����
                dishesId.setTextContent(info.getDishesId() +" ");
                // ���û�ID��ǩ����Ϊ�û���ǩ�ӱ�ǩ
                dishes.appendChild(dishesId);

                // �����û�����ǩ
                Element dishesName = doc.createElement("dishesName");
                // �����û�����ǩ�ı�����
                dishesName.setTextContent(info.getDishesName());
                // ���û�����ǩ����Ϊ�û���ǩ�ӱ�ǩ
                dishes.appendChild(dishesName);

                // ������ɫid��ǩ
                Element dishesDiscript = doc.createElement("dishesDiscript");
                // ���ý�ɫid��ǩ�ı�����
                dishesDiscript.setTextContent(info.getDishesDiscript());
                // ����ɫid��ǩ����Ϊ�û���ǩ�ӱ�ǩ
                dishes.appendChild(dishesDiscript);

                Element dishesImg = doc.createElement("dishesImg");
                // ���ý�ɫ����ǩ�ı�����
                dishesImg.setTextContent(info.getDishesImg() + " ");
                // ����ɫ����ǩ����Ϊ�û���ǩ�ӱ�ǩ
                dishes.appendChild(dishesImg);

                // ������ɫid��ǩ
                Element dishesTxt = doc.createElement("dishesTxt");
                // ���ý�ɫid��ǩ�ı�����
                dishesTxt.setTextContent(info.getDishesDiscript());
                // ����ɫid��ǩ����Ϊ�û���ǩ�ӱ�ǩ
                dishes.appendChild(dishesTxt);

                Element dishesPrice = doc.createElement("dishesPrice");
                // ���ý�ɫ����ǩ�ı�����
                dishesPrice.setTextContent(info.getDishesPrice() + " ");
                // ����ɫ����ǩ����Ϊ�û���ǩ�ӱ�ǩ
                dishes.appendChild(dishesPrice);

                // ������ɫ����ǩ
                Element recommend = doc.createElement("recommend");
                // ���ý�ɫ����ǩ�ı�����
                recommend.setTextContent(info.getRecommend() + " ");
                // ����ɫ����ǩ����Ϊ�û���ǩ�ӱ�ǩ
                dishes.appendChild(recommend);

                // �����û���ǩΪ����ǩ�ӱ�ǩ
                root.appendChild(dishes);
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
