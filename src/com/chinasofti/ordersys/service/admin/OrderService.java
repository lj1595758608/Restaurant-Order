package com.chinasofti.ordersys.service.admin;

import com.chinasofti.ordersys.vo.*;
import com.chinasofti.util.jdbc.template.JDBCTemplateWithDS;

import java.util.ArrayList;
import com.chinasofti.util.sec.Passport;

public class OrderService {

    public ArrayList<OrderInfo> getSearchOrder(String starttime, String endtime) {

        // ��ȡ�������ӳص����ݿ�ģ��������߶���
        JDBCTemplateWithDS helper = JDBCTemplateWithDS.getJDBCHelper();
        // ͨ����ѯ����ȡ��Ӧҳ������
        ArrayList<OrderInfo> list = helper.preparedQueryForList(
                        "SELECT * FROM orderinfo WHERE orderBeginDate >=? AND orderEndDate <= ?",
                        new Object[] { starttime, endtime }, OrderInfo.class);
        // ���ؽ��
//        SELECT * FROM orderinfo WHERE orderBeginDate >='2015-10-12 16:49' AND orderEndDate <= '2016-10-19 16:49'
        return list;

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

//    public float getTotalPrice(int orderId){
//        // ��ȡ�������ӳص����ݿ�ģ��������߶���
//        JDBCTemplateWithDS helper = JDBCTemplateWithDS.getJDBCHelper();
//        // ͨ����ѯ����ȡ��Ӧҳ������
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
        // ��ȡ�������ӳص����ݿ�ģ��������߶���
        JDBCTemplateWithDS helper = JDBCTemplateWithDS.getJDBCHelper();
        // �����û�ID�������в�ѯ����
        float totalPrice = 0;
        ArrayList<OrderDishesInfo> list = helper
                .preparedQueryForList(
                        "select * from orderdishes where orderReference=?",
                        new Object[]{ orderId }, OrderDishesInfo.class);
        // ���ظ���ID��Ӧ���û���Ϣ
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
     * ��ҳ��ȡ�û����ݵķ���
     *
     * @param page
     *            Ҫ��ȡ���ݵ�ҳ��
     * @param pageSize
     *            ÿҳ��ʾ����Ŀ��
     * @return ��ǰҳ���û������б�
     * */
    public ArrayList<KitchenInfo> getByPage(int page, int pageSize) {
        // ��ȡ�������ӳص����ݿ�ģ��������߶���
        JDBCTemplateWithDS helper = JDBCTemplateWithDS.getJDBCHelper();
        // ͨ����ѯ����ȡ��Ӧҳ������
        ArrayList<KitchenInfo> list = helper
                .preparedForPageList(
                        "select tableId,num,dishesName,dishesState,odId from orderinfo,orderdishes,dishesinfo where orderinfo.orderId=orderdishes.orderReference and dishesinfo.dishesId=orderdishes.dishes and (dishesState=0 or dishesState=1)  order by odId",
                        new Object[] {}, page, pageSize, KitchenInfo.class);
        // ���ؽ��
        return list;
    }

    /**
     * ��ȡ�û���Ϣ�����ҳ��
     *
     * @param pageSize
     *            ÿҳ��ʾ����Ŀ��
     * @return ��ǰ���ݿ������ݵ����ҳ��
     * */
    public int getMaxPage(int pageSize) {
        // ��ȡ�������ӳص����ݿ�ģ��������߶���
        JDBCTemplateWithDS helper = JDBCTemplateWithDS.getJDBCHelper();
        // ��ȡ���ҳ����Ϣ
        Long rows = (Long) helper.preparedQueryForObject(
                "select count(*) from orderinfo,orderdishes,dishesinfo where orderinfo.orderId=orderdishes.orderReference and dishesinfo.dishesId=orderdishes.dishes and dishesState=0 or dishesState=1", new Object[] {});
        System.out.println(rows.longValue());
        // �������ҳ��
        return (int) ((rows.longValue() - 1) / pageSize + 1);
    }

    /**
     * �޸��û�������Ϣ�ķ���
     *
     * @param
     *
     * */
    public void modify(int dishesState,int odId) {
        // ��ȡ�������ӳص����ݿ�ģ��������߶���
        JDBCTemplateWithDS helper = JDBCTemplateWithDS.getJDBCHelper();
        // �������ܹ���
        Passport passport = new Passport();
        // �޸ı�����Ϣ
        helper.executePreparedUpdate(
                "update orderdishes set dishesState=? where odId=?",
                new Object[] { new Integer(dishesState),odId}
        );

    }

    public ArrayList<OrderInfo> getCheckoutInformation(){
        // ��ȡ�������ӳص����ݿ�ģ��������߶���
        JDBCTemplateWithDS helper = JDBCTemplateWithDS.getJDBCHelper();
        // ͨ����ѯ����ȡ��Ӧҳ������
        ArrayList<OrderInfo> list = helper.preparedQueryForList(
                "SELECT * FROM orderinfo WHERE orderState=1",
                new Object[] {}, OrderInfo.class);
        // ���ؽ��
//        SELECT * FROM orderinfo WHERE orderBeginDate >='2015-10-12 16:49' AND orderEndDate <= '2016-10-19 16:49'
        return list;
    }

    public void DeleteOrder( int orderId ){

        // ��ȡ�������ӳص����ݿ�ģ��������߶���
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
        //��ȡ�������ӳص����ݿ�ģ��������߶���
        JDBCTemplateWithDS helper = JDBCTemplateWithDS.getJDBCHelper();
        helper.executePreparedUpdate(
                "update orderinfo set orderState=2 where orderId=?",
                new Object[] { orderId }
        );
    }

    public ArrayList<OrderDetailInfo> getDishesDetail(int orderId){
        // ��ȡ�������ӳص����ݿ�ģ��������߶���
        JDBCTemplateWithDS helper = JDBCTemplateWithDS.getJDBCHelper();
        // ͨ����ѯ����ȡ��Ӧҳ������
        ArrayList<OrderDetailInfo> list = helper.preparedQueryForList(
                "select dishesName,dishesPrice,num from dishesinfo,orderdishes where dishesinfo.dishesId=orderdishes.dishes and orderdishes.orderReference=?",
                new Object[] { orderId }, OrderDetailInfo.class);
        // ���ؽ��
//        SELECT * FROM orderinfo WHERE orderBeginDate >='2015-10-12 16:49' AND orderEndDate <= '2016-10-19 16:49'
        return list;
    }


}
