package com.yanfan.study_hard_togather.provider;

import com.yanfan.study_hard_togather.datatransor.AccessTokenDTO;
import com.yanfan.study_hard_togather.datatransor.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson2.JSON;
import java.io.IOException;

@Component
public class GithubProvider {
    public String getAccessToken(AccessTokenDTO accessTokenDTO) {
        MediaType mediateType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(mediateType, JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
//
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            System.out.println(string);

            String token = string.split("&")[0].split("=")[1];
            return token;


        } catch (IOException e) {
        }
        return null;
    }
    public  GithubUser getUser(String accessToken) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
//                .url("http://api.github.com/user?access_token="+accessToken)
//                .build();
                .url("https://api.github.com/user?access_token="+accessToken)
                .build();

        try {
            Response   response = client.newCall(request).execute();
            String  string = response.body().string();
            GithubUser githubUser = JSON.parseObject(string, GithubUser.class);
            return githubUser;
        } catch (IOException e) {

        }
        return null;

}
    }



