package com.dhenton9000.spring.mvc.config;

import java.time.Instant;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;

import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;

/**
 * class intended to be placed into the security context. It allows an OidcUser
 * to be seen as a standard security user because it implements UserDetails the
 * OidcUser is mapped to this in the userInfoEndpoint in WebSecurityConfig
 *
 * @author dhenton
 */
public class UserHybrid extends DefaultOidcUser implements UserDetails {

    public UserHybrid(Collection<? extends GrantedAuthority> authorities,
            OidcIdToken idToken, OidcUserInfo userInfo, String nameAttributeKey) {
        super(authorities, idToken, userInfo, nameAttributeKey);
    }

    public UserHybrid(Collection<? extends GrantedAuthority> authorities,
            OidcIdToken idToken) {
        super(authorities, idToken);
    }

    public UserHybrid(Collection<? extends GrantedAuthority> authorities,
            OidcIdToken idToken, String nameAttributeKey) {
        super(authorities, idToken, nameAttributeKey);
    }

    public UserHybrid(Collection<? extends GrantedAuthority> authorities,
            OidcIdToken idToken, OidcUserInfo userInfo) {
        super(authorities, idToken, userInfo);
    }

    @Override
    public String getPassword() {
        return null;
         
    }

    @Override
    public String getUsername() {
        return super.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        Instant expireTime = super.getExpiresAt();
        Instant.now();
        return expireTime.compareTo(Instant.now()) > 0;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; //since you got thru login on okta
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; //since you got thru login on okta
    }

    @Override
    public boolean isEnabled() {
        return true; //since you got thru login on okta
    }

}
