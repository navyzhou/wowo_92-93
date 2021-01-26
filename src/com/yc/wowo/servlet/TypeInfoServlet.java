package com.yc.wowo.servlet;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yc.wowo.bean.TypeInfo;
import com.yc.wowo.biz.ITypeInfoBiz;
import com.yc.wowo.biz.impl.TypeInfoBizImpl;
import com.yc.wowo.util.RequestParamUtil;

/**
 * 商品类型控制
 * company 源辰信息
 * @author navy
 * @date 2020年10月26日
 * Email haijunzhou@hnit.edu.cn
 */
@WebServlet("/type/*")
public class TypeInfoServlet extends BaseServlet {
	private static final long serialVersionUID = 1865483082245657427L;

	public void finds(HttpServletRequest request, HttpServletResponse response) throws IOException {
		ITypeInfoBiz typeInfoBiz = new TypeInfoBizImpl();
		this.send(response, typeInfoBiz.finds());
	}

	public void update(HttpServletRequest request, HttpServletResponse response) throws IOException {
		TypeInfo typeInfo = RequestParamUtil.getParams(TypeInfo.class, request);
		
		// 调用业务模型层，处理
		ITypeInfoBiz typeInfoBiz = new TypeInfoBizImpl();
		int result = typeInfoBiz.update(typeInfo);
		if (result > 0) { // 说明添加成功
			this.send(response, 200, "成功");
			return;
		} 
		this.send(response, 500, "失败");
		return;
	}

	/**
	 * 添加商品类型
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	public void add(HttpServletRequest request, HttpServletResponse response) throws IOException {
		TypeInfo typeInfo = RequestParamUtil.getParams(TypeInfo.class, request);
		
		// 调用业务模型层，处理
		ITypeInfoBiz typeInfoBiz = new TypeInfoBizImpl();
		int result = typeInfoBiz.add(typeInfo);
		if (result > 0) { // 说明添加成功
			this.send(response, 200, "成功");
			return;
		} 
		this.send(response, 500, "失败");
		return;
	}

	public void findByPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int page = Integer.parseInt(request.getParameter("page"));
		int rows = Integer.parseInt(request.getParameter("rows"));
		ITypeInfoBiz typeInfoBiz = new TypeInfoBizImpl();
		this.send(response, typeInfoBiz.findByPage(page, rows));
	}

	/**
	 * 查询所有商品类型信息
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	public void findAll(HttpServletRequest request, HttpServletResponse response) throws IOException {
		ITypeInfoBiz typeInfoBiz = new TypeInfoBizImpl();
		this.send(response, typeInfoBiz.findAll());
	}
}
