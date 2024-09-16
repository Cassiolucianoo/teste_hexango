# Relatório de Desenvolvimento do Projeto Hexagon

Este relatório documenta as decisões tomadas durante o desenvolvimento do projeto Hexagon, descrevendo as bibliotecas utilizadas, funcionalidades implementadas e as abordagens adotadas para atender aos requisitos do projeto.

## Objetivo do Projeto

O projeto **Hexagon** é um aplicativo Android desenvolvido utilizando **Jetpack Compose** para a interface de usuário (UI) e **MVVM** (Model-View-ViewModel) como padrão de arquitetura. Ele permite a adição, edição e gerenciamento de pessoas, diferenciando entre pessoas ativas e inativas. Também oferece suporte para inserção de fotos e formatação de campos como CPF e data de nascimento.

## Decisões de Desenvolvimento

### Arquitetura MVVM

O projeto foi estruturado utilizando a arquitetura **MVVM** para separar a lógica de negócios da UI, facilitando a manutenção do código e escalabilidade. As principais camadas da arquitetura são:

- **ViewModel**: Gerencia os dados de forma reativa e notifica a UI sobre as atualizações.
- **Repository**: Fornece dados ao ViewModel, interagindo com o banco de dados **SQLite**.
- **UI Composables**: Responsáveis por renderizar a interface com base nos dados fornecidos pelo ViewModel.

### Persistência com SQLite

Para armazenamento local, o projeto utiliza **Room** para abstrair o **SQLite**. A tabela armazena informações de pessoas, incluindo nome, data de nascimento, CPF, cidade, status ativo/inativo, e foto.

### Navegação

A navegação entre as telas do aplicativo é gerenciada por **Jetpack Navigation Compose**. As principais telas são:

- Lista de Pessoas Ativas
- Lista de Pessoas Inativas
- Tela de Adição de Pessoa
- Tela de Edição de Pessoa

### Gerenciamento de Fotos

O aplicativo permite upload de fotos de perfil usando o seletor de imagens do Android via **ActivityResultContracts.GetContent()**. A imagem selecionada é salva localmente utilizando o método `saveImageToFile()`.

## Bibliotecas Utilizadas

### Jetpack Compose

Toda a UI foi desenvolvida com **Jetpack Compose**, uma abordagem moderna e declarativa para criação de interfaces.

- `androidx.compose.ui:ui`: Criação de componentes de UI.
- `androidx.compose.material3:material3`: Implementação de componentes de design (botões, caixas de texto).
- `androidx.compose.ui:ui-tooling-preview`: Fornece pré-visualização dos componentes.

### Room (SQLite)

Para persistência dos dados, foi utilizada a biblioteca **Room**, que abstrai o **SQLite** e facilita a interação com o banco de dados.

- `androidx.room:room-runtime`: Interação com o banco de dados.
- `androidx.room:room-ktx`: Integração com corrotinas.
- `androidx.room:room-compiler`: Para gerar código necessário.

### Gson

Para serialização e desserialização de dados JSON:

- `com.google.code.gson:gson`: Manipulação de JSON.

### Navegação com Compose

A navegação entre telas foi implementada com **Navigation Compose**:

- `androidx.navigation:navigation-compose`: Gerenciamento de rotas e navegação.

### Coil

Para carregamento de imagens, utilizamos **Coil**, que oferece suporte nativo ao **Jetpack Compose**.

- `io.coil-kt:coil-compose`: Para carregamento de imagens de forma eficiente.

## Funcionalidades Implementadas

### 1. Cadastro de Pessoas

Os usuários podem adicionar novas pessoas, preenchendo informações como nome, CPF (com máscara), data de nascimento (com máscara), cidade, e status ativo/inativo.

- O campo de CPF utiliza uma máscara para formatação no padrão **"###.###.###-##"**.
- O campo de data de nascimento segue o formato **"DD/MM/AAAA"**.

### 2. Edição de Pessoas

Os usuários podem editar pessoas cadastradas, incluindo atualização da foto. A navegação para a tela de edição é feita a partir das listas de pessoas ativas ou inativas.

### 3. Listagem de Pessoas Ativas/Inativas

As pessoas cadastradas são organizadas em listas de pessoas ativas e inativas. O status da pessoa pode ser alterado diretamente na lista, permitindo desativar ou reativar um cadastro. A navegação entre as listas é gerenciada por um menu na parte superior do app.

### 4. Upload e Salvamento de Fotos

Os usuários podem adicionar fotos durante o cadastro ou edição de pessoas. As fotos são selecionadas da galeria do dispositivo e salvas no armazenamento do app.

### 5. Máscaras de Formatação

- **CPF**: Formatado no padrão brasileiro, facilitando a experiência do usuário ao digitar os números.
- **Data de Nascimento**: O campo utiliza máscara para formatação no padrão **"DD/MM/AAAA"**.

## Considerações Finais

O projeto **Hexagon** implementa um conjunto robusto de funcionalidades para gerenciamento de pessoas, utilizando uma arquitetura modular e tecnologias modernas. A escolha por **Jetpack Compose**, **Room** e **Coil** resultou em uma experiência de desenvolvimento fluida e uma interface de usuário dinâmica e responsiva. A separação de responsabilidades por meio da arquitetura **MVVM** garante a fácil manutenção e expansão do código.
