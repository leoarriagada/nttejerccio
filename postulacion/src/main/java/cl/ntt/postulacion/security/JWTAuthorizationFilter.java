package cl.ntt.postulacion.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.stream.Collectors;

import cl.ntt.postulacion.utils.UtilsFunctions;

public class JWTAuthorizationFilter  extends OncePerRequestFilter {
    private final String header = "Auth";
    private final String prefix = UtilsFunctions.toSha256("nttpost");

    public JWTAuthorizationFilter() throws NoSuchAlgorithmException {
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(checkJWTToken(request, response)){
            Claims claims = validateToken(request);
            if (claims.get("authorities")!=null){
                setUpSpringAuthentication(claims);
                
            }else{
                SecurityContextHolder.clearContext();
            }
        }else{
            SecurityContextHolder.clearContext();
        }
        filterChain.doFilter(request, response);
    }

    private void setUpSpringAuthentication(Claims claims) {
        List<?> authorities = (List<?>) claims.get("authorities");
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                claims.getSubject(), null,
                authorities.stream().map(role -> new SimpleGrantedAuthority((String) role)).collect(Collectors.toList())
        );
        SecurityContextHolder.getContext().setAuthentication(auth);

    }

    private boolean checkJWTToken(HttpServletRequest request, HttpServletResponse response) {
        String authenticationHeader = request.getHeader(header);
        return authenticationHeader !=null && authenticationHeader.startsWith(prefix);
    }

    private Claims validateToken(HttpServletRequest request) {
        String jwtToken = request.getHeader(header).replace(prefix, "");
        String secret = "clave_post";
        return Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(jwtToken).getBody();
    }
}
