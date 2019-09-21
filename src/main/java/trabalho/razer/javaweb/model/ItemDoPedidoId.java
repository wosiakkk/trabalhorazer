package trabalho.razer.javaweb.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/*Classe de composite key para a tabela auxiliar item_do_pedido*/

@Embeddable
public class ItemDoPedidoId implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Column(name = "id_pedido")
	private Long idpedido;
	@Column(name = "id_produto")
	private Long idproduto;
	
	public Long getIdpedido() {
		return idpedido;
	}
	public void setIdpedido(Long idpedido) {
		this.idpedido = idpedido;
	}
	public Long getIdproduto() {
		return idproduto;
	}
	public void setIdproduto(Long idproduto) {
		this.idproduto = idproduto;
	}
	
	public ItemDoPedidoId() {
		// TODO Auto-generated constructor stub
	}
	
	public ItemDoPedidoId(Long idpedido, Long idproduto) {
		super();
		this.idpedido = idpedido;
		this.idproduto = idproduto;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idpedido == null) ? 0 : idpedido.hashCode());
		result = prime * result + ((idproduto == null) ? 0 : idproduto.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ItemDoPedidoId other = (ItemDoPedidoId) obj;
		if (idpedido == null) {
			if (other.idpedido != null)
				return false;
		} else if (!idpedido.equals(other.idpedido))
			return false;
		if (idproduto == null) {
			if (other.idproduto != null)
				return false;
		} else if (!idproduto.equals(other.idproduto))
			return false;
		return true;
	}
	
	
}
