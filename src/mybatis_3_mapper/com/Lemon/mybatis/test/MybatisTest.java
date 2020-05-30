package mybatis_3_mapper.com.Lemon.mybatis.test;

import mybatis_3_mapper.com.Lemon.mybatis.bean.Department;
import mybatis_3_mapper.com.Lemon.mybatis.bean.Employee;
import mybatis_3_mapper.com.Lemon.mybatis.dao.DepartmentMapper;
import mybatis_3_mapper.com.Lemon.mybatis.dao.EmployeeMapper;
import mybatis_3_mapper.com.Lemon.mybatis.dao.EmployeeMapperAnnotation;
import mybatis_3_mapper.com.Lemon.mybatis.dao.EmployeeMapperPlus;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 1. 接口式编程
 * 原生：         Dao接口    ====>   DaoImpl实现类
 * MyBatis：     Mapper接口 ====>   xxxMapper.xml配置文件
 * <p>
 * 2. SqlSession代表和数据库的一次会话，用完必须关闭
 * <p>
 * 3. SqlSession和Connection一样都是非线程安全的，每次使用都应该获取新的对象，而不能在类中定义为成员变量共享使用
 * <p>
 * 4. mapper接口没有实现类，但是MyBatis会为接口生成一个Proxy代理对象
 * （将接口和xml进行绑定）
 * EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class)
 * <p>
 * 5. 两个重要的配置文件：
 * （1）MyBatis全局配置文件（mybatis-config.xml）：
 * 包含数据库连接池信息，事务管理器信息。。。系统运行环境信息
 * （2）sql映射文件（EmployeeMapper.xml）：
 * 保存了每一个sql语句的映射信息：id唯一标识，resultType返回值类型
 * ——最终目的：MyBatis可以将sql语句抽取出来
 */
public class MybatisTest {

    /**
     * 一、通过MyBatis获取数据库mybatis中的表tbl_employee信息
     *
     * @throws IOException 步骤：
     *                     1. 设置xml配置文件（全局配置文件mybatis-config.xml），该配置文件存储数据源运行环境的4个基本信息
     *                     2. 设置sql映射文件EmployeeMapper.xml，配置了每一个sql语句和sql的封装规则
     *                     3. 将sql映射文件（EmployeeMapper.xml）一定要注册到全局配置文件（mybatis-config.xml）中
     *                     4. 写主函数代码：
     *                     1）根据xml配置文件（全局配置文件mybatis-config.xml），创建一个SqlSessionFactory对象
     *                     2）使用sqlSession工厂，获取SqlSession实例，能直接执行已经映射的sql语句，进行CRUD操作
     *                     TODO 一个sqlSession就代表和数据库的一次会话，用完要关闭
     *                     3）使用sql的唯一标识id，告诉MyBatis执行哪个sql（sql语句保存在sql映射文件EmployeeMapper.xml中）
     */


    // 公共方法：创建sqlSessionFactory对象
    public SqlSessionFactory getSqlSessionFactory() throws IOException {
        String resource = "mybatis_3_mapper/conf/mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        return sqlSessionFactory;
    }

    /**
     * TODO 推荐！！！
     * 二、接口式编程：将查询并封装的操作放到接口中，通过代理创建对象
     *
     * @throws IOException
     */
    @Test
    public void test2() throws IOException {
        // 1. 获取sqlSessionFactory对象
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();

        // 2. 获取SqlSession实例
        SqlSession openSession = sqlSessionFactory.openSession();

        try {
            // 3. 获取接口的实现类对象
            // TODO 会为接口自动创建一个Proxy代理对象，通过代理对象去执行CRUD方法
            EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);

            // 从这里可以看出mapper是一个代理类
            System.out.println(mapper.getClass());

            Employee employee = mapper.getEmpById(1);

            System.out.println(employee);
        } finally {
            openSession.close();
        }
    }

    /**
     * 三、<mapper class=""/>
     * 基于注解的sql注册
     * @throws IOException
     */
    @Test
    public void test3() throws IOException {
        // 1. 获取sqlSessionFactory对象
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();

        // 2. 获取SqlSession实例
        SqlSession openSession = sqlSessionFactory.openSession();

        try {
            // 3. 获取接口的实现类对象
            // TODO 会为接口自动创建一个Proxy代理对象，通过代理对象去执行CRUD方法
            EmployeeMapperAnnotation mapper = openSession.getMapper(EmployeeMapperAnnotation.class);

            // 从这里可以看出mapper是一个代理类
            System.out.println(mapper.getClass());

            Employee employee = mapper.getEmpById(1);

            System.out.println(employee);
        } finally {
            openSession.close();
        }
    }

    /**
     * TODO 3.3 测试CRUD：一定要把所有的地址都改一下
     *  1. MyBatis允许增删改直接定义以下类型的返回值：Integer，Long，Boolean
     *  2. sqlSessionFactory.openSession()：需要手动提交数据
     *     sqlSessionFactory.openSession(true)：自动提交
     */
    @Test
    public void test4() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();

        // TODO 获取到的SqlSession默认不会自动提交数据
        SqlSession openSession = sqlSessionFactory.openSession();

        try{
            EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);

            // 添加
            Employee employee = new Employee(null, "jerry", "jerry@Lemon.com", "1");
            Long addEmp = mapper.addEmp(employee);
            System.out.println(addEmp);
            // 3.4 获取自增主键的值
            System.out.println(employee.getId());

            // 修改
//            Employee employee = new Employee(3, "jerry", "jerry@Lemon.com", "0");
//            boolean b = mapper.updateEmp(employee);
//            System.out.println(b);

            // 删除
//            Long deleteEmp = mapper.deleteEmp(3);
//            System.out.println(deleteEmp);

            // TODO 手动提交数据
            openSession.commit();
        }finally {
            openSession.close();
        }
    }

    /**
     * TODO 3.5 测试条件表达式为多个参数的查询
     * @throws IOException
     */
    @Test
    public void test5() throws IOException {
        // 1. 获取sqlSessionFactory对象
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();

        // 2. 获取SqlSession实例
        SqlSession openSession = sqlSessionFactory.openSession();

        try {
            // 3. 获取接口的实现类对象
            // TODO 会为接口自动创建一个Proxy代理对象，通过代理对象去执行CRUD方法
            EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);

            // 3.5.1 多个参数：命名参数
//            Employee employee = mapper.getEmpByIdAndLastName(1, "tom");

            // 3.5.2 Map
//            Map<String, Object> map = new HashMap<>();
//            map.put("id", 1);
//            map.put("lastName", "Tom");
            // 3.6 使用 ${} 对 表名 进行拼接
//            map.put("tableName", "tbl_employee");
//            Employee employee = mapper.getEmpByMap(map);

//            System.out.println(employee);

            // 3.7.1 模糊查询（名字含有e字母的），返回集合List类型
//            List<Employee> like = mapper.getEmpsByLastNameLike("%e%");
//            for (Employee employee : like) {
//                System.out.println(employee);
//            }

            // 3.7.2 返回Map类型
//            Map<String, Object> map = mapper.getEmpByIdReturnMap(1);
//            System.out.println(map);

            // 3.7.3 多条记录封装成一个map
            Map<Integer, Employee> map = mapper.getEmpByLastNameLikeReturnMap("%r%");
            System.out.println(map);

        } finally {
            openSession.close();
        }
    }

    /**
     * TODO 3.8 ResultMap：自定义结果映射规则
     * @throws IOException
     */
    @Test
    public void test6() throws IOException {

        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();

        SqlSession openSession = sqlSessionFactory.openSession();

        try {
            EmployeeMapperPlus mapper = openSession.getMapper(EmployeeMapperPlus.class);

            // 3.8.1 自定义结果集映射规则
            // TODO 如果使用resultType，需要开启mapUnderscoreToCamelCase驼峰命名规则，否则last_name不识别
            //  而现在使用resultMap自定义规则，则可以关闭mapUnderscoreToCamelCase，依然可以获取到last_name
//            Employee employee = mapper.getEmpById(1);
//            System.out.println(employee);

            // 3.9.1 场景一
//            Employee empAndDept = mapper.getEmpAndDept(1);
//            System.out.println(empAndDept);
//            System.out.println(empAndDept.getDept());

            // 3.9.2 association分步查询
            Employee employee = mapper.getEmpByIdStep(1);
//            System.out.println(employee);
//            System.out.println(employee.getDept());

            /**
             * 3.9.3 association延迟加载
             * 如果     <setting name="lazyLoadingEnabled" value="true"/>
             *         <setting name="aggressiveLazyLoading" value="false"/>不配置的话，
             *         系统会发送两条sql语句，查询employee和查询dept
             * 而配置后，只会发送查询employee的sql语句，只会在要用到的时候才发送查询dept的sql语句
             * 并且是先打印 tom，然后再发送dept的sql语句，查询到dept信息
              */
            System.out.println(employee.getLastName());
            System.out.println(employee.getDept());

        }finally {
            openSession.close();
        }
    }

    @Test
    public void test7() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession openSession = sqlSessionFactory.openSession();
        try {
            DepartmentMapper mapper = openSession.getMapper(DepartmentMapper.class);
            // 3.9.4 场景二
//            Department department = mapper.getDeptByIdPlus(1);
//            System.out.println(department);
//            System.out.println(department.getEmps());

            // 3.9.5 collection分步查询和延迟加载
            Department deptByIdStep = mapper.getDeptByIdStep(1);
            System.out.println(deptByIdStep.getDepartmentName());
            System.out.println(deptByIdStep.getEmps());

        }finally {
            openSession.close();
        }
    }

    @Test
    public void test8() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();

        SqlSession openSession = sqlSessionFactory.openSession();

        try {
            EmployeeMapperPlus mapper = openSession.getMapper(EmployeeMapperPlus.class);

            // 3.9.6 discriminator鉴别器
            Employee employee = mapper.getEmpByIdStep(4);
            System.out.println(employee);
            System.out.println(employee.getDept());

        }finally {
            openSession.close();
        }
    }

}