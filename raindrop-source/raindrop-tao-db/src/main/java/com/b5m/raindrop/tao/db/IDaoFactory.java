package com.b5m.raindrop.tao.db;


/**
 * 有关Tao的Dao层的工厂
 * @author jacky
 *
 */
public interface IDaoFactory{

	public IGoodsDao createGoodsDao();
	
	public ITopDao createTopDao();
	
	public IAdsDao createAdsDao();
	
	public void destroyDataSource();
}
