package dependency_inversion;

/**
 * O Princípio da Inversão de Dependência (DIP) sugere que as classes de alto nível não devem
 * depender das classes de baixo nível, mas sim de abstrações (interfaces).
 * Além disso, as abstrações não devem depender dos detalhes, mas os detalhes devem depender das abstrações.
 *
 * Neste exemplo, vamos incluir uma camada de Repository para separar a lógica de persistência de dados
 * da lógica de negócios.
 */

public class DependencyInversionPrinciple {

    /**
     * Interface de abstração para operações no banco de dados.
     * Qualquer implementação de banco de dados que desejarmos usar no sistema deve implementar essa interface.
     */
    interface Database {
        void connect();
        void disconnect();
        String readData(String query);
        void writeData(String data);
    }

    /**
     * Implementação concreta do banco de dados MySQL.
     * Esta classe implementa a interface Database, fornecendo uma conexão específica ao MySQL.
     */
    static class MySQLDatabase implements Database {
        @Override
        public void connect() {
            System.out.println("Conectando ao banco de dados MySQL...");
        }

        @Override
        public void disconnect() {
            System.out.println("Desconectando do banco de dados MySQL...");
        }

        @Override
        public String readData(String query) {
            // Simulando a leitura de dados do banco de dados
            return "Resultado da consulta: " + query;
        }

        @Override
        public void writeData(String data) {
            // Simulando a escrita de dados no banco de dados
            System.out.println("Escrevendo no banco de dados MySQL: " + data);
        }
    }

    /**
     * Implementação concreta do banco de dados PostgreSQL.
     * Esta classe também implementa a interface Database, mas com uma implementação específica para o PostgreSQL.
     */
    static class PostgreSQLDatabase implements Database {
        @Override
        public void connect() {
            System.out.println("Conectando ao banco de dados PostgreSQL...");
        }

        @Override
        public void disconnect() {
            System.out.println("Desconectando do banco de dados PostgreSQL...");
        }

        @Override
        public String readData(String query) {
            // Simulando a leitura de dados do banco de dados
            return "Resultado da consulta no PostgreSQL: " + query;
        }

        @Override
        public void writeData(String data) {
            // Simulando a escrita de dados no banco de dados
            System.out.println("Escrevendo no banco de dados PostgreSQL: " + data);
        }
    }

    /**
     * Interface do Repository que abstrai a lógica de persistência.
     * A camada de serviço interage apenas com essa interface, sem se preocupar com a implementação do banco de dados.
     */
    interface UserRepository {
        String getUserData(String query);
        void saveUserData(String data);
    }

    /**
     * Implementação concreta de UserRepository que usa a abstração Database para se conectar ao banco de dados.
     */
    static class UserRepositoryImpl implements UserRepository {
        private final Database database;

        public UserRepositoryImpl(Database database) {
            this.database = database;
        }

        @Override
        public String getUserData(String query) {
            database.connect();
            String data = database.readData(query);
            database.disconnect();
            return data;
        }

        @Override
        public void saveUserData(String data) {
            database.connect();
            database.writeData(data);
            database.disconnect();
        }
    }

    /**
     * Classe de serviço que depende da abstração UserRepository para realizar operações no banco de dados.
     * Ela não conhece a implementação concreta do banco de dados, apenas interage com o repositório.
     */
    static class UserService {
        private final UserRepository userRepository;

        /**
         * O construtor recebe a abstração UserRepository, permitindo a injeção de qualquer implementação de repositório.
         */
        public UserService(UserRepository userRepository) {
            this.userRepository = userRepository;
        }

        /**
         * Método para ler dados do banco de dados.
         */
        public String getUserData(String query) {
            return userRepository.getUserData(query);
        }

        /**
         * Método para escrever dados no banco de dados.
         */
        public void saveUserData(String data) {
            userRepository.saveUserData(data);
        }
    }

    /**
     * Método principal que demonstra o uso do DIP com a camada de Repository.
     */
    public static void main(String[] args) {
        // Criando uma conexão com MySQL e utilizando o UserRepository
        Database mySQLDatabase = new MySQLDatabase();
        UserRepository userRepositoryMySQL = new UserRepositoryImpl(mySQLDatabase);
        UserService userServiceMySQL = new UserService(userRepositoryMySQL);

        // Usando os métodos de leitura e escrita do UserService
        System.out.println(userServiceMySQL.getUserData("SELECT * FROM users;")); // Leitura do MySQL
        userServiceMySQL.saveUserData("INSERT INTO users VALUES ('John Doe');"); // Escrita no MySQL

        // Criando uma conexão com PostgreSQL e utilizando o UserRepository
        Database postgreSQLDatabase = new PostgreSQLDatabase();
        UserRepository userRepositoryPostgreSQL = new UserRepositoryImpl(postgreSQLDatabase);
        UserService userServicePostgreSQL = new UserService(userRepositoryPostgreSQL);

        // Usando os métodos de leitura e escrita do UserService com PostgreSQL
        System.out.println(userServicePostgreSQL.getUserData("SELECT * FROM employees;")); // Leitura do PostgreSQL
        userServicePostgreSQL.saveUserData("INSERT INTO employees VALUES ('Jane Doe');"); // Escrita no PostgreSQL
    }
}
