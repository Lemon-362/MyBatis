package mybatis_5_cache.com.Lemon.mybatis.bean;

import org.apache.ibatis.type.Alias;

@Alias("emp")
public class Employee {

    private Integer id;

    private String lastName;

    private String email;

    private Department dept;

    public Employee() {
    }

    public Employee(Integer id, String lastName, String email,  String gender, Department dept) {
        this.id = id;
        this.lastName = lastName;
        this.email = email;
        this.dept = dept;
        this.gender = gender;
    }

    public Employee(Integer id, String lastName, String email, String gender) {
        this.id = id;
        this.lastName = lastName;
        this.email = email;
        this.gender = gender;
    }

    private String gender;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Department getDept() {
        return dept;
    }

    public void setDept(Department dept) {
        this.dept = dept;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", gender='" + gender + '\'' +
                '}';
    }
}
