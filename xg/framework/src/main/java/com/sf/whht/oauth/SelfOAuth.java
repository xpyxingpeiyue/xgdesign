package com.sf.whht.oauth;

public interface SelfOAuth extends OAuth {
    boolean login(String username, String password);
}
