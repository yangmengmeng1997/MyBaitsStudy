package com.newcoder.community;

import com.newcoder.community.dao.AlphaDao;
import com.newcoder.community.dao.AlphaDaoImpl;
import com.newcoder.community.service.AlphaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringBootVersion;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.SimpleFormatter;


@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
class CommunityApplicationTests implements ApplicationContextAware {

	private ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	@Autowired   //自动注入变量，不再需要getBean重新获取创建
	@Qualifier("alphaDaoImpl")   //指定注解某个bean的值
	private AlphaDao alphaDao;   // 等价于 AlphaDao alphaDao = applicationContext.getBean(AlphaDao.class);  //通用，使用接口，按照类型访问

	@Autowired
	private AlphaService alphaService;

	@Autowired
	private SimpleDateFormat simpleDateFormat;

	//***************************************测试方法***********************************************************

	@Test
	public void testApplication(){
		System.out.println(applicationContext);
		//AlphaDao alphaDao = applicationContext.getBean(AlphaDao.class);  //通用，使用接口，按照类型访问
		AlphaDao alphaDao =  applicationContext.getBean("alphaDaoImpl" , AlphaDao.class);  //后面指定返回的类型,按照名称访问
		System.out.println(alphaDao.select());
	}

	@Test
	public void testBinMana(){
		AlphaService alphaService = applicationContext.getBean(AlphaService.class);
		AlphaService alphaService1 = applicationContext.getBean(AlphaService.class);
		System.out.println(alphaService);
		System.out.println(alphaService1);  //Spring容器中创建的对象都是单例模式
	}

	@Test
	public void testBeanConfig(){
		SimpleDateFormat simpleDateFormat = (SimpleDateFormat) applicationContext.getBean("simpleDateFormat");
		System.out.println(simpleDateFormat.format(new Date()));  //格式化日期
	}

	@Test
	public void testDI(){
		System.out.println(alphaDao);
		System.out.println(alphaService);
		System.out.println(simpleDateFormat);
	}

	@Test
	public void test(){
		System.out.println(SpringBootVersion.getVersion());
	}

	//******************************************************************************************************

}
