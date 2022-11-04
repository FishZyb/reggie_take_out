package com.itheima.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.reggie.entity.Orders;

/**
 * @author 一只鱼zzz
 * @version 1.0
 */
public interface OrdersService extends IService<Orders> {

  /**
   * 用户下单
   * @param orders
   */
  public void submit(Orders orders);
}
