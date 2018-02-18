package chapter1;

import java.util.*;

import static java.util.stream.Collectors.groupingBy;

/**
 * Created by simjunbo on 2018-02-11.
 */
public class StreamTest {
    public static class Transaction {
        private final Currency currency;
        private final double value;

        public Transaction(Currency currency, double value) {
            this.currency = currency;
            this.value = value;
        }

        public Currency getCurrency() {
            return currency;
        }

        public double getPrice() {
            return value;
        }

        @Override
        public String toString() {
            return currency + " " + value;
        }
    }

    public enum Currency {
        EUR, USD, JPY, GBP, CHF
    }

    public static List<Transaction> transactions = Arrays.asList( new Transaction(Currency.EUR, 1500.0),
            new Transaction(Currency.USD, 2300.0),
            new Transaction(Currency.GBP, 9900.0),
            new Transaction(Currency.EUR, 1100.0),
            new Transaction(Currency.JPY, 7800.0),
            new Transaction(Currency.CHF, 6700.0),
            new Transaction(Currency.EUR, 5600.0),
            new Transaction(Currency.USD, 4500.0),
            new Transaction(Currency.CHF, 3400.0),
            new Transaction(Currency.GBP, 3200.0),
            new Transaction(Currency.USD, 4600.0),
            new Transaction(Currency.JPY, 5700.0),
            new Transaction(Currency.EUR, 6800.0) );

    public static void main(String[] args) {

        Map<Currency, List<Transaction>> transactionByCurrencies = new HashMap<>();
        for(Transaction transaction : transactions) {
            if(transaction.getPrice() > 1000) {
                Currency currency = transaction.getCurrency();
                List<Transaction> transactionForCurrency = transactionByCurrencies.get(currency);
                if(transactionForCurrency == null) {
                    transactionForCurrency = new ArrayList<>();
                    transactionByCurrencies.put(currency, transactionForCurrency);
                }
                transactionForCurrency.add(transaction);
            }
        }

        Map<Currency, List<Transaction>> transactionByCurrencies2 =
                transactions.stream()
                    .filter((Transaction t) -> t.getPrice() > 1000)
                    .collect(groupingBy(Transaction::getCurrency));
    }


}
