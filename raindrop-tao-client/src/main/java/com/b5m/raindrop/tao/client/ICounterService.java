package com.b5m.raindrop.tao.client;


/**
 * 提供商品点击量的统计服务
 * @author jacky
 *
 */
public interface ICounterService{

	/**
	 * 统计某个商品的点击量
	 * @param goodsId
	 * @param count
	 */
	public void countGoods(String goodsId, int count, IExceptionCallback callback);
	
	/**
	 * 统计某个商品的一次的点击量
	 * @param goodsId
	 */
	public void clickGoods(String goodsId, IExceptionCallback callback);
	
	/**
	 * 统计某个广告的点击量
	 * @param adsId 广告编号
	 * @param count
	 * @param callback
	 */
	public void countAds(String adsId, int count, IExceptionCallback callback);
	
	/**
	 * 统计某个广告的一次的点击量
	 * @param adsId
	 * @param callback
	 */
	public void clickAds(String adsId, IExceptionCallback callback);
}
