class Student{
    String name;
    static String school;
}

class BGMI{
    public static void main(String args[]){
        Student.school = "Sarvodaya";

        Student s1 = new Student();
        s1.name = "Tushar";

        System.out.println("Name: " + s1.name);
        System.out.println("School: " + Student.school);
    }
}
