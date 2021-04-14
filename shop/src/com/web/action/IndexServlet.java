package com.web.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.domain.Category;
import com.domain.PageBean;
import com.domain.Product;
import com.service.CategoryService;
import com.service.ProductService;
import com.service.impl.CategoryServiceImpl;
import com.service.impl.ProductServiceImpl;

/**
 * Servlet implementation class IndexServlet
 */
@WebServlet("/IndexServlet")
public class IndexServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public IndexServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//�����ҳ��ͨ��SQL�����Ʋ�ѯ��������mysql�п��Ʒ�ҳ��䣺select * from �� ����limit ����1�����Ŀ�ʼ����Ϊbegin��,����2��ÿҳ��ʾ��¼������Ϊlimit��
		//��������￪ʼ�����ݵ�ǰҳ��page���㣩����1begin=(page-1)*����2limit
		//ǰ̨���̨���ݵ����ݣ���ǰ��ҳ��page
		//��̨��ǰ̨���ݵ����ݣ���ǰ��ҳ��page  ÿҳ��ʾ�ļ�¼��limit ÿҳ��ʾ�����ݵļ��� List<Product> ��ҳ��totalPage �ܼ�¼�� totalCount
				      //����Ҫ�����ݷ�װ��JavaBean�У����浽�����request��session������
		//�õ���ǰҳ��
		int page = 0;
		String currPage = request.getParameter("page");
		if(currPage==null) {
			page = 1;
		}else {
			page = Integer.parseInt(currPage);
		}
		//��ѯ
		//��ѯ����
		CategoryService categoryService = new CategoryServiceImpl();
		List<Category> categoryList = categoryService.findAll();		
		//��ҳ��ѯ��Ʒ����
		ProductService productService =new ProductServiceImpl();
		PageBean<Product> pageBean = productService.findByPage(page);
		System.out.println(pageBean);
		//ҳ����ת
		request.setAttribute("categoryList", categoryList);
		request.setAttribute("pageBean",pageBean);
		request.getRequestDispatcher("/index.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
