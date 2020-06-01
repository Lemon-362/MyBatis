package mybatis_3_mapper.com.Lemon.mybatis.dao;

import mybatis_3_mapper.com.Lemon.mybatis.bean.Employee;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface EmployeeMapper {

    public Employee getEmpById(Integer id);

    /**
     * TODO 3.1 CRUD
     * @param employee
     */
    public Long addEmp(Employee employee);

    public boolean updateEmp(Employee employee);

    public Long deleteEmp(Integer id);

    /**
     * TODO 3.5 参数处理
     *  使用命名参数来明确指定封装参数时map的key名称
     */
    // 3.5.1 多个参数：命名参数
    public Employee getEmpByIdAndLastName(@Param("id") Integer id, @Param("lastName") String lastName);

    // 3.5.2 POJO Map
    public Employee getEmpByMap(Map<String, Object> map);

    /**
     * TODO 3.7 select
     */
    // 3.7.1 select模糊查询，返回集合List类型
    public List<Employee> getEmpsByLastNameLike(String lastName);

    // 3.7.2 select，返回一条记录的map，key：列名，value：列对应的值
    public Map<String, Object> getEmpByIdReturnMap(Integer id);

    // 3.7.3 多条记录封装成一个map：Map<Integer, Employee>，key是记录的主键，value是记录封装后的javaBean对象
    // TODO @MapKey("id")：告诉mybatis封装这个map时，使用哪个属性作为map的key，也可以用lastName作为key
    @MapKey("lastName")
    public Map<Integer, Employee> getEmpByLastNameLikeReturnMap(String lastName);
}
