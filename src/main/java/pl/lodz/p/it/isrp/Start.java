package pl.lodz.p.it.isrp;

import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * Rozwiązanie problemu sortowania tablicy (zawiera błędy które należy usunąć z
 * zastosowanie nadzorowanego wykonania programu (debug)). Diagnostykę należy
 * przeprowadzić z zastosowaniem programu narzędziowego debuggera jdb
 */
public class Start {
    public static void main(String[] args) {
        System.out.println(System.getenv("DB_USERNAME"));
        System.out.println(System.getenv("DB_PASSWORD"));
        System.out.println(System.getenv("DB_CONNECTION_URL"));
        System.out.println(System.getenv("DB_NAME"));

        if (args.length == 0) {
            System.out.println("Brak podanej liczby całkowitej jako argumentu wywołania");
            System.exit(1);
        }
        try (DbManager dbManager = new DbManager()) {
            dbManager.connect();
            SortTabNumbers sortExample = new SortTabNumbers(Integer.parseInt(args[0].trim()));

            System.out.println("Przed sortowaniem: " + sortExample); //niejawne wywołanie metody sortExample.toString()

            sortExample.sort();
            String randomUUID = UUID.randomUUID().toString().replace("-", "");
            String sql = "CREATE TABLE table_" + randomUUID + " (";
            sql += "createdAt TIMESTAMP,";
            for(int i = 0; i < sortExample.getTab().length; i++) {
                sql += "nr" + (i + 1) + " BIGINT";
                if(i != sortExample.getTab().length - 1) {
                    sql += ",";
                }
            }
            sql += ")";
            dbManager.executeSql(sql);

            DateTimeFormatter formatter
                    = DateTimeFormatter.ofPattern(
                    "yyyy-MM-dd HH:mm:ss.SS");
            LocalDateTime now = LocalDateTime.now();
            String dateTimeString = now.format(formatter);
            String sqlInsert = "INSERT INTO table_" + randomUUID + " VALUES ("
                    + "'" + dateTimeString + "'" + ",";
            for(int i = 0; i < sortExample.getTab().length; i++) {
                sqlInsert += sortExample.getTab()[i];
                if(i != sortExample.getTab().length - 1) {
                    sqlInsert += ",";
                }
            }
            sqlInsert += ")";
            dbManager.executeSql(sqlInsert);

            if (sortExample.checkMinOrderSort()) {
                System.out.println("Po sortowaniu: " + sortExample); //niejawne wywołanie metody sortExample.toString()
            }
        } catch (NumberFormatException nfe) {
            System.out.println("Podany argument nie jest liczbą");
            System.exit(2);
        } catch (Throwable ex) {
            System.out.println("Zakończenie programu w wyniku zgłoszenia wyjątku typu " + ex.getClass().getName());
            System.exit(3);
        }
    }
}
