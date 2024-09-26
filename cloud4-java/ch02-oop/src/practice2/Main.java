package practice2;

// Getter Setter 예제
public class Main {
    public static void main(String args[]) {
        Archer archer = new Archer("체셔");
        Wizard wizard = new Wizard("도도새");

        System.out.println(archer);
        System.out.println(wizard);

        archer.attack(wizard);
        archer.attack(wizard);
        System.out.println(wizard);
    }
}
