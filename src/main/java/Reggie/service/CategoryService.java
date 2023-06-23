package Reggie.service;

import Reggie.entity.Category;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

@Service
public interface CategoryService extends IService<Category> {

    public void remove(Long id);

}
