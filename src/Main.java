import java.sql.SQLException;
import java.util.Scanner;
import package1_Mysql.MysqlCon;
public class Main {
    public static void main(String[] args) throws SQLException {

        Integer operation_index;
        Scanner sc = new Scanner(System.in);
        String url = "jdbc:mysql://localhost/tp03";
        String user = "root";
        String password = "";

        MysqlCon conn= new MysqlCon(url,user,password);

        do {

            conn.list0fOperations();
            operation_index = sc.nextInt();

            switch (operation_index) {
                case 1:
                    conn.display_all_orders();
                    break;
                case 2:
                    conn.display_all_products();
                    break;
                case 3:
                    conn.creat_product();
                    break;
                case 4 :
                    conn.creat_order();
                    break;
                default:
                    break;
            }
        }while(operation_index != 5);
        conn.closeConnection();
    }
}