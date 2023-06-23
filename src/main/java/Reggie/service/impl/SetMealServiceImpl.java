package Reggie.service.impl;

import Reggie.entity.Setmeal;
import Reggie.mapper.SetMealMapper;
import Reggie.service.SetMealService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class SetMealServiceImpl extends ServiceImpl<SetMealMapper , Setmeal> implements SetMealService {
}
