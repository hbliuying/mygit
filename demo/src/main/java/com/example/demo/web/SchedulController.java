package com.example.demo.web;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.ConfigProperties;
import com.example.demo.ConfigProperties.CollectDatasource;
import com.example.demo.domain.User;
import com.example.demo.entity.UserEntity;
import com.example.demo.repository.UserRepository;

@RestController
public class SchedulController {
	@Autowired
	private UserRepository repository;
	@Autowired
	ConfigProperties configProperties;
	
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
    //@Bean     //声明其为Bean实例  
    //@Primary  //在同样的DataSource中，首先使用被标注的DataSource  
	protected DataSource buildDataSource() {
		DruidDataSource ds = new DruidDataSource();
		CollectDatasource collectDS = configProperties.getCollectDatasource();
		String url = collectDS.getUrl();
		if (StringUtils.isBlank(url)) {
			throw new RuntimeException("未找到配置信息 ：collect-datasource.url");
		}
		ds.setUrl(url);
		ds.setUsername(collectDS.getUsername());
		ds.setPassword(collectDS.getPassword());
		return ds;
	}
	
	protected NamedParameterJdbcTemplate buildJdbcTemplte() {
		if (jdbcTemplate == null) {
			jdbcTemplate = new NamedParameterJdbcTemplate(this.buildDataSource());
		}
		return jdbcTemplate;
	}
	
	@Scheduled(cron="0 18 22 ? * *")
	public void save() {
		System.out.println("************");
		User user = new User();
		UserEntity entity = new UserEntity();
		user.setAge(121);
		user.setName("刘1");
		user.setSex("男1");
		BeanUtils.copyProperties(user, entity);
		this.repository.save(entity);
		
		
	}
	@GetMapping(value="/collect")
   public void collect() throws Exception, InstantiationException, InvocationTargetException, IntrospectionException {
	   logger.info("采集数据任务开始");

		NamedParameterJdbcTemplate template = this.buildJdbcTemplte();
		List<Map<String, Object>> result = null;
		long s = System.currentTimeMillis();
		List<Integer> ids = new ArrayList<>();
		ids.add(1);
		ids.add(2);
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("score", 90);
		parameters.addValue("id", ids);

		try {
			 result = template.queryForList(getSQL(ids), parameters);
			//result = template.query(getSQL(ages), parameters, new MonitroDataMapping());
		} catch (DataAccessException e) {
			logger.error("数据采集异常:", e);
			return;
		}

		logger.info("查询用时{}毫秒", (System.currentTimeMillis() - s));
		s = System.currentTimeMillis();
		logger.info("共有{}条记录", result.size());

		for(Map<String, Object> map :result) {
			Object json = JSONObject.toJSON(map);
			System.out.println(json);
			System.out.println(map.get("id"));
			System.out.println(map.get("name"));
			System.out.println(map.get("subject"));
			System.out.println(map.get("score"));
			
			
		}

		
   }
   
   

	protected String getSQL(List<Integer> id) {
		StringBuilder sb = new StringBuilder("SELECT * FROM tb_user WHERE score=:score ");
		if (CollectionUtils.isNotEmpty(id)) {
			sb.append(" AND id in (:id)");
		}
		sb.append(" LIMIT 0,10000");
		return sb.toString();
	}
	class User1{
		private int id ;
		private String name;
		private String subject;
		private String score;
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getSubject() {
			return subject;
		}
		public void setSubject(String subject) {
			this.subject = subject;
		}
		public String getScore() {
			return score;
		}
		public void setScore(String score) {
			this.score = score;
		}
		@Override
		public String toString() {
			return "User1 [id=" + id + ", name=" + name + ", subject=" + subject + ", score=" + score + "]";
		}
		
	}
}
