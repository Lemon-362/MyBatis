package mybatis_4_DynamicSQL.com.Lemon.mybatis.test;

import mybatis_4_DynamicSQL.com.Lemon.mybatis.bean.Department;
import mybatis_4_DynamicSQL.com.Lemon.mybatis.bean.Employee;
import mybatis_4_DynamicSQL.com.Lemon.mybatis.dao.EmployeeMapperDynamicSQL;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MyBatisTest {

    public SqlSessionFactory getSqlSessionFactory() throws IOException {
        String resource = "mybatis_4_DynamicSQL/conf/mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        return sqlSessionFactory;
    }

    @Test
    public void testDynamicSQL() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession openSession = sqlSessionFactory.openSession();

        try {
            EmployeeMapperDynamicSQL mapper = openSession.getMapper(EmployeeMapperDynamicSQL.class);

            /**
             * （1）if：and必须放在查询条件之前
             */
            // gender为空：查询条件不带gender —— where id = ? and last_name like ? and email = ?
//            Employee employee = new Employee(4, "%e%", "jerry@Lemon.com", null);

            // email和gender为空：查询条件不带email和gender —— where id = ? and last_name like ?
//            Employee employee = new Employee(4, "%e%", null, null);

            /** TODO 当第一个where条件不满足时，会报错 where and last_name like ? —— 拼串问题
             * 解决方法：
             * 1. 在where后面先加一个绝对正确的条件，之后所有的条件都加上and
             * 2. TODO 推荐：MyBatis使用where标签，将所有的查询条件包含在内，但是只会去掉开头的第一个多出来的and或者or
             */

//            Employee employee = new Employee(null, "%e%", null, null);
//
//            List<Employee> emps = mapper.getEmpsByConditionIf(employee);
//            for (Employee emp : emps) {
//                System.out.println(emp);
//            }

            /**
             * （2）trim：
             * 如果没有suffixOverrides="and"，就会报错
             */
            // where last_name like ?
//            Employee employee = new Employee(null, "%e%", null, null);
//
//            List<Employee> emps = mapper.getEmpsByConditionTrim(employee);
//            for (Employee emp : emps) {
//                System.out.println(emp);
//            }

            /**
             * （3）choose-when-otherwise：
             */
            // where last_name like ?
//            Employee employee = new Employee(null, "%e%", null, null);

            // where id=?
//            Employee employee = new Employee(1, "%e%", null, null);

            // where gender=0
//            Employee employee = new Employee(null, null, null, null);
//
//            List<Employee> emps = mapper.getEmpsByConditionChoose(employee);
//            for (Employee emp : emps) {
//                System.out.println(emp);
//            }

            /**
             * 4.1.3 set
             */
            // SET last_name=? where id=?
            Employee employee = new Employee(1, "Admin", null, null);

            mapper.updateEmp(employee);
            openSession.commit();

        } finally {
            openSession.close();
        }
    }

    @Test
    public void testDynamicSQL1() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession openSession = sqlSessionFactory.openSession();

        try {
            EmployeeMapperDynamicSQL mapper = openSession.getMapper(EmployeeMapperDynamicSQL.class);

            /** 4.1.4
             * （4）foreach
             */
            // where id in ( ? , ? , ? , ? )
            List<Employee> list = mapper.getEmpsByConditionForeach(Arrays.asList(1, 2, 3, 4));
            for (Employee employee : list) {
                System.out.println(employee);
            }

        } finally {
            openSession.close();
        }
    }

    @Test
    public void testDynamicSQL2() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession openSession = sqlSessionFactory.openSession();

        try {
            EmployeeMapperDynamicSQL mapper = openSession.getMapper(EmployeeMapperDynamicSQL.class);

            /**
             * 4.1.5 foreach批量保存
             */
            // INSERT INTO mybatis.tbl_employee(last_name, email, gender, d_id) VALUES (?, ?, ?, ?) , (?, ?, ?, ?)
            List<Employee> emps = new ArrayList<>();
            emps.add(new Employee(null, "tom", "tom@126.com", "0", new Department(1)));
            emps.add(new Employee(null, "frank", "frank@126.com", "1", new Department(2)));

            mapper.addEmps(emps);

            openSession.commit();

        } finally {
            openSession.close();
        }
    }

    @Test
    public void testInnerParam() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession openSession = sqlSessionFactory.openSession();

        try {
            EmployeeMapperDynamicSQL mapper = openSession.getMapper(EmployeeMapperDynamicSQL.class);

            /**
             * 4.2 MyBatis的两个内置参数
             */
            Employee employee1 = new Employee();
//            employee1.setLastName("%e%");
            employee1.setLastName("e");

            List<Employee> list = mapper.getEmpsTestInnerParam(employee1);

            for (Employee employee : list) {
                System.out.println(employee);
            }

            openSession.commit();

        } finally {
            openSession.close();
        }
    }
}
