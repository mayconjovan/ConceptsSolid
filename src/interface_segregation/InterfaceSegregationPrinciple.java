package interface_segregation;

/**
 * O Princípio da Segregação de Interfaces (ISP) afirma que as interfaces devem ser específicas
 * e direcionadas a necessidades concretas, sem forçar as classes a implementar métodos que não
 * são necessários para elas.
 *
 * Neste exemplo, criamos várias interfaces que segregam comportamentos específicos de aves,
 * permitindo que as classes implementem apenas as interfaces que são relevantes para o seu comportamento.
 */
public class InterfaceSegregationPrinciple {

    /**
     * Interface para comportamentos de voo, para aves que podem voar.
     * Apenas aves que realmente voam devem implementar essa interface.
     */
    interface Flyable {
        void fly();
    }

    /**
     * Interface para comportamentos de natação, para aves que podem nadar.
     * Apenas aves que realmente nadam devem implementar essa interface.
     */
    interface Swimmable {
        void swim();
    }

    /**
     * Interface para comportamentos de caminhada, para aves que podem andar.
     * Apenas aves que realmente andam devem implementar essa interface.
     */
    interface Walkable {
        void walk();
    }

    /**
     * Classe "Sparrow" (pardal) que pode voar e andar. Ela implementa as interfaces Flyable e Walkable.
     */
    static class Sparrow implements Flyable, Walkable {
        @Override
        public void fly() {
            System.out.println("O pardal está voando");
        }

        @Override
        public void walk() {
            System.out.println("O pardal está andando");
        }
    }

    /**
     * Classe "Penguin" (pinguim) que não pode voar, mas pode nadar e andar. Ela implementa as interfaces
     * Swimmable e Walkable, mas não implementa a interface Flyable, pois não é relevante para o comportamento do pinguim.
     */
    static class Penguin implements Swimmable, Walkable {
        @Override
        public void swim() {
            System.out.println("O pinguim está nadando");
        }

        @Override
        public void walk() {
            System.out.println("O pinguim está andando");
        }
    }

    /**
     * Classe "Ostrich" (avestruz) que não pode voar, mas pode andar. Ela implementa apenas a interface Walkable.
     */
    static class Ostrich implements Walkable {
        @Override
        public void walk() {
            System.out.println("O avestruz está andando");
        }
    }

    /**
     * Método para demonstrar a implementação de diferentes comportamentos para diferentes tipos de aves
     */
    public static void main(String[] args) {
        // Criando um pardal que pode voar e andar
        Sparrow sparrow = new Sparrow();
        sparrow.fly();  // Saída: "O pardal está voando"
        sparrow.walk(); // Saída: "O pardal está andando"

        // Criando um pinguim que pode nadar e andar, mas não voar
        Penguin penguin = new Penguin();
        penguin.swim();  // Saída: "O pinguim está nadando"
        penguin.walk();  // Saída: "O pinguim está andando"

        // Criando um avestruz que pode apenas andar
        Ostrich ostrich = new Ostrich();
        ostrich.walk();  // Saída: "O avestruz está andando"
    }
}
