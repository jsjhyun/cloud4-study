package practice1;

// Getter Setter 예제
public class Main {
    public static void main(String args[]) {
        Character ch1 = new Character("헬로빗");
        Character ch2 = new Character("캐터필러", "Caterpillar", 50);

        System.out.println("ch1.getName(): " + ch1.getName());
        ch1.setNickname("Hellobit");
        System.out.println("ch1.getNickname(): " + ch1.getNickname());
        System.out.println("ch1.getHP(): " + ch1.getHP());
        ch1.setAP(30);
        System.out.println("ch1.getAP(): " + ch1.getAP());

        ch1.attack(ch2);

        // getter, setter 메서드 없이 직접 접근 / 수정할 때 오류가 발생합니다. 아래 코드의 주석을 풀고 실행하여 확인해 보세요.
        // System.out.println(ch1.name);
        // ch1.name = "Elice";
    }
}
