package mybatis_3_mapper.com.Lemon.mybatis.dao;

import mybatis_3_mapper.com.Lemon.mybatis.bean.Employee;
import org.apache.ibatis.annotations.Select;

public interface EmployeeMapperAnnotation {

    @Select("select * from tbl_employee where id = #{id} ")
    public Employee getEmpById(Integer id);

}