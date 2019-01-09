package com.funcas.pboot.module.upms.rest;

import com.funcas.pboot.common.ApiResult;
import com.funcas.pboot.common.base.BaseController;
import com.funcas.pboot.common.util.EncodeUtils;
import com.funcas.pboot.common.util.JacksonUtils;
import com.funcas.pboot.common.util.MatrixToImageUtils;
import com.funcas.pboot.common.util.ServletUtils;
import com.funcas.pboot.conf.SysProps;
import com.funcas.pboot.module.upms.auth.AuthUtils;
import com.funcas.pboot.module.upms.entity.QrLoginMessage;
import com.funcas.pboot.module.upms.entity.User;
import com.funcas.pboot.module.upms.enumeration.QrLoginCode;
import com.funcas.pboot.module.upms.listener.MessageContainer;
import com.funcas.pboot.module.upms.service.IAccountService;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.async.DeferredResult;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Hashtable;
import java.util.Map;

/**
 * @author funcas
 * @version 1.0
 * @date 2018年11月27日
 */
@Slf4j
@RestController
@RequestMapping("/wechat")
public class QrLoginController extends BaseController {

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    MessageContainer<QrLoginMessage> messageContainer;
    @Autowired
    private SysProps sysProps;
    @Autowired
    private IAccountService accountService;

    @RequestMapping("/qrlogin")
    public Object getMessage(String ticket, @RequestParam("_t") long timestamp) throws InterruptedException {
        //设置超时30s,超时返回null
        DeferredResult<QrLoginMessage> result = new DeferredResult<>(27000L, null);
        final Map<String, DeferredResult<QrLoginMessage>> resultMap = messageContainer.getUserMessages();
        resultMap.put(ticket, result);

        result.onCompletion(() -> System.out.println("complete"));
        result.onTimeout(() -> {
            QrLoginMessage qrLoginMessage = new QrLoginMessage();
            qrLoginMessage.setCode(408);
            qrLoginMessage.setTimestamp(System.currentTimeMillis());
            result.setResult(qrLoginMessage);
        });

        return result;
    }

    @RequestMapping("/ticket")
    public ApiResult getTicket(){
        long d = System.currentTimeMillis();
        String ticket = AuthUtils.generateTicket(d, sysProps.getAppid());
        return success(map("ticket", ticket, "_t", d));
    }

//    @RequestMapping("/test-pub")
//    public String pubMessage(String channel, String ticket, int code) {
//        QrLoginMessage qrLoginMessage = new QrLoginMessage();
//        qrLoginMessage.setId(ticket);
//        qrLoginMessage.setCode(code);
//        qrLoginMessage.setTimestamp(System.currentTimeMillis());
//        redisTemplate.convertAndSend(channel, FastJsonUtil.toJson(qrLoginMessage));
//        return "success";
//    }

    @RequestMapping("show-code")
    public void showQrCode(HttpServletRequest request, HttpServletResponse response) {
        ServletOutputStream stream = null;
        response.setContentType("image/png");
        try {
            String ticket = request.getParameter("ticket");
            String timestamp = request.getParameter("_t");
            String content = ServletUtils.getServerUriPrefix(request) + "/wechat/scan?ticket=" + ticket + "&_t=" + timestamp;
            stream = response.getOutputStream();
            QRCodeWriter writer = new QRCodeWriter();
            Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            hints.put(EncodeHintType.MARGIN, 1);
            BitMatrix m = writer.encode(content, BarcodeFormat.QR_CODE, 200, 200, hints);
            MatrixToImageUtils.writeToStream(m, "png", stream);
        } catch (Exception e) {
            log.error(null, e);
        } finally {
            if (stream != null) {
                try {
                    stream.flush();
                    stream.close();
                } catch (IOException e) {
                    log.error(null, e);
                }

            }
        }
    }


    @RequestMapping("/doLogin")
    public ApiResult doLogin(String ticket,String username, Long timestamp){
        if(AuthUtils.verifySign(ticket, timestamp, sysProps.getAppid())){
            User user = accountService.findUserByOpenid(username);
            if(user != null){
                RestTemplate restTemplate = new RestTemplate();
                HttpHeaders headers = new HttpHeaders();
                String auth = "app" + ":" + "app";
                String encodedAuth = EncodeUtils.encodeBase64(auth.getBytes(Charset.forName("UTF-8")));
                String authHeader = "Basic " + encodedAuth;
                headers.set("Authorization", authHeader);
                headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                MultiValueMap<String, String> params= new LinkedMultiValueMap<String, String>();
                params.add("username", username);
                params.add("password", ticket);
                params.add("grant_type", "password");
                params.add("auth_type", "wx_scan");
                HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(params, headers);

                OAuth2AccessToken ret = restTemplate.postForObject("http://localhost:9080/api/oauth/token", requestEntity, OAuth2AccessToken.class);

                AuthUtils.sendMessage(ticket, QrLoginCode.SUCCESS.getValue(), JacksonUtils.obj2json(ret));
                AuthUtils.removeSign(ticket);
                return success(ret);
            }else{
                return ApiResult.builder().retCode("400").retMessage("未绑定").build();
            }
        }
        return failure("非法请求");

    }
}
