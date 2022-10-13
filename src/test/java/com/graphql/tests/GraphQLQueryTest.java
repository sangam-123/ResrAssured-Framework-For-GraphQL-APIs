package com.graphql.tests;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.pojos.GraphQLQuery;
import com.qa.pojos.QueryVariable;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class GraphQLQueryTest {

	@Test
	public void getAllFilmsTest() {
		
		//https://swapi-graphql.netlify.app/.netlify/functions/index
		
		RestAssured.baseURI = "https://swapi-graphql.netlify.app";
		String query = "{\"query\":\"\\n{\\n\\tallFilms {\\n  \\tfilms {\\n    \\ttitle\\n  \\t}\\n\\t}\\n}\\n  \"}";
		
		given().log().all()
			.contentType("application/json")
			.body(query)
				.when().log().all()
					.post("/.netlify/functions/index")
						.then().log().all()
							.assertThat()
								.statusCode(200)
									.and()
										.body("data.allFilms.films[0].title", equalTo("A New Hope"));
		
	}
	
	
	@DataProvider
	public Object[][] getQueryData() {
		return new Object[][] {{"10", "akshayapsangi123", "Flutter development"}};  
	}
	
	
	@Test(dataProvider = "getQueryData")
	public void getAllUsersTest(String limit, String name, String title) {
		
		RestAssured.baseURI = "https://hasura.io";
		String query = "{\"query\":\"{\\n  users(limit: "+limit+", where: {name: {_eq: \\\""+name+"\\\"}}) {\\n    id\\n    name\\n    todos(where: {title: {_eq: \\\""+title+"\\\"}}) {\\n      title\\n    }\\n  }\\n}\\n\",\"variables\":null}";
		
		given().log().all()
			.contentType("application/json")
			.header("Authorization", "Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6Ik9FWTJSVGM1UlVOR05qSXhSRUV5TURJNFFUWXdNekZETWtReU1EQXdSVUV4UVVRM05EazFNQSJ9.eyJodHRwczovL2hhc3VyYS5pby9qd3QvY2xhaW1zIjp7IngtaGFzdXJhLWRlZmF1bHQtcm9sZSI6InVzZXIiLCJ4LWhhc3VyYS1hbGxvd2VkLXJvbGVzIjpbInVzZXIiXSwieC1oYXN1cmEtdXNlci1pZCI6ImF1dGgwfDYzMzg1M2U4NjgzNmEyNDQxNDljYjZlMCJ9LCJuaWNrbmFtZSI6InNhbmdzaW5nMTI2IiwibmFtZSI6InNhbmdzaW5nMTI2QGdtYWlsLmNvbSIsInBpY3R1cmUiOiJodHRwczovL3MuZ3JhdmF0YXIuY29tL2F2YXRhci9hOWZjMGQyZDhlOGVjODM4NTZkNTZkYmY0NDMzMWQyOT9zPTQ4MCZyPXBnJmQ9aHR0cHMlM0ElMkYlMkZjZG4uYXV0aDAuY29tJTJGYXZhdGFycyUyRnNhLnBuZyIsInVwZGF0ZWRfYXQiOiIyMDIyLTEwLTA4VDExOjMyOjQ5LjE2MloiLCJpc3MiOiJodHRwczovL2dyYXBocWwtdHV0b3JpYWxzLmF1dGgwLmNvbS8iLCJzdWIiOiJhdXRoMHw2MzM4NTNlODY4MzZhMjQ0MTQ5Y2I2ZTAiLCJhdWQiOiJQMzhxbkZvMWxGQVFKcnprdW4tLXdFenFsalZOR2NXVyIsImlhdCI6MTY2NTMzMDc4MSwiZXhwIjoxNjY1MzY2NzgxLCJhdF9oYXNoIjoiWlFjb2hhR0tjdkdPU0otanNSampHQSIsInNpZCI6InNzNzN5WGtGMXZKdzdfN1V6VFp3SVpvak1Ka0QySFlUIiwibm9uY2UiOiJSejQ3MDVFZTBkRTZwYzJPQ0hDdHpxMU9rVHpvQWltRSJ9.c79cq0tFiEpP1nmFhNnLlN9KHNhC0obHypPCXYHcXVzGiQzo2ycLFITFxzkpSPedrusmOWwQZGLqnKPcEBepjYuk4ryQd8wlquh5CVOnNVJYrVXTmLDPFFp8TQQShw6-Nr9LxlxSvJqWh7rkMJPmjYQCD0y5o1riIcej9ED7zoB2pDn2so6ztpGhL-HWiPW4dVu5qgL8G0E5SSOsxj-WC3ULbzVRw3wkOrxhd2qSXMNXQUmQJfgEuB-Ow_l8lmPjKl4j5DixwhDdKjPGd5xMvqA4vM8I5CjSEOW_KyFkpvxYyamvUBqrMqXzP904BeUul8-mTLL09J_xoR7acklkng")
			
				.body(query)
					.when().log().all()
						.post("/learn/graphql")
							.then().log().all()
								.assertThat()
									.statusCode(200); 
																				
	}
	
	@Test
	public void getAllUsers_WithPojoTest() {
		
		RestAssured.baseURI = "https://hasura.io";
		GraphQLQuery query = new GraphQLQuery();
		
		query.setQuery("query ($limit: Int!, $name: String!) {\r\n"
				+ "  users(limit: $limit, where: {name: {_eq: $name}}) {\r\n"
				+ "    id\r\n"
				+ "    name\r\n"
				+ "  }\r\n"
				+ "}");
		
		QueryVariable variable = new QueryVariable();
		variable.setLimit(10);
		variable.setName("tui.glen");
		
		query.setVariables(variable);
		
		given().log().all()
			.contentType(ContentType.JSON)    
			.header("Authorization", "Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6Ik9FWTJSVGM1UlVOR05qSXhSRUV5TURJNFFUWXdNekZETWtReU1EQXdSVUV4UVVRM05EazFNQSJ9.eyJodHRwczovL2hhc3VyYS5pby9qd3QvY2xhaW1zIjp7IngtaGFzdXJhLWRlZmF1bHQtcm9sZSI6InVzZXIiLCJ4LWhhc3VyYS1hbGxvd2VkLXJvbGVzIjpbInVzZXIiXSwieC1oYXN1cmEtdXNlci1pZCI6ImF1dGgwfDYzMzg1M2U4NjgzNmEyNDQxNDljYjZlMCJ9LCJuaWNrbmFtZSI6InNhbmdzaW5nMTI2IiwibmFtZSI6InNhbmdzaW5nMTI2QGdtYWlsLmNvbSIsInBpY3R1cmUiOiJodHRwczovL3MuZ3JhdmF0YXIuY29tL2F2YXRhci9hOWZjMGQyZDhlOGVjODM4NTZkNTZkYmY0NDMzMWQyOT9zPTQ4MCZyPXBnJmQ9aHR0cHMlM0ElMkYlMkZjZG4uYXV0aDAuY29tJTJGYXZhdGFycyUyRnNhLnBuZyIsInVwZGF0ZWRfYXQiOiIyMDIyLTEwLTEzVDE3OjU4OjQxLjgyNloiLCJpc3MiOiJodHRwczovL2dyYXBocWwtdHV0b3JpYWxzLmF1dGgwLmNvbS8iLCJzdWIiOiJhdXRoMHw2MzM4NTNlODY4MzZhMjQ0MTQ5Y2I2ZTAiLCJhdWQiOiJQMzhxbkZvMWxGQVFKcnprdW4tLXdFenFsalZOR2NXVyIsImlhdCI6MTY2NTY4MzkyMywiZXhwIjoxNjY1NzE5OTIzLCJhdF9oYXNoIjoiNGZqWFFUSXk2ZzdaVmxFblZVUUljUSIsInNpZCI6InBESE5jNUFZWktldThMa2pLb19ZSzdBV1VmQVNENWNsIiwibm9uY2UiOiJ5cDQ0VU1WYmx4eUI3SlgyektiWUlKNUZQVEp-Qng0cSJ9.XncnczlciaT9JFFGMl7VCUEIpdQThoSJS2gRrAfUYnW35x4KSDCCS65SQTjs_LiUHl3zBEnihq0M-jta81CUOIbSk_qiw8UyqD54L68ofRm-6vp1TRk0_MhOatKbZ7gEThh-Ysxjl7nw-GaIwjqqYl4P48kicezwETr0bNk1Vr6lPOutJr_ZxkTQ9SzRF4ydvrExkzJwpcv99pE_7CLAI9DUUNNRPP0jrO8fI6jsrF2mh5cptkA7wt-OClpFlX5PADxxn217QwSjcgwTkBzyOc5FL1Cj1CRpBpSOCYa-EysQPo-k5uXoKzGs7d-qBS2Ig8cSpaOLkPZdi2wl9ErKOg")
			
			.body(query)
		.when().log().all()
			.post("/learn/graphql")
		.then().log().all()
			.assertThat()
				.statusCode(200)
					.and()
						.body("data.users[0].name", equalTo("tui.glen"));
			
	}
	
	
}
