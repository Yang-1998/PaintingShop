package com.web.action;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.domain.Category;
import com.domain.Product;
import com.service.CategoryService;
import com.service.ProductService;
import com.service.impl.CategoryServiceImpl;
import com.service.impl.ProductServiceImpl;
import com.utils.UploadUtils;

@WebServlet("/ProductServlet")
public class ProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// ����method�Ĳ���:
		String methodName = request.getParameter("method");
		if("findAll".equals(methodName)){
			findAll(request,response);
		}else if("saveUI".equals(methodName)){
			saveUI(request,response);
		}else if("save".equals(methodName)){
			save(request,response);
		}else if("edit".equals(methodName)){
			edit(request,response);
		}else if("update".equals(methodName)){
			update(request,response);
		}else if("delete".equals(methodName)){
			delete(request,response);
		}
	}
	
	/**
	 * ��̨������Ʒģ�飬ɾ����Ʒ�ķ���
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	private void delete(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// ������ƷID:
		Integer pid = Integer.parseInt(request.getParameter("pid"));
		// ����ҵ��㴦������:
		ProductService productService = new ProductServiceImpl();
		// ��ѯ��Ʒ��Ϣ��
		Product product = productService.findOne(pid);
		String path = product.getPath();
		if(path != null && !"".equals(path)){
			String realPath = this.getServletContext().getRealPath(path);//�õ��ļ�����·��
			System.out.println(realPath);
			File file = new File(realPath);
			if(file.exists()){
				file.delete();//ɾ���ļ�
			}
		}
		productService.delete(pid);
		// ҳ����ת��
		response.sendRedirect(request.getContextPath()+"/ProductServlet?method=findAll");
	}

	/**
	 * ��̨��Ʒ�����޸���Ʒ�ķ���
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	private void update(HttpServletRequest request, HttpServletResponse response) throws IOException {
		System.out.println("ServletUpdateִ��");
		// ��������
		Map<String,String> map = UploadUtils.uploadFile(request);
		// ��װ����
		
		Product product = new Product();
		product.setPid(Integer.parseInt(request.getParameter("pid")));
		product.setPname(map.get("pname"));
		product.setAuthor(map.get("author"));
		product.setPrice(Double.parseDouble(map.get("price")));
		product.setDescription(map.get("description"));
		product.setFilename(map.get("filename"));
		product.setPath(map.get("path"));
		product.getCategory().setCid(Integer.parseInt(map.get("cid")));
		// ��������
		ProductService productService = new ProductServiceImpl();
		productService.update(product);
		//  ҳ����ת
		response.sendRedirect(request.getContextPath()+"/ProductServlet?method=findAll");
	}

	/**
	 * ��̨��Ʒ�����޸���Ʒ�ķ���
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void edit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		
		// ��������:
		Integer pid = Integer.parseInt(request.getParameter("pid"));
		// ����ҵ��㴦������:
		ProductService  productService = new ProductServiceImpl();
		Product product = productService.findOne(pid);
		// System.out.println(product);
		// ��ѯ���з���:
		CategoryService categoryService = new CategoryServiceImpl();
		List<Category> categoryList = categoryService.findAll();
		// ҳ����ת
		request.setAttribute("product", product);
		request.setAttribute("categoryList", categoryList);
		request.getRequestDispatcher("/admin/product_update.jsp").forward(request, response);
	}

	/**
	 * ��̨��Ʒ����������Ʒ�ķ���
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	private void save(HttpServletRequest request, HttpServletResponse response) throws IOException {
		System.out.println("save=============");
		// �ļ��ϴ�
		Map<String,String> map = UploadUtils.uploadFile(request);
		// ��������ɷ�װ
		Product product = new Product();
		product.setPname(map.get("pname"));
		product.setAuthor(map.get("author"));
		product.setPrice(Double.parseDouble(map.get("price")));
		product.setDescription(map.get("description"));
		product.setFilename(map.get("filename"));
		product.setPath(map.get("path"));
		product.getCategory().setCid(Integer.parseInt(map.get("cid")));
		// ��������
		ProductService productService = new ProductServiceImpl();
		productService.save(product);
		// ҳ����ת��
		response.sendRedirect(request.getContextPath()+"/ProductServlet?method=findAll");
	}

	/**
	 * ��Ʒģ�飬��ת�����ҳ��
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void saveUI(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// ��ѯ���з������Ϣ:
		CategoryService categoryService = new CategoryServiceImpl();
		//��������װ��ҵ��㷽�㸴��
		List<Category> list = categoryService.findAll();
		// ҳ����ת
		request.setAttribute("categoryList", list);
		request.getRequestDispatcher("/admin/product_add.jsp").forward(request, response);
	}

	/**
	 * ��Ʒģ�飬��ѯ������Ʒ�ķ���:
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// �����ղ���
		System.out.println("ProductServlet��findAll����ִ����...");
		// ����ҵ��㴦������:
		ProductService productService = new ProductServiceImpl();
		List<Product> list = productService.findAll();
		/*for (Product product : list) {
			System.out.println(product);
		}*/
		// ҳ����ת��
		request.setAttribute("list", list);
		request.getRequestDispatcher("/admin/product_list.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
