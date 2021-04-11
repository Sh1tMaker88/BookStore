package com.propertyInjector;

import java.lang.reflect.Field;

public class SetValue {

    public static void castFieldAndSetValue(Object obj, Field field, String toType, String value){
        field.setAccessible(true);
        try {

//            obj.getDeclaredConstructor().newInstance()
            if (field.getType().isPrimitive()) {
                switch (toType.toLowerCase()) {
                    case "byte":
                        field.setByte(obj, Byte.parseByte(value));
                        break;
                    case "short":
                        field.setShort(obj, Short.parseShort(value));
                        break;
                    case "int":
                    case "integer":
                        field.setInt(obj, Integer.parseInt(value));
                        break;
                    case "long":
                        field.setLong(obj, Long.parseLong(value));
                        break;
                    case "float":
                        field.setFloat(obj, Float.parseFloat(value));
                        break;
                    case "double":
                        field.setDouble(obj, Double.parseDouble(value));
                        break;
                    case "boolean":
                        field.setBoolean(obj, Boolean.parseBoolean(value));
                        break;
                    case "char":
                    case "character":
                        field.setChar(obj, value.charAt(0));
                        break;
                }
            } else {
                String[] fieldType = field.getType().toString().split(".");
                if (fieldType[fieldType.length - 1].equalsIgnoreCase(toType)) {
                    if (toType.equals("String")) {
                        field.set(obj, value);
                    }
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
