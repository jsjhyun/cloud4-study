package practice2;

class Archer extends Character {
    private int arrowNum = 10;

    Archer(String name) { super(name); }
    Archer(String name, int AP) { super(name, AP); }
    Archer(String name, String nickname) { super(name, nickname); }
    Archer(String name, String nickname, int AP) { super(name, nickname, AP); }

    public int getArrowNum() { return arrowNum; }
    public void setArrowNum(int arrowNum) {
        if(arrowNum < 0) arrowNum = 0;
        this.arrowNum = arrowNum;
    }

    public void attack(Character target) {
        if(arrowNum == 0) {
            System.out.println(super.getName() + "의 화살 개수가 부족하여 공격하지 못했습니다.");
            return;
        }

        super.attack(target);
        setArrowNum(arrowNum - 1);
        System.out.println(super.getName() + "의 화살이 " + arrowNum + "만큼 남아있습니다.");
    }

    public String toString() {
        String result = "";

        result += "<궁수>\n";
        result += super.toString();

        return result;
    }
}
