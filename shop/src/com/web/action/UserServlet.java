package com.web.action;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.domain.User;
import com.service.UserService;
import com.service.impl.UserServiceImpl;

/**
 * Servlet implementation class UserServlet
 */
@WebServlet("/UserServlet")
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//接收参数
		String methodName = request.getParameter("method");
		//判断传过来的参数
		if("login".equals(methodName)) {
			login(request,response);
		}
		if("logout".equals(methodName)) {
			logout(request,response);
		}
	}

//	UserServlet中的登录的方法
	private void login(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		//接收用户名和密码
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		System.out.println(username +"  "+password);
		//数据的封装
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		//处理数据
		UserService userService = new UserServiceImpl();
		User existUser = userService.login(user);
		//根据处理结果完成页面跳转
		if(existUser==null) {
			//登录失败，重定向至登陆页面
			request.setAttribute("msg","用户名或密码错误");
			try {
				request.getRequestDispatcher("/login.jsp").forward(request, response);
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else {
			//登陆成功，转发请求到category页面
			request.getSession().setAttribute("existUser", existUser);
			try {
				response.sendRedirect(request.getContextPath()+"/CategoryServlet?method=findAll");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
//	UserServlet中的退出登录的方法
	private void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
		//获取session并销毁
		request.getSession().invalidate();
		//重定向到登陆界面
		response.sendRedirect(request.getContextPath()+"/login.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
