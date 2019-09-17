package trabalho.razer.javaweb.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private ImplementacaoUserDetailService implementacaoUserDetailService;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.authorizeRequests()
		.antMatchers("/", "/home").permitAll()
		.antMatchers("/restrito").hasAnyRole("ADMIN")
		.anyRequest().authenticated()
		.and()
		.exceptionHandling().accessDeniedPage("/acessonegado")
		.and()
		.formLogin()
		.loginPage("/login")
		.permitAll()
		.defaultSuccessUrl("/bemvindo")
		.and()
		.logout().permitAll();
		
	}

	
	
	@Override //Cria a autenticação do usuário com banco de dados ou em memória
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		/*Realizando a autenticação com o banco de dados e com criptografia na senha*/
		auth.userDetailsService(implementacaoUserDetailService)
		.passwordEncoder(new BCryptPasswordEncoder());
	}
	
}
