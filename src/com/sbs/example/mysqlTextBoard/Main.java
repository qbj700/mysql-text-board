package com.sbs.example.mysqlTextBoard;

import com.sbs.example.mysqlTextBoard.util.Util;

public class Main {

	public static void main(String[] args) {
		testApi();

		// new App().run();
	}

	private static void testApi() {
		String url = "https://disqus.com/api/3.0/forums/listThreads.json";
		String rs = Util.callApi(url, "api_key=JwuisN0miTk9d7pW130RaW3m5IDDVcceKdINNfSxOPyKPyzsY3IAWzzdyyZANXWd",
				"forum=chs-ssg", "thread:ident=article_detail_2.html");
		System.out.println(rs);
	}
}
