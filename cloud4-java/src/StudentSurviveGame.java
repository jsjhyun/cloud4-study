import java.util.Scanner;

public class StudentSurviveGame {
    public static void main(String[] args) {

        String[] subjects = {"국어", "영어", "수학", "과학", "사회", "예체능"};

        int myHealth = 100;
        int subjectCnt = 0;

        Scanner sc = new Scanner(System.in);
        System.out.println("학생님의 이름을 알려주세요.");
        String name = sc.nextLine();

        System.out.println(name + "님, 이상한 학교에 오신 것을 환영합니다!");
        System.out.println("이제 공부를 시작합니다.");

        while (myHealth > 0 && subjectCnt < 4) {
            int subjectHealth = (int) (Math.random() * 50 + 1);
            int attack = (int) (Math.random() * 30 + 1);
            int damage = (int) (Math.random() * 30 + 1);

            int opponent = (int) (Math.random() * subjects.length);

            System.out.println("===============================");
            System.out.println(subjects[opponent] + "이(가) 나타났습니다.");
            System.out.printf("\t%s의 체력: %d\n", name, myHealth);
            System.out.printf("\t%s의 체력: %d\n", subjects[opponent], subjectHealth);
            System.out.println("===============================");

            while (subjectHealth > 0 && myHealth > 0) {
                System.out.println("액션을 선택해 주세요.");
                System.out.println("\t1. 공부");
                System.out.println("\t2. 도망");

                int action = sc.nextInt();

                if (action == 1) {
                    System.out.printf("> 적에게 %d만큼의 데미지를 입었습니다.\n", damage);
                    System.out.printf("> 적에게 %d만큼의 데미지를 주었습니다.\n", attack);

                    myHealth -= damage;
                    subjectHealth -= attack;

                    if (subjectHealth <= 0) {
                        System.out.printf("%s을(를) 물리쳤습니다.\n", subjects[opponent]);
                        subjectCnt++;
                        System.out.printf("물리친 과목의 수: %d\n", subjectCnt);
                        System.out.printf("현재 남은 체력: %d\n", myHealth);
                        System.out.println("===============================");
                    } else {
                        System.out.printf("\t%s의 체력: %d\n", name, myHealth);
                        System.out.printf("\t%s의 체력: %d\n", subjects[opponent], subjectHealth);
                    }
                } else if (action == 2) {
                    System.out.printf("%s로 부터 도망갑니다!\n", subjects[opponent]);
                    break;
                } else {
                    System.out.println("잘못된 명령입니다.");
                }
            }

            if (myHealth <= 0) {
                System.out.println(name + "님이 기절하였습니다.");
                System.out.println("조퇴 합니다.");
                break;
            }

            if (subjectCnt >= 4) {
                System.out.println("최종 승리");
                System.out.println("학교 수업이 끝났습니다.");
                break;
            }

            System.out.println("계속 공부를 하시겠습니까?");
            System.out.println("1. 공부 계속 하기");
            System.out.println("2. 공부 그만 하기");

            int choose = sc.nextInt();

            if (choose == 2) {
                System.out.println("공부를 종료합니다.");
                System.out.println("집으로 향합니다.");
                break;
            } else if (choose != 1) {
                System.out.println("잘못된 명령입니다.");
            }
        }
    }
}
