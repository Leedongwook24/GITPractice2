package todo;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ListServlet
 */
@WebServlet("/update")
public class UpdateServlet extends HttpServlet {

	public void doGet(HttpServletRequest request,
		    HttpServletResponse response) throws ServletException,
		    IOException{
		        if(request.getAttribute("message")==null){
		            request.setAttribute("message", "todoを管理しましょう");
		        }
		        
		        int postId = Integer.parseInt(request.getParameter("id"));
		        String title = request.getParameter("title");
		        String content = request.getParameter("content");
		        
		        String url = "jdbc:mysql://localhost:3306/todo";
		        String user = "root";
		        String password ="password";
		        
		        try{
		            Class.forName("com.mysql.cj.jdbc.Driver");
		        } catch(Exception e){
		            e.printStackTrace();
		        }
		        String sql ="UPDATE posts SET title = ?, content = ? WHERE id = ?";
		        try (Connection connection = DriverManager.getConnection
		        (url, user, password);
		        PreparedStatement statement = connection.prepareStatement(sql);) {
		        	
		        	statement.setString(1, title);
		        	statement.setString(2, content);		        	
		        	statement.setInt(3, postId);
			    int number = statement.executeUpdate();
			    request.setAttribute("message", "Id:"+postId+"の更新に成功しました。");
			        
		        	// id가 1인 DB의 내용을 가져온다.
		        	
		        }   catch(SQLException e){
		            e.printStackTrace();
		        }
		            catch (Exception e){
		            request.setAttribute("message", "Exception" + e.getMessage());
		        }
		        String forward="show?id=" + postId;
		        RequestDispatcher dispatcher= request .getRequestDispatcher(forward);
		        dispatcher.forward(request, response);
		    }
}
