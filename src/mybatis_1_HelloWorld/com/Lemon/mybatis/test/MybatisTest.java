package mybatis_1_HelloWorld.com.Lemon.mybatis.test;

import mybatis_1_HelloWorld.com.Lemon.mybatis.bean.Employee;
import mybatis_1_HelloWorld.com.Lemon.mybatis.dao.EmployeeMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

/**
 * 1. 接口式编程
 *  原生：         Dao接口    ====>   DaoImpl实现类
 *  MyBatis：     Mapper接口 ====>   xxxMapper.xml配置文件
 *
 * 2. SqlSession代表和数据库的一次会话，用完必须关闭
 *
 * 3. SqlSession和Connection一样都是非线程安全的，每次使用都应该获取新的对象，而不能在类中定义为成员变量共享使用
 *
 * 4. mapper接口没有实现类，但是MyBatis会为接口生成一个Proxy代理对象
 *     （将接口和xml进行绑定）
 *     EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class)
 *
 * 5. 两个重要的配置文件：
 *  （1）MyBatis全局配置文件（mybatis-config.xml）：
 *        包含数据库连接池信息，事务管理器信息。。。系统运行环境信息
 *  （2）sql映射文件（EmployeeMapper.xml）：
 *        保存了每一个sql语句的映射信息：id唯一标识，resultType返回值类型
 * ——最终目的：MyBatis可以将sql语句抽取出来
 */
public class MybatisTest {

    /**
     * 一、通过MyBatis获取数据库mybatis中的表tbl_employee信息
     * @throws IOException
     * 步骤：
     *  1. 设置xml配置文件（全局配置文件mybatis-config.xml），该配置文件存储数据源运行环境的4个基本信息
     *  2. 设置sql映射文件EmployeeMapper.xml，配置了每一个sql语句和sql的封装规则
     *  3. 将sql映射文件（EmployeeMapper.xml）一定要注册到全局配置文件（mybatis-config.xml）中
     *  4. 写主函数代码：
     *      1）根据xml配置文件（全局配置文件mybatis-config.xml），创建一个SqlSessionFactory对象
     *      2）使用sqlSession工厂，获取SqlSession实例，能直接执行已经映射的sql语句，进行CRUD操作
     *         TODO 一个sqlSession就代表和数据库的一次会话，用完要关闭
     *      3）使用sql的唯一标识id，告诉MyBatis执行哪个sql（sql语句保存在sql映射文件EmployeeMapper.xml中）
     */
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
        String resource = "mybatis_1_HelloWorld/conf/mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        return sqlSessionFactory;
    }

    /** TODO 推荐！！！
     * 二、接口式编程：将查询并封装的操作放到接口中，通过代理创建对象
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

}