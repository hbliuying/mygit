package com.example.demo;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "hbmis")
public class ConfigProperties {

	private CollectDatasource collectDatasource;


	public CollectDatasource getCollectDatasource() {
		return collectDatasource;
	}

	public void setCollectDatasource(CollectDatasource collectDatasource) {
		this.collectDatasource = collectDatasource;
	}



	/**
	 * 数据采集源
	 */
	public static class CollectDatasource {
		private String url;
		private String username;
		private String password;

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		@Override
		public String toString() {
			return ToStringBuilder.reflectionToString(this);
		}
	}


	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
