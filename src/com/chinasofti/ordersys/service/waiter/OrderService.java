package com.chinasofti.ordersys.service.waiter;

import com.chinasofti.ordersys.vo.OrderInfo;
import com.chinasofti.ordersys.vo.UserInfo;
import com.chinasofti.util.jdbc.template.JDBCTemplateWithDS;
import com.chinasofti.util.sec.Passport;

import java.util.ArrayList;

public class OrderService {

    public int addOrder(String nowTime, int waiterId, int tableId) {
        // 获取带有连接池的数据库模版操作工具对象
        JDBCTemplateWithDS helper = JDBCTemplateWithDS.getJDBCHelper();
        // 执行用户信息插入操作
        helper.executePreparedUpdate(
                "insert into orderinfo(orderBeginDate,waiterId,orderState,tableId) values(?,?,0,?)",
                new Object[] { nowTime, waiterId, tableId });

        int newOrderId = (int) helper.preparedQueryForObject(
                "select max(orderId) from orderinfo",
                new Object[] {});

        return newOrderId;
    }

    public void addOrderDishes(int orderReference, int dishes, int num){
        // 获取带有连接池的数据库模版操作工具对象
        JDBCTemplateWithDS helper = JDBCTemplateWithDS.getJDBCHelper();
        // 执行用户信息插入操作
        helper.executePreparedUpdate(
                "insert into orderdishes(orderReference,dishes,num) values(?,?,?)",
                new Object[] { orderReference, dishes, num });

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

    public ArrayList<OrderInfo> getCheckoutDishes (){

        // 获取带有连接池的数据库模版操作工具对象
        JDBCTemplateWithDS helper = JDBCTemplateWithDS.getJDBCHelper();
        // 通过查询语句获取对应页的数据
        ArrayList<OrderInfo> list = helper.preparedQueryForList(
                "SELECT * FROM orderinfo WHERE orderState=0",
                new Object[] {}, OrderInfo.class);
        // 返回结果
//        SELECT * FROM orderinfo WHERE orderBeginDate >='2015-10-12 16:49' AND orderEndDate <= '2016-10-19 16:49'
        return list;

    }

    public void DeleteCheckoutDishes(int orderId, String nowTime){
        // 获取带有连接池的数据库模版操作工具对象
        JDBCTemplateWithDS helper = JDBCTemplateWithDS.getJDBCHelper();
        // 执行用户信息插入操作
        helper.executePreparedUpdate(
                "update orderinfo set orderEndDate=?,orderState=1 where orderId=?",
                new Object[] { nowTime, orderId });

    }

}
