package com.chinasofti.ordersys.service.admin;

import com.chinasofti.ordersys.vo.DishesInfo;
import com.chinasofti.ordersys.vo.UserInfo;
import com.chinasofti.util.jdbc.template.JDBCTemplateWithDS;
import com.chinasofti.util.sec.Passport;

import java.util.ArrayList;

public class DishesService {

    /**
     * ��ҳ��ȡ��Ʒ���ݵķ���
     *
     * @param page
     *            Ҫ��ȡ���ݵ�ҳ��
     * @param pageSize
     *            ÿҳ��ʾ����Ŀ��
     * @return ��ǰҳ���û������б�
     * */
    public ArrayList<DishesInfo> getByPage(int page, int pageSize) {
        // ��ȡ�������ӳص����ݿ�ģ��������߶���
        JDBCTemplateWithDS helper = JDBCTemplateWithDS.getJDBCHelper();
        // ͨ����ѯ����ȡ��Ӧҳ������
        ArrayList<DishesInfo> list = helper.preparedForPageList("select dishesId,dishesName,dishesDiscript,dishesImg,dishesTxt,recommend,dishesPrice from dishesinfo", new Object[] {}, page, pageSize, DishesInfo.class);
        // ���ؽ��
        return list;
    }

    /**
     * ��ȡ��Ʒ��Ϣ�����ҳ��
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
                "select count(*) from dishesinfo", new Object[] {});
        // �������ҳ��
        return (int) ((rows.longValue() - 1) / pageSize + 1);
    }

    public DishesInfo getDishesById(Integer dishesId) {
        // ��ȡ�������ӳص����ݿ�ģ��������߶���
        JDBCTemplateWithDS helper = JDBCTemplateWithDS.getJDBCHelper();
        // �����û�ID�������в�ѯ����
        ArrayList<DishesInfo> list = helper
                .preparedQueryForList(
                        "select dishesId,dishesName,dishesDiscript,dishesImg,dishesTxt,recommend,dishesPrice from dishesinfo where dishesId=?",
                        new Object[] { dishesId }, DishesInfo.class);
        // ���ظ���ID��Ӧ���û���Ϣ
        return list.get(0);
    }

    public void deleteDishes(Integer dishesId) {
        // ��ȡ�������ӳص����ݿ�ģ��������߶���
        JDBCTemplateWithDS helper = JDBCTemplateWithDS.getJDBCHelper();
        // ɾ������ID���û���Ϣ
        helper.executePreparedUpdate("delete from orderdishes where dishes=?",
                new Object[] { dishesId });
        helper.executePreparedUpdate("delete from dishesinfo where dishesId=?",
                new Object[] { dishesId });
    }

    public void addDishes(DishesInfo info) {
        // ��ȡ�������ӳص����ݿ�ģ��������߶���
        JDBCTemplateWithDS helper = JDBCTemplateWithDS.getJDBCHelper();
        // �������ܹ���
//        Passport passport = new Passport();
        // ִ���û���Ϣ�������
        helper.executePreparedUpdate(
                "insert into dishesinfo(dishesName,dishesDiscript,dishesImg,dishesTxt,recommend,dishesPrice) values(?,?,?,?,?,?)",
                new Object[] { info.getDishesName(),
                        info.getDishesDiscript(),
                        info.getDishesImg(),
                        info.getDishesTxt(),
                        new Integer(info.getRecommend()),
                        new Float(info.getDishesPrice()),
                }
        );
    }

    public void dishesModify(DishesInfo info) {
        // ��ȡ�������ӳص����ݿ�ģ��������߶���
        JDBCTemplateWithDS helper = JDBCTemplateWithDS.getJDBCHelper();
        // �������ܹ���
//        Passport passport = new Passport();
        // �޸ı�����Ϣ
        helper.executePreparedUpdate(
                "update dishesinfo set dishesName=?,dishesDiscript=?,dishesImg=?,dishesTxt=?,recommend=?,dishesPrice=? where dishesId=?",
                new Object[] { info.getDishesName(),
                        info.getDishesDiscript(),
                        info.getDishesImg(),
                        info.getDishesTxt(),
                        new Integer(info.getRecommend()),
                        new Float(info.getDishesPrice()),
                        new Integer(info.getDishesId()),
                }
        );

    }

    public ArrayList<DishesInfo> getRecommendDishes(){
        JDBCTemplateWithDS helper = JDBCTemplateWithDS.getJDBCHelper();
        ArrayList<DishesInfo> list = helper.preparedQueryForList("select dishesId,dishesName,dishesDiscript,dishesImg,dishesTxt,recommend,dishesPrice from dishesinfo where recommend=1", new Object[] {}, DishesInfo.class);
        // ���ؽ��
        return list;
    }

}
