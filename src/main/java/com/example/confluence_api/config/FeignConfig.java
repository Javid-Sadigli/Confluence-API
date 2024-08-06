package com.example.confluence_api.config;

import java.io.InputStream;
import java.security.KeyStore;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManagerFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.Client;
import feign.auth.BasicAuthRequestInterceptor;

@Configuration
public class FeignConfig 
{
    @Value("${feign.auth.username}")
    private String username;

    @Value("${feign.auth.token}")
    private String token;

    @Bean 
    public BasicAuthRequestInterceptor basicAuthRequestInterceptor() 
    {
        return new BasicAuthRequestInterceptor(username, token);
    }

    @Bean
    public Client feignClient() throws Exception {
        SSLContext sslContext = SSLContext.getInstance("TLS");
        
        // Load the certificate from the resource
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        InputStream certInputStream = getClass().getResourceAsStream("/atlassian.crt");
        X509Certificate caCert = (X509Certificate) cf.generateCertificate(certInputStream);

        // Create a KeyStore containing our trusted certificate
        KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        keyStore.load(null, null);
        keyStore.setCertificateEntry("caCert", caCert);

        // Create a TrustManager that trusts the CAs in our KeyStore
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        tmf.init(keyStore);

        sslContext.init(null, tmf.getTrustManagers(), new java.security.SecureRandom());
        
        return new Client.Default(sslContext.getSocketFactory(), new NoopHostnameVerifier());
    }

    private static class NoopHostnameVerifier implements HostnameVerifier {
        @Override
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }
    
}
