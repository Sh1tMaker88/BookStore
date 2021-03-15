package propertyInjector;

import java.lang.reflect.Field;

public class SetValue {

    public static void castFieldAndSetValue(Object obj, Field field, String toType, String value){
        field.setAccessible(true);
        try {
            if (field.getType().isPrimitive()) {
                switch (toType.toLowerCase()) {
                    case "boolean":
                        field.setBoolean(obj, Boolean.parseBoolean(value));
                        break;
                    case "int":
                    case "integer":
                        field.setInt(obj, Integer.parseInt(value));
                        break;
                    case "double":
                        field.setDouble(obj, Double.parseDouble(value));
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
