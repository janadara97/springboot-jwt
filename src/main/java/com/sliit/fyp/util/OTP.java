package com.sliit.fyp.util;

import java.util.concurrent.TimeUnit;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import org.springframework.stereotype.Component;

@Component
public class OTP {

  private static final Integer EXPIRE_MINS = 5;

  private LoadingCache<String, Integer> otpCache;

  public OTP() {
    super();
    otpCache = CacheBuilder.newBuilder().expireAfterWrite(EXPIRE_MINS, TimeUnit.MINUTES)
        .build(new CacheLoader<String, Integer>() {
          public Integer load(String key) {
            return 0;
          }
        });
  }

  public Integer generateOtp(String key) {
    Integer otp = (int) (Math.random() * (9999 - 1000 + 1) + 1000);
    otpCache.put(key, otp);
    return otp;
  }

  public int getOtp(String key) {
    try {
      return otpCache.get(key);
    } catch (Exception e) {
      return 0;
    }
  }

  public void clearOTP(String key) {
    otpCache.invalidate(key);
  }

}
