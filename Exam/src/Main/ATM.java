package Main;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ATM {
    private Map<String, Double> balance;
    private Map<String, Double> exchangeRates;

    public ATM() {
        balance = new HashMap<>();
        balance.put("USD", 0.00);
        balance.put("CNY", 0.00);
        balance.put("KZT", 0.00);
        balance.put("TRY", 0.00);
        balance.put("RUB", 0.00);

        exchangeRates = new HashMap<>();
        exchangeRates.put("USD", 91.45);
        exchangeRates.put("CNY", 11.91);
        exchangeRates.put("KZT", 0.19);
        exchangeRates.put("TRY", 2.69);
        exchangeRates.put("RUB", 1.00);
    }

    public void deposit(double amount, String currency) {
        if (balance.containsKey(currency)) {
            balance.put(currency, balance.get(currency) + amount);
            balance.put(currency, round(balance.get(currency)));
            System.out.printf("Внесено %.2f %s.%n", amount, currency);
        } else {
            System.out.println("Неверная валюта.");
        }
    }

    public void withdraw(double amount, String currency) {
        if (balance.containsKey(currency)) {
            if (balance.get(currency) >= amount) {
                balance.put(currency, balance.get(currency) - amount);
                balance.put(currency, round(balance.get(currency)));
                System.out.printf("Снято %.2f %s.%n", amount, currency);
            } else {
                System.out.println("Недостаточно средств.");
            }
        } else {
            System.out.println("Неверная валюта.");
        }
    }

    public void exchange(double amount, String fromCurrency, String toCurrency) {
        if (balance.containsKey(fromCurrency) && balance.containsKey(toCurrency)) {
            if (balance.get(fromCurrency) >= amount) {
                balance.put(fromCurrency, balance.get(fromCurrency) - amount);
                balance.put(fromCurrency, round(balance.get(fromCurrency)));
                double exchangedAmount = amount * exchangeRates.get(fromCurrency) / exchangeRates.get(toCurrency);
                balance.put(toCurrency, balance.get(toCurrency) + exchangedAmount);
                balance.put(toCurrency, round(balance.get(toCurrency)));
                System.out.printf("Обменено %.2f %s на %.2f %s.%n", amount, fromCurrency, exchangedAmount, toCurrency);
            } else {
                System.out.println("Недостаточно средств для обмена.");
            }
        } else {
            System.out.println("Неверная валюта.");
        }
    }

    public void showBalance() {
        System.out.println("Баланс:");
        for (Map.Entry<String, Double> entry : balance.entrySet()) {
            System.out.printf("%s: %.2f%n", entry.getKey(), entry.getValue());
        }
    }

    private double round(double value) {
        return Math.round(value * 100.0) / 100.0;
    }

    public static void main(String[] args) {
        ATM atm = new ATM();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Введите операцию (Внести, Снять, Обменять, Баланс, Выход): ");
            String operation = scanner.nextLine().trim().toLowerCase();

            switch (operation) {
                case "внести":
                    try {
                        System.out.print("Введите сумму и валюту (например, 100.50 RUB): ");
                        String[] depositInput = scanner.nextLine().trim().split(" ");
                        double depositAmount = Double.parseDouble(depositInput[0]);
                        String depositCurrency = depositInput[1].toUpperCase();
                        atm.deposit(depositAmount, depositCurrency);
                    } catch (Exception e) {
                        System.out.println("Неверный формат ввода.");
                    }
                    break;
                case "снять":
                    try {
                        System.out.print("Введите сумму и валюту (например, 50.00 RUB): ");
                        String[] withdrawInput = scanner.nextLine().trim().split(" ");
                        double withdrawAmount = Double.parseDouble(withdrawInput[0]);
                        String withdrawCurrency = withdrawInput[1].toUpperCase();
                        atm.withdraw(withdrawAmount, withdrawCurrency);
                    } catch (Exception e) {
                        System.out.println("Неверный формат ввода.");
                    }
                    break;
                case "обменять":
                    try {
                        System.out.print("Введите сумму, валюту для обмена и валюту, на которую меняете (например, 10.00 USD RUB): ");
                        String[] exchangeInput = scanner.nextLine().trim().split(" ");
                        double exchangeAmount = Double.parseDouble(exchangeInput[0]);
                        String fromCurrency = exchangeInput[1].toUpperCase();
                        String toCurrency = exchangeInput[2].toUpperCase();
                        atm.exchange(exchangeAmount, fromCurrency, toCurrency);
                    } catch (Exception e) {
                        System.out.println("Неверный формат ввода.");
                    }
                    break;
                case "баланс":
                    atm.showBalance();
                    break;
                case "выход":
                    System.out.println("Выход из программы.");
                    scanner.close();
                    return;
                default:
                    System.out.println("Неизвестная операция.");
                    break;
            }
        }
    }
}
