package mybatis_6_ssm.com.Lemon.mybatis.service;

import mybatis_6_ssm.com.Lemon.mybatis.bean.Employee;
import mybatis_6_ssm.com.Lemon.mybatis.dao.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    public List<Employee> getEmps(){
        return employeeMapper.getEmps();
    }

}
