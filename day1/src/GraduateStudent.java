public class GraduateStudent extends Student{
    private  String researchDirection;

    public GraduateStudent(){}

    public GraduateStudent(String name, int age, String studentId, String researchDirection){
        super(name, age, studentId);
        this.researchDirection = researchDirection;
    }

    public String getResearchDirection(){
        return researchDirection;
    }

    public void setResearchDirection(String researchDirection){
        this.researchDirection = researchDirection;
    }

    @Override
    public void showInfo(){
        super.showInfo();
        System.out.println("研究方向：" + researchDirection);
    }
}