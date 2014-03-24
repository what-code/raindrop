package com.b5m.raindrop.cache.memcache;

import java.io.Serializable;


public class GoodGoods implements Serializable{

		/**
	 * 
	 */
	private static final long serialVersionUID = -8701632650176604302L;
		private String id = "gggg";
		private String name = "";
		private String ext = "111";
		
		public String getExt() {
			return ext;
		}

		public void setExt(String ext) {
			this.ext = ext;
		}

		public GoodGoods()
		{
			StringBuilder sb = new StringBuilder();
			//for (int i=0; i<1024; i++)
			{
				sb.append("123");
			}
			name = sb.toString();
			ext = "111";
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
