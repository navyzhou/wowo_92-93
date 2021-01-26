package com.yc.wowo.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yc.wowo.bean.CartInfo;
import com.yc.wowo.bean.MemberInfo;
import com.yc.wowo.biz.ICartInfoBiz;
import com.yc.wowo.biz.impl.CartInfoBizimpl;
import com.yc.wowo.util.SessionKeyConstant;

/**
 * 商品类型控制
 * company 源辰信息
 * @author navy
 * @date 2020年10月26日
 * Email haijunzhou@hnit.edu.cn
 */
@WebServlet("/cart/*")
public class CartInfoServlet extends BaseServlet {
	private static final long serialVersionUID = 1865483082245657427L;

	public void del(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String cid = request.getParameter("cid");
		
		ICartInfoBiz cartInfoBiz = new CartInfoBizimpl();
		if (cartInfoBiz.del(cid) > 0) {
			this.send(response, 200, "成功");
			return;
		}
		this.send(response, 500, "失败");
	}

	public void update(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String cid = request.getParameter("cid");
		String num = request.getParameter("num");
		
		ICartInfoBiz cartInfoBiz = new CartInfoBizimpl();
		if (cartInfoBiz.update(cid, num) > 0) {
			this.send(response, 200, "成功");
			return;
		}
		this.send(response, 500, "失败");
	}

	public void add(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Object obj = request.getSession().getAttribute(SessionKeyConstant.MEMBERINFOLOGIN);
		if (obj == null) {
			this.send(response, 501, "未登录");
			return;
		}
		MemberInfo mf = (MemberInfo) obj;
		String gid = request.getParameter("gid");
		String num = request.getParameter("num");
		
		ICartInfoBiz cartInfoBiz = new CartInfoBizimpl();
		if (cartInfoBiz.add(mf.getMid(), gid, num) > 0) {
			this.send(response, 200, "成功");
			return;
		}
		this.send(response, 500, "失败");
	}

	public void findByMid(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Object obj = request.getSession().getAttribute(SessionKeyConstant.MEMBERINFOLOGIN);
		if (obj == null) {
			this.send(response, 501, "未登录");
			return;
		}
		MemberInfo mf = (MemberInfo) obj;
		ICartInfoBiz cartInfoBiz = new CartInfoBizimpl();
		List<CartInfo> list = cartInfoBiz.findByMid(mf.getMid());
		if (list != null && !list.isEmpty()) {
			this.send(response, 200, list);
			return;
		}
		this.send(response, 500, "失败");
	}
}
