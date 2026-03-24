package bai1;

@FunctionalInterface
interface MathOperation {
    int compute(int a, int b);
}

public class LambdaMathExample {
    public static void main(String[] args) {
        MathOperation addition = (a, b) -> a + b;
        MathOperation subtraction = (a, b) -> a - b;
        MathOperation multiplication = (a, b) -> a * b;
        
        MathOperation division = (a, b) -> {
            if (b == 0) {
                throw new ArithmeticException("Lỗi: Không thể chia cho 0!");
            }
            return a / b;
        };

        System.out.println("=== KẾT QUẢ CÁC PHÉP TÍNH ===");
        System.out.println("10 + 5 = " + addition.compute(10, 5));
        System.out.println("10 - 5 = " + subtraction.compute(10, 5));
        System.out.println("10 * 5 = " + multiplication.compute(10, 5));
        System.out.println("10 / 5 = " + division.compute(10, 5));
    }
}