package todo;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ListServlet
 */
//@WebServlet("/ListServlet")
@WebServlet("/list")
public class ListServlet extends HttpServlet {

	public void doGet(HttpServletRequest request,
		    HttpServletResponse response) throws ServletException,
		    IOException{
		        if(request.getAttribute("message")==null){
		            request.setAttribute("message", "todoを管理しましょう");
		        }
		        String url = "jdbc:mysql://localhost:3306/todo";
		        String user = "root";
		        String password ="password";
		        try{
		            Class.forName("com.mysql.cj.jdbc.Driver");
		        } catch(Exception e){
		            e.printStackTrace();
		        }
		        String sql ="SELECT * FROM posts";
		        try (Connection connection = DriverManager.getConnection
		        (url, user, password);
		        PreparedStatement statement = connection.prepareStatement(sql);
		        ResultSet results = statement.executeQuery()) {
		                ArrayList<HashMap<String,String>> rows =new
		                ArrayList<HashMap<String,String>>();
		                while(results.next()){
		                    HashMap<String,String> columns = new
		                    HashMap<String,String>();
		                    String id=results.getString("id");
		                    columns.put("id", id);
		                    String title=results.getString("title");
		                    columns.put("title", title);
		                    String content=results.getString("content");
		                    columns.put("content", content);
		                    rows.add(columns);
		                }
		                request.setAttribute("rows",rows);
		        }   catch(SQLException e){
		            e.printStackTrace();
		        }
		            catch (Exception e){
		            request.setAttribute("message", "Exception" + e.getMessage());
		        }
		        String view="WEB-INF/view/list.jsp";
		        RequestDispatcher dispatcher= request .getRequestDispatcher(view);
		        dispatcher.forward(request, response);
		    }
}
