create table tb_usuario (
    id_usuario serial not null,
    nome varchar(70) not null,
    email varchar(50) not null,
    senha varchar(30) not null,
    ativo boolean default false,

    constraint pk_tb_usuario primary key(id_usuario),
    constraint un_tb_usuario_email unique(email)
);

create table tb_sistema (
    id_sistema serial not null,
    sigla varchar(15) not null,
    nome varchar(80) not null,
    ativo boolean default false,
    clienteId text not null,
    clienteSecret text not null,
    chave_privada text,

    constraint pk_tb_sistema primary key(id_sistema),
    constraint un_tb_sistema_sigla unique(sigla)
);

create table tb_perfil (
    id_perfil serial not null,
    rule varchar(30) not null,
    descricao varchar(100),

    constraint pk_tb_perfil primary key(id_perfil),
    constraint un_tb_perfil_rule unique(rule)
);

create table tb_gestao_acesso (
    id_sistema integer not null,
    id_usuario integer not null,
    id_perfil integer not null,

    constraint pk_tb_sistema_usuario_perfil
        primary key (id_sistema, id_usuario, id_perfil),

    constraint fk_tb_sistema_usuario_perfil_id_sistema
        foreign key (id_sistema) references tb_sistema(id_sistema),

    constraint fk_tb_sistema_usuario_perfil_id_usuario
        foreign key (id_usuario) references tb_usuario(id_usuario),
    
    constraint fk_tb_sistema_usuario_perfil_id_perfil
        foreign key (id_perfil) references tb_perfil(id_perfil)
);