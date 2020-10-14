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
@Table(name = "tb_usuario")
public class Usuario extends PanacheEntityBase {

    Usuario() {
        this.perfis = new ArrayList<>();
    }

    @Id
    @Column(name = "id_usuario")
    public Integer idUsuario;

    public String nome;
    public String email;
    public String senha;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "tb_gestao_acesso",
        joinColumns = @JoinColumn(name = "id_usuario"),
        inverseJoinColumns = @JoinColumn(name = "id_perfil")
    )
    private List<Perfil> perfis;

    @ManyToMany(mappedBy = "usuarios", fetch = FetchType.LAZY)
    private List<Sistema> sistemas;

    public List<Perfil> getPerfis() {
        return Collections.unmodifiableList(this.perfis);
    }

    public List<Sistema> getSistemas() {
        return Collections.unmodifiableList(this.sistemas);
    }
}