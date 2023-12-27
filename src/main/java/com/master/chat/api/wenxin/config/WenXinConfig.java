package com.master.chat.api.wenxin.config;

import cn.hutool.json.JSONUtil;
import lombok.Data;
import lombok.NoArgsConstructor;
import okhttp3.*;

import java.io.IOException;
import java.time.LocalDate;

@Data
public class WenXinConfig {
    static final OkHttpClient HTTP_CLIENT = new OkHttpClient().newBuilder().build();

    private static String accessToken;
    private static LocalDate accessTokenReceiveDate;
    private static String apiKey;
    private static String secretKey;

    public static String getAccessToken(String apiKey, String secretKey){
        WenXinConfig.apiKey = apiKey;
        WenXinConfig.secretKey = secretKey;
        try {
            return updateAccessTokenFromBaidu();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static String updateAccessTokenFromBaidu() throws IOException {
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "");
        Request request = new Request.Builder()
                .url("https://aip.baidubce.com/oauth/2.0/token?client_id=" + apiKey
                        + "&client_secret=" + secretKey + "&grant_type=client_credentials")
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .build();
        Response response = HTTP_CLIENT.newCall(request).execute();
        AuthResponseVO vo = null;
        if (response.body() != null) {
            vo = JSONUtil.parseObj(response.body().string()).toBean(AuthResponseVO.class);
        }
        if (vo != null) {
            accessToken = vo.access_token;
            accessTokenReceiveDate = LocalDate.now();
        }

        return accessToken;
    }
    public static String refreshAccessToken(){
        if (accessTokenReceiveDate.plusDays(25).isBefore(LocalDate.now())){
            try {
                updateAccessTokenFromBaidu();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return accessToken;
    }

    @Data
    @NoArgsConstructor
    static class AuthResponseVO {
        String refresh_token;
        Long expires_in;
        String session_key;
        String access_token;
        String scope;
        String session_secret;
    }
}
