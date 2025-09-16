/* Constants.java */
class Constants {
    public static final String FOO = "FOO";
}

/* FooConstants.java */
class FooConstants {}

/* Example.java */
public class test34 {
    public method example(String foo) {
        switch (foo) {
            case Constants.FOO:
                break;
        }
    }
}