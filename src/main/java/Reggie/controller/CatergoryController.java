package Reggie.controller;


import Reggie.common.R;
import Reggie.entity.Category;
import Reggie.service.CategoryService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/category")
@Slf4j
public class CatergoryController {

    @Autowired
    private CategoryService categoryService;

    //菜品、套餐添加
    @PostMapping
    public R<String> save(@RequestBody Category category){
        categoryService.save(category);
        return R.success("菜品添加成功");
    }

    //分页展示
    @GetMapping("/page")
    public R<Page> page(int page , int pageSize){
        Page<Category> pageInfo = new Page<>(page , pageSize);
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByAsc(Category::getSort);
        categoryService.page(pageInfo , queryWrapper);
        return R.success(pageInfo);
    }

    //菜品删除
    @DeleteMapping
    public R<String> delete(Long id){
        categoryService.remove(id);
        return R.success("菜品删除成功");
    }

    //菜品修改
    @PutMapping
    public R<String> update(@RequestBody Category category){
        categoryService.updateById(category);
        return R.success("菜品修改成功");
    }

    @GetMapping("/list")
    public R<List<Category>> lsit(Category category){
        //分类条件
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();


        //添加条件
        queryWrapper.eq(category.getType() != null , Category::getType , category.getType());

        //排序
        queryWrapper.orderByAsc(Category::getSort).orderByDesc(Category::getUpdateTime);

        List<Category> list = categoryService.list(queryWrapper);

        return R.success(list);
    }
}