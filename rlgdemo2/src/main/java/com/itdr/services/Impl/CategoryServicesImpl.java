package com.itdr.services.Impl;

import com.itdr.common.Const;
import com.itdr.common.ServerResponse;
import com.itdr.mappers.CategoryMapper;
import com.itdr.pojo.Category;
import com.itdr.services.CategoryServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ConcurrentModificationException;
import java.util.List;

@Service
public class CategoryServicesImpl implements CategoryServices {

    @Autowired
    private CategoryMapper categoryMapper;

    /*
     * 获取品类子节点(平级)
     * */
    @Override
    public ServerResponse getCategory(Integer categoryId) {
        //非空校验
        if (categoryId==null){
            return ServerResponse.errorRS(Const.USER_NOLL_MSG);
        }
        //查询子类别
        List<Category> categories=categoryMapper.selectCategoryById(categoryId);
        //返回结果
        return ServerResponse.sucessRS(categories);
    }

    /*
     * .增加节点
     * */
    @Override
    public ServerResponse addCategory(Integer parentId,String categoryName) {
        //参数校验
        if (parentId==null || parentId.equals("")){
            return ServerResponse.errorRS(Const.USER_NOLL_MSG);
        }
        if (categoryName==null || categoryName.equals("")){
            return ServerResponse.errorRS(Const.USER_NOLL_MSG);
        }
        //添加节点
        int result=categoryMapper.insert(parentId,categoryName);
        if (result>0){
            return ServerResponse.sucessRS("添加商品成功");
        }
        //返回结果
        return ServerResponse.errorRS("添加商品失败");
    }

    /*
     * .修改节点
     * */
    @Override
    public ServerResponse setCategory(Integer categoryId, String categoryName) {
        //参数校验
        if (categoryId==null || categoryId.equals("")){
            return ServerResponse.errorRS(Const.USER_NOLL_MSG);
        }
        if (categoryName==null || categoryName.equals("")){
            return ServerResponse.errorRS(Const.USER_NOLL_MSG);
        }
        //修改节点
        int result=categoryMapper.updateByPrimaryKey(categoryId,categoryName);
        if (result>0){
            return ServerResponse.sucessRS("修改成功");
        }
        //返回结果
        return ServerResponse.errorRS("修改失败");
    }

    /*
     * 获取当前分类id及递归子节点categoryId
     * */
    @Override
    public ServerResponse getDeepCategory(Integer categoryId) {
        return null;
    }

    /*
     * 根据id查询类别
     * */
    @Override
    public ServerResponse<Category> selectCategory(Integer categoryId) {
        return null;
    }
}
