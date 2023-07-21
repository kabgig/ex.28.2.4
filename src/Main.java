import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws NoSuchFieldException {
        Article article = new Article();
        Author author = new Author();
        Class[] classes = {article.getClass(), author.getClass()};

        for (var clazz : classes) {
            Field[] declaredFields = clazz.getDeclaredFields();
            Method[] declaredMethods = clazz.getDeclaredMethods();

            for (Field field : declaredFields) {
                int modifiers = field.getModifiers();
                if (!Modifier.isPrivate(modifiers))
                    System.out.println("Field with the name " + field.getName() + " should be private");

                boolean hasSetter = Arrays.stream(declaredMethods)
                        .anyMatch(method -> method.getName().equals("set" + capitalize(field.getName())));
                boolean hasGetter = Arrays.stream(declaredMethods)
                        .anyMatch(method -> method.getName().equals("get" + capitalize(field.getName())));

                if (!hasSetter) System.out.println(clazz.getName() + " has no setter for " + field.getName());
                if(!hasGetter) System.out.println(clazz.getName() + " has no getter for " + field.getName());
            }
        }
    }

    private static String capitalize(String str) {
        return str.substring(0,1).toUpperCase() + str.substring(1);
    }

}