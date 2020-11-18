package com.yc.wowo.biz.impl;

import com.yc.wowo.bean.MemberInfo;
import com.yc.wowo.biz.IMemberInfoBiz;
import com.yc.wowo.dao.IMemberInfoDao;
import com.yc.wowo.dao.impl.MemberInfoDaoImpl;
import com.yc.wowo.util.StringUtil;

public class MemberInfoBizImpl implements IMemberInfoBiz{

	@Override
	public int reg(MemberInfo mf) {
		if (StringUtil.checkNull(mf.getNickName(), mf.getEmail(), mf.getPwd())) {
			return -1;
		}
		IMemberInfoDao memberInfo = new MemberInfoDaoImpl();
		return memberInfo.reg(mf);
	}

	@Override
	public MemberInfo login(String account, String pwd) {
		if (StringUtil.checkNull(account, pwd)) {
			return null;
		}
		IMemberInfoDao memberInfo = new MemberInfoDaoImpl();
		return memberInfo.login(account, pwd);
	}
}
