# Sparkus - Backend

## Sobre

API feita para a rede social [sparkus](https://github.com/MoDasby/sparkus/)

## Rotas

Todas as rotas tem o prefixo /api

### [POST] /auth - faz login no sistema

JSON de solicitação:

| Body   | Tipo      | Descrição                           |
| :---------- |:----------| :---------------------------------- |
| `credential` | string    | Email ou Nome de Usuário|
| `password` | string    | Senha|

JSON de resposta:

| Body   | Tipo      | Descrição                           |
| :---------- |:----------| :---------------------------------- |
| `token` | string    | Token JWT usado para autenticação|
| `user` | UserDetails    | Dados do usuário (nome, foto etc)|

### [POST] /auth/signup - usado para criar uma conta

JSON de solicitação:

| Body   | Tipo      | Descrição                           |
| :---------- |:----------| :---------------------------------- |
| `user` | User    | Dados do usuário, incluindo senha e email

### [GET] /feed - retorna o feed do usuário

| Body   | Tipo      | Descrição                           |
| :---------- |:----------| :---------------------------------- |
| `newUsers` | Array de UserDetails    | Dados do usuário (nome, foto etc)
 `posts` | Array de Post    | Dados do post (texto e autor)

### [POST] /feed/post - faz um novo post

| Body   | Tipo      | Descrição                           |
| :---------- |:----------| :---------------------------------- |
| `newUsers` | Array de UserDetails    | Dados do usuário (nome, foto etc)
 `posts` | Array de Post    | Dados do post (texto e autor)
