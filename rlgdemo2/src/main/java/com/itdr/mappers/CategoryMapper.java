package com.itdr.mappers;

import com.itdr.pojo.Category;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CategoryMapper {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(Category record);

    Category selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Category record);

    //修改品类名字
    int updateByPrimaryKey(@Param("categoryId") Integer categoryId,@Param("categoryName") String categoryName);

    //获取品类子节点
    List<Category> selectCategoryById(Integer categoryId);

    //增加节点
    int insert(@Param("parentId")Integer parentId, @Param("categoryName")String categoryName);

}