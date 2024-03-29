package trabalho.razer.javaweb.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"cpf"})} )
public class Cliente implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/*#######  Atributos  ######*/
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotEmpty(message = "O CPF não pode ser vazio!")
	@NotNull(message = "O CPF não pode ser nulo!")
	@Column(nullable = false, unique = true)
	private String cpf;
	
	@NotEmpty(message = "O Nome não pode ser vazio!")
	@NotNull(message = "O Nome não pode ser nulo!")
	@Column(nullable = false)
	private String nome;
	
	@NotEmpty(message = "O Sobrenome não pode ser vazio!")
	@NotNull(message = "O Sobrenome não pode ser nulo!")
	@Column(nullable = false)
	private String sobrenome;
	/*###########################*/

	/*######## Getters e Setters ########*/
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getSobrenome() {
		return sobrenome;
	}
	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}
	/*###########################*/
	
	
	/*######## Equals e Hashcode por Id #####*/
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
		Cliente other = (Cliente) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	/*###############################*/
}
