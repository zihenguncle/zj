package jx.com.shoppingtrolley_zihenguncle.tool;

import android.widget.Toast;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jx.com.shoppingtrolley_zihenguncle.app.MyApplication;

import static java.util.regex.Pattern.compile;

public class VerifyUtils {

    public static VerifyUtils instance;
    public static VerifyUtils getInstance(){
        if(instance == null){
            synchronized (VerifyUtils.class){
                instance = new VerifyUtils();
            }
        }
        return instance;
    }

    //验证手机号
    public boolean verifyPhoneNum(String phonenum){
        Pattern compile = compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
        Matcher matcher = compile.matcher(phonenum);
        return matcher.matches();
    }

    public boolean verifyPassword(String pwd){
        if(pwd.length() >= 6 && pwd.length() <= 20){
            return true;
        }
        return false;
    }

    public String getRamdom(){
        Random random = new Random();
        Set<Integer> set = new HashSet<Integer>();
        while (set.size() < 4)
        {
            int randomInt = random.nextInt(10);
            set.add(randomInt);
        }
        StringBuffer sb = new StringBuffer();
        for (Integer i : set)
        {
            sb.append("" + i);
        }
        return sb.toString();
    }

    public void toast(String str){
        Toast.makeText(MyApplication.getApplicationContent(),str,Toast.LENGTH_SHORT).show();
    }

}
