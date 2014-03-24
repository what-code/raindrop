package com.b5m.raindrop.tao.db.module;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

/**
 * 封装了有关Tao商品的属性
 * @author jacky
 *
 */
public class GoodsBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5542871021225616183L;

	private Integer id;
	
	private String sourceUrl;
	
	private String sourceItem;
	
	private String name;
	
	private Integer categoryId;
	
	private String imgUrls;
	
	private String imgUrl;
	
	private Float sourcePrice;
	
	private Float salesPrice;
	
	private Float discount;
	
	private Integer postal;
	
	private Float expressPrice;
	
	private Integer volume;
	
	private Integer stock;
	
	private Float rebate;
	
	private Character saleStatus;
	
	private Character upStatus;
	
	private Date predict;
	
	private Integer initClick;
	
	private Integer totalClick;
	
	private String spread;
	
	private Timestamp updateTime;
	
	private Integer userId;
	
	private String auditDate;
	
	private Integer modifierUserId;
	
	private String modifiedTime;
	
	private String clickUrl;
	
	private String shopClickUrl;
	
	private Integer checkStatus;
	
	private String b5mImg;
	
	private String sameCategory;
	
	private String sameShop;
	
	private Timestamp addTime;
	
	private Character sourceFrom;
	
	private Integer validity;
	
	private String source;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSourceUrl() {
		return sourceUrl;
	}

	public void setSourceUrl(String sourceUrl) {
		this.sourceUrl = sourceUrl;
	}

	public String getSourceItem() {
		return sourceItem;
	}

	public void setSourceItem(String sourceItem) {
		this.sourceItem = sourceItem;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public String getImgUrls() {
		return imgUrls;
	}

	public void setImgUrls(String imgUrls) {
		this.imgUrls = imgUrls;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public Float getSourcePrice() {
		return sourcePrice;
	}

	public void setSourcePrice(Float sourcePrice) {
		this.sourcePrice = sourcePrice;
	}

	public Float getSalesPrice() {
		return salesPrice;
	}

	public void setSalesPrice(Float salesPrice) {
		this.salesPrice = salesPrice;
	}

	public Float getDiscount() {
		return discount;
	}

	public void setDiscount(Float discount) {
		this.discount = discount;
	}

	public Integer getPostal() {
		return postal;
	}

	public void setPostal(Integer postal) {
		this.postal = postal;
	}

	public Float getExpressPrice() {
		return expressPrice;
	}

	public void setExpressPrice(Float expressPrice) {
		this.expressPrice = expressPrice;
	}

	public Integer getVolume() {
		return volume;
	}

	public void setVolume(Integer volume) {
		this.volume = volume;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public Float getRebate() {
		return rebate;
	}

	public void setRebate(Float rebate) {
		this.rebate = rebate;
	}

	public Character getSaleStatus() {
		return saleStatus;
	}

	public void setSaleStatus(Character saleStatus) {
		this.saleStatus = saleStatus;
	}

	public Character getUpStatus() {
		return upStatus;
	}

	public void setUpStatus(Character upStatus) {
		this.upStatus = upStatus;
	}

	public Date getPredict() {
		return predict;
	}

	public void setPredict(Date predict) {
		this.predict = predict;
	}

	public Integer getInitClick() {
		return initClick;
	}

	public void setInitClick(Integer initClick) {
		this.initClick = initClick;
	}

	public Integer getTotalClick() {
		return totalClick;
	}

	public void setTotalClick(Integer totalClick) {
		this.totalClick = totalClick;
	}

	public String getSpread() {
		return spread;
	}

	public void setSpread(String spread) {
		this.spread = spread;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getAuditDate() {
		return auditDate;
	}

	public void setAuditDate(String auditDate) {
		this.auditDate = auditDate;
	}

	public Integer getModifierUserId() {
		return modifierUserId;
	}

	public void setModifierUserId(Integer modifierUserId) {
		this.modifierUserId = modifierUserId;
	}

	public String getModifiedTime() {
		return modifiedTime;
	}

	public void setModifiedTime(String modifiedTime) {
		this.modifiedTime = modifiedTime;
	}

	public String getClickUrl() {
		return clickUrl;
	}

	public void setClickUrl(String clickUrl) {
		this.clickUrl = clickUrl;
	}

	public String getShopClickUrl() {
		return shopClickUrl;
	}

	public void setShopClickUrl(String shopClickUrl) {
		this.shopClickUrl = shopClickUrl;
	}

	public Integer getCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(Integer checkStatus) {
		this.checkStatus = checkStatus;
	}

	public String getB5mImg() {
		return b5mImg;
	}

	public void setB5mImg(String b5mImg) {
		this.b5mImg = b5mImg;
	}

	public String getSameCategory() {
		return sameCategory;
	}

	public void setSameCategory(String sameCategory) {
		this.sameCategory = sameCategory;
	}

	public String getSameShop() {
		return sameShop;
	}

	public void setSameShop(String sameShop) {
		this.sameShop = sameShop;
	}

	public Timestamp getAddTime() {
		return addTime;
	}

	public void setAddTime(Timestamp addTime) {
		this.addTime = addTime;
	}

	public Character getSourceFrom() {
		return sourceFrom;
	}

	public void setSourceFrom(Character sourceFrom) {
		this.sourceFrom = sourceFrom;
	}

	public Integer getValidity() {
		return validity;
	}

	public void setValidity(Integer validity) {
		this.validity = validity;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}
}
