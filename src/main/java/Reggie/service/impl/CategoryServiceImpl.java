package Reggie.service.impl;


import Reggie.common.CustomException;
import Reggie.entity.Category;
import Reggie.entity.Dish;
import Reggie.entity.Setmeal;
import Reggie.mapper.CategoryMapper;
import Reggie.service.CategoryService;
import Reggie.service.DishService;
import Reggie.service.SetMealService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper , Category> implements CategoryService {

    @Autowired
    private DishService dishService;

    @Autowired
    private SetMealService setMealService;

    @Override
    public void remove(Long id) {
        //设置查询的目标表
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();

        //设置查询条件
        queryWrapper.eq(Dish::getCategoryId , id);

        //判断菜品是否关联
        int count = dishService.count(queryWrapper);

        if (count != 0) {
            throw new CustomException("该项已关联菜品，无法删除");
        }



        //查询套餐
        LambdaQueryWrapper<Setmeal> queryWrapper1 = new LambdaQueryWrapper<>();
        queryWrapper1.eq(Setmeal::getCategoryId , id);
        int count1 = setMealService.count(queryWrapper1);
        if (count1 != 0) {
            throw new CustomException("该项已关联套餐，无法删除");
        }

        super.removeById(id);

    }
}
