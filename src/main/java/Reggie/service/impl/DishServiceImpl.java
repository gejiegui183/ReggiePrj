package Reggie.service.impl;

import Reggie.entity.Dish;
import Reggie.mapper.DishMapper;
import Reggie.service.DishService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class DishServiceImpl extends ServiceImpl<DishMapper , Dish> implements DishService {
}
