package com.chinasofti.ordersys.service.admin;

import com.chinasofti.ordersys.vo.DishesInfo;
import com.chinasofti.ordersys.vo.UserInfo;
import com.chinasofti.util.jdbc.template.JDBCTemplateWithDS;
import com.chinasofti.util.sec.Passport;

import java.util.ArrayList;

public class DishesService {

    /**
     * 分页获取菜品数据的方法
     *
     * @param page
     *            要获取数据的页号
     * @param pageSize
     *            每页显示的条目数
     * @return 当前页的用户数据列表
     * */
    public ArrayList<DishesInfo> getByPage(int page, int pageSize) {
        // 获取带有连接池的数据库模版操作工具对象
        JDBCTemplateWithDS helper = JDBCTemplateWithDS.getJDBCHelper();
        // 通过查询语句获取对应页的数据
        ArrayList<DishesInfo> list = helper.preparedForPageList("select dishesId,dishesName,dishesDiscript,dishesImg,dishesTxt,recommend,dishesPrice from dishesinfo", new Object[] {}, page, pageSize, DishesInfo.class);
        // 返回结果
        return list;
    }

    /**
     * 获取菜品信息的最大页数
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
                "select count(*) from dishesinfo", new Object[] {});
        // 返回最大页数
        return (int) ((rows.longValue() - 1) / pageSize + 1);
    }

    public DishesInfo getDishesById(Integer dishesId) {
        // 获取带有连接池的数据库模版操作工具对象
        JDBCTemplateWithDS helper = JDBCTemplateWithDS.getJDBCHelper();
        // 按照用户ID条件进行查询操作
        ArrayList<DishesInfo> list = helper
                .preparedQueryForList(
                        "select dishesId,dishesName,dishesDiscript,dishesImg,dishesTxt,recommend,dishesPrice from dishesinfo where dishesId=?",
                        new Object[] { dishesId }, DishesInfo.class);
        // 返回给定ID对应的用户信息
        return list.get(0);
    }

    public void deleteDishes(Integer dishesId) {
        // 获取带有连接池的数据库模版操作工具对象
        JDBCTemplateWithDS helper = JDBCTemplateWithDS.getJDBCHelper();
        // 删除给定ID的用户信息
        helper.executePreparedUpdate("delete from orderdishes where dishes=?",
                new Object[] { dishesId });
        helper.executePreparedUpdate("delete from dishesinfo where dishesId=?",
                new Object[] { dishesId });
    }

    public void addDishes(DishesInfo info) {
        // 获取带有连接池的数据库模版操作工具对象
        JDBCTemplateWithDS helper = JDBCTemplateWithDS.getJDBCHelper();
        // 创建加密工具
//        Passport passport = new Passport();
        // 执行用户信息插入操作
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
        // 获取带有连接池的数据库模版操作工具对象
        JDBCTemplateWithDS helper = JDBCTemplateWithDS.getJDBCHelper();
        // 创建加密工具
//        Passport passport = new Passport();
        // 修改本人信息
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
        // 返回结果
        return list;
    }

}
