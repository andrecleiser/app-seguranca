package pt.com.hc.entidade;

import java.util.Collections;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

@Entity
@Table(name = "tb_perfil")
public class Perfil extends PanacheEntityBase {
    @Id
    @Column(name = "id_perfil")
    public Integer idPerfil;
    public String rule;
    public String descricao;

    @ManyToMany(mappedBy = "perfis")
    private List<Usuario> usuarios;

    public List<Usuario> getUsuarios() {
        return Collections.unmodifiableList(this.usuarios);
    }
}
