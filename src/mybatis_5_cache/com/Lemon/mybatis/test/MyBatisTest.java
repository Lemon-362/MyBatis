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
     *  3. sqlSession相同，两次查询之间执行了增删改操作（这次增删改操作可能对当前数据有影响）
     *  4. sqlSession相同，手动清除了一级缓存
     */
    @Test
    public void testFirstLevelCache() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession openSession = sqlSessionFactory.openSession();

        try {
            EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);

            Employee emp01 = mapper.getEmpById(1);
            System.out.println(emp01);

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


}
