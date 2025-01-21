package liskov_substituition;

/**
 * O Princípio da Substituição de Liskov (LSP) é um dos princípios SOLID que afirma que:
 * "Os objetos de uma classe derivada devem ser substituíveis por objetos da classe base
 * sem alterar a correção do programa".
 * Em outras palavras, uma subclasse deve poder ser utilizada no lugar de sua superclasse
 * sem causar efeitos colaterais indesejados ou falhas no comportamento do programa.
 *
 * Este princípio visa garantir que a hierarquia de herança seja lógica e consistente,
 * de forma que qualquer substituição de objetos não afete o comportamento esperado.
 */
public class LiskovSubstitutionPrinciple {

    /**
     * Exemplo 1: Violando o Liskov Substitution Principle
     *
     * Neste exemplo, temos a classe base "Bird" com um método "fly()", que representa o comportamento
     * de voar para todos os pássaros. Porém, quando criamos uma subclasse "Ostrich" (avestruz),
     * que não pode voar, a substituição do comportamento do método "fly()" lança uma exceção, violando
     * o Princípio da Substituição de Liskov.
     */
    static class Bird {
        public void fly() {
            System.out.println("O pássaro está voando");
        }
    }

    static class Ostrich extends Bird {
        @Override
        public void fly() {
            throw new UnsupportedOperationException("O avestruz não pode voar!");
        }
    }

    /**
     * Exemplo 2: Respeitando o Liskov Substitution Principle
     *
     * Agora, vamos refatorar o exemplo para garantir que as subclasses de "Bird" possam ser usadas
     * sem violar o Liskov Substitution Principle. Para isso, ajustamos a classe base "Bird" para ser
     * uma classe abstrata e tornamos o comportamento do método "fly()" opcional para aves que não voam.
     */
    abstract static class BirdBase {
        public abstract void fly();
    }

    static class Sparrow extends BirdBase {
        @Override
        public void fly() {
            System.out.println("O pardal está voando");
        }
    }

    static class OstrichSubstitute extends BirdBase {
        @Override
        public void fly() {
            System.out.println("O avestruz não pode voar");
        }
    }

    /**
     * Método para demonstrar a substituição correta de objetos.
     */
    public static void main(String[] args) {
        // Exemplo de substituição violando o Liskov Substitution Principle
        Bird bird1 = new Bird();
        bird1.fly(); // Saída: "O pássaro está voando"

        // Substituindo por um "Ostrich", que causa uma exceção
        try {
            Bird ostrich = new Ostrich();
            ostrich.fly(); // Lançará uma exceção UnsupportedOperationException
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage()); // Saída: "Erro: O avestruz não pode voar!"
        }

        // Exemplo de substituição respeitando o Liskov Substitution Principle
        BirdBase sparrow = new Sparrow();
        sparrow.fly(); // Saída: "O pardal está voando"

        BirdBase ostrichSubstitute = new OstrichSubstitute();
        ostrichSubstitute.fly(); // Saída: "O avestruz não pode voar"
    }
}
