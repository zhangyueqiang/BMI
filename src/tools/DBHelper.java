package tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entity.BMIBean;

//操作数据库的工具类

public class DBHelper {
//	jdbc:mysql:127.0.0.1:3306/数据库名?userUnicode=true&characterEncoding=utf8
	public static final String url="jdbc:mysql://127.0.0.1:3306/bmi?userUnicode=true&characterEncoding=utf8";
//	用户名
	public static final String user="root";
	
//	密码
	public static final String password="123456";
//	jdbc连接数据库访问数据需要该包的支持
	public static final String name="com.mysql.jdbc.Driver";
	public Connection connection;
	public PreparedStatement pst;
	
	public DBHelper(String sql){
//		ctr+1
		try {
//			使用工具类加载驱动
			Class.forName(name);
			System.out.println("驱动加载成功");
			connection = DriverManager.getConnection(url,user,password);
			System.out.println("成功获取连接");
//			准备可执行的sql语句
            pst = connection.prepareStatement(sql);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("驱动加载失败");
		} catch (SQLException e) {			
			e.printStackTrace();
			System.out.println("获取连接失败");
		}
	}
	
//	关闭数据库连接	
	public void close(){
		if(connection!=null){
			try {
				connection.close();
				pst.close();
			} catch (SQLException e) {			
				e.printStackTrace();
			}finally{
				connection=null;
				pst=null;
			}
		}
		
	}
	
	public static boolean addBMI(BMIBean bean){
		String sql="insert into bmiinfo(date,height,weight,bmi) values(?,?,?,?)";
		DBHelper db=new DBHelper(sql);
		try {
			db.pst.setString(1, bean.getDate());
			db.pst.setString(2, bean.getHeight());
			db.pst.setString(3, bean.getWeight());
			db.pst.setString(4, bean.getBmi());
			int update=db.pst.executeUpdate();
			if (update>0)
				return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			db.close();
		}

		return false;
	}
	
	/*
	 * 获取数据
	 * 
	 */
	public static List<BMIBean> getBmi() {
		String  sql="select * from bmiinfo";
		DBHelper db=new DBHelper(sql);
		List<BMIBean> list=null;
		try {
			ResultSet set=db.pst.executeQuery();
			list=new ArrayList<>();
			while (set.next()){
				BMIBean bean=new BMIBean();
				bean.setBmi(set.getString("bmi"));
				bean.setDate(set.getString("date"));
				bean.setHeight(set.getString("height"));
				bean.setWeight(set.getString("weight"));
				bean.setId(set.getInt("id"));
				list.add(bean);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			db.close();
		}
		return list;
	}


	public static boolean deleteBMI(String id){
		String sql="delete from bmiinfo where id=?";
		DBHelper db=new DBHelper(sql);
		int v=Integer.valueOf(id);
		try {
			db.pst.setInt(1,v);
			int update=db.pst.executeUpdate();
			if (update>0)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			db.close();
		}
		return false;
	}
	
	
}
