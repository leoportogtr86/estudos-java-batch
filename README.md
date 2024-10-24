# Estudos Java Batch

Este projeto demonstra como realizar processamento em lote (batch processing) usando Java, com leitura de dados de um arquivo CSV e inserção desses dados em um banco de dados.

## Estrutura do Projeto

- **src/main/java/com/porto/BatchProcess.java**: Contém a lógica principal para leitura do arquivo CSV e inserção dos dados no banco de dados.
- **src/main/java/com/porto/connection/DatabaseConnection.java**: Classe responsável por gerenciar a conexão com o banco de dados.

## Pré-requisitos

- JDK 8 ou superior
- Banco de dados configurado (MySQL, PostgreSQL, etc.)
- Arquivo CSV (`dados.csv`) com os dados a serem processados

## Configuração

1. Clone o repositório:
    ```bash
    git clone https://github.com/leoportogtr86/estudos-java-batch.git
    cd estudos-java-batch
    ```

2. Configure a conexão do banco de dados na classe `DatabaseConnection.java`:
    ```java
    // DatabaseConnection.java
    public class DatabaseConnection {
        public static Connection getConnection() {
            // Implementar a lógica de conexão aqui
        }
    }
    ```

3. Coloque o arquivo CSV (`dados.csv`) na raiz do projeto.

## Execução

Compile e execute o projeto:
```bash
javac src/main/java/com/porto/BatchProcess.java
java -cp src/main/java com.porto.BatchProcess
```

## Funcionamento

- O programa lê o arquivo `dados.csv` linha por linha.
- Cada linha é separada por vírgulas e os dados são extraídos.
- Os dados são inseridos no banco de dados usando uma instrução SQL `INSERT`.

## Exemplo de Saída

```
--- Executando a query ---
Linhas afetadas: 1
--- Executando a query ---
Linhas afetadas: 1
...
```

## Tratamento de Erros

Erros de IO e SQL são capturados e impressos no console.

## Contribuição

Contribuições são bem-vindas! Sinta-se à vontade para abrir issues e pull requests.

## Licença

Este projeto está licenciado sob a Licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.
