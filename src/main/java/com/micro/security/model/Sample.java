package com.micro.security.model;

import org.apache.commons.collections.ListUtils;
import org.springframework.util.AntPathMatcher;

import java.util.Arrays;
import java.util.List;

/**
 * Created by saipkri on 20/02/16.
 */
public class Sample {

    public static void main(String[] args) throws Exception {
        String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzYWlrcmlzIiwiZXhwIjoxNDU2MDMwODQ2LCJ1c2VyIjp7ImlkIjoic2Fpa3JpcyIsImNuIjoiU2FpIEtyaXMiLCJzbiI6IlNhaXAiLCJwb3N0YWxBZGRyZXNzIjoiTm8gNDksIEFsYmFjb3JlIFdheSwgSGF5ZXMsIE1pZGRsZXNleCBVQjMgMkZTIiwidGVsZXBob25lTnVtYmVyIjoiMjU0LTMyMy0xOTIwIiwidGl0bGUiOm51bGwsImZpcnN0TmFtZSI6bnVsbCwibWlkZGxlTmFtZSI6bnVsbCwibGFzdE5hbWUiOm51bGwsImVtYWlsIjpudWxsLCJyb2xlVG9rZW5zIjpudWxsLCJwZXJtaXNzaW9uVG9rZW5zIjpbIlJFQURfQVBQXzEiLCJSRUFEX0FQUF8yIl0sImF0dHJpYnV0ZXMiOnt9fSwiYWNjZXNzUnVsZXMiOlt7ImlkIjoiN2ViYWZiMjMtNTMwMS00NTA0LWI1ZmItNzY1MWYwZmFmMjIxIiwidXJpIjoic3RyaW5nIiwicGVybWlzc2lvblJ1bGUiOnsicmVxdWlyZWRQZXJtaXNzaW9ucyI6WyJzdHJpbmciXSwibWluaW11bU51bWJlck9mUGVybWlzc2lvbnNNYXRjaCI6MH0sInRva2VuRXhwaXJhdGlvblJ1bGUiOnsicGVybWlzc2lvblRva2VuIjoic3RyaW5nIiwiZXhwaXJhdGlvblRpbWVJblNlY29uZHMiOjB9LCJudW1iZXJPZlRyYW5zYWN0aW9uc1J1bGUiOnsicGVybWlzc2lvblRva2VuIjpudWxsLCJtYXhOdW1iZXJPZlRyYW5zYWN0aW9uc0FsbG93ZWQiOjAsInVudGlsVGltZSI6MH19XX0.DDOlaTmNlUvuK7GyjkNSOqZpED3TIOmEXgvG6PzSZwuTjazAQMOcRyNvfpqyOEhLJe1gC6-xFYIeuVdwAKGuYw";
        //Claims body = Jwts.parser().setSigningKey(new SecretKeySpec("s3cret".getBytes(), SignatureAlgorithm.HS512.name())).parseClaimsJws(token).getBody();
        //System.out.println(body);

        String pattern = "/add/**";
        String actual = "/add/jha/sss";

        AntPathMatcher am = new AntPathMatcher("/");
        System.out.println(am.match(pattern, actual));

        List<String> l1 = Arrays.asList("1", "2", "3");
        List<String> l2 = Arrays.asList("1", "4", "5");

        System.out.println(ListUtils.intersection(l1, l2));

        /*
            if(intersection.size > 0 && (minimumMatch || intersection.size == size))
         */
    }
}
