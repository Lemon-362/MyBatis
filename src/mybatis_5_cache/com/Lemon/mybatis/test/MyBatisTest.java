package mybatis_5_cache.com.Lemon.mybatis.test;

import mybatis_5_cache.com.Lemon.mybatis.bean.Employee;
import mybatis_5_cache.com.Lemon.mybatis.dao.EmployeeMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;


import java.io.IOException;
import java.io.InputStream;

/**
 * TODO 5.1 两级缓存
 * 1. 一级缓存（本地缓存）：与数据库同一次会话期间，查询到的数据会放在本地缓存中
 *      以后如果需要获取相同的数据，直接从缓存中拿，没必要再去查询数据库
 * 2. 二级缓存（全局缓存）
 */
public class MyBatisTest {

    public SqlSessionFactory getSqlSessionFactory() throws IOException {
        String resource = "mybatis_5_cache/conf/mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        return sqlSessionFactory;
    }

    /**
     * TODO 5.1.1 一级缓存：sqlSession级别的缓存，一直开启的 —— 实际上就是sqlSession级别的一个Map
     *      会发现emp01和emp02两个对象是相同的，说明在查emp02的时候，时调用的本地缓存的数据
     *      并不会重新创建一个新的对象，且只会发送一个sql语句
     *
     * 一级缓存失效情况（发送多个sql语句）：
     *  1. sqlSession不同
     *  2. sqlSession相同，查询条件不同（当前一级缓存中没有这个数据）
     *  3. sqlSession相同，两次查询之间执行了增删改操作（这次增删改操作可能对当前数据有影响）——因为设置了flushCache="true"
     *  4. sqlSession相同，手动清除了一级缓存 openSession.clearCache();
     */
    @Test
    public void testFirstLevelCache() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession openSession = sqlSessionFactory.openSession();

        try {
            EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);

            Employee emp01 = mapper.getEmpById(1);
            System.out.println(emp01);

            // 一级缓存
//            Employee emp02 = mapper.getEmpById(1);

            // 一级缓存失效情况1：sqlSession不同
//            SqlSession openSession2 = sqlSessionFactory.openSession();
//            EmployeeMapper mapper2 = openSession2.getMapper(EmployeeMapper.class);
//            Employee emp02 = mapper2.getEmpById(1);

            // 一级缓存失效情况2：sqlSession相同，查询条件不同
//            Employee emp02 = mapper.getEmpById(3);

            // 一级缓存失效情况3：sqlSession相同，两次查询之间执行了增删改操作
//            mapper.addEmp(new Employee(null, "testCache", "cache", "1"));
//            System.out.println("数据添加成功");
//            Employee emp02 = mapper.getEmpById(1);

            // 一级缓存失效情况4：sqlSession相同，手动清除了一级缓存
            openSession.clearCache();
            Employee emp02 = mapper.getEmpById(1);

            System.out.println(emp02);
            System.out.println(emp01 == emp02);

//            openSession2.close();
        }finally {
            openSession.close();
        }
    }

    /**
     * TODO 5.1.2 二级缓存：基于NameSpace级别的缓存 —— 一个NameSpace就对应一个二级缓存
     * 工作机制：
     *  1. 一个会话查询一条数据，这个数据就会被放在当前会话的一级缓存中
     *  2. 如果会话关闭，一级缓存的数据会被保存到二级缓存中；新的会话查询信息，就可以参照二级缓存中的内容
     *  3. sqlSession：
     *      (1) EmployeeMapper ==> Employee
     *      (2) DepartmentMapper ==> Department
     *    不同的NameSpace查出的数据会放在对应的缓存（map）中
     *
     * 使用步骤：
     *  1. 开启全局二级缓存配置：<setting name="cacheEnabled" value="true"/>
     *  2. 在每个Mapper.xml中配置二级缓存
     *  3. POJO（每个JavaBean对象）需要实现序列化接口
     *
     *  TODO 查出的数据会默认先放在一级缓存中，只有关闭会话后，一级缓存中的数据才会转移到二级缓存中
     *
     */
     @Test
    public void testSecondLevelCache() throws IOException {
         SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
         SqlSession openSession = sqlSessionFactory.openSession();
         SqlSession openSession2 = sqlSessionFactory.openSession();

         try {
             // 两个sqlSession会话
             // 如果不配置全局二级缓存，会发送两个sql语句，而配置后就只会发送一个sql语句
             EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
             EmployeeMapper mapper2 = openSession2.getMapper(EmployeeMapper.class);

             // 第一个会话
             Employee emp1 = mapper.getEmpById(1);
             System.out.println(emp1);
             openSession.close();

             // 第二个会话
             Employee emp2 = mapper2.getEmpById(1);
             System.out.println(emp2);
             openSession2.close();

             // TODO 当readOnly设置成true时，才会返回true，说明拿到的是同一个对象
             System.out.println(emp1 == emp2);

         }finally {
             openSession.close();
         }
     }

    /**
     * TODO 5.1.3 和缓存相关的设置/属性：
     *  1. <setting name="cacheEnabled" value="true"/>
     *      当设置成false时，二级缓存会被关闭，而一级缓存一直可用，不会被关闭
     *  2. 每个select标签都有 useCache="true" 设置
     *      当设置成false时，二级缓存会被关闭，而一级缓存一直可用，不会被关闭
     *  3. 增删改标签： flushCache="true"，！！！每次执行完都会清除缓存！！！
     *      当设置成true时，一级、二级缓存都会被清除
     *  4. 查询标签： flushCache="false"
     *      当设置成true时，一级、二级缓存都会被清除
     *  5. openSession.clearCache()：只是清除当前session的一级缓存
     *  6. localCacheScope：本地缓存作用域（一级缓存）
     *      （1）session：当前会话的所有数据保存在会话缓存中
     *      （2）statement：可以禁用一级缓存
     */
}
