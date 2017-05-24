package com.huamengtong.wms.utils;

import com.caucho.hessian.io.Hessian2Input;
import com.caucho.hessian.io.Hessian2Output;
import com.caucho.hessian.io.HessianInput;
import com.caucho.hessian.io.HessianOutput;
import com.caucho.hessian.io.SerializerFactory;
import org.apache.commons.lang3.StringUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


public class SerializationUtils {

    public static String serialize(Object value) {
        String stringValue= null;
        ByteArrayOutputStream bos = null;
        ObjectOutputStream os = null;
        try {
            bos = new ByteArrayOutputStream();
            os = new ObjectOutputStream(bos);
            os.writeObject(value);
            os.close();
            bos.close();
            stringValue = bos.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringValue;
    }

    public static Object deserialize(String stringValue) {
        return deserialize(stringValue, Object.class);
    }

    public static <T> T deserialize(String stringValue, Class<T> requiredType) {
        Object object = null;
        ByteArrayInputStream bis = null;
        ObjectInputStream is = null;
        try {
            if (StringUtils.isNotEmpty(stringValue)) {
                bis = new ByteArrayInputStream(stringValue.getBytes());
                is = new ObjectInputStream(bis);
                object = is.readObject();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (T) object;
    }


    public static byte[] HSerialize(Object obj) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        HessianOutput ho = new HessianOutput(os);
        ho.writeObject(obj);
        return os.toByteArray();
    }

    public static Object HDeserialize(byte[] by) throws IOException{
        ByteArrayInputStream is = new ByteArrayInputStream(by);
        HessianInput hi = new HessianInput(is);
        return hi.readObject();
    }

    public static byte[] H2Serialize(Object obj) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream(2048);
        Hessian2Output hessianOutput = new Hessian2Output(byteBuffer);
        hessianOutput.setSerializerFactory(reponseSerializerFactory);
        hessianOutput.writeObject(obj);
        hessianOutput.flush();
        return byteBuffer.toByteArray();
    }
    static SerializerFactory reponseSerializerFactory = new SerializerFactory();

    public static Object H2Deserialize(byte[] bytes,Class cls) throws IOException {
        ByteArrayInputStream input = new ByteArrayInputStream(bytes);
        Hessian2Input hessianInput = new Hessian2Input(input);
        hessianInput.setSerializerFactory(reponseSerializerFactory);
        return hessianInput.readObject(cls);
    }

}
