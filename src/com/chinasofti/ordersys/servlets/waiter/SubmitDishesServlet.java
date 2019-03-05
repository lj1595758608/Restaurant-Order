package com.chinasofti.ordersys.servlets.waiter;

import com.chinasofti.ordersys.service.admin.DishesService;
import com.chinasofti.ordersys.service.waiter.OrderService;
import com.chinasofti.ordersys.vo.DishesInfo;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;

public class SubmitDishesServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//�������ڸ�ʽ
        String nowTime = new String(df.format(new Date()));
        String dishesDetail = request.getParameter("dishes");
        String[] dishesDetailArray = dishesDetail.split(",");
        String tableId = request.getParameter("tableId");
        String waiterId = request.getParameter("waiterId");
        OrderService serviceo = new OrderService();

        int newOrderId = serviceo.addOrder(nowTime, Integer.parseInt(waiterId), Integer.parseInt(tableId));
        for(int i = 0; i<=dishesDetail.length()/2; i=i+2){
            serviceo.addOrderDishes(newOrderId, Integer.parseInt(dishesDetailArray[i]), Integer.parseInt(dishesDetailArray[i+1]));
        }

//        System.out.println(tableId);
//        System.out.println(dishesDetail);
//        System.out.println(waiterId);
//        System.out.println(nowTime);


        // ���÷��ص�MIME����Ϊxml
        response.setContentType("text/xml");
        // ��ȡϣ����ʾ��ҳ����
        int page = Integer.parseInt(request.getParameter("page"));
        // �����û������������
        DishesService service = new DishesService();
        // ��ȡ���ҳ����
        int maxPage = service.getMaxPage(7);
        // �Ե�ǰ��ҳ�������о��������С��1����ֱ����ʾ��һҳ������
        page = page < 1 ? 1 : page;
        // �Ե�ǰ��ҳ�������о���������������ҳ�룬��ֱ����ʾ���һҳ������
        page = page > maxPage ? maxPage : page;
        // ���з�ҳ���ݲ�ѯ
        ArrayList<DishesInfo> list = service.getByPage(page, 7);
        // ���Խ�����ṹ��Ϊxml�ĵ�
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