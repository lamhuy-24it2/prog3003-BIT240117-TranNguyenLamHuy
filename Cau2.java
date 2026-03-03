package baithijava;

import StudentManager.StudentManager;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class Cau2 {
    
    public static double calculateAverageGpa(List<Student> students) {
        if (students == null || students.isEmpty()) {
            return 0.0;
        }
        double sum = 0;
        for (Student student : students) {
            sum += student.getGpa();
        }
        return sum / students.size();
    }

    public static void main(String[] args) {
        StudentManager<Student> manager = new StudentManager<>();
        manager.add(new Student("SV01", "Nguyen Van A", 3.5));
        manager.add(new Student("SV02", "Tran Thi B", 3.8));
        manager.add(new Student("SV03", "Le Van C", 3.2));

        CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000); // Giả lập độ trễ 1 giây
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return calculateAverageGpa(manager.getAll());
        }).thenAccept(avgGpa -> {
            System.out.println("Điểm trung bình GPA của hệ thống là: " + avgGpa);
        }).exceptionally(ex -> {
            System.out.println("Lỗi: " + ex.getMessage());
            return null;
        });

        // Tạm dừng luồng chính để đợi CompletableFuture in kết quả ra màn hình trước khi tắt chương trình
        try {
            Thread.sleep(2000); 
        } catch (InterruptedException e) {
        }
    }
}