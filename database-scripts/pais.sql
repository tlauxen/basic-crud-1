create sequence pais_seq;

create table pais (
	id bigint not null,
	sigla varchar(2) not null,
	nome varchar(100) not null
);

alter table pais add constraint pais_pk primary key (id);