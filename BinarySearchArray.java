import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

public class BinarySearchArray {

    private static class Product {
        String number;
        String name;
        String mainCategory;
        String subCategory;
        String image;
        String link;
        String rating;
        String numberOfRatings;
        String discountPrice;
        String actualPrice;

        public Product(String number, String name, String mainCategory, String subCategory,
                       String image, String link, String rating, String numberOfRatings,
                       String discountPrice, String actualPrice) {
            this.number = number;
            this.name = name;
            this.mainCategory = mainCategory;
            this.subCategory = subCategory;
            this.image = image;
            this.link = link;
            this.rating = rating;
            this.numberOfRatings = numberOfRatings;
            this.discountPrice = discountPrice;
            this.actualPrice = actualPrice;
        }
    }

    private static final int INITIAL_CAPACITY = 1000000;
    private static Product[] products = new Product[INITIAL_CAPACITY];
    private static int size = 0;

    public static void main(String[] args) {
        

        String file = "./Amazon-Products_dup_remove.csv";

        // Read data from CSV file
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            reader.readLine(); // Skip the header line if it exists
            while ((line = reader.readLine()) != null) {
                if (size >= products.length) {
                    products = Arrays.copyOf(products, products.length * 2);
                }
                products[size++] = parseProductFromCSV(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Sort the products array by the name field
        Arrays.sort(products, 0, size, Comparator.comparing(p -> p.name));

        // Create a Scanner object for user input
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the product name to search:");
        String targetName = scanner.nextLine();
        scanner.close();

        // Perform the binary search for the entered product name
        long startTime = System.nanoTime();
        int index = binarySearchByName(targetName);
        long endTime = System.nanoTime();
        long elapsedTime = endTime - startTime;
        double elapsedTimeInMilliseconds = (double) elapsedTime / 1_000_000_000.0;
        if (index != -1) {
            System.out.println("Product found: " + products[index].name);
            System.out.println("Number: " + products[index].number);
            System.out.println("Main Category: " + products[index].mainCategory);
            System.out.println("Sub Category: " + products[index].subCategory);
            System.out.println("Image: " + products[index].image);
            System.out.println("Link: " + products[index].link);
            System.out.println("Ratings: " + products[index].rating);
            System.out.println("Number of Ratings: " + products[index].numberOfRatings);
            System.out.println("Discount Price: " + products[index].discountPrice);
            System.out.println("Actual Price: " + products[index].actualPrice);
        } else {
            System.out.println("Product not found.");
        }
        
        
        System.out.println("Elapsed time: " + elapsedTimeInMilliseconds + " seconds");
        
    }

    private static Product parseProductFromCSV(String csvRow) {
        String[] parts = csvRow.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
        return new Product(
            parts[0].replaceAll("\"", "").trim(),
            parts[1].replaceAll("\"", "").trim(),
            parts[2].replaceAll("\"", "").trim(),
            parts[3].replaceAll("\"", "").trim(),
            parts[4].replaceAll("\"", "").trim(),
            parts[5].replaceAll("\"", "").trim(),
            parts[6].replaceAll("\"", "").trim(),
            parts[7].replaceAll("\"", "").trim(),
            parts[8].replaceAll("\"", "").trim(),
            parts[9].replaceAll("\"", "").trim()
        );
    }

    private static int binarySearchByName(String searchName) {
        int low = 0;
        int high = size - 1;
        while (low <= high) {
            int mid = (low + high) / 2;
            Product midProduct = products[mid];
            int cmp = midProduct.name.compareTo(searchName);
            if (cmp < 0) {
                low = mid + 1;
            } else if (cmp > 0) {
                high = mid - 1;
            } else {
                return mid;
            }
        }
        return -1; 
    }
}
