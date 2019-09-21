package trabalho.razer.javaweb.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import trabalho.razer.javaweb.model.Cliente;

public interface ClienteRepository extends CrudRepository<Cliente, Long>{
	
	@Query(value = "select c from Cliente c where c.cpf = ?1")
	Cliente buscaPorCpf(String cpf);
}
