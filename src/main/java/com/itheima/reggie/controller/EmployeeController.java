package com.itheima.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.reggie.commom.R;
import com.itheima.reggie.entity.Employee;
import com.itheima.reggie.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 * @author 一只鱼zzz
 * @version 1.0
 */
@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {

  @Autowired
  private EmployeeService employeeService;

  /**
   *  员工登录
   * @param request 将数据保存到session域
   * @param employee 为了接受前端发来的JSON格式的数据
   * @return
   */
  @PostMapping("/login")
  public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee){
    //1、将页面提交的密码进行md5加密处理
    String password = employee.getPassword();
    password = DigestUtils.md5DigestAsHex(password.getBytes());
    //2、根据页面提交的用户名username查询数据库
    LambdaQueryWrapper<Employee> wrapper = new LambdaQueryWrapper<>();
    wrapper.eq(Employee::getUsername, employee.getUsername());
    Employee emp = employeeService.getOne(wrapper);
    //3、如果没有查询到，则返回登录失败的结果
    if(emp==null){
      return R.error("账号不存在，登录失败");
    }
    //4、密码比对，如果不一致则返回登录失败结果
    if(!emp.getPassword().equals(password)){
      return R.error("密码错误，登陆失败");
    }
    //5、查看员工状态，如果为已禁用状态，则返回员工已禁用
    if(emp.getStatus()==0){
      return R.error("账号已禁用，登陆失败");
    }
    //6、登录成功，将员工的id存入session，并返回登录成功结果
    request.getSession().setAttribute("employee",emp.getId());

    return R.success(emp);
  }

  /**
   * 员工退出
   * @param request
   * @return
   */
  @PostMapping("/logout")
  public R<String> logout(HttpServletRequest request){
    //1、清理session中保存的当前登录员工的id
    request.getSession().removeAttribute("employee");
    return R.success("退出成功");
  }

  /**
   * 新增员工
   * @param employee
   * @return
   */
  @PostMapping
  public R<String> save(HttpServletRequest request,@RequestBody Employee employee){
    log.info("新增员工，员工信息：{}",employee.toString());
    //设置初始密码为123456，需要进行md5加密处理
    employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));

    //声明创建时间和更新时间
   // employee.setCreateTime(LocalDateTime.now());
    //employee.setUpdateTime(LocalDateTime.now());
    //获得当前登录用户的id
    //Long empId = (Long) request.getSession().getAttribute("employee");
    //声明创建者和和更新者
    //employee.setCreateUser(empId);
    //employee.setUpdateUser(empId);
    employeeService.save(employee);

    return R.success("新增员工成功");
  }

  /**
   * 员工信息分页查询
   * @param page
   * @param pageSize
   * @param name
   * @return
   */
  @GetMapping("/page")
  public R<Page> page(int page,int pageSize,String name){
    //构造分页构造器
    Page pageInfo = new Page(page, pageSize);
    //构造条件构造器
    LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper();
    //添加一个过滤条件
    queryWrapper.like(StringUtils.isNotEmpty(name),Employee::getName,name);
    //添加排序条件
    queryWrapper.orderByDesc(Employee::getUpdateTime);
    //执行查询
    employeeService.page(pageInfo,queryWrapper);
    return R.success(pageInfo);
  }

  /**
   * 根据id修改员工信息
   * @param request
   * @param employee
   * @return
   */
  @PutMapping
  public R<String> update(HttpServletRequest request,@RequestBody Employee employee){
    log.info(employee.toString());

    //Long empId = (Long) request.getSession().getAttribute("employee");
    //employee.setUpdateTime(LocalDateTime.now());
    //employee.setUpdateUser(empId);
    employeeService.updateById(employee);

    return R.success("员工信息修改完成");
  }

  @GetMapping("/{id}")
  public R<Employee> getById(@PathVariable Long id){
    log.info("根据id查询员工信息");
    Employee employee = employeeService.getById(id);
    if(employee!=null){
      return R.success(employee);
    }
    return R.error("没有查询到对应员工信息");
  }

}














