package com.thoughtmechanix.zuulsvr.filters;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.thoughtmechanix.zuulsvr.config.ServiceConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.impl.DefaultClaims;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.xml.bind.DatatypeConverter;

@Component
public class TrackingFilter extends ZuulFilter{
    private static final int      FILTER_ORDER =  1;
    private static final boolean  SHOULD_FILTER=true;
    private static final Logger logger = LoggerFactory.getLogger(TrackingFilter.class);

    @Autowired
    private FilterUtils filterUtils;

    @Autowired
    private ServiceConfig serviceConfig;

    @Override
    public String filterType() {
        return FilterUtils.PRE_FILTER_TYPE;
    }

    @Override
    public int filterOrder() {
        return FILTER_ORDER;
    }

    public boolean shouldFilter() {
        return SHOULD_FILTER;
    }

    private boolean isCorrelationIdPresent(){
      if (filterUtils.getCorrelationId() !=null){
          return true;
      }

      return false;
    }

    private String generateCorrelationId(){
        return java.util.UUID.randomUUID().toString();
    }

    private String getOrganizationId(){

        String result="";
        if (filterUtils.getAuthToken()!=null){

            String authToken = filterUtils.getAuthToken().replace("Bearer ","");
            try {
            	byte[] signing_key = serviceConfig.getJwtSigningKey().getBytes("UTF-8");//proveniente da zuulservice-prod.yml
            	JwtParser parser = Jwts.parser();
            	parser.setSigningKey(signing_key);
            	//parser.parseClaimsJws(authToken);
            	Jwt jwt_token = parser.parse(authToken);//throws ExpiredJwtException, MalformedJwtException, SignatureException, IllegalArgumentException;
            	DefaultClaims body = (DefaultClaims)jwt_token.getBody();
            	String orgid = body.get("organizationId", String.class);
            	result =orgid;
                //result = (String) claims.get("organizationId");
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        return result;
    }

    public Object run() {

        RequestContext ctx = RequestContext.getCurrentContext();

        if (isCorrelationIdPresent()) {
            logger.debug("tmx-correlation-id found in tracking filter: {}. ", filterUtils.getCorrelationId());
        }
        else{
            filterUtils.setCorrelationId(generateCorrelationId());
            logger.debug("tmx-correlation-id generated in tracking filter: {}.", filterUtils.getCorrelationId());
        }
        String orgid =  getOrganizationId();
        System.out.println("The organization id from the token is : " +orgid);
        filterUtils.setOrgId(orgid);
        logger.debug("Processing incoming request for {}.",  ctx.getRequest().getRequestURI());
        return null;
    }
}