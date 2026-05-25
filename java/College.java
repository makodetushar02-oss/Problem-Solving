public class College {

    // Public Inner Class
    public class Student {
        void display() {
            System.out.println("This is Public Class");
        }
    }

    // Protected Inner Class
    protected class Teacher {
        void display() {
            System.out.println("This is Protected Class");
        }
    }

    // Private Inner Class
    private class Admin {
        void display() {
            System.out.println("This is Private Class");
        }
    }

    public static void main(String[] args) {

        College c = new College();

        // Access Public Class
        College.Student s = c.new Student();
        s.display();

        // Access Protected Class
        College.Teacher t = c.new Teacher();
        t.display();

        // Access Private Class
        College.Admin a = c.new Admin();
        a.display();
    }
}