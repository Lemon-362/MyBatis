package mybatis_6_ssm.com.Lemon.mybatis.dao;

import mybatis_6_ssm.com.Lemon.mybatis.bean.Employee;

import java.util.List;

public interface EmployeeMapper {

    public Employee getEmpById(Integer id);

    public List<Employee> getEmps();
}
