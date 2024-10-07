### Guia do Zero: Criando Batch em Java Nativo

**Objetivo**: Criar um batch com Java nativo, sem o uso de frameworks, que processe um arquivo e interaja com um banco
de dados usando JDBC. O foco será em um cenário real de processamento de dados a partir de um arquivo CSV e gravação das
informações em um banco de dados relacional.

#### 1. Estrutura básica de um Batch Process

- **Entrada**: Um arquivo CSV contendo dados a serem processados.
- **Processamento**: Leitura do arquivo, transformação dos dados se necessário e gravação no banco de dados.
- **Saída**: Resultados do processamento, geralmente logs ou confirmações no banco de dados.

#### 2. Pré-requisitos

- **Java JDK** instalado (versão 8 ou superior).
- Banco de dados relacional (usaremos o PostgreSQL neste exemplo).
- **JDBC driver** do PostgreSQL (ou outro banco de sua escolha).
- Um **arquivo CSV** para ser processado.

#### 3. Arquitetura Simples

1. **Leitura de Arquivo**: O batch fará a leitura de um arquivo CSV.
2. **Transformação dos Dados**: Processamento básico dos dados do CSV.
3. **Persistência**: Inserção dos dados no banco de dados via JDBC.
4. **Relatório**: Logs sobre o sucesso ou falha das operações.

#### 4. Exemplo Real de Mundo

##### 4.1. Exemplo de arquivo CSV (`dados.csv`)

```csv
id,nome,email
1,Leonardo,leonardo@example.com
2,Mariana,mariana@example.com
3,Carlos,carlos@example.com
```

##### 4.2. Configuração do Banco de Dados

Crie uma tabela chamada `usuarios` no PostgreSQL:

```sql
CREATE TABLE usuarios
(
    id    SERIAL PRIMARY KEY,
    nome  VARCHAR(255),
    email VARCHAR(255) UNIQUE
);
```

##### 4.3. Configurando o JDBC

Baixe o driver JDBC para o PostgreSQL e adicione ao seu classpath.

Exemplo de conexão JDBC:

```java
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    public static Connection getConnection() throws SQLException {
        String url = "jdbc:postgresql://localhost:5432/meubanco";
        String username = "usuario";
        String password = "senha";

        return DriverManager.getConnection(url, username, password);
    }
}
```

##### 4.4. Leitura e Processamento do Arquivo CSV

Aqui usaremos o `BufferedReader` para ler o arquivo linha por linha:

```java
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BatchProcess {

    public static void main(String[] args) {
        String csvFile = "dados.csv";
        String line;
        String csvSeparator = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            Connection connection = DatabaseConnection.getConnection();
            String sqlInsert = "INSERT INTO usuarios (id, nome, email) VALUES (?, ?, ?)";

            while ((line = br.readLine()) != null) {
                String[] data = line.split(csvSeparator);

                // Processar a linha e salvar no banco
                try (PreparedStatement stmt = connection.prepareStatement(sqlInsert)) {
                    stmt.setInt(1, Integer.parseInt(data[0])); // id
                    stmt.setString(2, data[1]); // nome
                    stmt.setString(3, data[2]); // email

                    stmt.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                    // Log para tratamento de erro em inserção
                }
            }

            connection.close();
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }
}
```

##### 4.5. Explicação do Código

- **Leitura do CSV**: O código usa `BufferedReader` para ler cada linha do arquivo CSV e então quebra os valores
  separados por vírgula usando o método `split()`.
- **Conexão com o Banco**: Uma conexão é estabelecida com o banco de dados PostgreSQL através de JDBC.
- **Inserção no Banco de Dados**: Para cada linha lida, o batch prepara uma instrução SQL `INSERT INTO` para inserir os
  dados lidos no banco de dados.
- **Tratamento de Erros**: O código tem um tratamento básico de exceções, registrando logs no console para eventuais
  falhas.

##### 4.6. Melhorias Possíveis

- **Validação dos Dados**: Antes de inserir, você pode validar os dados lidos do CSV.
- **Controle de Transação**: Utilizar transações para garantir que as operações sejam consistentes.
- **Logging Profissional**: Implementar um sistema de logs usando o `java.util.logging` ou bibliotecas como Log4J ou
  SLF4J para gerar logs em arquivo.

#### 5. Próximos Passos

1. **Transações e Rollback**: Adicione controle de transações para garantir que, em caso de falha, o sistema possa
   reverter as operações já realizadas.
2. **Paralelização**: Dependendo da necessidade, o batch pode ser configurado para processar várias partes do arquivo
   simultaneamente.
3. **Monitoramento**: Implementar relatórios e métricas sobre o processamento (tempo, quantidade de registros
   processados, etc.).

Esse exemplo cobre um caso básico de um batch de processamento de arquivos com JDBC e oferece uma base sólida para criar
soluções mais avançadas.