package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import entity.BMIBean;
import tools.DBHelper;

/**
 * Servlet implementation class BMIServlet
 */
@WebServlet("/BMIServlet")
public class BMIServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private HttpServletRequest request;			//请求码
    private HttpServletResponse response;		//响应码
    private PrintWriter writer;
    
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BMIServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.request=request;
		this.response=response;
		this.request.setCharacterEncoding("utf-8");
		this.response.setCharacterEncoding("utf-8");
		this.response.setContentType("text/html;charset=utf-8");
		//跨域处理
		this.response.setHeader("Access-Control-Allow-Origin", "*");
		writer=this.response.getWriter();
	
		
		String method=this.request.getParameter("method");
		
//		System.out.println("method:"+method);
		
		switch(method){
		case "add_bmi":
			add_bmi();
			break;
		case "start_bmi":	
			writer.print(getBeanData());
			break;
		case "delete_bmi":
			delete_bmi();
			break;
		default:
			
			break;
		}
		
		//清空流
		writer.flush();
	}
	
	private JsonArray getBeanData(){					
		List<BMIBean> list=DBHelper.getBmi();
		Gson gson=new Gson();
		String json=gson.toJson(list);
		System.out.println(json);
		JsonArray array=new JsonParser().parse(json).getAsJsonArray();
		
		
		return array;
		

	}
	

	private void add_bmi() {
		String height=this.request.getParameter("height");
		String weight=this.request.getParameter("weight");
		String dateTime=this.request.getParameter("dateTime");
		String bmi=this.request.getParameter("bmi");
		BMIBean bean=new BMIBean();
		bean.setBmi(bmi);
		bean.setDate(dateTime);
		bean.setHeight(height);
		bean.setWeight(weight);
		
		
		if (DBHelper.addBMI(bean)==true){
			writer.print(getBeanData());		//error,it should add the bean,not the array;
		}
	}

	private void delete_bmi(){
		String id=this.request.getParameter("id");

		if (DBHelper.deleteBMI(id)==true){
			writer.println("delete success");
		}else{
			writer.println("delete error");
		}

	}


}
