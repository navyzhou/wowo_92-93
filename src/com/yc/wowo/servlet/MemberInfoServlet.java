package com.yc.wowo.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yc.wowo.bean.MemberInfo;
import com.yc.wowo.biz.IMemberInfoBiz;
import com.yc.wowo.biz.impl.MemberInfoBizImpl;
import com.yc.wowo.util.RequestParamUtil;
import com.yc.wowo.util.SessionKeyConstant;

/**
 * 商品类型控制
 * company 源辰信息
 * @author navy
 * @date 2020年10月26日
 * Email haijunzhou@hnit.edu.cn
 */
@WebServlet("/member/*")
public class MemberInfoServlet extends BaseServlet {
	private static final long serialVersionUID = 1865483082245657427L;

	public void check(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Object obj = request.getSession().getAttribute(SessionKeyConstant.MEMBERINFOLOGIN);
		if (obj == null) {
			this.send(response, 500, "失败");
			return;
		}
		this.send(response, 200, obj);
	}

	public void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String account = request.getParameter("account");
		String pwd = request.getParameter("pwd");
		
		IMemberInfoBiz memberInfoBiz = new MemberInfoBizImpl();
		MemberInfo mf = memberInfoBiz.login(account, pwd);
		if (mf != null) {
			request.getSession().setAttribute(SessionKeyConstant.MEMBERINFOLOGIN, mf);
			this.send(response, 200, "成功");
			return;
		}
		this.send(response, 500, "失败");
	}

	public void reg(HttpServletRequest request, HttpServletResponse response) throws IOException {
		MemberInfo mf = RequestParamUtil.getParams(MemberInfo.class, request);
		
		String code = String.valueOf(request.getSession().getAttribute(SessionKeyConstant.VERIFICATIONCODE));
		if (!code.equalsIgnoreCase(mf.getRealName())) {
			this.send(response, 501, "验证码错误");
			return;
		}
		
		IMemberInfoBiz memberInfoBiz = new MemberInfoBizImpl();
		if (memberInfoBiz.reg(mf) > 0) {
			this.send(response, 200, "成功");
			return;
		}
		this.send(response, 500, "失败");
	}
}
