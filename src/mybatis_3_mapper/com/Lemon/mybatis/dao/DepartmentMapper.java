package mybatis_3_mapper.com.Lemon.mybatis.dao;

import mybatis_3_mapper.com.Lemon.mybatis.bean.Department;


public interface DepartmentMapper {

    public Department getDeptById(Integer id);

    // 3.9.4 场景二
    public Department getDeptByIdPlus(Integer id);

    // 3.9.5 使用collection进行分步查询
    public Department getDeptByIdStep(Integer id);

}
