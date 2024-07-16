package org.example;

import java.time.LocalDate;
import java.util.*;

public class ElectronicsStoreManagementSystem {

    private static List<Product> products = new ArrayList<>();
    private static List<Customer> customers = new ArrayList<>();
    private static List<Order> orders = new ArrayList<>();
    private static int productIdCounter = 100;
    private static int customerIdCounter = 1000;
    private static int orderIdCounter = 1;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Добро пожаловать в систему управления магазином электроники!");
            System.out.println("1. Добавить новый товар");
            System.out.println("2. Обновить информацию о товаре");
            System.out.println("3. Удалить товар");
            System.out.println("4. Просмотреть все товары");
            System.out.println("5. Создать заказ");
            System.out.println("6. Обновить статус заказа");
            System.out.println("7. Просмотреть все заказы");
            System.out.println("8. Добавить клиента");
            System.out.println("9. Обновить информацию о клиенте");
            System.out.println("10. Просмотреть всех клиентов");
            System.out.println("11. Генерация отчета о продажах");
            System.out.println("12. Выход");
            System.out.print("Введите ваш выбор: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    addNewProduct(scanner);
                    break;
                case 2:
                    updateProduct(scanner);
                    break;
                case 3:
                    deleteProduct(scanner);
                    break;
                case 4:
                    viewAllProducts();
                    break;
                case 5:
                    createOrder(scanner);
                    break;
                case 6:
                    updateOrderStatus(scanner);
                    break;
                case 7:
                    viewAllOrders();
                    break;
                case 8:
                    addNewCustomer(scanner);
                    break;
                case 9:
                    updateCustomer(scanner);
                    break;
                case 10:
                    viewAllCustomers();
                    break;
                case 11:
                    generateSalesReport(scanner);
                    break;
                case 12:
                    System.out.println("Выход из системы.");
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Неверный выбор. Пожалуйста, попробуйте снова.");
            }
        }
    }

    private static void addNewProduct(Scanner scanner) {
        scanner.nextLine(); // consume newline
        System.out.print("Введите название товара: ");
        String name = scanner.nextLine();
        System.out.print("Введите описание: ");
        String description = scanner.nextLine();
        System.out.print("Введите категорию: ");
        String category = scanner.nextLine();
        System.out.print("Введите цену: ");
        double price = scanner.nextDouble();
        System.out.print("Введите количество на складе: ");
        int stockQuantity = scanner.nextInt();

        Product product = new Product(productIdCounter++, name, description, category, price, stockQuantity);
        products.add(product);
        System.out.println("Товар \"" + name + "\" успешно добавлен в инвентарь!");
    }

    private static void updateProduct(Scanner scanner) {
        System.out.print("Введите ID товара для обновления: ");
        int productId = scanner.nextInt();
        scanner.nextLine();

        Product product = findProductById(productId);
        if (product == null) {
            System.out.println("Товар с ID " + productId + " не найден.");
            return;
        }

        System.out.print("Введите новое название товара: ");
        String name = scanner.nextLine();
        System.out.print("Введите новое описание: ");
        String description = scanner.nextLine();
        System.out.print("Введите новую категорию: ");
        String category = scanner.nextLine();
        System.out.print("Введите новую цену: ");
        double price = scanner.nextDouble();
        System.out.print("Введите новое количество на складе: ");
        int stockQuantity = scanner.nextInt();

        product.setName(name);
        product.setDescription(description);
        product.setCategory(category);
        product.setPrice(price);
        product.setStockQuantity(stockQuantity);

        System.out.println("Информация о товаре успешно обновлена!");
    }

    private static void deleteProduct(Scanner scanner) {
        System.out.print("Введите ID товара для удаления: ");
        int productId = scanner.nextInt();

        Product product = findProductById(productId);
        if (product == null) {
            System.out.println("Товар с ID " + productId + " не найден.");
            return;
        }

        products.remove(product);
        System.out.println("Товар успешно удален!");
    }

    private static void viewAllProducts() {
        System.out.println("Список всех товаров:");
        for (Product product : products) {
            System.out.println(product);
        }
    }

    private static void createOrder(Scanner scanner) {
        System.out.print("Введите идентификатор клиента: ");
        int customerId = scanner.nextInt();

        Customer customer = findCustomerById(customerId);
        if (customer == null) {
            System.out.println("Клиент с ID " + customerId + " не найден.");
            return;
        }

        List<OrderItem> items = new ArrayList<>();
        while (true) {
            System.out.print("Введите ID товара для добавления в заказ (или 0 для завершения): ");
            int productId = scanner.nextInt();
            if (productId == 0) {
                break;
            }
            System.out.print("Введите количество: ");
            int quantity = scanner.nextInt();

            Product product = findProductById(productId);
            if (product == null) {
                System.out.println("Товар с ID " + productId + " не найден.");
                continue;
            }

            items.add(new OrderItem(product, quantity));
        }

        System.out.print("Введите дату заказа (формат ГГГГ-ММ-ДД): ");
        scanner.nextLine(); // consume newline
        String orderDateStr = scanner.nextLine();
        LocalDate orderDate = LocalDate.parse(orderDateStr);

        double totalAmount = items.stream().mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity()).sum();

        Order order = new Order(orderIdCounter++, customer, items, orderDate, totalAmount, "Запланирован");
        orders.add(order);
        System.out.println("Заказ успешно создан и запланирован на отправку!");
    }

    private static void updateOrderStatus(Scanner scanner) {
        System.out.print("Введите ID заказа для обновления статуса: ");
        int orderId = scanner.nextInt();
        scanner.nextLine(); // consume newline

        Order order = findOrderById(orderId);
        if (order == null) {
            System.out.println("Заказ с ID " + orderId + " не найден.");
            return;
        }

        System.out.print("Введите новый статус заказа: ");
        String status = scanner.nextLine();
        order.setStatus(status);
        System.out.println("Статус заказа успешно обновлен!");
    }

    private static void viewAllOrders() {
        System.out.println("Список всех заказов:");
        for (Order order : orders) {
            System.out.println(order);
        }
    }

    private static void addNewCustomer(Scanner scanner) {
        scanner.nextLine(); // consume newline
        System.out.print("Введите имя клиента: ");
        String name = scanner.nextLine();
        System.out.print("Введите контактные данные клиента: ");
        String contactInfo = scanner.nextLine();

        Customer customer = new Customer(customerIdCounter++, name, contactInfo);
        customers.add(customer);
        System.out.println("Клиент \"" + name + "\" успешно добавлен!");
    }

    private static void updateCustomer(Scanner scanner) {
        System.out.print("Введите ID клиента для обновления: ");
        int customerId = scanner.nextInt();
        scanner.nextLine(); // consume newline

        Customer customer = findCustomerById(customerId);
        if (customer == null) {
            System.out.println("Клиент с ID " + customerId + " не найден.");
            return;
        }

        System.out.print("Введите новое имя клиента: ");
        String name = scanner.nextLine();
        System.out.print("Введите новые контактные данные клиента: ");
        String contactInfo = scanner.nextLine();

        customer.setName(name);
        customer.setContactInfo(contactInfo);
        System.out.println("Информация о клиенте успешно обновлена!");
    }

    private static void viewAllCustomers() {
        System.out.println("Список всех клиентов:");
        for (Customer customer : customers) {
            System.out.println(customer);
        }
    }

    private static void generateSalesReport(Scanner scanner) {
        System.out.print("Введите начальную дату (формат ГГГГ-ММ-ДД): ");
        scanner.nextLine(); // consume newline
        String startDateStr = scanner.nextLine();
        LocalDate startDate = LocalDate.parse(startDateStr);

        System.out.print("Введите конечную дату (формат ГГГГ-ММ-ДД): ");
        String endDateStr = scanner.nextLine();
        LocalDate endDate = LocalDate.parse(endDateStr);

        double totalRevenue = 0;
        int totalItemsSold = 0;

        for (Order order : orders) {
            if (!order.getOrderDate().isBefore(startDate) && !order.getOrderDate().isAfter(endDate)) {
                totalRevenue += order.getTotalAmount();
                totalItemsSold += order.getItems().stream().mapToInt(OrderItem::getQuantity).sum();
            }
        }

        System.out.println("Отчет о продажах за период с " + startDate + " по " + endDate);
        System.out.println("Общая выручка: $" + totalRevenue);
        System.out.println("Продано товаров: " + totalItemsSold + " штук");
    }

    private static Product findProductById(int id) {
        return products.stream().filter(product -> product.getId() == id).findFirst().orElse(null);
    }

    private static Customer findCustomerById(int id) {
        return customers.stream().filter(customer -> customer.getId() == id).findFirst().orElse(null);
    }

    private static Order findOrderById(int id) {
        return orders.stream().filter(order -> order.getId() == id).findFirst().orElse(null);
    }
}
