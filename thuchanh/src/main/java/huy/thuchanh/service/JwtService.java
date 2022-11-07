package huy.thuchanh.service;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.stereotype.Service;
import java.util.Date;

@Service
public class JwtService {
    public static final String USERNAME="username";
    public static final String SECRET_KEY="111111111111111111111111111111111";
    public static final int EXPIRE_TIME= 840000;
    public String generateTokenLogin(String username){
        String token= null;
        try{
            // tao tai khoan
            JWSSigner signer=new MACSigner(generateShareSecret());
            JWTClaimsSet.Builder builder=new JWTClaimsSet.Builder();
            builder.claim(USERNAME,username);
            builder.expirationTime(generateExpirationDate());
            JWTClaimsSet claimsSet=builder.build();
            SignedJWT signedJWT=new SignedJWT(new JWSHeader(JWSAlgorithm.HS256),claimsSet);
            // ap dung bao ve
            signedJWT.sign(signer);
            token=signedJWT.serialize();
        }catch (Exception e){
            e.printStackTrace();
        }
        return token;
    }
    private JWTClaimsSet getClaimsFromToken(String token){
        JWTClaimsSet claimsSet=null;
        try{
            SignedJWT signedJWT=SignedJWT.parse(token);
            JWSVerifier verifier=new MACVerifier(generateShareSecret());
            if(signedJWT.verify(verifier)){
                claimsSet=signedJWT.getJWTClaimsSet();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return claimsSet;
    }
    private Date generateExpirationDate(){
        return new Date(System.currentTimeMillis()+EXPIRE_TIME);
    }
    private Date getExpirationDataFromToken( String token){
        Date expiration=null;
        JWTClaimsSet claimsSet=getClaimsFromToken(token);
        expiration=claimsSet.getExpirationTime();
        return expiration;
    }
    public String getUsernameFromToken(String token){
        String username=null;
        try{
            JWTClaimsSet claimsSet=getClaimsFromToken(token);
            username =claimsSet.getStringClaim(USERNAME);
        }catch (Exception e){
            e.printStackTrace();
        }
        return username;
    }
    private byte[] generateShareSecret(){
        byte[] sharedSecret=new byte[32];
        sharedSecret = SECRET_KEY.getBytes();
        return sharedSecret;
    }
    private Boolean isTokenExpired(String token){
        Date expiration = getExpirationDataFromToken(token);
        return expiration.before(new Date());
    }
    public Boolean validateTokenLogin(String token){
        if(token==null || token.trim().length()==0){
            return false;
        }
        String username = getUsernameFromToken(token);
        if (username == null || username.isEmpty()){
            return false;
        }
        if(isTokenExpired(token)){
            return false;
        }
        return true;
    }

}
