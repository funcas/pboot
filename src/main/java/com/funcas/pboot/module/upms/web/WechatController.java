package com.funcas.pboot.module.upms.web;

import com.funcas.pboot.common.exception.ServiceException;
import com.funcas.pboot.common.jwt.JwtUtil;
import com.funcas.pboot.common.util.ServletUtils;
import com.funcas.pboot.conf.SysProps;
import com.funcas.pboot.module.upms.auth.AuthUtils;
import com.funcas.pboot.module.upms.entity.User;
import com.funcas.pboot.module.upms.enumeration.QrLoginCode;
import com.funcas.pboot.module.upms.service.IAccountService;
import com.funcas.pboot.module.util.VariableUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import weixin.popular.api.SnsAPI;
import weixin.popular.bean.sns.SnsToken;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

/**
 * @author funcas
 * @version 1.0
 * @date 2018年11月28日
 */
@Slf4j
@Controller
@RequestMapping("/wechat")
public class WechatController {

    private final SysProps sysProps;
    private final IAccountService accountService;
    private final PasswordEncoder passwordEncoder;
    private final RedisTemplate<String, String> redisTemplate;

    @Autowired
    public WechatController(SysProps sysProps, IAccountService accountService, PasswordEncoder passwordEncoder, RedisTemplate<String, String> redisTemplate) {
        this.sysProps = sysProps;
        this.accountService = accountService;
        this.passwordEncoder = passwordEncoder;
        this.redisTemplate = redisTemplate;
    }

    @RequestMapping(value = "/scan",method = RequestMethod.GET)
    public String scanQrCode(HttpServletRequest request){
        String ticket = request.getParameter("ticket");
        Long timestamp = VariableUtils.cast(request.getParameter("_t"), Long.class);
        String redirectUrl = ServletUtils.getServerUriPrefix(request) + "/wechat/wxlogin?ticket=" + ticket + "&timestamp=" + timestamp;
        String uri = SnsAPI.connectOauth2Authorize(sysProps.getAppid(), redirectUrl, true, "pboot");
        AuthUtils.sendMessage(ticket, QrLoginCode.SCANED.getValue(), null);
        AuthUtils.removeSign(ticket);
        return "redirect:" + uri;
    }

    @RequestMapping("/wxlogin")
    public void wxLogin(String code, String ticket, Model model){
        if(StringUtils.isEmpty(code)){
            throw new ServiceException("请使用微信登陆");
        }
        SnsToken snsToken = SnsAPI.oauth2AccessToken(sysProps.getAppid(), sysProps.getSecret(), code);
        weixin.popular.bean.user.User user = SnsAPI.userinfo(snsToken.getAccess_token(), snsToken.getOpenid(), "zh_CN");
        JwtUtil.sign(ticket, sysProps.getJwtSecurity(), null);
        redisTemplate.opsForHash().put(AuthUtils.KEY_WECHAT_SESSION, ticket, user.getOpenid());
        redisTemplate.expire(AuthUtils.KEY_WECHAT_SESSION,  AuthUtils.EXPIRE_SECONDS, TimeUnit.SECONDS);
        model.addAttribute("ticket", ticket);
    }

    @RequestMapping("/bind")
    public void bind(String ck, Model model){
        model.addAttribute("ck", ck);
    }

    @RequestMapping("/bind-success")
    public void bindSuccess(){}

    @RequestMapping("/login-success")
    public void loginSuccess(){}

    @RequestMapping("/bind-error")
    public void bindError(){}

    @RequestMapping("/login-error")
    public void loginError(){}

    @RequestMapping(value = "/doBind",method = RequestMethod.POST)
    public String bindWechat(String username, String password, String ticket){
        User user = accountService.getUserByUsername(username);
        String openid = redisTemplate.<String,String>opsForHash().get(AuthUtils.KEY_WECHAT_SESSION, ticket);
        if(user != null){
            if(passwordEncoder.matches(password, user.getPassword())){
                User updatableUser = new User();
                updatableUser.setId(user.getId());
                updatableUser.setOpenid(openid);
                accountService.updateOpenidByUserId(updatableUser);
                return "redirect:/wechat/bind-success";
            }
        }
        log.info("绑定失败：用户名密码错误！");
        return "redirect:/wechat/bind-error";
    }


}
