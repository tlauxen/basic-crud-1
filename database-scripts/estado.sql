create sequence estado_seq;

create table estado (
	id bigint not null,
	idpais bigint not null,
	sigla varchar(2) not null,
	nome varchar(100) not null
);

alter table estado add constraint estado_pk primary key (id);

alter table estado add constraint est_pai_fk foreign key (idpais) references pais (id);
create index est_pai_fk_ix on estado(idpais);
