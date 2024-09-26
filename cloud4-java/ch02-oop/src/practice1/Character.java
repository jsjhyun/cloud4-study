package practice1;

import java.util.ArrayList;

public class Character {
    private String name;
    private String nickname;
    private int HP;
    private int AP;
    private ArrayList<String> inventory = new ArrayList<String>();

    // 생성자 메서드
    public Character(String name) {
        this(name, "별명 없음", 10);
    }

    public Character(String name, int AP) {
        this(name, "별명 없음", AP);
    }

    public Character(String name, String nickname) {
        this(name, nickname, 10);
    }

    public Character(String name, String nickname, int AP) {
        this.name = name;
        this.nickname = nickname;
        this.HP = 100;
        this.AP = AP;
    }

    // getter 메서드
    public String getName() { return name; }
    public String getNickname() { return nickname; }
    public int getHP() { return HP; }
    public int getAP() { return AP; }

    // setter 메서드
    public void setName(String name) { this.name = name; }
    public void setNickname(String nickname) { this.nickname = nickname; }

    public void setHP(int HP) {
        if(HP < 0) HP = 0;
        this.HP = HP;
    }

    public void setAP(int AP) {
        if(AP < 0) AP = 0;
        this.AP = AP;
    }

    // 일반 메서드
    public void printDashboard() {
        System.out.println("====================");
        System.out.println("이름: " + name);
        System.out.println("별명: " + nickname);
        System.out.println("체력: " + HP);
        System.out.println("공격력: " + AP);
        System.out.print("인벤토리: ");
        for(String item: inventory) System.out.print(item + " ");
        System.out.println("\n====================");
    }

    protected boolean isInventoryEmpty() {
        return inventory.size() == 0;
    }

    public void attack(Character target) {
        target.setHP(target.getHP() - AP);
        System.out.println(name + "이(가) " + target.getName() + "을(를) 일반 공격으로 " + AP + "만큼 데미지를 입혔습니다!");
    }

    protected String getNthItemFromInventory(int n) {
        return inventory.get(n);
    }

    void increaseStat() {
        this.HP += 10;
        System.out.println(name + "의 HP가 10만큼 증가했습니다.");
    }

    void increaseStat(int amount) {
        this.HP += amount;
        System.out.println(name + "의 HP가 " + amount + "만큼 증가했습니다.");
    }

    void increaseStat(int amount, String stat) {
        if(stat.equals("HP")) {
            this.HP += amount;
            System.out.println(name + "의 HP가 " + amount + "만큼 증가했습니다.");
        }
        else if(stat.equals("AP")) {
            this.AP += amount;
            System.out.println(name + "의 AP가 " + amount + "만큼 증가했습니다.");
        }
        else {
            System.out.println("유효하지 않은 스탯입니다.");
        }
    }

    public void addItem(String item) {
        inventory.add(item);
        System.out.println(name + "이(가) " + item + " 아이템을 얻었습니다!");
    }

    public void giveItem(Character receiver, String item) {
        inventory.remove(item);
        receiver.addItem(item);
        System.out.println(name + "이(가) " + receiver.name + "에게 " + item + " 아이템을 주었습니다!");
    }

    public void useItem(String item) {
        inventory.remove(inventory.indexOf(item));
        System.out.println(name + "이(가) " + item + " 아이템을 사용했습니다!");
    }
}
