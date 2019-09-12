package com.itdr.controllers.backend;

import com.itdr.common.Const;
import com.itdr.common.RoleEnum;
import com.itdr.common.ServerResponse;
import com.itdr.pojo.Category;
import com.itdr.pojo.User;
import com.itdr.services.CategoryServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/manage/category")
public class CategoryController {

    @Autowired
    private CategoryServices categoryServices;
    /*
     * 获取品类子节点(平级)
     * */
    @RequestMapping("/get_category.do")
    public ServerResponse getCategory(Integer categoryId, HttpSession session){
        User user = (User) session.getAttribute(Const.USER_ONE);
        //判断用户是否登录
        if(user == null){
            return ServerResponse .errorRS(Const.USER_USERNOLOGIN_MSG);
        }
//        //判断用户是否有权限
//        if (user.getRole() != RoleEnum.ROLE_ADMIN.getRole()){
//            return ServerResponse.errorRS(Const.USER_ERRORS_CODE,Const.USER_NULL_MSG);
//        }
        return categoryServices.getCategory(categoryId);
    }

    /*
     * 增加节点
     * */
    @RequestMapping("/add_category.do")
    public ServerResponse addCategory(Integer parentId, String categoryName, HttpSession session){
        User user = (User) session.getAttribute(Const.USER_ONE);
        //判断用户是否登录
        if(user == null){
            return ServerResponse .errorRS(Const.USER_USERNOLOGIN_MSG);
        }
//        //判断用户是否有权限
//        if (user.getRole() != RoleEnum.ROLE_ADMIN.getRole()){
//            return ServerResponse.errorRS(Const.USER_ERRORS_CODE,Const.USER_NULL_MSG);
//        }
        return categoryServices.addCategory(parentId,categoryName);
    }

    /*
     * .修改节点
     * */
    @RequestMapping("/set_category_name.do")
    public ServerResponse setCategory(Integer categoryId, String categoryName, HttpSession session){
        User user = (User) session.getAttribute(Const.USER_ONE);
        //判断用户是否登录
        if(user == null){
            return ServerResponse .errorRS(Const.USER_USERNOLOGIN_MSG);
        }
//        //判断用户是否有权限
//        if (user.getRole() != RoleEnum.ROLE_ADMIN.getRole()){
//            return ServerResponse.errorRS(Const.USER_ERRORS_CODE,Const.USER_NULL_MSG);
//        }
        return categoryServices.setCategory(categoryId,categoryName);
    }

    /*
     * 获取当前分类id及递归子节点categoryId
     * */
    @RequestMapping("/get_deep_category")
    public ServerResponse getDeepCategory(Integer categoryId, HttpSession session){
        User user = (User) session.getAttribute(Const.USER_ONE);
        //判断用户是否登录
        if(user == null){
            return ServerResponse .errorRS(Const.USER_USERNOLOGIN_MSG);
        }
//        //判断用户是否有权限
//        if (user.getRole() != RoleEnum.ROLE_ADMIN.getRole()){
//            return ServerResponse.errorRS(Const.USER_ERRORS_CODE,Const.USER_NULL_MSG);
//        }
        return categoryServices.getDeepCategory(categoryId);
    }

}












