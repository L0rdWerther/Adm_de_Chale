# Gerenciador de Chalés e Hospedagens

Este projeto é uma aplicação Java para gerenciar clientes, chalés, hospedagens e serviços associados. Ele utiliza um banco de dados PostgreSQL para armazenar as informações.

## Requisitos

- **Java**: Versão 8 ou superior.
- **PostgreSQL**: Banco de dados relacional.
- **IDE Java**: Recomenda-se o uso do IntelliJ IDEA.

## Configuração do Projeto

1. Clone o repositório:
    ```sh
    git clone https://github.com/L0rdWerther/Trabalho-Final-PC2
    ```

2. Abra o projeto na sua IDE Java.

3. Configure as credenciais do banco de dados no arquivo `src/DatabaseUtil.java`:
    ```java
    private static final String URL = "jdbc:postgresql://localhost:5432/seu_banco_de_dados";
    private static final String USER = "seu_usuario";
    private static final String PASSWORD = "sua_senha";
    ```

4. Execute o script `schema.sql` para criar as tabelas e funções no banco de dados.

## Estrutura do Projeto

### Diretórios Principais
- `src/`: Contém os arquivos Java da aplicação.
- `dependencia/`: Contém o driver JDBC para PostgreSQL.

### Funcionalidades
- **Gerenciamento de Clientes**: Cadastro e visualização de clientes.
- **Gerenciamento de Chalés**: Controle de disponibilidade e informações dos chalés.
- **Hospedagens**: Registro de hospedagens, cálculo de valores e atualização de status.
- **Serviços**: Adição de serviços às hospedagens.
- **Relatórios**: Visualização de hóspedes ativos e outros dados.

## Compilação e Execução

### Compilação Manual
1. Compile os arquivos Java:
    ```sh
    javac -cp ./dependencia/postgresql-42.7.4.jar src/*.java
    ```

2. Execute a aplicação:
    ```sh
    java -cp "src:./dependencia/postgresql-42.7.4.jar" MainApp
    ```

3. Para limpar os arquivos compilados:
    ```sh
    rm src/*.class
    ```

### Execução na IDE
1. Configure o projeto na sua IDE.
2. Execute a classe `MainApp` para iniciar a aplicação.

## Estrutura do Banco de Dados

### Tabelas
- **Cliente**: Armazena informações dos clientes.
- **Chale**: Gerencia os chalés disponíveis.
- **Hospedagem**: Registra as hospedagens realizadas.
- **Servico**: Define os serviços disponíveis.
- **HospedagemServico**: Relaciona hospedagens com serviços.
- **Funcionario**: Gerencia os funcionários.
- **FuncionarioServico**: Relaciona funcionários com serviços.

### Funções e Triggers
- **valor_total_gasto(hospede_id)**: Calcula o valor total gasto por um cliente.
- **liberar_chale(cod_chale)**: Libera um chalé e atualiza o status da hospedagem.
- **hospedes_ativos**: View que lista os hóspedes ativos.

## Observações

- Certifique-se de que o banco de dados PostgreSQL esteja em execução antes de iniciar a aplicação.
- Caso encontre erros, verifique as configurações de conexão no arquivo `DatabaseUtil.java`.

## Licença

Este projeto é de uso acadêmico e não possui licença específica.