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
            // 创建XML DOM树
            Document doc = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder().newDocument();
            // 创建XML根节点
            Element root = doc.createElement("dishes");
            // 将根节点加入DOM树
            doc.appendChild(root);
            // 循环遍历结果集合中的用户信息
            for (DishesInfo info : list) {
                // 每一个用户创建一个用户标签
                Element dishes = doc.createElement("dish");

                // 创建用户ID标签
                Element dishesId = doc.createElement("dishesId");
                // 设置用户ID标签文本内容
                dishesId.setTextContent(info.getDishesId() +" ");
                // 将用户ID标签设置为用户标签子标签
                dishes.appendChild(dishesId);

                // 创建用户名标签
                Element dishesName = doc.createElement("dishesName");
                // 设置用户名标签文本内容
                dishesName.setTextContent(info.getDishesName());
                // 将用户名标签设置为用户标签子标签
                dishes.appendChild(dishesName);

                // 创建角色id标签
                Element dishesDiscript = doc.createElement("dishesDiscript");
                // 设置角色id标签文本内容
                dishesDiscript.setTextContent(info.getDishesDiscript());
                // 将角色id标签设置为用户标签子标签
                dishes.appendChild(dishesDiscript);

                Element dishesImg = doc.createElement("dishesImg");
                // 设置角色名标签文本内容
                dishesImg.setTextContent(info.getDishesImg() + " ");
                // 将角色名标签设置为用户标签子标签
                dishes.appendChild(dishesImg);

                // 创建角色id标签
                Element dishesTxt = doc.createElement("dishesTxt");
                // 设置角色id标签文本内容
                dishesTxt.setTextContent(info.getDishesDiscript());
                // 将角色id标签设置为用户标签子标签
                dishes.appendChild(dishesTxt);

                Element dishesPrice = doc.createElement("dishesPrice");
                // 设置角色名标签文本内容
                dishesPrice.setTextContent(info.getDishesPrice() + " ");
                // 将角色名标签设置为用户标签子标签
                dishes.appendChild(dishesPrice);

                // 创建角色名标签
                Element recommend = doc.createElement("recommend");
                // 设置角色名标签文本内容
                recommend.setTextContent(info.getRecommend() + " ");
                // 将角色名标签设置为用户标签子标签
                dishes.appendChild(recommend);

                // 设置用户标签为根标签子标签
                root.appendChild(dishes);
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
