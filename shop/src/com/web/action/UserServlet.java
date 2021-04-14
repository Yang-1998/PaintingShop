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
		//���ղ���
		String methodName = request.getParameter("method");
		//�жϴ������Ĳ���
		if("login".equals(methodName)) {
			login(request,response);
		}
		if("logout".equals(methodName)) {
			logout(request,response);
		}
	}

//	UserServlet�еĵ�¼�ķ���
	private void login(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		//�����û���������
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		System.out.println(username +"  "+password);
		//���ݵķ�װ
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		//��������
		UserService userService = new UserServiceImpl();
		User existUser = userService.login(user);
		//���ݴ��������ҳ����ת
		if(existUser==null) {
			//��¼ʧ�ܣ��ض�������½ҳ��
			request.setAttribute("msg","�û������������");
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
			//��½�ɹ���ת������categoryҳ��
			request.getSession().setAttribute("existUser", existUser);
			try {
				response.sendRedirect(request.getContextPath()+"/CategoryServlet?method=findAll");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
//	UserServlet�е��˳���¼�ķ���
	private void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
		//��ȡsession������
		request.getSession().invalidate();
		//�ض��򵽵�½����
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
