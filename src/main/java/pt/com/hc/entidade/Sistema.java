package pt.com.hc.entidade;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

@Entity
@Table(name = "tb_sistema")
public class Sistema extends PanacheEntityBase {

    Sistema() {
        this.usuarios = new ArrayList<>();
    }

    @Id
    @Column(name = "id_sistema")
    public Integer idSistema;
    
    public String sigla;
    public String nome;
    public Boolean ativo;
    public String chave_privada;
    public String clienteId;
    public String clienteSecret;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "tb_gestao_acesso",
        joinColumns = @JoinColumn(name = "id_sistema"),
        inverseJoinColumns = @JoinColumn(name = "id_usuario")
    )
    private List<Usuario> usuarios;
    
    public List<Usuario> getUsuarios() {
        return Collections.unmodifiableList(this.usuarios);
    }
}