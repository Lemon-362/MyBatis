package mybatis_4_DynamicSQL.com.Lemon.mybatis.bean;

import mybatis_4_DynamicSQL.com.Lemon.mybatis.bean.Employee;

import java.util.List;

public class Department {

    private Integer id;

    private String departmentName;

    // 3.9.4
    private List<Employee> emps;

    @Override
    public String toString() {
        return "Department{" +
                "id=" + id +
                ", departmentName='" + departmentName + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public List<Employee> getEmps() {
        return emps;
    }

    public void setEmps(List<Employee> emps) {
        this.emps = emps;
    }
}
