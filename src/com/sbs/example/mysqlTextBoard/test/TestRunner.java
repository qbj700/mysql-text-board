package com.sbs.example.mysqlTextBoard.test;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbs.example.mysqlTextBoard.Container;
import com.sbs.example.mysqlTextBoard.apidto.DisqusApiDataListThread;
import com.sbs.example.mysqlTextBoard.util.Util;

public class TestRunner {
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class TestDataType1 {
		public int age;
		public String name;
		public int height;
	}

	public void run() {
		testGoogleCredentials();
	}

	private void testGoogleCredentials() {
		String keyFilePath = System.getenv("GOOGLE_APPLICATION_CREDENTIALS");
		System.out.println(keyFilePath);
	}

	private void testJackson() {
		String jsonStirng = "{\"age\":22, \"name\":\"홍길동\"}";

		ObjectMapper ob = new ObjectMapper();
		Map rs = null;

		try {
			rs = ob.readValue(jsonStirng, Map.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return;
		}

		System.out.println(rs.get("age"));
	}

	private void testJackson2() {
		String jsonStirng = "1";

		ObjectMapper ob = new ObjectMapper();
		Integer rs = null;

		try {
			rs = ob.readValue(jsonStirng, Integer.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return;
		}

		System.out.println(rs);

	}

	private void testJackson3() {
		String jsonStirng = "[1, 2, 3]";

		ObjectMapper ob = new ObjectMapper();
		List<Integer> rs = null;

		try {
			rs = ob.readValue(jsonStirng, List.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return;
		}

		System.out.println(rs.get(1));

	}

	private void testJackson4() {
		String jsonStirng = "[{\"age\":22, \"name\":\"홍길동\"}, {\"age\":23, \"name\":\"홍길순\"}, {\"age\":24, \"name\":\"임꺽정\"}]";

		ObjectMapper ob = new ObjectMapper();
		List<Map<String, Object>> rs = null;

		try {
			rs = ob.readValue(jsonStirng, List.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return;
		}

		System.out.println(rs.get(2).get("age"));

	}

	private void testJackson5() {
		String jsonStirng = "[{\"age\":22, \"name\":\"홍길동\"}, {\"age\":23, \"name\":\"홍길순\", \"height\":180}, {\"age\":24, \"name\":\"임꺽정\", \"height\":178}]";

		ObjectMapper ob = new ObjectMapper();
		List<TestDataType1> rs = null;

		try {
			rs = ob.readValue(jsonStirng, new TypeReference<List<TestDataType1>>() {
			});
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return;
		}

		System.out.println(rs.get(1).height + rs.get(2).height);

	}

	private void testApi() {
		String url = "https://disqus.com/api/3.0/forums/listThreads.json";
		String rs = Util.callApi(url, "api_key=" + Container.config.getDisqusApiKey(), "forum=chs-ssg",
				"thread:ident=article_detail_2.html");
		System.out.println(rs);
	}

	private void testApi2() {
		String url = "https://disqus.com/api/3.0/forums/listThreads.json";
		Map<String, Object> rs = Util.callApiResponseToMap(url, "api_key=" + Container.config.getDisqusApiKey(),
				"forum=chs-ssg", "thread:ident=article_detail_2.html");
		List<Map<String, Object>> response = (List<Map<String, Object>>) rs.get("response");
		Map<String, Object> thread = response.get(0);
		System.out.println((int) thread.get("likes"));
	}

	private void testApi3() {
		String url = "https://disqus.com/api/3.0/forums/listThreads.json";
		DisqusApiDataListThread rs = (DisqusApiDataListThread) Util.callApiResponseTo(DisqusApiDataListThread.class,
				url, "api_key=" + Container.config.getDisqusApiKey(), "forum=chs-ssg",
				"thread:ident=article_detail_2.html");
		System.out.println(rs.response.get(0).likes + rs.response.get(0).posts);
	}

}
