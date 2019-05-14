package one.auditfinder.server.config;

import java.net.DatagramSocket;
import java.net.InetAddress;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

@Configuration
@ComponentScan(basePackages={"one.auditfinder.server.comps", "one.auditfinder.server.service"})
@EnableScheduling
@PropertySources( {
		@PropertySource("classpath:appvalues.properties")
} )
@EnableTransactionManagement
@MapperScan("one.auditfinder.server.mapper")
public class RootConfig implements TransactionManagementConfigurer {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public PlatformTransactionManager annotationDrivenTransactionManager() {
		return this.transactionManager();
	}
	
	@Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
       return new PropertySourcesPlaceholderConfigurer();
    }
	
	@Bean
	public PropertiesFactoryBean fileConf() {
	    PropertiesFactoryBean bean = new PropertiesFactoryBean();
	    bean.setLocation(new ClassPathResource("fileConf.properties"));
	    if(logger.isDebugEnabled()) {
			logger.debug("Call fileConf...");
		}
	    return bean;
	}
	
	@Bean
	public String serviceIpStr() {
		if(logger.isDebugEnabled()) {
			logger.debug("Call serviceIpStr...");
		}
		String serviceIpStr = "";
		try {
			try(DatagramSocket s=new DatagramSocket())
			{
				if(logger.isDebugEnabled()) {
					logger.debug("Call Datagram Sockert..");
				}
			    s.connect(InetAddress.getByAddress(new byte[]{1,1,1,1}), 53);
			    String str = s.getLocalAddress().toString();
			    if( str.startsWith("/"))
			    	serviceIpStr = str.substring(1);
			    else 
			    	serviceIpStr = str;
			}
		}catch ( Exception e) {}
		return serviceIpStr;
	}
	
		
	@Value("${jdbc.driverClassName}")
    private String jdbcDriverClassName;

    @Value("${jdbc.url}")
    private String jdbcUrl;

    @Value("${jdbc.username}")
    private String jdbcUsername;

    @Value("${jdbc.password}")
    private String jdbcPassword;
        
    @Bean(destroyMethod="close")
    public DataSource dataSource()
    {
    	if(logger.isDebugEnabled()) {
			logger.debug("Call dataSource...");
		}
    	BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(this.jdbcDriverClassName);
        dataSource.setUrl(this.jdbcUrl);
        dataSource.setUsername(this.jdbcUsername);
        dataSource.setPassword(this.jdbcPassword);
        dataSource.setInitialSize(1);
        dataSource.setMaxActive(30);
        dataSource.setMaxIdle(2);
        dataSource.setMaxWait(30000);
        dataSource.setValidationQuery("select 1 ");
        dataSource.setTestOnBorrow(true);
        dataSource.setTestOnReturn(false);
        dataSource.setTestWhileIdle(true);
        dataSource.setTimeBetweenEvictionRunsMillis(60000);
        dataSource.setMinEvictableIdleTimeMillis(60000);
        return dataSource;
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception
    {
    	if(logger.isDebugEnabled()) {
			logger.debug("Call sqlSessionFactory...");
		}
    	
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(this.dataSource());
        sqlSessionFactoryBean.setTypeAliasesPackage("one.auditfider.server.vo");
        return sqlSessionFactoryBean.getObject();
    }

    @Bean
    public SqlSession sqlSession() throws Exception
    {
    	if(logger.isDebugEnabled()) {
			logger.debug("Call sqlSession...");
		}
    	SqlSession sqlSession = new SqlSessionTemplate(this.sqlSessionFactory());
        return sqlSession;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
    	if(logger.isDebugEnabled()) {
			logger.debug("Call transactionManager...");
		}
    	DataSourceTransactionManager trman = new DataSourceTransactionManager();
    	trman.setDataSource(this.dataSource());
    	return trman;
    }
	    
    /**
	 * message source 들을 등록함
	 *
	 * @return
	 */
	@Bean
	public MessageSource messageSource() {

		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasenames("classpath:msgs/messages", "classpath:msgs/valid");
		messageSource.setUseCodeAsDefaultMessage(true);
		messageSource.setDefaultEncoding("UTF-8");
		// # -1 : never reload, 0 always reload
		messageSource.setCacheSeconds(-1);
		return messageSource;
	}
	
	@Bean
	public MessageSourceAccessor messageSourceAccessor() {
		return new MessageSourceAccessor( messageSource());
	}
	
}
