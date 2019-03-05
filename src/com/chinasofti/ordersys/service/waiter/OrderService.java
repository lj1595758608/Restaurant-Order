package com.chinasofti.ordersys.service.waiter;

import com.chinasofti.ordersys.vo.OrderInfo;
import com.chinasofti.ordersys.vo.UserInfo;
import com.chinasofti.util.jdbc.template.JDBCTemplateWithDS;
import com.chinasofti.util.sec.Passport;

import java.util.ArrayList;

public class OrderService {

    public int addOrder(String nowTime, int waiterId, int tableId) {
        // ��ȡ�������ӳص����ݿ�ģ��������߶���
        JDBCTemplateWithDS helper = JDBCTemplateWithDS.getJDBCHelper();
        // ִ���û���Ϣ�������
        helper.executePreparedUpdate(
                "insert into orderinfo(orderBeginDate,waiterId,orderState,tableId) values(?,?,0,?)",
                new Object[] { nowTime, waiterId, tableId });

        int newOrderId = (int) helper.preparedQueryForObject(
                "select max(orderId) from orderinfo",
                new Object[] {});

        return newOrderId;
    }

    public void addOrderDishes(int orderReference, int dishes, int num){
        // ��ȡ�������ӳص����ݿ�ģ��������߶���
        JDBCTemplateWithDS helper = JDBCTemplateWithDS.getJDBCHelper();
        // ִ���û���Ϣ�������
        helper.executePreparedUpdate(
                "insert into orderdishes(orderReference,dishes,num) values(?,?,?)",
                new Object[] { orderReference, dishes, num });

    }

    public String getWaiterName(int waiterId){
        // ��ȡ�������ӳص����ݿ�ģ��������߶���
        JDBCTemplateWithDS helper = JDBCTemplateWithDS.getJDBCHelper();
        // ͨ����ѯ����ȡ��Ӧҳ������
        String waiterName = (String)helper.preparedQueryForObject(
                "SELECT userAccount FROM userinfo WHERE userId=?",
                new Object[] { waiterId });
        return waiterName;
    }

    public ArrayList<OrderInfo> getCheckoutDishes (){

        // ��ȡ�������ӳص����ݿ�ģ��������߶���
        JDBCTemplateWithDS helper = JDBCTemplateWithDS.getJDBCHelper();
        // ͨ����ѯ����ȡ��Ӧҳ������
        ArrayList<OrderInfo> list = helper.preparedQueryForList(
                "SELECT * FROM orderinfo WHERE orderState=0",
                new Object[] {}, OrderInfo.class);
        // ���ؽ��
//        SELECT * FROM orderinfo WHERE orderBeginDate >='2015-10-12 16:49' AND orderEndDate <= '2016-10-19 16:49'
        return list;

    }

    public void DeleteCheckoutDishes(int orderId, String nowTime){
        // ��ȡ�������ӳص����ݿ�ģ��������߶���
        JDBCTemplateWithDS helper = JDBCTemplateWithDS.getJDBCHelper();
        // ִ���û���Ϣ�������
        helper.executePreparedUpdate(
                "update orderinfo set orderEndDate=?,orderState=1 where orderId=?",
                new Object[] { nowTime, orderId });

    }

}
