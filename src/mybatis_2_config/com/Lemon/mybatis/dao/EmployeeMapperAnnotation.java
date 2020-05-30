package mybatis_2_config.com.Lemon.mybatis.dao;

import mybatis_2_config.com.Lemon.mybatis.bean.Employee;
import org.apache.ibatis.annotations.Select;

public interface EmployeeMapperAnnotation {

    @Select("select * from tbl_employee where id = #{id} ")
    public Employee getEmpById(Integer id);

}
