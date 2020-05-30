package mybatis_3_mapper.com.Lemon.mybatis.dao;

import mybatis_3_mapper.com.Lemon.mybatis.bean.Employee;

import java.util.List;

/**
 * TODO 3.8 ResultMap：自定义结果映射规则
 */
public interface EmployeeMapperPlus {

    // 3.8.1 自定义结果集映射规则
    public Employee getEmpById(Integer id);

    // 3.9.1 场景一
    public Employee getEmpAndDept(Integer id);

    // 3.9.2 association分步查询
    public Employee getEmpByIdStep(Integer id);

    // 3.9.5 collection分步查询
    public List<Employee> getEmpsByDeptId(Integer deptId);
}
