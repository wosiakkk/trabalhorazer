package trabalho.razer.javaweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EntityScan(basePackages = "trabalho.razer.javaweb.model") //anotação para informar ao spring boot onde estão as entidades
@ComponentScan(basePackages = {"trabalho.*"}) //forca o spring a mapear todas os pacotes, pois pode ocorrer do spring não encontrar as classes controllers, e isso força ele mapemar todas
@EnableJpaRepositories(basePackages = {"trabalho.razer.javaweb.repository"}) //ativando o mapemando dos repositories
@EnableTransactionManagement //ativando o recurso de trnasações usados na persistência de dados
@EnableWebMvc //para poder utilizar métodos do  WebMvcConfigurer
public class Application implements WebMvcConfigurer{

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	
	}
	
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {

		registry.addViewController("/index").setViewName("/index");
		registry.addViewController("/").setViewName("index");
		registry.addViewController("/login").setViewName("login");
		registry.addViewController("/hello").setViewName("hello");
		registry.addViewController("/home").setViewName("home");
		registry.addViewController("/restrito").setViewName("restrito");
		registry.addViewController("/acessonegado").setViewName("acessonegado");
		registry.addViewController("/bemvindo").setViewName("bemvindo");
		registry.addViewController("/teste").setViewName("teste");
		
		
		 
	}

}
