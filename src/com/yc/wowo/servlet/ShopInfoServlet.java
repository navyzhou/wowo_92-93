package com.yc.wowo.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspFactory;
import javax.servlet.jsp.PageContext;

import com.yc.wowo.bean.ShopInfo;
import com.yc.wowo.biz.IShopInfoBiz;
import com.yc.wowo.biz.impl.ShopInfoBizImpl;
import com.yc.wowo.util.FileUploadUtil;

@WebServlet("/shop")
public class ShopInfoServlet extends BaseServlet{
	private static final long serialVersionUID = -655382709996550270L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String op = request.getParameter("op");
		
		switch(op) {
		case "findByPage": findByPage(request, response); break; // 分页查询所有
		case "add": add(request, response); break; // 添加
		case "findCondition": findCondition(request, response); break; // 多条件组合分页查询
		case "findBySid": findBySid(request, response); break; // 根据店铺编号查询店铺信息
		case "finds": finds(request, response); break; // 获取所有可用店铺信息
		default: this.error(request, response); break;
		}
	}

	private void finds(HttpServletRequest request, HttpServletResponse response) throws IOException {
		IShopInfoBiz shopInfoBiz = new ShopInfoBizImpl();
		this.send(response, shopInfoBiz.finds());
	}

	private void findBySid(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String sid = request.getParameter("sid");
		IShopInfoBiz shopInfoBiz = new ShopInfoBizImpl();
		ShopInfo shopInfo = shopInfoBiz.findBySid(sid);
		if (shopInfo == null) {
			this.send(response, 400, null);
			return;
		}
		this.send(response, 200, shopInfo);
	}

	private void findCondition(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String tid = request.getParameter("tid");
		String sname = request.getParameter("sname");
		String status = request.getParameter("status");
		int page = Integer.parseInt(request.getParameter("page"));
		int rows = Integer.parseInt(request.getParameter("rows"));
		IShopInfoBiz shopInfoBiz = new ShopInfoBizImpl();
		this.send(response, shopInfoBiz.findByCondition(tid, sname, status, page, rows));
	}

	private void add(HttpServletRequest request, HttpServletResponse response) {
		FileUploadUtil fileUploadUtil = new FileUploadUtil();
		PageContext pageContext = JspFactory.getDefaultFactory().getPageContext(this, request, response, null, true, 8192, true);
		try {
			ShopInfo shopInfo = fileUploadUtil.uploads(ShopInfo.class, pageContext);
			IShopInfoBiz shopInfoBiz = new ShopInfoBizImpl();
			int result = shopInfoBiz.add(shopInfo);
			if (result > 0) {
				this.send(response, 200, "成功");
				return;
			} 
			this.send(response, 500, "失败");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void findByPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int page = Integer.parseInt(request.getParameter("page"));
		int rows = Integer.parseInt(request.getParameter("rows"));
		
		IShopInfoBiz shopInfoBiz = new ShopInfoBizImpl();
		this.send(response, shopInfoBiz.findByPage(page, rows));
	}
}
