package tools;

import java.sql.SQLException;

public class CreateTable {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String sql="create table bmiinfo(id int not null auto_increment,date varchar(45),height varchar(45)" +
				",weight varchar(45),bmi varchar(45),primary key(id))default charset=utf8";
		DBHelper db=new DBHelper(sql);
		try {
			int update = db.pst.executeUpdate();
			System.out.println(update);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
