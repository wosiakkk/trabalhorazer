package trabalho.razer.javaweb.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

@Entity
@Table(name = "item_do_pedido")
public class ItemDoPedido implements Serializable{

	private static final long serialVersionUID = 1L;
	@EmbeddedId
	private ItemDoPedidoId id;
	
	@ManyToOne
	@MapsId("idpedido")
	@JoinColumn(name = "id_pedido")
	private Pedido pedido;
	
	@ManyToOne
	@MapsId("idproduto")
	@JoinColumn(name = "id_produto")
	private Produto produto;
	
	@Column(name = "quantidade")
	private Long quantidade;

	
	

	public ItemDoPedido(Pedido pedido, Produto produto, Long quantidade) {
		super();
		this.pedido = pedido;
		this.produto = produto;
		this.quantidade = quantidade;
		this.id = new ItemDoPedidoId(pedido.getId(), produto.getId());
	}

	public ItemDoPedido() {
		// TODO Auto-generated constructor stub
	}
	
	public ItemDoPedidoId getId() {
		return id;
	}

	public void setId(ItemDoPedidoId id) {
		this.id = id;
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public Long getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Long quantidade) {
		this.quantidade = quantidade;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		ItemDoPedido other = (ItemDoPedido) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
}
