package trabalho.razer.javaweb.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import trabalho.razer.javaweb.model.Usuario;
import trabalho.razer.javaweb.repository.UsuarioRepository;

@Service
@Transactional
public class ImplementacaoUserDetailService implements UserDetailsService{

	/*Injeção do repository user*/
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		/*Buscando o usuário*/
		Usuario user = usuarioRepository.findUserByLogin(username);
		/*Validando*/
		if(user == null) {
			throw new UsernameNotFoundException("Usuário não foi encontrado");
		}
		/*Retornando UserDetails*/
		return new User(user.getLogin(),user.getSenha(),
				user.isEnabled(),user.isAccountNonExpired(),
				user.isAccountNonExpired(),user.isAccountNonLocked(),
				user.getAuthorities());
	}

}
