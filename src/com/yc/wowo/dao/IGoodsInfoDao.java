package com.yc.wowo.dao;

import java.util.List;

import com.yc.wowo.bean.GoodsInfo;

public interface IGoodsInfoDao {
	/**
	 * 后端分页查询
	 * @param page
	 * @param rows
	 * @return
	 */
	public List<GoodsInfo> findByPage(int page, int rows);
	
	/**
	 * 获取总记录数
	 * @ flag 为true查询所有商品总数，为false查询在架的商品总数
	 * @return
	 */
	public int total(boolean flag);
	
	/**
	 * 添加商品信息
	 * @param sp
	 * @return
	 */
	public int add(GoodsInfo gf);
	
	/**
	 * 修改商品信息
	 * @param sp
	 * @return
	 */
	public int update(GoodsInfo gf);
	
	/**
	 * 查询所有在架是商品信息
	 * @return
	 */
	public List<GoodsInfo> finds(int page, int rows);
	
	/**
	 * 根据商品编号查询商品详细
	 * @param gid
	 * @return
	 */
	public GoodsInfo findByGid(String gid);
	
	
	public int total(String sid, String gname, String status);
	
	/**
	 * 多条件组合分页查询
	 * @param sp
	 * @param page
	 * @param rows
	 * @return
	 */
	public List<GoodsInfo> findByCondition(String sid, String gname, String status, int page, int rows);
}
