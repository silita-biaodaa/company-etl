package com.silita.utils;


import com.google.common.base.Preconditions;
import com.silita.annotations.FieldChangeRecord;
import com.silita.model.FieldChange;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class BeanUtils {
    private static Logger logger = LoggerFactory.getLogger(BeanUtils.class);
    private static BeanUtils instance = new BeanUtils();

    private BeanUtils() {
    }

    public static BeanUtils getInstance() {
        return instance;
    }

    /**
     * 对象转字节数组
     *
     * @param obj
     * @return
     */
    public static byte[] ObjectToBytes(Object obj) {
        byte[] bytes = null;
        ByteArrayOutputStream bo = null;
        ObjectOutputStream oo = null;
        try {
            bo = new ByteArrayOutputStream();
            oo = new ObjectOutputStream(bo);
            oo.writeObject(obj);
            bytes = bo.toByteArray();
        } catch (Exception e) {
            logger.warn(ExceptionUtils.getStackTrace(e));
        } finally {
            try {
                if (bo != null) {
                    bo.close();
                }
                if (oo != null) {
                    oo.close();
                }
            } catch (Exception e) {
                logger.warn(ExceptionUtils.getStackTrace(e));
            }
        }
        return bytes;
    }

    /**
     * 字节数组转对象
     *
     * @param bytes
     * @return
     */
    public static Object BytesToObject(byte[] bytes) {
        Object obj = null;
        ByteArrayInputStream bi = null;
        ObjectInputStream oi = null;
        try {
            bi = new ByteArrayInputStream(bytes);
            oi = new ObjectInputStream(bi);
            obj = oi.readObject();
        } catch (Exception e) {
            logger.warn(ExceptionUtils.getStackTrace(e));
        } finally {
            try {
                if (bi != null) {
                    bi.close();
                }
                if (oi != null) {
                    oi.close();
                }
            } catch (Exception e) {
                logger.warn(ExceptionUtils.getStackTrace(e));
            }
        }
        return obj;
    }

    /**
     * 通过反射比较两个对象实体字段的差异
     *
     * @param newObject 新对象
     * @param oldObject 旧对象
     */
    public static List<FieldChange> compare(Object newObject, Object oldObject) {
        List<FieldChange> list = new ArrayList<>();
        try {
            Preconditions.checkArgument(null != newObject && null != oldObject, "实体对象不能为空！");
            Class class1 = newObject.getClass();
            Class class2 = oldObject.getClass();
            FieldChangeRecord record = (FieldChangeRecord) class1.getDeclaredAnnotation(FieldChangeRecord.class);
            Preconditions.checkArgument(null != record, String.format("%s未设置%s注解！", class1.getName(), FieldChangeRecord.class.getSimpleName()));
            String[] fields = record.fields();
            String table = "";
            if (StringUtils.isNotBlank(record.table())) {
                table = record.table();
            } else {
                Field field = class1.getDeclaredField("table");
                if (null != field) {
                    field.setAccessible(true);
                    table = (String) field.get(newObject);
                }
            }
            String tableId = record.id();
            Preconditions.checkArgument(StringUtils.isNotBlank(table), "table不能为空");
            Preconditions.checkArgument(StringUtils.isNotBlank(tableId), String.format("%s中的注解%s的id为空！", class1.getName(), FieldChangeRecord.class.getSimpleName()));
            Preconditions.checkArgument(null != fields && fields.length > 0, String.format("%s中的注解%s未设置fields属性或者为空！", class1.getName(), FieldChangeRecord.class.getSimpleName()));
            for (String field : fields) {
                Field fieldObject1 = class1.getDeclaredField(field);
                Field fieldObject2 = class2.getDeclaredField(field);
                Field idField = class2.getDeclaredField(tableId);
                Preconditions.checkArgument(null != idField, String.format("%s中未定义的字段%s", class2.getName(), tableId));
                Preconditions.checkArgument(null != fieldObject1, String.format("%s中未定义的字段%s", class1.getName(), field));
                Preconditions.checkArgument(null != fieldObject2, String.format("%s中未定义的字段%s", class2.getName(), field));
                fieldObject1.setAccessible(true);
                fieldObject2.setAccessible(true);
                idField.setAccessible(true);
                String fieldValue1 = StringUtils.defaultIfBlank((String) fieldObject1.get(newObject), "");
                String fieldValue2 = StringUtils.defaultIfBlank((String) fieldObject2.get(oldObject), "");
                String id = (String) idField.get(oldObject);
                Preconditions.checkArgument(StringUtils.isNotBlank(id), String.format("%s中%s字段值不能为空！", class2.getName(), tableId));
                if (!fieldValue1.equals(fieldValue2)) {
                    FieldChange fieldChange = new FieldChange();
                    fieldChange.setTb_name(table);
                    fieldChange.setTb_id(id);
                    fieldChange.setColumn_name(field);
                    fieldChange.setNew_value(StringUtils.defaultIfBlank(fieldValue1, ""));
                    fieldChange.setOld_value(StringUtils.defaultIfBlank(fieldValue2, ""));
                    list.add(fieldChange);
                    logger.info(fieldChange.toString());
                }
            }
        } catch (Exception e) {
            logger.warn(e.getMessage());
        }
        return list;
    }
}
