package trabalho.razer.javaweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
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
		registry.addViewController("/acessonegado").setViewName("acessonegado");
		registry.addViewController("/bemvindo").setViewName("bemvindo");
		registry.addViewController("/navbar").setViewName("navbar");
		registry.addViewController("/cadastrocliente").setViewName("cadastrocliente");
		registry.addViewController("/cadastroproduto").setViewName("cadastroproduto");
		
	}

	//método para configurar os recursos estaticos como javascript
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/js/**").addResourceLocations("classpath:/static/js/");
		registry.addResourceHandler("/fragments/**").addResourceLocations("classpath:/templates/fragments");
	}
	
	
}
