CREATE TABLE filmes(
  id         integer primary key autoincrement
, nome       varchar(200) not null
, descricao  varchar(500)
, ano        integer not null
, lancamento integer not null
, imagem     varchar(100)
);