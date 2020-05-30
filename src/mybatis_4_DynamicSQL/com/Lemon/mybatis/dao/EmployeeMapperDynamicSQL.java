package mybatis_4_DynamicSQL.com.Lemon.mybatis.dao;

import mybatis_4_DynamicSQL.com.Lemon.mybatis.bean.Employee;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EmployeeMapperDynamicSQL {

    // 4.1.1 查询员工，要求：查询哪个字段，那么查询条件就带上这个字段的值
    // if
    public List<Employee> getEmpsByConditionIf(Employee employee);

    // trim
    public List<Employee> getEmpsByConditionTrim(Employee employee);

    // 4.1.2 如果带了id就用id查，如果带了lastName就用lastName查，只会查询一个
    // choose
    public List<Employee> getEmpsByConditionChoose(Employee employee);

    // 4.1.3 根据id更新员工信息
    // set
    public void updateEmp(Employee employee);

    // 4.1.4 查询员工id在给定集合中的员工信息
    public List<Employee> getEmpsByConditionForeach(@Param("ids")List<Integer> ids);

    // 4.1.5 foreach批量保存
    public void addEmps(@Param("emps")List<Employee> emps);

    // 4.2 MyBatis的两个内置参数
    public List<Employee> getEmpsTestInnerParam(Employee employee);

}
