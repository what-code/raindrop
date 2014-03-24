package com.b5m.raindrop.cache.memcache;

import java.io.Serializable;


public class Goods implements Serializable{

		/**
	 * 
	 */
	private static final long serialVersionUID = -5254562786129245338L;
		String id = "goodsids";
		String name = "";
		String extra = "asdfdf";
		
		public Goods()
		{
			StringBuilder sb = new StringBuilder();
			//for (int i=0; i<1024; i++)
			{
				sb.append("1234567890");
			}
			name = sb.toString();
		}
		
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
	
		
}
