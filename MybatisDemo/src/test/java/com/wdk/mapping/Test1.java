package com.wdk.mapping;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.BeforeClass;
import org.junit.Test;

import com.wdk.entity.Menu;

public class Test1 {
	private static SqlSessionFactory sqlSessionFactory;
	
	@BeforeClass
	public static void inited(){
		try {
			Reader reader = Resources.getResourceAsReader("mybatis-config.xml");
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	public void test(){
		SqlSession sqlSession = sqlSessionFactory.openSession();
		List<Menu> list = sqlSession.selectList("selectMenu");
		System.out.println(list);
		sqlSession.close();
	}
}
