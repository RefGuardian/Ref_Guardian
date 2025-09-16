import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class SomeService{
    public void newMethod(){}
}
class test55{
    void test() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = SomeService.class.getMethod("methodToBeRenamed",null);
        method.invoke(new SomeService());
    }
}