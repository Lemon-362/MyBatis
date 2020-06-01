package mybatis_6_ssm.com.Lemon.mybatis.bean;

import java.io.Serializable;
import java.util.List;

public class Department implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private String departmentName;

    public Department() {
    }

    public Department(Integer id) {
        this.id = id;
    }

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
