package package1_Mysql;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
public class MysqlCon{
    private static Connection conn;
    public MysqlCon(String url,String user, String password){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public void closeConnection() throws SQLException {
        conn.close();
    }
    public static void list0fOperations(){
        System.out.println("choose an operation : ");
        System.out.println("    1 -> display all orders.");
        System.out.println("    2 -> display all products.");
        System.out.println("    3 -> create a product.");
        System.out.println("    4 -> create an order.");
        System.out.println("    5 -> QUITE.");
    }
    public  void display_all_orders() throws SQLException {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from orders;");
            System.out.println("ID | owner");
            System.out.println("---+-------------+");
            while (rs.next())
                System.out.println(rs.getInt(1) + "  | " + rs.getString(2));
            System.out.println("---+--------------+");
    }
    public static void display_all_products() throws SQLException {
                Statement stmt=conn.createStatement();
                ResultSet rs=stmt.executeQuery("select * from product");
                System.out.println("ID  |         NOM         |       prix     ");
                System.out.println("----+---------------------+---------------");
                while(rs.next())
                    System.out.println(rs.getInt(1)+"   | "+ rs.getString(2) + spaces(rs.getString(2)) +"| "+rs.getFloat(3));
                System.out.println("----+---------------------+---------------");
    }
    public static void creat_product() throws SQLException {
        Scanner sc = new Scanner(System.in);
        String productName;
        double productPrice;

        System.out.println("name of the product : ");
        productName = sc.nextLine();
        System.out.println("price of the product : ");
        productPrice = sc.nextDouble();

        Statement stmt=conn.createStatement();
        String qeuryString = "insert into product(name,price) values(\"" + productName + "\"" + "," + Double.toString(productPrice) + ");";
        stmt.executeUpdate(qeuryString);
    }
    public static void creat_order() throws SQLException {
        Scanner sc = new Scanner(System.in);
        String name;
        Integer product_id;
        Integer quantity;

        System.out.println("enter a name : ");
        name = sc.nextLine();
        System.out.println("available products");
        display_all_products();
        System.out.println("choose a product ID :");
        product_id = sc.nextInt();
        System.out.println("enter the quantity : ");
        quantity = sc.nextInt();

        Integer id_command = creat_order(name);

        Statement stmt = conn.createStatement();
        stmt.executeUpdate("insert into order_items(order_id,product_id,quantity) values(" + id_command + ", " + product_id + ", " + quantity + ");");
        System.out.println("added with success !");
    }
    public static int creat_order(String owner) throws SQLException {

        String qr = "insert into orders(owner) values('" + owner + "');";
        int generatedKey = -1;
            PreparedStatement ps = conn.prepareStatement(qr, Statement.RETURN_GENERATED_KEYS);
            ps.execute();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                generatedKey = rs.getInt(1);
            }
            return generatedKey;
    }
    public static String spaces(String input){
        String spaces ="";
        for (int i = 0 ; i < 20 - input.length();i++)
            spaces += " ";
        return spaces;
    }
}  