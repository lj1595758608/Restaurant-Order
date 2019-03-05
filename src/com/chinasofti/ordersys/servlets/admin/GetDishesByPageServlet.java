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


public class GetDishesByPageServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 设置返回的MIME类型为xml
        response.setContentType("text/xml");
        // 获取希望显示的页码数
        int page = Integer.parseInt(request.getParameter("page"));
        // 创建用户管理服务对象
        DishesService service = new DishesService();
        // 获取最大页码数
        int maxPage = service.getMaxPage(7);
        // 对当前的页码数进行纠错，如果小于1，则直接显示第一页的内容
        page = page < 1 ? 1 : page;
        // 对当前的页码数进行纠错，如果大于最大页码，则直接显示最后一页的内容
        page = page > maxPage ? maxPage : page;
        // 进行分页数据查询
        ArrayList<DishesInfo> list = service.getByPage(page, 7);
        // 尝试将结果结构化为xml文档
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
        // for (UserInfo info : list) {
        // System.out.println(info.getUserId() + "\t" + info.getUserAccount());
        // }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
