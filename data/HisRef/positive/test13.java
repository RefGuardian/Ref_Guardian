class test13 {
    class Model<Row extends java.util.List> {
        Row getObjectAtRow(){
            return new java.util.HashSet<Row>().iterator().next();
        }
    }
    class UI<Clazz extends java.util.ArrayList>{
    }
    Model<java.util.ArrayList> tableModel_;
    void foo(){
        Clazz[] getBeginAndEndSelections = (ArrayList[])
                java.lang.reflect.Array.newInstance(new UI<java.util.ArrayList>
                        ().tableModel_.getObjectAtRow().getClass(),1);
    }
}