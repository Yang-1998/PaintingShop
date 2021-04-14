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
		//物理分页（通过SQL语句控制查询条数）：mysql中控制分页语句：select * from 表 ……limit 参数1（从哪开始，设为begin）,参数2（每页显示记录数，设为limit）
		//计算从哪里开始（根据当前页数page计算）参数1begin=(page-1)*参数2limit
		//前台向后台传递的数据：当前的页数page
		//后台向前台传递的数据：当前的页数page  每页显示的记录数limit 每页显示的数据的集合 List<Product> 总页数totalPage 总记录数 totalCount
				      //将需要的数据封装到JavaBean中，保存到域对象（request、session……）
		//得到当前页数
		int page = 0;
		String currPage = request.getParameter("page");
		if(currPage==null) {
			page = 1;
		}else {
			page = Integer.parseInt(currPage);
		}
		//查询
		//查询分类
		CategoryService categoryService = new CategoryServiceImpl();
		List<Category> categoryList = categoryService.findAll();		
		//分页查询商品数据
		ProductService productService =new ProductServiceImpl();
		PageBean<Product> pageBean = productService.findByPage(page);
		System.out.println(pageBean);
		//页面跳转
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
