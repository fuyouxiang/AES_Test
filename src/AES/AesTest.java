package AES;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.Security;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;


public class AesTest {
    /**
     * 算法名称
     */
    private static final String KEY_ALGORITHM = "AES";
    /**
     * 加解密算法/模式/填充方式
     */
    private static final String ALGORITHM = "AES/CBC/PKCS7Padding";

    private static byte[] iv = { 0x30, 0x31, 0x30, 0x32, 0x30, 0x33, 0x30, 0x34, 0x30, 0x35, 0x30, 0x36, 0x30, 0x37, 0x30, 0x38 };

    private static byte[] init(byte[] keyBytes){
        // 如果密钥不足16位，那么就补足.  这个if 中的内容很重要
        int base = 16;
        if (keyBytes.length % base != 0) {
            int groups = keyBytes.length / base + (keyBytes.length % base != 0 ? 1 : 0);
            byte[] temp = new byte[groups * base];
            Arrays.fill(temp, (byte) 0);
            System.arraycopy(keyBytes, 0, temp, 0, keyBytes.length);
            keyBytes = temp;
        }
        return keyBytes;
    }
    /**
     * 加密方法
     *
     * @param content
     *            要加密的字符串
     * @param keyBytes
     *            加密密钥
     * @return
     */
    public static byte[] encrypt(byte[] content, byte[] keyBytes) {
        byte[] encryptedText = null;
        Cipher cipher = null;
        init(keyBytes);
        // 初始化
        Security.addProvider(new BouncyCastleProvider());
        // 转化成JAVA的密钥格式
        Key key = new SecretKeySpec(keyBytes, KEY_ALGORITHM);
        try {
            // 初始化cipher
            cipher = Cipher.getInstance(ALGORITHM, "BC");
        } catch (Exception e) {
            e.printStackTrace();
        }
        //加密前加上iv
        String contentStr = new String(iv)+new String(content);
        try {
            if (cipher != null) {
                cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(iv));
                encryptedText = cipher.doFinal(contentStr.getBytes(StandardCharsets.UTF_8));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encryptedText;
    }

    /**
     * 解密方法
     *
     * @param encryptedData
     *            要解密的字符串
     * @param keyBytes
     *            解密密钥
     * @return
     */
    public static byte[] decrypt(byte[] encryptedData, byte[] keyBytes,byte[] deIv) {
        byte[] encryptedText = null;
        Cipher cipher = null;
        // 初始化
        init(keyBytes);
        Security.addProvider(new BouncyCastleProvider());
        // 转化成JAVA的密钥格式
        Key key = new SecretKeySpec(keyBytes, KEY_ALGORITHM);
        try {
            // 初始化cipher
            cipher = Cipher.getInstance(ALGORITHM, "BC");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            if (cipher != null) {
                cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(deIv));
                encryptedText = cipher.doFinal(encryptedData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encryptedText;
    }
    
    public static String JiaMi(String context,String key) {
    	//String key="xbAp4d6KVqTXkP20vaDfQzffA1C0SOvb";
    	// 加密调用对返回结果做AES对称加密
    	String enReData = Base64.encodeBase64String(AesTest.encrypt(context.toString().getBytes(),key.getBytes(StandardCharsets.UTF_8)));
    	System.out.println(enReData);
    	return enReData;
    }
    
    public static void main(String[] args) {
    	String json_str = "{\"order_id\":\"AQCADTPupa-aBeIKDD96k7MbdosN\",\"status\":3,\"pay_channel\":\"wx_nontax\",\"pay_finish_time\":1508806264}"; 
    	System.out.println(json_str);
    	//String key="1234567890123456";//普通加密
    	
    	//微信是256位加密，jdk限制在128位，需要替换环境变量jar包local_policy.jar和US_export_policy.jar
    	//从1.8.0_161-b12版本后，默认将采用无限制的加密算法，也就是使用 unlimited下的jar包。我们也可以通过设置java.security文件的crypto.policy的值来改变这个默认的值。
    	String key="7UiYDbfzgPC2dF6AMx5OAc93YL9tKyfN";
    	String data =AesTest.JiaMi(json_str, key);
    	System.out.println(data);
    }

}
