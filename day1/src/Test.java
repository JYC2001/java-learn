
public class Test{
    public static void main(String[] args){
        Student student = new Student("张三", 20, "2024001");
        System.out.println("=====普通学生信息=====");
        student.showInfo();
        System.out.println(("------------------"));
        GraduateStudent graduateStudent = new GraduateStudent("李四", 24, "2024002", "人工智能");
        System.out.println(("=====研究生信息====="));
        graduateStudent.showInfo();
        System.out.println("研究生姓名（调用父类getter)：" + graduateStudent.getName());
    }
}