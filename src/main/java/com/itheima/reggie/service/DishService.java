package com.itheima.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.reggie.dto.DishDto;
import com.itheima.reggie.entity.Dish;

/**
 * @author 一只鱼zzz
 * @version 1.0
 */

public interface DishService extends IService<Dish> {

  /**
   * 新增菜品，同时插入菜品对应的口味数据，需要操作两张表：dish、dishFlavor
   * @param dishDto
   */
  public void saveWithFlavor(DishDto dishDto);

  /**
   * 根据id查询菜品信息和对应的口味信息
   * @param id
   * @return
   */
  public DishDto getByIdWithFlavor(Long id);

  /**
   * 更新菜品信息，同时更新对应的口味信息表
   * @param dishDto
   */
  public void updateWithFlavor(DishDto dishDto);

}
