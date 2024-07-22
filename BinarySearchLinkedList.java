import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Comparator;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Arrays;

public class BinarySearchLinkedList {

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

    public static void main(String[] args) {
        String file = "./Amazon-Products_dup_remove.csv";
        ArrayList<Product> productList = new ArrayList<>();

        // Read data from CSV file and store in ArrayList
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            reader.readLine(); // Skip the header line if it exists
            while ((line = reader.readLine()) != null) {
                productList.add(parseProductFromCSV(line));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

       
        Product[] productsArray = productList.toArray(new Product[0]);
        
        Arrays.sort(productsArray, Comparator.comparing(p -> p.name));

        
        LinkedList<Product> productsLinkedList = new LinkedList<>(Arrays.asList(productsArray));

        
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the product name to search:");
        String targetName = scanner.nextLine();
        scanner.close();

        
        long startTime = System.nanoTime();
        Product foundProduct = binarySearchByName(productsLinkedList, targetName);
        if (foundProduct != null) {
            System.out.println("Product found: " + foundProduct.name);
            System.out.println("Number: " + foundProduct.number);
            System.out.println("Main Category: " + foundProduct.mainCategory);
            System.out.println("Sub Category: " + foundProduct.subCategory);
            System.out.println("Image URL: " + foundProduct.image);
            System.out.println("Link: " + foundProduct.link);
            System.out.println("Rating: " + foundProduct.rating);
            System.out.println("Number of Ratings: " + foundProduct.numberOfRatings);
            System.out.println("Discount Price: " + foundProduct.discountPrice);
            System.out.println("Actual Price: " + foundProduct.actualPrice);
        } else {
            System.out.println("Product not found.");
        }
        long endTime = System.nanoTime();
        long elapsedTime = endTime - startTime;
        double elapsedTimeInMilliseconds = (double) elapsedTime / 1_000_000_000.0;
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

    private static Product binarySearchByName(LinkedList<Product> products, String searchName) {
        int low = 0;
        int high = products.size() - 1;
        while (low <= high) {
            int mid = (low + high) >>> 1;
            Product midProduct = products.get(mid);
            int cmp = midProduct.name.compareTo(searchName);
            if (cmp < 0) {
                low = mid + 1;
            } else if (cmp > 0) {
                high = mid - 1;
            } else {
                return midProduct;
            }
        }
        return null;
    }
}
