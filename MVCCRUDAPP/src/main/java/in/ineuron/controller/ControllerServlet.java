package in.ineuron.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import in.ineuron.dto.Student;
import in.ineuron.service.IStudentService;
import in.ineuron.servicefactory.StudentServiceFactory;

@WebServlet("/controller/*")
public class ControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	} 

	private void doProcess(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		IStudentService studentService = StudentServiceFactory.getStudentService();
		
		System.out.println("Request URI :: " + request.getRequestURI());
		System.out.println("Path info   :: " + request.getPathInfo());

		if (request.getRequestURI().endsWith("addform")) {
			String sname = request.getParameter("sname");
			String sage = request.getParameter("sage"); 
			String smail = request.getParameter("smail");
			String saddress = request.getParameter("saddr");

			Student student = new Student();
			student.setSname(sname); 
			student.setSage(Integer.parseInt(sage));
			student.setSaddress(saddress);

			String status = studentService.addStudent(student);
			RequestDispatcher requestDispatcher = null;

			if (status.equalsIgnoreCase("success")) {
				request.setAttribute("status", "success");
				requestDispatcher = request.getRequestDispatcher("/insertResult.jsp");
				requestDispatcher.forward(request, response);
			} else {
				request.setAttribute("status", "failure");
				requestDispatcher = request.getRequestDispatcher("/insertResult.jsp");
				requestDispatcher.forward(request, response);
			}
		}

		if (request.getRequestURI().endsWith("searchform")) {
			String sid = request.getParameter("sid");

			Student student = studentService.searchStudent(Integer.parseInt(sid));
			request.setAttribute("student", student);
			
			RequestDispatcher requestDispatcher = null;
			requestDispatcher = request.getRequestDispatcher("/display.jsp"); 
			requestDispatcher.forward(request, response);
		} 

		if (request.getRequestURI().endsWith("deleteform")) {
			String sid = request.getParameter("sid");
			String status = studentService.deleteStudent(Integer.parseInt(sid));
			RequestDispatcher requestDispatcher = null;

			if (status.equalsIgnoreCase("success")) {
				request.setAttribute("status", "success");
				requestDispatcher = request.getRequestDispatcher("/deleteResult.jsp");
				requestDispatcher.forward(request, response);
			} else if(status.equalsIgnoreCase("failure")) {
				request.setAttribute("status", "failure");
				requestDispatcher = request.getRequestDispatcher("/deleteResult.jsp");
				requestDispatcher.forward(request, response);
			} else {
				request.setAttribute("status", "not found");
				requestDispatcher = request.getRequestDispatcher("/deleteResult.jsp");
				requestDispatcher.forward(request, response);
			}
		}
		
		if(request.getRequestURI().endsWith("editform")) {
			String sid = request.getParameter("sid");
			
			Student student = studentService.searchStudent(Integer.parseInt(sid));
			RequestDispatcher requestDispatcher = null;
			
			if(student != null) {
				request.setAttribute("student", student);
				requestDispatcher = request.getRequestDispatcher("/updateForm.jsp"); 
				requestDispatcher.forward(request, response);
			} else {
				request.setAttribute("status", "not found");
				requestDispatcher = request.getRequestDispatcher("/updateForm.jsp"); 
				requestDispatcher.forward(request, response);
				
			}
		}
											 
		if(request.getRequestURI().endsWith("updateRecord")) {
			String sid = request.getParameter("sid");
			String name = request.getParameter("sname");
			String age = request.getParameter("sage");
			String address = request.getParameter("saddress");
			
			Student student = new Student();
			student.setSid(Integer.parseInt(sid));
			student.setSname(name);
			student.setSage(Integer.parseInt(age));
			student.setSaddress(address); 
			
			String status = studentService.updateStudent(student);
			RequestDispatcher requestDispatcher = null;
			if(status.equalsIgnoreCase("success")) {
				request.setAttribute("status", "success");
				requestDispatcher = request.getRequestDispatcher("/updateResult.jsp");
				requestDispatcher.forward(request, response);
			} else {
				request.setAttribute("status", "failure");
				requestDispatcher = request.getRequestDispatcher("/updateResult.jsp");
				requestDispatcher.forward(request, response);
			}
		}

	}
}
