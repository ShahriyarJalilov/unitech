package az.unibank.cn.unitech.util;


import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.Setter;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.text.ParseException;


import static com.nimbusds.jose.JWSAlgorithm.RS256;

@Setter
public class JwtUtil {
    private SignedJWT signedJWT;

    public static JwtUtil parse(String token) {
        try {
            return new JwtUtil().setSignedJWT(
                    SignedJWT.parse(token)
            );
        } catch (ParseException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static JwtUtil of(String claimSetString) {
        try {
            return new JwtUtil().setSignedJWT(
                    new SignedJWT(
                            new JWSHeader(RS256),
                            JWTClaimsSet.parse(claimSetString)
                    )
            );
        } catch (ParseException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public JWTClaimsSet verify(PublicKey publicKey) {
        try {
            if (!signedJWT.verify(new RSASSAVerifier((RSAPublicKey) publicKey))) {
                throw new RuntimeException();
            }
            return signedJWT.getJWTClaimsSet();
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    public SignedJWT withKey(PrivateKey privateKey) {
        try {
            signedJWT.sign(new RSASSASigner(privateKey));
            return signedJWT;
        } catch (JOSEException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
