package baithijava;

public class Cau1 {
    public static void main(String[] args) {
        StudentManager<Student> manager = new StudentManager<>();

        manager.add(new Student("SV01", "Nguyen Van A", 3.5));
        manager.add(new Student("SV02", "Tran Thi B", 3.8));
        manager.add(new Student("SV03", "Le Van C", 3.2));

        for (Student student : manager.getAll()) {
            System.out.println(student.toString());
        }
    }
}