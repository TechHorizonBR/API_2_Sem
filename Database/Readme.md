# Banco de Dados: MySQL
### Descrição: Este banco de dados contém informações sobre orientador, aluno, tg, matricula, nota, entrega


## Tabelas

<br id="topo">
<p align="center">
    <a href="#Orientador">Orientador</a>  |
    <a href="#aluno">Aluno</a>  |
    <a href="#turma">Turma</a>  |
    <a href="#matricula">Matricula</a>  |
    <a href="#tg">TG</a>   |
    <a href="#entrega">Entrega</a>   |
    <a href="#nota">Nota</a>  |
    <a href="#sql">Código SQL</a>
</p>
</b>
<span id="Orientador">

### Tabela Orientador

| Nome da Coluna    | Tipo         | Propriedades  | Descrição           |
| ------------------ | ------------ | ------------- | ------------------- |
| id          | BIGINT  | NOT NULL, PK  | ID do Orientador|
| nome               | VARCHAR(255)  | NOT NULL      | Nome do orientador    |
| emailFatec              | VARCHAR(255)  | NOT NULL, UNIQUE      | Email Fatec do orientador   |

<span id="aluno">

### Tabela Aluno

| Nome da Coluna    | Tipo         | Propriedades  | Descrição           |
| ------------------ | ------------ | ------------- | ------------------- |
| id               | BIGINT | NOT NULL, PK  | Id do Aluno     |
| nome       | VARCHAR(255) | NOT NULL      | Nome do Aluno |
| emailPessoal    | VARCHAR(255)  | NOT NULL | Email pessoal do Aluno |
| emailFatec    | VARCHAR(255)  | NOT NULL, UNIQUE | Email Fatec do Aluno |
| idOrientador    | BIGINT  | FOREIGN KEY | Chave estrangeira da tabela Orientador |

<span id="turma">

### Tabela Turma

| Nome da Coluna    | Tipo         | Propriedades  | Descrição           |
| ------------------ | ------------ | ------------- | ------------------- |
| id               | BIGINT  | NOT NULL, PK     | Id da Turma |
| ano         | INT(4)  | NOT NULL, UNIQUE  | Ano da Turma |
| semestre              | INT(1)  | NOT NULL, UNIQUE | Semestre da Turma |
| matricula         | INT(1)  | NOT NULL, UNIQUE | Matricula da Turma, sendo TG1 ou TG2 |

<span id="matricula">

### Tabela Matrícula

| Nome da Coluna       | Tipo         | Propriedades  | Descrição           |
| --------------------- | ------------ | ------------- | ------------------- |
| idAluno               | BIGINT      | NOT NULL, PK, FOREIGN KEY | Id do Aluno, sendo chave estrangeira e primaria|
| idTurma               | BIGINT      | NOT NULL, PK, FOREIGN KEY | Id da turma, sendo chave estrangeira e primaria|

<span id="tg">

### Tabela TG

| Nome da Coluna            | Tipo               | Propriedades  | Descrição           |
| -------------------------- | ------------------ | ------------- | ------------------- |
| id                         | BIGINT             | NOT NULL, PK  | ID do TG |
| tipo                       | VARCHAR(255)       | NOT NULL      | Tipo de TG |
| disciplina                 | VARCHAR(255)       | -             | Disciplina do TG, em caso relatório técnico|
| empresa                    | VARCHAR(255)       | -             | Empresa do TG, em caso relatório técnico|
| problema                   | VARCHAR(255)       | -             | Problema do TG, em caso de Artigo Cientifico|

<span id="entrega">

### Tabela Entrega

| Nome da Coluna       | Tipo         | Propriedades  | Descrição           |
| --------------------- | ------------ | ------------- | ------------------- |
| id                    | BIGINT       | NOT NULL, PK | Id da entrega|
| titulo                | VARCHAR(255) | NOT NULL | Titulo da entrega|
| dataEntrega           | DATE         | NOT NULL | Data da entrega|
| tipo                | VARCHAR(20) | - | Tipo de entrega|
| idTurma               | BIGINT       | NOT NULL, FOREIGN KEY | Id da Turma|


<span id="nota">

### Tabela Nota

| Nome da Coluna       | Tipo         | Propriedades  | Descrição           |
| --------------------- | ------------ | ------------- | ------------------- |
| id                    | BIGINT       | NOT NULL, PK | Id da nota do Aluno|
| feeback                | VARCHAR(255) | - | Feeback do professor para o aluno|
| valor           | DOUBLE         | NOT NULL | Nota do Aluno|
| idAluno               | BIGINT       | NOT NULL, FOREIGN KEY | Id do Aluno|
| idTurma               | BIGINT       | NOT NULL, FOREIGN KEY | Id da Turma|


<span id="sql">

### Código SQL

```
CREATE DATABASE tgsync;

USE tgsync;

CREATE TABLE turma(
    id bigint PRIMARY KEY AUTO_INCREMENT,
    ano INT NOT NULL,
    semestre INT NOT NULL,
    disciplina INT NOT NULL,
    UNIQUE INDEX (ano, semestre, disciplina)
);

CREATE TABLE orientador(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    emailFatec VARCHAR(255) NOT NULL,
    nome VARCHAR(255) NOT NULL,
    UNIQUE INDEX (emailFatec)
);

CREATE TABLE aluno(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(255) NOT NULL,
    emailFatec VARCHAR(255) NOT NULL,
    emailPessoal VARCHAR(255) NOT NULL,
    idOrientador BIGINT,
    UNIQUE INDEX (emailFatec),
    FOREIGN KEY (idOrientador) REFERENCES orientador(id)
);

CREATE TABLE tg(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    tipo VARCHAR(255) NOT NULL,
    disciplina VARCHAR(255),
    problema VARCHAR(255),
    empresa VARCHAR(255),
    idAluno BIGINT NOT NULL,
    FOREIGN KEY (idAluno) REFERENCES aluno(id)
);

CREATE TABLE entrega(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    titulo VARCHAR(255) NOT NULL,
    dataEntrega DATE NOT NULL,
    idTurma BIGINT NOT NULL,
    FOREIGN KEY (idTurma) REFERENCES turma(id)
);

CREATE TABLE nota(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    feedback VARCHAR(255),
    valor DOUBLE NOT NULL,
    idAluno BIGINT NOT NULL,
    idEntrega BIGINT NOT NULL,
    FOREIGN KEY (idAluno) REFERENCES aluno(id),
    FOREIGN KEY (idEntrega) REFERENCES entrega(id)
);

CREATE TABLE matricula(
    idAluno BIGINT,
    idTurma BIGINT,
    PRIMARY KEY (idAluno, idTurma),
    FOREIGN KEY (idAluno) REFERENCES aluno(id),
    FOREIGN KEY (idTurma) REFERENCES turma(id)
);

ALTER TABLE entrega 
ADD tipo varchar(20);

```

### Para criação de Usuário:

```
CREATE USER 'devs'@'localhost' IDENTIFIED BY 'password123';
GRANT SELECT, INSERT, DELETE, UPDATE on tgsync.* TO 'devs'@'localhost';
```

### Para inserir a base de dados de Orientadores:

```
INSERT INTO orientador(nome, emailFatec)
VALUES  ('Ana Cecília Rodrigues Medeiros', 'ana.medeiros@fatec.sp.gov.br'),
        ('Antônio Egydio São Tiago Graça', 'antonio.graca@fatec.sp.gov.br'),
        ('Arley Ferreira de Souza', 'arley.souza@fatec.sp.gov.br'),
        ('Cícero Soares da Silva', 'cicero.silva@fatec.sp.gov.br'),
        ('Claúdio Etelvino de Lima', 'claudio.elima@fatec.sp.gov.br'),
        ('Cristie Luis Kugelmeier', 'cristie.kugelmeier@fatec.sp.gov.br'),
        ('Dercy Félix da Silva', 'dfelix2008@fatec.sp.gov.br'),
        ('Diogo Branquinho Ramos', 'diogo.branquinho@fatec.sp.gov.br'),
        ('Eduardo Sakaue', 'e.sakaue@fatec.sp.gov.br'),
        ('Eliane Penha Mergulhão Dias', 'eliane.dias@fatec.sp.gov.br'),
        ('Emanuel Mineda Carneiro', 'emanuel.mineda@fatec.sp.gov.br'),
        ('Fabricio Galendes Marques de Carvalho', 'fabricio.carvalho01@fatec.sp.gov.br'),
        ('Fernando Masaroni Ashikaga', 'fmasanori@fatec.sp.gov.br'),
        ('Geraldo José Lombardi de Souza', 'geraldo.souza3@fatec.sp.gov.br'),
        ('Gerson da Penha Neto', 'gerson.penha@fatec.sp.gov.br'),
        ('Giuliano Araújo Bertoti', 'giuliano.bertoti@fatec.sp.gov.br'),
        ('Guaraci Lima de Morais', 'guaraci.morais@fatec.sp.gov.br'),
        ('Jean Carlos Lourenço Costa', 'jean.costa4@fatec.sp.gov.br'),
        ('Joares Lidovino dos Reis', 'joares.lidovino@fatec.sp.gov.br'),
        ('José Walmir Gonçalves Duque', 'walmir.duque@fatec.sp.gov.br'),
        ('Juliana Forin Pasquini Martinez', 'pasquini.juliana01@fatec.sp.gov.br'),
        ('Lise Virgínia Vieira de Azevedo', 'lise.azevedo@fatec.sp.gov.br'),
        ('Nanci de Oliveira', 'nanci.oliveira@fatec.sp.gov.br'),
        ('Nilo Jerônimo Vieira', 'nilo.vieira@fatec.sp.gov.br'),
        ('Reinaldo Gen Ichiro Arakaki', 'reinaldo.arakaki@fatec.sp.gov.br');
```
