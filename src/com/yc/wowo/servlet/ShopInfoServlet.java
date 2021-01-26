package com.yc.wowo.servlet;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspFactory;
import javax.servlet.jsp.PageContext;

import com.yc.wowo.bean.ShopInfo;
import com.yc.wowo.biz.IShopInfoBiz;
import com.yc.wowo.biz.impl.ShopInfoBizImpl;
import com.yc.wowo.util.FileUploadUtil;

@WebServlet("/shop/*")
public class ShopInfoServlet extends BaseServlet{
	private static final long serialVersionUID = -655382709996550270L;
	
	public void finds(HttpServletRequest request, HttpServletResponse response) throws IOException {
		IShopInfoBiz shopInfoBiz = new ShopInfoBizImpl();
		this.send(response, shopInfoBiz.finds());
	}

	public void findBySid(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String sid = request.getParameter("sid");
		IShopInfoBiz shopInfoBiz = new ShopInfoBizImpl();
		ShopInfo shopInfo = shopInfoBiz.findBySid(sid);
		if (shopInfo == null) {
			this.send(response, 400, null);
			return;
		}
		this.send(response, 200, shopInfo);
	}

	public void findCondition(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String tid = request.getParameter("tid");
		String sname = request.getParameter("sname");
		String status = request.getParameter("status");
		int page = Integer.parseInt(request.getParameter("page"));
		int rows = Integer.parseInt(request.getParameter("rows"));
		IShopInfoBiz shopInfoBiz = new ShopInfoBizImpl();
		this.send(response, shopInfoBiz.findByCondition(tid, sname, status, page, rows));
	}

	public void add(HttpServletRequest request, HttpServletResponse response) {
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

	public void findByPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int page = Integer.parseInt(request.getParameter("page"));
		int rows = Integer.parseInt(request.getParameter("rows"));
		
		IShopInfoBiz shopInfoBiz = new ShopInfoBizImpl();
		this.send(response, shopInfoBiz.findByPage(page, rows));
	}
}
