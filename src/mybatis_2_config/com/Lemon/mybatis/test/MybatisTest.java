package mybatis_2_config.com.Lemon.mybatis.test;

import mybatis_2_config.com.Lemon.mybatis.bean.Employee;
import mybatis_2_config.com.Lemon.mybatis.dao.EmployeeMapper;
import mybatis_2_config.com.Lemon.mybatis.dao.EmployeeMapperAnnotation;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

public class MybatisTest {


    @Test
    public void test1() throws IOException {
        // 1. 根据xml配置文件（全局配置文件mybatis-config.xml），创建一个SqlSessionFactory对象
        // 该配置文件存储数据源运行环境的4个基本信息
        String resource = "mybatis_1_HelloWorld/conf/mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        // 2. 获取SqlSession实例，能直接执行已经映射的sql语句
        SqlSession openSession = sqlSessionFactory.openSession();

        /** openSession.selectOne()
         * 1. statement：sql的唯一标识 —— namespace.id的方式
         * 2. parameter：执行sql要用的参数
         */
        try {
            Employee employee = openSession.selectOne
                    ("com.Lemon.mybatis.bean.EmployeeMapper.selectBlog", 1);

            System.out.println(employee);
        } finally {
            openSession.close();
        }
    }

    // 公共方法：创建sqlSessionFactory对象
    public SqlSessionFactory getSqlSessionFactory() throws IOException {
        String resource = "mybatis_2_config/conf/mybatis-config.xml";
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
     *
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

}