package single_responsibility;

/**
 * @author Maycon
 * <p>
 * Uma classe, método ou função deve ter uma e **somente uma razão para mudar**.
 * Deve ser especializada em um único assunto e possuir apenas uma responsabilidade.
 */
public class SingleResponsibilityPrinciple {

    /**
     * Exemplo:
     * Uma classe repository deve ter a única responsabilidade de fazer o meio de campo entre as classes
     * de serviço e o banco de dados. Assim como um método deve ser responsável apenas por uma única função,
     * como calcular o salário de um funcionário.
     *
     * Este princípio incentiva a **quebra de métodos e classes** para que façam apenas o que foram projetados para fazer.
     */
    static class EmployerService {

        private final Repository repository;

        public EmployerService(Repository repository) {
            this.repository = repository;
        }

        /**
         * Exemplo de método **errado**.
         * Este método viola o princípio da responsabilidade única porque:
         * 1. Ele busca o empregado no banco de dados (responsabilidade do repositório).
         * 2. Calcula o valor final (responsabilidade do serviço de cálculo).
         * 3. Busca os descontos no banco de dados (responsabilidade do repositório).
         */
        public double calculateIncomeWrong(Long employerRegistryNumber, int totalHours) {
            var employer = repository.getEmployer(employerRegistryNumber);
            var discounts = repository.getEmployerDiscounts(employer.getRegistryNumber());
            return employer.getValueHour() * totalHours - discounts;
        }

        /**
         * Exemplo de método **correto**.
         * Este método tem **responsabilidade única** de calcular o valor total baseado nos parâmetros fornecidos.
         */
        public double calculateIncomeCorrect(double valueHour, int totalHours, double discounts) {
            return valueHour * totalHours - discounts;
        }

        /**
         * Neste método, delegamos o cálculo ao método correto, e o foco aqui é apenas buscar
         * os dados necessários e organizar a lógica.
         */
        public double calculateEmployerIncome(Long employerRegistryNumber, int totalHours) {
            var employer = repository.getEmployer(employerRegistryNumber);
            var discounts = repository.getEmployerDiscounts(employerRegistryNumber);
            return calculateIncomeCorrect(employer.getValueHour(), totalHours, discounts);
        }
    }

    /**
     * Este exemplo é de uma classe que **viola o SRP** ao acumular responsabilidades desconexas.
     */
    static class ClassGod {
        public void saveEmployer() {}
        public double calculateSalary() { return 0; }
        public Employee getEmployerDetails() { return null; }
        public double calculateDiscount() { return 0; }
        public void sendMail() {}
        public void printFile() {}
        public void publishRabbitMQMessage() {}
    }

    /**
     * Exemplo de como distribuir as responsabilidades corretamente em diferentes classes.
     */
    class EmployerService {
        public void saveEmployer() {}
        public double calculateSalary() { return 0; }
        public Employee getEmployerDetails() { return null; }
        public double calculateDiscount() { return 0; }
    }

    class FileService {
        public void printFile() {}
    }

    class MailService {
        public void sendMail() {}
    }

    class RabbitMQService {
        public void publishMessage() {}
    }
}

/**
 * Representa o empregado, com dados básicos necessários para o cálculo.
 */
class Employee {
    private String name;
    private Long registryNumber;
    private Double valueHour;

    public Employee(String name, Long registryNumber, Double valueHour) {
        this.name = name;
        this.registryNumber = registryNumber;
        this.valueHour = valueHour;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getRegistryNumber() {
        return registryNumber;
    }

    public void setRegistryNumber(Long registryNumber) {
        this.registryNumber = registryNumber;
    }

    public Double getValueHour() {
        return valueHour;
    }

    public void setValueHour(Double valueHour) {
        this.valueHour = valueHour;
    }
}

/**
 * Responsável por lidar com persistência e recuperação de dados.
 */
class Repository {
    public Employee getEmployer(Long registryNumber) {
        return new Employee("Fulano", registryNumber, 8.0);
    }

    public double getEmployerDiscounts(Long registryNumber) {
        return 50.0;
    }
}
