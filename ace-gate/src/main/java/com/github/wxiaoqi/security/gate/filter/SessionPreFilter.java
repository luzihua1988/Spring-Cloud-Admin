package com.github.wxiaoqi.security.gate.filter;

import com.github.wxiaoqi.security.api.vo.user.UserInfo;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.session.Session;
import org.springframework.session.SessionRepository;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

/**
 * ${DESCRIPTION}
 *
 * @author wanghaobin
 * @create 2017-06-23 8:25
 */
@Component
public class SessionPreFilter extends ZuulFilter {
  @Autowired
  private SessionRepository<?> repository;

  @Override
  public String filterType() {
    return "pre";
  }

  @Override
  public int filterOrder() {
    return 1;
  }

  @Override
  public boolean shouldFilter() {
    return true;
  }

  @Override
  public Object run() {
    RequestContext ctx = RequestContext.getCurrentContext();
    HttpSession httpSession = ctx.getRequest().getSession();
    Session session = repository.getSession(httpSession.getId());
    ctx.addZuulRequestHeader("Cookie", "SESSION=" + session.getId());
    return null;
  }
}
