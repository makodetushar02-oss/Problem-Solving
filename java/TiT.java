class Student {
    String name;
    static String collage;
}

protected class Enroll extends Student {
    int enroll ;
}
private class Ag extends Enroll{
    int age;
}
class TiT extends Age {
    public static void main(String args[]){
        Student.collage = "TiT - Ex";

        Student s1 = new Student();
        s1.name = "Tushar";
        Age a = new Age();
        a.age = 20 ;
        Enroll E1 = new Enroll();
        E1.enroll = 241030 ;
        System.out.println("Name: " + s1.name);
        System.out.println("School: " + Student.collage);
        System.out.println("Enrollment : " + E1.enroll);
        System.out.println("Age : " + a.age);
    }
}