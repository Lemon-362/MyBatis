package mybatis_2_config.com.Lemon.mybatis.dao;

import mybatis_2_config.com.Lemon.mybatis.bean.Employee;

/**
    二、定义一个接口，根据id值查到数据封装成Employee对象
        而sql映射文件本身就是为了查到数据封装成Employee对象
        所以MyBatis提供了一个接口和xml配置文件动态绑定的功能
 */
public interface EmployeeMapper {

    public Employee getEmpById(Integer id);

}
