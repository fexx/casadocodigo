package br.com.casadocodigo.loja.conf;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;

import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;


/**
 * Classe de configração do jpa com hibernate
 * 
 * @author Fernando
 *
 */
@EnableTransactionManagement //estamos habilitando a transação com a anotação "@EnableTransactionManagement" para avisar ao spring que ele que vai tomar conta da transação
public class JPAConfiguration {
	
	
	/**
	 * Resopnsavel em configurar o jpa com hibernate para acessar o banco mysql
	 * 
	 * @return
	 */
	@Bean //para o spring usar esse metodo
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(){

		//criando o factoryBean
		LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
		
		JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		factoryBean.setJpaVendorAdapter(vendorAdapter);
		
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setUsername("root");
		dataSource.setPassword("root");
		dataSource.setUrl("jdbc:mysql://localhost:3306/casadocodigo");
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		
		factoryBean.setDataSource(dataSource);
		
		Properties props = new Properties();
		props.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
		props.setProperty("hibernate.show_sql", "true");
		props.setProperty("hibernate.hbm2ddl.auto", "update"); //hibernate criar sozinho as tabelas
		
		factoryBean.setJpaProperties(props);
		
		factoryBean.setPackagesToScan("br.com.casadocodigo.loja.models"); //informa qual pacote se encontra as classe de entidade jpa
		return factoryBean;
	}
	
	@Bean // vai ser o metodo que vai criar a transação, atraves do spring
	private JpaTransactionManager transactionManager(EntityManagerFactory emf) {
		return new JpaTransactionManager(emf);
	}
}
