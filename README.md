# Sparkus - Backend

## Sobre

API feita para a rede social [sparkus](https://github.com/MoDasby/sparkus/)

## Tipos

### UserDetails

| Body   | Tipo      | Descrição                           |
| :---------- |:----------| :---------------------------------- |
| `username` | string    | Nome de Usuário|
| `name` | string    | Nome |
| `iconPath` | string    | Link da foto de perfil |

### User

| Body   | Tipo      | Descrição                           |
| :---------- |:----------| :---------------------------------- |
| `username` | string    | Nome de Usuário|
| `name` | string    | Nome |
| `iconPath` | string    | Link da foto de perfil |
| `email` | string    | Email do usuário |
| `password` | string    | Senha do usuário |

### Post

| Body   | Tipo      | Descrição                           |
| :---------- |:----------| :---------------------------------- |
| `text` | string    | Texto do post |
| `authorUsername` | string    | Nome de usuário do autor do post |

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
| `user` | [UserDetails](#userdetails)    | Dados do usuário (nome, foto etc)|

### [POST] /auth/signup - usado para criar uma conta

JSON de solicitação:

| Body   | Tipo      | Descrição                           |
| :---------- |:----------| :---------------------------------- |
| `user` | [User](#user)    | Dados do usuário, incluindo senha e email

### [GET] /feed - retorna o feed do usuário

JSON de resposta:

| Body   | Tipo      | Descrição                           |
| :---------- |:----------| :---------------------------------- |
| `newUsers` | Array de [UserDetails](#userdetails)    | Dados do usuário (nome, foto etc)
 `posts` | Array de [Post](#post)    | Dados do post (texto e autor)

### [POST] /feed/post - faz um novo post

JSON de solicitação:

| Body   | Tipo      | Descrição                           |
| :---------- |:----------| :---------------------------------- |
| `Objeto` | [Post](#post)    | Dados do post (texto e autor)

### [GET] /search/{usuário} - busca contas pelo nome de usuário

JSON de resposta:

| Body   | Tipo      | Descrição                           |
| :---------- |:----------| :---------------------------------- |
| `Array` | Array de [UserDetails](#userdetails)    | Dados do usuário (nome, foto etc)

### [GET] /userDetails/{username} - retorna dados de uma conta

JSON de resposta:

| Body   | Tipo      | Descrição                           |
| :---------- |:----------| :---------------------------------- |
| `Objeto` | [UserDetails](#userdetails)    | Dados do usuário (nome, foto etc)