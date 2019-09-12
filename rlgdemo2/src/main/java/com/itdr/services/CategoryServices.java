package com.itdr.services;

import com.itdr.common.ServerResponse;
import com.itdr.pojo.Category;

public interface CategoryServices {

    /*
     * 获取品类子节点(平级)
     * */
    public ServerResponse getCategory(Integer categoryId);

    /*
     * .增加节点
     * */
    public ServerResponse addCategory(Integer parentId,String categoryName);

    /*
     * .修改节点
     * */
    public ServerResponse setCategory(Integer categoryId, String categoryName);

    /*
     * 获取当前分类id及递归子节点categoryId
     * */
    public ServerResponse getDeepCategory(Integer categoryId);

    /*
     * 根据id查询类别
     * */
    public ServerResponse<Category> selectCategory(Integer categoryId);


}
