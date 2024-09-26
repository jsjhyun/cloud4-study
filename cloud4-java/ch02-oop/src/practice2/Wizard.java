package practice2;

class Wizard extends Character {
    Wizard(String name) { super(name); }
    Wizard(String name, int AP) { super(name, AP); }
    Wizard(String name, String nickname) { super(name, nickname); }
    Wizard(String name, String nickname, int AP) { super(name, nickname, AP); }

    public String toString() {
        String result = "";

        result += "<마법사>\n";
        result += super.toString();

        return result;
    }
}
