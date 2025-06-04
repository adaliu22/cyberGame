import java.util.*;

public class Main {
    static Random random = new Random();
    static Scanner scanner = new Scanner(System.in);

    static String[] attackScenarios = {
            "Spoofing", "Man-in-the-Middle", "XSS", "SQL Injection (SQLi)", "Denial of Service (DoS)",
            "Social Engineering", "Physical Security Flaw", "Weak Configuration/Permissions",
            "Lack of Awareness", "Insecure Wi-Fi", "Zero Day Exploit", "Phishing"
    };

    static String[] defenseScenarios = {
            "Secure Configuration", "Access Control", "Malware Protection", "Security Training",
            "Building Security", "Network Monitoring", "Security Policies",
            "Firewalls and Advanced Security Devices", "Data Backup"
    };

    static int[] defenseCosts = {10, 15, 20, 12, 22, 16, 11, 25, 14};
    static int[] defenseSecurityBoost = {8, 12, 16, 10, 20, 14, 9, 25, 12};

    static int round = 1;
    static int securityScore = 70;
    static int points = 20;
    static String lastDefense = "";
    static int comboCount = 0;

    public static void main(String[] args) {
        System.out.println("Instructions: Defend your network from attacks and keep security high!");
        System.out.println("Earn points and buy defenses to increase your security score, hit 100% security score to win and go 10% or under to lose");

        while (true) {

            System.out.println("--- Round " + round + " ---");
            System.out.println("Security Score: " + securityScore + "%");
            System.out.println("Points: " + points);


            int attackChance = 30 + round * 3;

            if (attackChance > 85) {
                attackChance = 85;
            }
            boolean attacked = random.nextInt(100) < attackChance;

            if (attacked) {

                String attack = attackScenarios[random.nextInt(attackScenarios.length)];
                int baseDamage = 3;
                double scalingDamage = round * 0.5;
                int lowSecPenalty = (100 - securityScore) / 20;
                double rawDamage = baseDamage + scalingDamage + random.nextInt(4) + lowSecPenalty;

                boolean critical = random.nextInt(100) < 15;

                if (critical) {
                    rawDamage *= 1.5;
                    System.out.println("CRITICAL ATTACK!!!!!");
                }

                int damage = (int) Math.min(rawDamage, 20);

                System.out.println("ATTACK: " + attack + " caused " + damage + "% security loss!!!!!!");
                securityScore -= damage;

                if (damage > 15) {
                    System.out.println("heavy hit! You should prolly upgrade ur defenses");

                }
                else if (damage > 8) {
                    System.out.println("Moderate hit");
                }
                else {
                    System.out.println("Minor damage!!!! Stay vigilant!");
                }
            }
            else {
                System.out.println("No attacks this round");
            }

            if (securityScore <= 15) {
                System.out.println("Security score fell below 15%. System compromised. The game is over.");
                break;
            }

            if (securityScore == 100) {
                System.out.println("CONGRATULATIONS! You hit 100% security score and won the game!!");
                break;
            }

            int earned = (securityScore / 15) + random.nextInt(2);

            if (earned < 1) {
                earned = 1;
            }
            points += earned;
            System.out.println("You earned " + earned + " points this round.");



            System.out.println("Choose a defense to buy:");
            for (int i = 0; i < defenseScenarios.length; i++) {
                System.out.println("  " + (i + 1) + ". " + defenseScenarios[i] + " (Cost: " + defenseCosts[i] + " points, +" + defenseSecurityBoost[i] + " Security)");

            }
            System.out.println("Enter the number of your choice or 0 to skip:");

            int choice = -1;
            while (choice < 0 || choice > defenseScenarios.length) {

                System.out.println("Enter the number of your choice or 0 to skip:");

                if (scanner.hasNextInt()) {
                    choice = scanner.nextInt();
                    scanner.nextLine();

                    if (choice < 0 || choice > defenseScenarios.length) {
                        System.out.println("Invalid input, please enter a number from 0 to " + defenseScenarios.length);
                    }

                }
                else {

                    System.out.println("Invalid input, please enter a valid number.");
                    scanner.nextLine();
                }
            }


            if (choice == 0) {
                System.out.println("No defense purchased ");

            }
            else {

                int cost = defenseCosts[choice - 1];
                int baseBoost = defenseSecurityBoost[choice - 1];

                String defense = defenseScenarios[choice - 1];

                if (points >= cost) {
                    points -= cost;

                    if (defense.equals(lastDefense)) {
                        comboCount++;

                        int comboBonus = (int) (comboCount * baseBoost * 0.25);

                        if (comboBonus > baseBoost) {
                            comboBonus = baseBoost;
                        }

                        int totalBoost = baseBoost + comboBonus;
                        securityScore += totalBoost;
                        if (securityScore > 100) {
                            securityScore = 100;
                        }

                        System.out.println("Defense deployed: " + defense + ". Security increased by " + baseBoost +
                                " plus combo bonus of " + comboBonus + ". Total security is now " + securityScore + ".");
                    } else {
                        comboCount = 0;
                        securityScore += baseBoost;

                        if (securityScore > 100) {
                            securityScore = 100;
                        }

                        System.out.println("Defense deployed: " + defense + ". Security increased by " + baseBoost +
                                ". Total security is now " + securityScore + ".");
                    }

                    lastDefense = defense;
                }
                else {
                    System.out.println("Not enough points ");
                }
            }

            round++;
        }

        System.out.println(" Game Over. You survived " + round + " rounds.");
    }
}
