package com.chinasofti.ordersys.service.admin;

import com.chinasofti.ordersys.vo.*;
import com.chinasofti.util.jdbc.template.JDBCTemplateWithDS;

import java.util.ArrayList;
import com.chinasofti.util.sec.Passport;

public class OrderService {

    public ArrayList<OrderInfo> getSearchOrder(String starttime, String endtime) {

        // 获取带有连接池的数据库模版操作工具对象
        JDBCTemplateWithDS helper = JDBCTemplateWithDS.getJDBCHelper();
        // 通过查询语句获取对应页的数据
        ArrayList<OrderInfo> list = helper.preparedQueryForList(
                        "SELECT * FROM orderinfo WHERE orderBeginDate >=? AND orderEndDate <= ?",
                        new Object[] { starttime, endtime }, OrderInfo.class);
        // 返回结果
//        SELECT * FROM orderinfo WHERE orderBeginDate >='2015-10-12 16:49' AND orderEndDate <= '2016-10-19 16:49'
        return list;

    }

    public String getWaiterName(int waiterId){
        // 获取带有连接池的数据库模版操作工具对象
        JDBCTemplateWithDS helper = JDBCTemplateWithDS.getJDBCHelper();
        // 通过查询语句获取对应页的数据
        String waiterName = (String)helper.preparedQueryForObject(
                "SELECT userAccount FROM userinfo WHERE userId=?",
                new Object[] { waiterId });
        return waiterName;
    }

//    public float getTotalPrice(int orderId){
//        // 获取带有连接池的数据库模版操作工具对象
//        JDBCTemplateWithDS helper = JDBCTemplateWithDS.getJDBCHelper();
//        // 通过查询语句获取对应页的数据
//        int[] dishesNum = (int[]) helper.preparedQueryForObject(
//                "SELECT num FROM orderdishes WHERE orderReference=?",
//                new Object[] { orderId });
////       System.out.println(dishesNum[0]);
////        String[] dishesnum = (String[]) dishesNum.toArray();
//
//        float[] dishesPrice = (float[]) helper.preparedQueryForObject(
//                "SELECT dishesPrice FROM orderdishes,dishesinfo WHERE orderdishes.dishes=dishesinfo.dishesId AND orderReference=?",
//                new Object[] { orderId });
////        String[] dishesprice = (String[]) dishesPrice.toArray();
//
//        float totalPrice = 0;
////        for(int i = 0; i < dishesNum.length; i++){
////            totalPrice += dishesNum[i] * dishesPrice[i];
////        }
//        return totalPrice;
//    }

    public float getTotalPrice(int orderId) {
        // 获取带有连接池的数据库模版操作工具对象
        JDBCTemplateWithDS helper = JDBCTemplateWithDS.getJDBCHelper();
        // 按照用户ID条件进行查询操作
        float totalPrice = 0;
        ArrayList<OrderDishesInfo> list = helper
                .preparedQueryForList(
                        "select * from orderdishes where orderReference=?",
                        new Object[]{ orderId }, OrderDishesInfo.class);
        // 返回给定ID对应的用户信息
        for (int i=0;i<list.size();i++) {
            ArrayList<DishesInfo> list1 = helper
                    .preparedQueryForList(
                            "select * from dishesinfo where dishesId=?",
                            new Object[]{ list.get(i).getDishes() }, DishesInfo.class);

            totalPrice += list1.get(0).getDishesPrice() * list.get(i).getNum();

        }
        return totalPrice;
    }

    /**
     * 分页获取用户数据的方法
     *
     * @param page
     *            要获取数据的页号
     * @param pageSize
     *            每页显示的条目数
     * @return 当前页的用户数据列表
     * */
    public ArrayList<KitchenInfo> getByPage(int page, int pageSize) {
        // 获取带有连接池的数据库模版操作工具对象
        JDBCTemplateWithDS helper = JDBCTemplateWithDS.getJDBCHelper();
        // 通过查询语句获取对应页的数据
        ArrayList<KitchenInfo> list = helper
                .preparedForPageList(
                        "select tableId,num,dishesName,dishesState,odId from orderinfo,orderdishes,dishesinfo where orderinfo.orderId=orderdishes.orderReference and dishesinfo.dishesId=orderdishes.dishes and (dishesState=0 or dishesState=1)  order by odId",
                        new Object[] {}, page, pageSize, KitchenInfo.class);
        // 返回结果
        return list;
    }

    /**
     * 获取用户信息的最大页数
     *
     * @param pageSize
     *            每页显示的条目数
     * @return 当前数据库中数据的最大页数
     * */
    public int getMaxPage(int pageSize) {
        // 获取带有连接池的数据库模版操作工具对象
        JDBCTemplateWithDS helper = JDBCTemplateWithDS.getJDBCHelper();
        // 获取最大页数信息
        Long rows = (Long) helper.preparedQueryForObject(
                "select count(*) from orderinfo,orderdishes,dishesinfo where orderinfo.orderId=orderdishes.orderReference and dishesinfo.dishesId=orderdishes.dishes and dishesState=0 or dishesState=1", new Object[] {});
        System.out.println(rows.longValue());
        // 返回最大页数
        return (int) ((rows.longValue() - 1) / pageSize + 1);
    }

    /**
     * 修改用户自身信息的方法
     *
     * @param
     *
     * */
    public void modify(int dishesState,int odId) {
        // 获取带有连接池的数据库模版操作工具对象
        JDBCTemplateWithDS helper = JDBCTemplateWithDS.getJDBCHelper();
        // 创建加密工具
        Passport passport = new Passport();
        // 修改本人信息
        helper.executePreparedUpdate(
                "update orderdishes set dishesState=? where odId=?",
                new Object[] { new Integer(dishesState),odId}
        );

    }

    public ArrayList<OrderInfo> getCheckoutInformation(){
        // 获取带有连接池的数据库模版操作工具对象
        JDBCTemplateWithDS helper = JDBCTemplateWithDS.getJDBCHelper();
        // 通过查询语句获取对应页的数据
        ArrayList<OrderInfo> list = helper.preparedQueryForList(
                "SELECT * FROM orderinfo WHERE orderState=1",
                new Object[] {}, OrderInfo.class);
        // 返回结果
//        SELECT * FROM orderinfo WHERE orderBeginDate >='2015-10-12 16:49' AND orderEndDate <= '2016-10-19 16:49'
        return list;
    }

    public void DeleteOrder( int orderId ){

        // 获取带有连接池的数据库模版操作工具对象
        JDBCTemplateWithDS helper = JDBCTemplateWithDS.getJDBCHelper();
        helper.executePreparedUpdate(
                "delete from orderdishes where orderReference=?",
                new Object[] { orderId }
                );
        helper.executePreparedUpdate(
                "delete from orderinfo where orderId=?",
                new Object[] { orderId }
        );

    }

    public void CheckoutOrder(int orderId){
        //获取带有连接池的数据库模版操作工具对象
        JDBCTemplateWithDS helper = JDBCTemplateWithDS.getJDBCHelper();
        helper.executePreparedUpdate(
                "update orderinfo set orderState=2 where orderId=?",
                new Object[] { orderId }
        );
    }

    public ArrayList<OrderDetailInfo> getDishesDetail(int orderId){
        // 获取带有连接池的数据库模版操作工具对象
        JDBCTemplateWithDS helper = JDBCTemplateWithDS.getJDBCHelper();
        // 通过查询语句获取对应页的数据
        ArrayList<OrderDetailInfo> list = helper.preparedQueryForList(
                "select dishesName,dishesPrice,num from dishesinfo,orderdishes where dishesinfo.dishesId=orderdishes.dishes and orderdishes.orderReference=?",
                new Object[] { orderId }, OrderDetailInfo.class);
        // 返回结果
//        SELECT * FROM orderinfo WHERE orderBeginDate >='2015-10-12 16:49' AND orderEndDate <= '2016-10-19 16:49'
        return list;
    }


}
