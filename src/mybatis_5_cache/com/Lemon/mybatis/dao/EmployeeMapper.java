package mybatis_5_cache.com.Lemon.mybatis.dao;

import mybatis_5_cache.com.Lemon.mybatis.bean.Employee;

public interface EmployeeMapper {

    public Employee getEmpById(Integer id);

    public Long addEmp(Employee employee);

}
