package trabalho.razer.javaweb;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {

	@Test
	public void contextLoads() {
	}
	
	@Test
	public void gerarSenha() {
		String encodedPassword = new BCryptPasswordEncoder().encode("123");
		System.out.println(encodedPassword);
	}
}
