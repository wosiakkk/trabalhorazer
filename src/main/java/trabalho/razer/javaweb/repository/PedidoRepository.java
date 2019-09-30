package trabalho.razer.javaweb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import trabalho.razer.javaweb.model.Pedido;

public interface PedidoRepository extends CrudRepository<Pedido, Long> {
	
	@Query(value = "select count(p) from Pedido p where p.cliente.id = ?1")
	Integer numeroDePedido(Long id);
	@Query(value = "select p from Pedido p where p.cliente.id = ?1")
	List<Pedido> buscarPedidosPorUsuario(Long id);
}
