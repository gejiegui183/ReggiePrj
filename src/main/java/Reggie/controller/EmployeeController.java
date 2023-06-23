package Reggie.controller;

import Reggie.common.R;
import Reggie.entity.Employee;
import Reggie.service.EmployeeService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    /*
    1、判断用户名
    2、判断密码
    3、判断用户是否被禁用
     */
    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request , @RequestBody Employee employee){
        //1、加密处理
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());

        //2、查用户名
        //将网页中的数据进行封装
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        //数据库查询，通过等值查询判断是否有此人
        queryWrapper.eq(Employee::getUsername , employee.getUsername());//等值查询
        //接收数据库查询的返回值
        Employee emp = employeeService.getOne(queryWrapper);
        if (emp == null) {
            return R.error("登录失败");
        }

        //3、密码比对
        if(!emp.getPassword().equals(password)){
            return R.error("密码错误");
        }

        //4、账号状态判断
        if (emp.getStatus() == 0) {
            return R.error("该账号已禁用");
        }

        //5、比对均通过后放行
        request.getSession().setAttribute("employee" , emp.getId());
        return R.success(emp);
    }

    //用户退出
    @PostMapping("/logout")//网页退出的请求方式为post
    private R<String> logout(HttpServletRequest request){
        //清除session数据
        request.getSession().removeAttribute("employee");
        return R.success("退出成功");
        //退出时需要返回界面同时清除浏览器会话中的数据
    }

    //新增用户
    @PostMapping
    public R<String> save(HttpServletRequest request , @RequestBody Employee employee){
        //密码初始化
        employee.setPassword(DigestUtils.md5DigestAsHex("123465".getBytes()));
        //添加用户创建时间
        employee.setCreateTime(LocalDateTime.now());
        employee.setUpdateTime(LocalDateTime.now());

        //获取网页发送request中的内容
        Long empID = (Long) request.getSession().getAttribute("employee");

        employee.setCreateUser(empID);
        employee.setUpdateUser(empID);

        employeeService.save(employee);
        return R.success("添加成功");
    }


    //用户信息查询
    @GetMapping("/page")
    public R<Page> page(int page , int pageSize , String name){

        //分页构造器
        Page pageInfo = new Page();

        //条件过滤器
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper();

        //添加过滤条件
        queryWrapper.like(StringUtils.isNotEmpty(name) , Employee::getName , name);

        //查询过滤条件
        employeeService.page(pageInfo , queryWrapper);

        return R.success(pageInfo);
    }

    //用户禁用设置
    @PutMapping
    public R<String> update(HttpServletRequest request , @RequestBody Employee employee){

        //获取当前网页session
        Long empID = (Long) request.getSession().getAttribute("employee");

        //修改时间
        employee.setUpdateTime(LocalDateTime.now());

        //修改人
        employee.setUpdateUser(empID);

        //修改后的状态写入数据库
        employeeService.updateById(employee);
        return R.success("修改成功");
    }

    //修改员工信息,获取修改界面的id
    @GetMapping("/{id}")
    public R<Employee> getID(@PathVariable Long id){
        //新建一个employee对象，并针对该对象进行内容修改，写回数据库
        Employee employee = employeeService.getById(id);
        //修改完毕后返回该对象
        return R.success(employee);
    }

}
