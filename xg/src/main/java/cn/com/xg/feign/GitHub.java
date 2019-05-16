package cn.com.xg.feign;

import feign.Headers;
import feign.Param;
import feign.RequestLine;

import java.util.List;

public interface GitHub {
    @RequestLine("GET /repos/{owner}/{repo}/contributors")
    List<Contributor> contributors(@Param("owner") String owner, @Param("repo") String repo);

    @Headers("Content-type: text/xml; charset=utf-8")
    @RequestLine("POST /ws/QueryWaybillInfo")
    String query();
}
