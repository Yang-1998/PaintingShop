package com.web.action;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.domain.Category;
import com.service.CategoryService;
import com.service.impl.CategoryServiceImpl;

/**
 * Servlet implementation class CategoryServlet
 */
@WebServlet("/CategoryServlet")
public class CategoryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CategoryServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String method = request.getParameter("method");
		if("findAll".equals(method)) {
			findAll(request,response);
		}else if("saveUI".equals(method)) {
			saveUI(request,response);
		}else if("save".equals(method)) {
			save(request,response);
		}else if("edit".equals(method)) {
			edit(request,response);
		}else if("update".equals(method)) {
			update(request,response);
		}else if("delete".equals(method)) {
			delete(request,response);
		}
	}

	/**
	 * ɾ���б���Ŀ
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	private void delete(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		//���ղ���
		Integer cid = Integer.parseInt(request.getParameter("cid"));
		//����ҵ��㴦������
		CategoryService categoryService = new CategoryServiceImpl();
		categoryService.delete(cid);
		//��תҳ��
		response.sendRedirect(request.getContextPath()+"/CategoryServlet?method=findAll");
		
	}

	/**
	 * ʵ���޸ĺ�����ݱ��浽���ݿ�
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	private void update(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		//���ղ���
		Integer cid = null;
		String cids = request.getParameter("cid");
		if (cids != null && !cids .equals("")){
		cid = Integer.parseInt(cids);
		}
		String cname = request.getParameter("cname");
		String cdesc = request.getParameter("cdesc");
		//����ҵ��㴦������
		Category category = new Category();
		category.setCdesc(cdesc);
		category.setCid(cid);
		category.setCname(cname);
		CategoryService categoryService = new CategoryServiceImpl();
		categoryService.update(category);
		//��תҳ��
		response.sendRedirect(request.getContextPath()+"/CategoryServlet?method=findAll");
	}

	/**
	 * ���б���б༭
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void edit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//���ղ���
		Integer cid = Integer.parseInt(request.getParameter("cid"));
		//����ҵ��㴦������
		Category category = new Category();
		CategoryService categoryService = new CategoryServiceImpl();
		category = categoryService.edit(cid);
		//��תҳ��
		request.setAttribute("category", category);
		request.getRequestDispatcher("/admin/category_update.jsp").forward(request, response);
	}

	/**
	 *�����������б�����ݿ�
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	private void save(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		//���ղ���
		String cname = request.getParameter("cname");
		String cdesc = request.getParameter("cdesc");
		//��װ����
		Category newCategory = new Category();
		newCategory.setCdesc(cdesc);
		newCategory.setCname(cname);
		//����ҵ��㴦������
		CategoryService catServ = new CategoryServiceImpl();
		catServ.save(newCategory);
		//��תҳ��
		response.sendRedirect(request.getContextPath()+"/CategoryServlet?method=findAll");
	}

	/**
	 * ��ӷ���
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void saveUI(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.getRequestDispatcher("/admin/category_add.jsp").forward(request, response);
	}

	/**
	 * ������ʾ���з���
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//���ղ���
		//��װ����
		//����ҵ��㴦������
		CategoryService categoryService = new CategoryServiceImpl();
		List<Category> categoryList= categoryService.findAll();
		System.out.println(categoryList);
		//ҳ����ת
		request.setAttribute("categoryList", categoryList);
		request.getRequestDispatcher("/admin/category_list.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
