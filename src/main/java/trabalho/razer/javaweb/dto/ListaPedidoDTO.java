package trabalho.razer.javaweb.dto;

import java.io.Serializable;
/*Classe apenas para transferir datos customizados da view de gerração de pedido.
 * Ela será enviada a controller de pedido e cada atributo seu, referente a um model do projeto será
 * utilizado para a persistência de um novo pedido*/
public class ListaPedidoDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String idproduto;
	private String quantidade;
	private String clientecpf;
	
	public String getIdproduto() {
		return idproduto;
	}
	public void setIdproduto(String idproduto) {
		this.idproduto = idproduto;
	}
	public String getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(String quantidade) {
		this.quantidade = quantidade;
	}
	public String getClientecpf() {
		return clientecpf;
	}
	public void setClientecpf(String clientecpf) {
		this.clientecpf = clientecpf;
	}
	@Override
	public String toString() {
		return "ListaPedidoDTO [idproduto=" + idproduto + ", quantidade=" + quantidade + ", clientecpf=" + clientecpf
				+ "]";
	}
}
