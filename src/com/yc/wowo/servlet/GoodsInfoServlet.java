package com.yc.wowo.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspFactory;
import javax.servlet.jsp.PageContext;

import com.yc.wowo.bean.GoodsInfo;
import com.yc.wowo.biz.IGoodsInfoBiz;
import com.yc.wowo.biz.impl.GoodsInfoBizImpl;
import com.yc.wowo.util.FileUploadUtil;

@WebServlet("/goods")
public class GoodsInfoServlet extends BaseServlet{
	private static final long serialVersionUID = -655382709996550270L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String op = request.getParameter("op");
		
		switch(op) {
		case "findByPage": findByPage(request, response); break; // 分页查询所有
		case "add": add(request, response); break; // 添加
		case "findCondition": findCondition(request, response); break; // 多条件组合分页查询
		case "findBySid": findByGid(request, response); break;
		case "finds": finds(request, response); break;
		case "findByFirst": findByFirst(request, response); break;
		case "upload": upload(request, response); break;
		case "findByGid": findByGid(request, response); break;
		default: this.error(request, response); break;
		}
	}

	private void findByFirst(HttpServletRequest request, HttpServletResponse response) throws IOException {
		IGoodsInfoBiz goodsInfoBiz = new GoodsInfoBizImpl();
		int page = Integer.parseInt(request.getParameter("page"));
		int rows = Integer.parseInt(request.getParameter("rows"));
		
		// 要返回第一页的数据以及总记录数
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("total", goodsInfoBiz.total());
		map.put("rows", goodsInfoBiz.finds(page, rows));
		this.send(response, map);
	}
	

	private void finds(HttpServletRequest request, HttpServletResponse response) throws IOException {
		IGoodsInfoBiz goodsInfoBiz = new GoodsInfoBizImpl();
		int page = Integer.parseInt(request.getParameter("page"));
		int rows = Integer.parseInt(request.getParameter("rows"));
		this.send(response, goodsInfoBiz.finds(page, rows));
	}

	private void upload(HttpServletRequest request, HttpServletResponse response) throws IOException {
		FileUploadUtil fileUploadUtil = new FileUploadUtil();
		PageContext pageContext = JspFactory.getDefaultFactory().getPageContext(this, request, response, null, true, 8192, true);
		Map<String, Object> result = new HashMap<String, Object>();
		
		try {
			Map<String, String> map = fileUploadUtil.uploads(pageContext);
			
			result.put("filename", "图片");
			result.put("url", "../../" + map.get("upload"));
			result.put("uploaded", 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.send(response, result);
	}


	private void findByGid(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String gid = request.getParameter("gid");
		IGoodsInfoBiz goodsInfoBiz = new GoodsInfoBizImpl();
		GoodsInfo goodsInfo = goodsInfoBiz.findByGid(gid);
		if (goodsInfo == null) {
			this.send(response, 500, null);
			return;
		}
		this.send(response, 200, goodsInfo);
	}

	private void findCondition(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String sid = request.getParameter("sid");
		String gname = request.getParameter("gname");
		String status = request.getParameter("status");
		int page = Integer.parseInt(request.getParameter("page"));
		int rows = Integer.parseInt(request.getParameter("rows"));
		IGoodsInfoBiz goodsInfoBiz = new GoodsInfoBizImpl();
		this.send(response, goodsInfoBiz.findByCondition(sid, gname, status, page, rows));
	}

	private void add(HttpServletRequest request, HttpServletResponse response) {
		FileUploadUtil fileUploadUtil = new FileUploadUtil();
		PageContext pageContext = JspFactory.getDefaultFactory().getPageContext(this, request, response, null, true, 8192, true);
		try {
			GoodsInfo goodsInfo = fileUploadUtil.uploads(GoodsInfo.class, pageContext);
			
			IGoodsInfoBiz goodsInfoBiz = new GoodsInfoBizImpl();
			int result = goodsInfoBiz.add(goodsInfo);
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
		
		IGoodsInfoBiz goodsInfoBiz = new GoodsInfoBizImpl();
		this.send(response, goodsInfoBiz.findByPage(page, rows));
	}
}
