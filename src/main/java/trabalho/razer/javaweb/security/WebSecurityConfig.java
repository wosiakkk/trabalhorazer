package trabalho.razer.javaweb.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private ImplementacaoUserDetailService implementacaoUserDetailService;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.csrf().disable().authorizeRequests() //Permitir restringir acessos
		.antMatchers("/", "/index").permitAll() //Dando acesso total a page inicial
		.antMatchers("/cadastrocliente").hasAnyRole("ADMIN") //Restringindo acesso a uma página de acrodo com alguma role
		.anyRequest().authenticated()
		.and()
		.exceptionHandling().accessDeniedPage("/acessonegado") //Definindo a página de acesso negado
		.and()
		.formLogin()
		.loginPage("/login")
		.permitAll() //Dando acesso a todos a página de login
		.defaultSuccessUrl("/home") //Definindo a página que será mostrada em casa de sucesso de login
		.and()
		.logout().permitAll() //Permitindo a todos acesso a requisição logout
		.logoutSuccessUrl("/login") //Definindo a página que será mostrada em casa de sucesso de logout
		.logoutRequestMatcher(new AntPathRequestMatcher("/logout")); //intercepetando todo tipo re quisção a url logout
		
	}

	
	
	@Override //Cria a autenticação do usuário com banco de dados ou em memória
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		/*Realizando a autenticação com o banco de dados e com criptografia na senha*/
		auth.userDetailsService(implementacaoUserDetailService)
		.passwordEncoder(new BCryptPasswordEncoder());
	}
	
}
