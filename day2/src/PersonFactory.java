public class PersonFactory {
    public static Person getPerson(String type){
        if("student".equals(type)){
            return new Student();
        }else if("teacher".equals(type)){
            return new Teacher();
        }else{
            return null;
        }
    }
}
