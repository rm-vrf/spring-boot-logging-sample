package com.mydomain.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class DemoService {

	private static final Logger LOG = LoggerFactory.getLogger(DemoService.class);

	@Autowired
	private RestTemplate restTemplate;

	public void baidu() throws InterruptedException {
		String html = restTemplate.getForObject("https://www.baidu.com", String.class);
		LOG.debug("html is {}", html);
	}

}
