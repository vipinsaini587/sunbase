package com.loginService.common;

import org.mindrot.jbcrypt.BCrypt;

public interface BcryptEncryption {

    public static String bcryptEncryption(String textPassword){
        return BCrypt.hashpw(textPassword, BCrypt.gensalt());
      }
    public static boolean checkPassword(String userPassword,String dbPassword){
        if (BCrypt.checkpw(userPassword, dbPassword)) {
            System.out.println("Password Matched");
            return true;
        } else {
            return false;
        }

    }


}
